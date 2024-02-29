package ru.ssau.todolist.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
public class Task {
    @Id
    private Long id;
    private Long projectId;
    private String taskName;
    private String description;
    private LocalDate planDateEnd;
    private Boolean isDone;
}
