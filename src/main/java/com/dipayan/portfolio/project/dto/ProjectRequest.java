package com.dipayan.portfolio.project.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProjectRequest {

    @NotBlank(message="Project title is required")
    @Size (max=100, message="Title must not exceed 100 characters")
    private String title;

    @NotBlank (message="Project description is required")
    @Size(max=2000, message="Description must not exceed 2000 characters")
    private String description;

    private String githubUrl;
    private String liveUrl;

    private boolean featured=false;
    private boolean published=true;

    public ProjectRequest(){
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
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

}
