package com.tagging.system.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class AddTagsToTicketRequest {
    
    @NotEmpty(message = "Tag names cannot be empty")
    private List<String> tagNames;
    
    private String userId;
    
    private String userName;

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
