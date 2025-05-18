package com.tagging.system.controller;

import com.tagging.system.dto.CreateTagRequest;
import com.tagging.system.dto.TagDTO;
import com.tagging.system.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagDTO>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTagById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TagDTO> getTagByName(@PathVariable String name) {
        return ResponseEntity.ok(tagService.getTagByName(name));
    }

    @PostMapping
    public ResponseEntity<TagDTO> createTag(@Valid @RequestBody CreateTagRequest request) {
        TagDTO createdTag = tagService.createTag(request);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable Long id, @Valid @RequestBody CreateTagRequest request) {
        return ResponseEntity.ok(tagService.updateTag(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<TagDTO>> searchTags(@RequestParam String query) {
        return ResponseEntity.ok(tagService.searchTags(query));
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<TagDTO>> autocompleteTags(@RequestParam String prefix) {
        return ResponseEntity.ok(tagService.autocompleteTags(prefix));
    }

    @GetMapping("/most-used")
    public ResponseEntity<List<TagDTO>> getMostUsedTags(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(tagService.getMostUsedTags(limit));
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<TagDTO>> getTagsByTicketId(@PathVariable Long ticketId) {
        return ResponseEntity.ok(tagService.getTagsByTicketId(ticketId));
    }
}
