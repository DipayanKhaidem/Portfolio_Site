
package com.dipayan.portfolio.project.dto;

import java.time.LocalDateTime;

public class ProjectResponse {

    private Long id;
    private String title;
    private String description;
    private String githubUrl;
    private String liveUrl;
    private boolean featured;
    private boolean published;
    private LocalDateTime createdAt;

    public ProjectResponse() {
    }

    public ProjectResponse(
            Long id,
            String title,
            String description,
            String githubUrl,
            String liveUrl,
            boolean featured,
            boolean published,
            LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.githubUrl = githubUrl;
        this.liveUrl = liveUrl;
        this.featured = featured;
        this.published = published;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getLiveUrl() {
        return liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
