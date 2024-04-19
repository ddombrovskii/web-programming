package ru.ssau.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.todolist.exceptions.EntityNotFoundException;
import ru.ssau.todolist.pojo.TaskPojo;
import ru.ssau.todolist.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // POST /projects/{projectId}/tasks - создаёт задачу
    @PostMapping
    public ResponseEntity<TaskPojo> createTask(@PathVariable Long projectId, @RequestBody TaskPojo pojo) {
        try {
            return new ResponseEntity<>(taskService.create(projectId, pojo), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET /projects/{projectId}/tasks/{taskId} - возвращает задачу по id задачи и id проекта
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskPojo> readTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        try {
            return new ResponseEntity<>(taskService.read(projectId, taskId), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET /projects/{projectId}/tasks - возвращает все задачи
    @GetMapping
    public ResponseEntity<List<TaskPojo>> readAllTasks(@PathVariable Long projectId) {
        try {
            return new ResponseEntity<>(taskService.readAll(projectId), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PUT /projects/{projectId}/tasks/{taskId} - обновляет задачу
    //  {
    //      "taskName": "task",
    //      "description": "desc",
    //      "isDone": false,
    //      "planDateEnd": "2020-02-02"
    //   }
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskPojo> updateTask(@PathVariable Long projectId, @PathVariable Long taskId,
                                               @RequestBody TaskPojo pojo) {
        try {
            return new ResponseEntity<>(taskService.update(projectId, taskId, pojo), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /projects/{projectId}/tasks/{taskId} - удаляет задачу
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        try {
            taskService.delete(projectId, taskId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /projects/{projectId}/tasks/delete-tasks - Функционал, позволяющий удалить все завершённые задачи
    // определённого проекта. Id проекта приходит по HTTP
    @DeleteMapping("/delete-tasks")
    public ResponseEntity<?> deleteCompletedTasks(@PathVariable Long projectId) {
        try {
            taskService.deleteCompletedTasks(projectId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
