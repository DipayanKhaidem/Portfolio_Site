package com.dipayan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.dipayan.portfolio.project.Project;
import com.dipayan.portfolio.project.ProjectRepository;
import com.dipayan.portfolio.project.ProjectService;
import com.dipayan.portfolio.project.dto.ProjectResponse;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void getAllProjects_shouldReturnPublishedProjects() {

    
    Project project = new Project();
    project.setTitle("Portfolio Website");
    project.setDescription("My personal portfolio");
    project.setPublished(true);

    
    when(projectRepository.findByPublishedTrue())
            .thenReturn(List.of(project));

    
    List<ProjectResponse> result = projectService.getAllProjects();

    
    assertEquals(1, result.size());
    assertEquals("Portfolio Website", result.get(0).getTitle());
    assertTrue(result.get(0).isPublished());

    
    verify(projectRepository, times(1)).findByPublishedTrue();
}

@Test
void getProjectById_shouldThrowExceptionForUnpublishedProject() {

    
    Project project = new Project();
    project.setTitle("Draft Project");
    project.setDescription("This is not published");
    project.setPublished(false);

    when(projectRepository.findById(1L))
            .thenReturn(java.util.Optional.of(project));

    
    assertThrows(
            ResponseStatusException.class,
            () -> projectService.getProjectById(1L)
    );

    verify(projectRepository, times(1)).findById(1L);
}
@Test
void getProjectById_shouldReturnPublishedProject() {

    
    Project project = new Project();
    project.setTitle("Job Application Tracker");
    project.setDescription("A Spring Boot project");
    project.setPublished(true);

    when(projectRepository.findById(1L))
            .thenReturn(java.util.Optional.of(project));

   
    ProjectResponse result = projectService.getProjectById(1L);

    
    assertNotNull(result);
    assertEquals("Job Application Tracker", result.getTitle());
    assertTrue(result.isPublished());

    verify(projectRepository, times(1)).findById(1L);
}
@Test
void getAllProjectsIncludingDrafts_shouldReturnAllProjects() {

    
    Project publishedProject = new Project();
    publishedProject.setTitle("Published Project");
    publishedProject.setDescription("Public project");
    publishedProject.setPublished(true);

    Project draftProject = new Project();
    draftProject.setTitle("Draft Project");
    draftProject.setDescription("Unpublished project");
    draftProject.setPublished(false);

    when(projectRepository.findAll())
            .thenReturn(List.of(publishedProject, draftProject));

    
    List<ProjectResponse> result =
            projectService.getAllProjectsIncludingDrafts();

    
    assertEquals(2, result.size());
    assertTrue(result.get(0).isPublished());
    assertFalse(result.get(1).isPublished());

    verify(projectRepository, times(1)).findAll();
}
}


