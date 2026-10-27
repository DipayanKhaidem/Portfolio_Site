
package com.dipayan.portfolio.project;

import com.dipayan.portfolio.project.dto.ProjectRequest;
import com.dipayan.portfolio.project.dto.ProjectResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectResponse> getAllProjects() {
        return projectService.getAllProjects();
    }
    @GetMapping ("/all")
    public List<ProjectResponse> getAllProjectsIncludingDrafts(){
        return projectService.getAllProjectsIncludingDrafts();
    }

    @GetMapping("/{id}")
    public ProjectResponse getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse createProject(
            @Valid @RequestBody ProjectRequest request) {
        return projectService.createProject(request);
    }

    @PutMapping("/{id}")
    public ProjectResponse updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequest request) {
        return projectService.updateProject(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
