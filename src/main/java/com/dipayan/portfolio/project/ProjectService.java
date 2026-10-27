
package com.dipayan.portfolio.project;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dipayan.portfolio.project.dto.ProjectRequest;
import com.dipayan.portfolio.project.dto.ProjectResponse;

import java.util.List;

@Service
public class ProjectService {

    
private ProjectResponse toResponse(Project project) {
    return new ProjectResponse(
            project.getId(),
            project.getTitle(),
            project.getDescription(),
            project.getGithubUrl(),
            project.getLiveUrl(),
            project.isFeatured(),
            project.isPublished(),
            project.getCreatedAt()
    );
}

private Project toEntity(ProjectRequest request) {
    Project project = new Project();
    project.setTitle(request.getTitle());
    project.setDescription(request.getDescription());
    project.setGithubUrl(request.getGithubUrl());
    project.setLiveUrl(request.getLiveUrl());
    project.setFeatured(request.isFeatured());
    project.setPublished(request.isPublished());
    return project;
}


    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

   
public List<ProjectResponse> getAllProjects() {
    return projectRepository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
}

public ProjectResponse getProjectById(Long id) {
    Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Project not found with id: " + id
            ));

    return toResponse(project);
}

public ProjectResponse createProject(ProjectRequest request) {
    Project project = toEntity(request);
    Project savedProject = projectRepository.save(project);

    return toResponse(savedProject);
}

public ProjectResponse updateProject(Long id, ProjectRequest request) {
    Project existingProject = projectRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Project not found with id: " + id
            ));

    existingProject.setTitle(request.getTitle());
    existingProject.setDescription(request.getDescription());
    existingProject.setGithubUrl(request.getGithubUrl());
    existingProject.setLiveUrl(request.getLiveUrl());
    existingProject.setFeatured(request.isFeatured());
    existingProject.setPublished(request.isPublished());

    Project updatedProject = projectRepository.save(existingProject);

    return toResponse(updatedProject);
}

public void deleteProject(Long id) {
    Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Project not found with id: " + id
            ));

    projectRepository.delete(project);
}

}
