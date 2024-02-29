package ru.ssau.todolist.repository;

import ru.ssau.todolist.model.Project;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository {
    Long createAndReturnId(Project project);

    void editProject(Long id, Project project);

    void deleteProject(Long id);

    Project getProjectById(Long id);

    List<Project> getFilteredProjects(LocalDate from_date, LocalDate to_date);

    List<Project> getAllProjects();

}
