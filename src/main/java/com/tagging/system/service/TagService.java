package com.tagging.system.service;

import com.tagging.system.dto.CreateTagRequest;
import com.tagging.system.dto.TagDTO;
import com.tagging.system.entity.Tag;
import com.tagging.system.entity.Ticket;
import com.tagging.system.repository.TagRepository;
import com.tagging.system.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public TagService(TagRepository tagRepository, TicketRepository ticketRepository) {
        this.tagRepository = tagRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional(readOnly = true)
    public List<TagDTO> getAllTags() {
        return tagRepository.findAll().stream()
                .map(TagDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TagDTO getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + id));
        return TagDTO.fromEntity(tag);
    }

    @Transactional(readOnly = true)
    public TagDTO getTagByName(String name) {
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with name: " + name));
        return TagDTO.fromEntity(tag);
    }

    @Transactional
    public TagDTO createTag(CreateTagRequest request) {
        if (tagRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Tag with name '" + request.getName() + "' already exists");
        }

        Tag tag = new Tag();
        tag.setName(request.getName());
        tag.setDescription(request.getDescription());
        tag.setUsageCount(0L);

        Tag savedTag = tagRepository.save(tag);
        return TagDTO.fromEntity(savedTag);
    }

    @Transactional
    public TagDTO updateTag(Long id, CreateTagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + id));

        // Check if the new name is already taken by another tag
        if (!tag.getName().equals(request.getName()) && tagRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Tag with name '" + request.getName() + "' already exists");
        }

        tag.setName(request.getName());
        tag.setDescription(request.getDescription());

        Tag updatedTag = tagRepository.save(tag);
        return TagDTO.fromEntity(updatedTag);
    }

    @Transactional
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + id));

        // Remove this tag from all tickets
        for (Ticket ticket : tag.getTickets()) {
            ticket.removeTag(tag);
            ticketRepository.save(ticket);
        }

        tagRepository.delete(tag);
    }

    @Transactional(readOnly = true)
    public List<TagDTO> searchTags(String query) {
        return tagRepository.findByNameContainingIgnoreCase(query).stream()
                .map(TagDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TagDTO> autocompleteTags(String prefix) {
        return tagRepository.findByNameStartingWithIgnoreCase(prefix).stream()
                .map(TagDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TagDTO> getMostUsedTags(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        Page<Tag> tags = tagRepository.findMostUsedTags(pageable);
        return tags.getContent().stream()
                .map(TagDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TagDTO> getTagsByTicketId(Long ticketId) {
        return tagRepository.findTagsByTicketId(ticketId).stream()
                .map(TagDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
