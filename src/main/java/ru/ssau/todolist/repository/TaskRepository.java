package ru.ssau.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.todolist.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(@Param("project_id") Long projectId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM task WHERE task.project_id=?1 AND is_done = true", nativeQuery = true)
    void deleteCompletedTasksByProjectId(Long projectId);

    @Query(value = "SELECT COUNT(*) FROM task WHERE task.is_done = false AND task.project_id = ?1", nativeQuery = true)
    int getCountOfOpenTasksByProjectId(Long projectId);
}
