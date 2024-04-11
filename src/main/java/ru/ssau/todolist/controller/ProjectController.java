package ru.ssau.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.ssau.todolist.model.Project;
import ru.ssau.todolist.service.ProjectService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        try {
            return new ResponseEntity<>(projectService.createProject(project), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Project> editProject(@PathVariable Long projectId, @RequestBody Project project) {
        try {
            return new ResponseEntity<>(projectService.editProject(projectId, project), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
            projectService.deleteProject(projectId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable Long projectId) {
        try {
            return new ResponseEntity<>(projectService.getProject(projectId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Project>> getFilteredProjects(@RequestParam(name="start_date") LocalDate date_start,
                                                     @RequestParam(name="end_date") LocalDate date_end) {
        List<Project> projectsFilteredList = projectService.getFilteredProjects(date_start, date_end);

        if (!projectsFilteredList.isEmpty())
            return new ResponseEntity<>(projectsFilteredList, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projectsList = projectService.getAllProjects();

        if (!projectsList.isEmpty())
            return new ResponseEntity<>(projectsList, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
