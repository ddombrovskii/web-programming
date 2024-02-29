package ru.ssau.todolist.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ssau.todolist.model.Project;
import ru.ssau.todolist.repository.ProjectRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;

    public Project createProject(Project project) {
        return repository.getProjectById(repository.createAndReturnId(project));
    }

    public Project editProject(Long id, Project project) {
        repository.editProject(id, project);
        return repository.getProjectById(id);
    }

    public void deleteProject(Long id) {
        repository.deleteProject(id);
    }

    public Project getProject(Long id) {
        return repository.getProjectById(id);
    }

    public List<Project> getFilteredProjects(LocalDate from_date, LocalDate to_date) {
        return repository.getFilteredProjects(from_date, to_date);
    }

    public List<Project> getAllProjects() {
        return repository.getAllProjects();
    }

}
