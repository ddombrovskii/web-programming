package ru.ssau.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.ssau.todolist.exceptions.EmptyEntityException;
import ru.ssau.todolist.pojo.ProjectPojo;
import ru.ssau.todolist.service.ProjectService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    // POST /projects - создаёт проект (без задач);
    @PostMapping
    public ResponseEntity<ProjectPojo> createProject(@RequestBody ProjectPojo pojo) {
        return new ResponseEntity<>(projectService.create(pojo), HttpStatus.OK);
    }

    // GET /projects/{projectId} - возвращает проект с id=projectId
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectPojo> readProject(@PathVariable Long projectId) {
        try {
            return new ResponseEntity<>(projectService.read(projectId), HttpStatus.OK);
        } catch (EmptyEntityException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PUT /projects/{projectId} - обновляет проект
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectPojo> updateProject(@PathVariable Long projectId, @RequestBody ProjectPojo project) {
        return new ResponseEntity<>(projectService.update(projectId, project), HttpStatus.OK);
    }

    // DELETE /projects/{projectId} - удаление проекта. При удалении проекта удаляются все связанные задачи.
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        projectService.delete(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // GET /projects?search={search} - возвращает все проекты с опциональной фильтрацией по тексту
    @GetMapping
    public ResponseEntity<List<ProjectPojo>> getProjectsBySearch(@RequestParam(name = "search") String query) {
        return new ResponseEntity<>(projectService.search(query), HttpStatus.OK);
    }

    // Получение информации о количестве незакрытых задач во всех проектах
    @GetMapping("/open-tasks")
    public ResponseEntity<HashMap<Long, Integer>> getOpenTasks() {
        return new ResponseEntity<>(projectService.getOpenTasks(), HttpStatus.OK);
    }

}
