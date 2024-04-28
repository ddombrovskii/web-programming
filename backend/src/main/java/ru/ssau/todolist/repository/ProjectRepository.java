package ru.ssau.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ssau.todolist.model.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByProjectNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String projectName, String description);
}
