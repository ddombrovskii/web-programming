package ru.ssau.todolist.pojo;

import lombok.Data;
import ru.ssau.todolist.model.Task;

import java.time.LocalDate;

@Data
public class TaskPojo {
    private Long id;
    private String taskName;
    private String description;
    private LocalDate planDateEnd;
    private Boolean isDone;

    public static TaskPojo fromEntity(Task task) {
        TaskPojo pojo = new TaskPojo();

        pojo.setId(task.getId());
        pojo.setTaskName(task.getTaskName());
        pojo.setDescription(task.getDescription());
        pojo.setPlanDateEnd(task.getPlanDateEnd());
        pojo.setIsDone(task.getIsDone());

        return pojo;
    }

    public static Task toEntity(TaskPojo pojo) {
        Task task = new Task();

        task.setId(pojo.getId());
        task.setTaskName(pojo.getTaskName());
        task.setDescription(pojo.getDescription());
        task.setPlanDateEnd(pojo.getPlanDateEnd());
        task.setIsDone(pojo.getIsDone());

        return task;
    }
}
