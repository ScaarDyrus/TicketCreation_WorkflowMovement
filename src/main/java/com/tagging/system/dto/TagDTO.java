package com.tagging.system.dto;

import com.tagging.system.entity.Tag;

public class TagDTO {
    private Long id;
    private String name;
    private String description;
    private Long usageCount;

    public TagDTO() {
    }

    public TagDTO(Long id, String name, String description, Long usageCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.usageCount = usageCount;
    }

    public static TagDTO fromEntity(Tag tag) {
        return new TagDTO(
            tag.getId(),
            tag.getName(),
            tag.getDescription(),
            tag.getUsageCount()
        );
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Long usageCount) {
        this.usageCount = usageCount;
    }
}
