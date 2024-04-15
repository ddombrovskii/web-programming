package ru.ssau.todolist.pojo;

import lombok.Data;
import ru.ssau.todolist.model.Project;

import java.time.LocalDate;

@Data
public class ProjectPojo {
    private Long id;
    private String projectName;
    private String description;
    private LocalDate dateStart;
    private LocalDate dateEnd;

    public static ProjectPojo fromEntity(Project project) {
        ProjectPojo pojo = new ProjectPojo();

        pojo.setId(project.getId());
        pojo.setProjectName(project.getProjectName());
        pojo.setDescription(project.getDescription());
        pojo.setDateStart(project.getDateStart());
        pojo.setDateEnd(project.getDateEnd());

        return pojo;
    }

    public static Project toEntity(ProjectPojo pojo) {
        Project project = new Project();
        project.setId(pojo.getId());
        project.setProjectName(pojo.getProjectName());
        project.setDescription(pojo.getDescription());
        project.setDateStart(pojo.getDateStart());
        project.setDateEnd(pojo.getDateEnd());

        return project;
    }
}
