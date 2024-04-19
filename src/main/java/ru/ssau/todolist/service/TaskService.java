package ru.ssau.todolist.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ssau.todolist.exceptions.EntityNotFoundException;
import ru.ssau.todolist.model.Task;
import ru.ssau.todolist.pojo.TaskPojo;
import ru.ssau.todolist.repository.ProjectRepository;
import ru.ssau.todolist.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    // создание задачи по id проекта
    public TaskPojo create(Long projectId, TaskPojo pojo) throws EntityNotFoundException {
        var opProject = projectRepository.findById(projectId);
        if (opProject.isEmpty()) {
            throw new EntityNotFoundException("Not found project by id " + projectId);
        }

        Task tsk = taskRepository.saveAndFlush(Task.builder()
                .taskName(pojo.getTaskName())
                .description(pojo.getDescription())
                .project(opProject.get())
                .planDateEnd(pojo.getPlanDateEnd())
                .isDone(pojo.getIsDone()).build());

        return TaskPojo.fromEntity(tsk);
    }

    // получение задачи по id проекта
    public TaskPojo read(Long projectId, Long taskId) throws EntityNotFoundException {
        var opProject = projectRepository.findById(projectId);
        if (opProject.isEmpty()) {
            throw new EntityNotFoundException("Not found project by id " + projectId);
        }

        var tsk = taskRepository.findById(taskId);
        if (tsk.isEmpty()) {
            throw new EntityNotFoundException("Not found project by id " + taskId);
        }

        return TaskPojo.fromEntity(tsk.get());
    }

    // получение всех задач по id проекта
    public List<TaskPojo> readAll(Long projectId) throws EntityNotFoundException {
        var opProject = projectRepository.findById(projectId);
        if (opProject.isEmpty()) {
            throw new EntityNotFoundException("Not found project by id " + projectId);
        }

        List<Task> tasks = taskRepository.findByProjectId(projectId);
        List<TaskPojo> tsk = new ArrayList<>();

        for (Task t : tasks) {
            tsk.add(TaskPojo.fromEntity(t));
        }

        return tsk;
    }

    // обновление задачи по id проекта и id задачи
    public TaskPojo update(Long projectId, Long taskId, TaskPojo pojo) throws EntityNotFoundException {
        var opProject = projectRepository.findById(projectId);
        if (opProject.isEmpty()) {
            throw new EntityNotFoundException("Not found project by id " + projectId);
        }

        Task taskToUpdate = taskRepository.getReferenceById(taskId);
        taskToUpdate.setTaskName(pojo.getTaskName());
        taskToUpdate.setDescription(pojo.getDescription());
        taskToUpdate.setPlanDateEnd(pojo.getPlanDateEnd());
        taskToUpdate.setIsDone(pojo.getIsDone());

        Task tsk = taskRepository.save(taskToUpdate);

        return TaskPojo.fromEntity(tsk);
    }

    // удаление задачи по id проекта и id задачи
    public void delete(Long projectId, Long taskId) throws EntityNotFoundException {
        var opProject = projectRepository.findById(projectId);
        if (opProject.isEmpty()) {
            throw new EntityNotFoundException("Not found project by id " + projectId);
        }

        taskRepository.deleteById(taskId);
    }

    // удаление всех завершенных задач в проекте по id проекта
    public void deleteCompletedTasks(Long projectId) throws EntityNotFoundException {
        var opProject = projectRepository.findById(projectId);
        if (opProject.isEmpty()) {
            throw new EntityNotFoundException("Not found project by id " + projectId);
        }

        taskRepository.deleteCompletedTasksByProjectId(projectId);
    }
}
