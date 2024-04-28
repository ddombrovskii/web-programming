package ru.ssau.todolist.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ssau.todolist.exceptions.EmptyEntityException;
import ru.ssau.todolist.pojo.ProjectPojo;
import ru.ssau.todolist.model.Project;
import ru.ssau.todolist.repository.ProjectRepository;
import ru.ssau.todolist.repository.TaskRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    // создание проекта
    public ProjectPojo create(ProjectPojo pojo) {
        Project pj = projectRepository.save(Project.builder()
                .projectName(pojo.getProjectName())
                .description(pojo.getDescription())
                .dateStart(pojo.getDateStart())
                .dateEnd(pojo.getDateEnd()).build());

        return ProjectPojo.fromEntity(pj);
    }

    // изменение проекта
    public ProjectPojo update(Long id, ProjectPojo dto) {
        Project projectToUpdate = projectRepository.getReferenceById(id);

        projectToUpdate.setProjectName(dto.getProjectName());
        projectToUpdate.setDescription(dto.getDescription());
        projectToUpdate.setDateStart(dto.getDateStart());
        projectToUpdate.setDateEnd(dto.getDateEnd());

        Project pj = projectRepository.save(projectToUpdate);

        return ProjectPojo.fromEntity(pj);
    }

    // получение проекта
    public ProjectPojo read(Long id) throws EmptyEntityException {
        var pj = projectRepository.findById(id);

        if (pj.isEmpty()) {
            throw new EmptyEntityException("Not found project by id " + id);
        }

        return ProjectPojo.fromEntity(pj.get());

    }

    // удаление проекта
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    // поиск по описанию (description)
    public List<ProjectPojo> search(String query) {
        List<Project> project = projectRepository.findByProjectNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
        List<ProjectPojo> pj = new ArrayList<>();

        for (Project p : project) {
            pj.add(ProjectPojo.fromEntity(p));
        }

        return pj;
    }

    // количество незакрытых (false) задач во всех проектах
    public HashMap<Long, Integer> getOpenTasks() {
        HashMap<Long, Integer> openTasks = new HashMap<>();
        List<Project> projects = projectRepository.findAll();

        for (Project p: projects) {
            openTasks.put(p.getId(), taskRepository.getCountOfOpenTasksByProjectId(p.getId()));
        }

        return openTasks;
    }
}
