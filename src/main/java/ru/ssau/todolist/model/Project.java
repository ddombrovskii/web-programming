package ru.ssau.todolist.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
public class Project {
    @Id
    private Long id;
    private String project_name;
    private String description;
    private LocalDate date_start;
    private LocalDate date_end;
}
