package ru.ssau.todolist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.ssau.todolist.model.Project;

import java.time.LocalDate;
import java.util.List;

@Repository
public class JdbcProjectRepository implements ProjectRepository {
    private final NamedParameterJdbcTemplate template;
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Project> projectMapper = (rs, rowNum) -> {
        Project project = new Project();
        project.setId(rs.getLong("id"));
        project.setProject_name(rs.getString("project_name"));
        project.setDescription(rs.getString("description"));
        project.setDate_start(rs.getDate("date_start").toLocalDate());
        project.setDate_end(rs.getDate("date_end").toLocalDate());
        return project;
    };

    public JdbcProjectRepository(NamedParameterJdbcTemplate template, JdbcTemplate jdbcTemplate) {
        this.template = template;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createAndReturnId(Project project) {
        String sql = "INSERT INTO project (project_name, description, date_start, date_end) " +
                "VALUES (:project_name, :description, :date_start, :date_end) RETURNING id";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("project_name", project.getProject_name())
                .addValue("description", project.getDescription())
                .addValue("date_start", project.getDate_start())
                .addValue("date_end", project.getDate_end());

        return template.queryForObject(sql, parameterSource, Long.class);
    }

    public void editProject(Long id, Project project) {
        String sql = "UPDATE project SET project_name = :project_name, description = :description, " +
                "date_start = :date_start WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("project_name", project.getProject_name())
                .addValue("description", project.getDescription())
                .addValue("date_start", project.getDate_start())
                .addValue("date_end", project.getDate_end());

        template.update(sql, parameterSource);
    }

    public void deleteProject(Long id) {
        String sql = "DELETE FROM project WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        template.update(sql, parameterSource);
    }

    public Project getProjectById(Long id) {
        String sql = "SELECT * FROM project WHERE project.id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);

        return template.queryForObject(sql, parameterSource, (rs, rowNum) -> {
            Project project = new Project();
            project.setId(rs.getLong("id"));
            project.setProject_name(rs.getString("project_name"));
            project.setDescription(rs.getString("description"));
            project.setDate_start(rs.getDate("date_start").toLocalDate());
            project.setDate_end(rs.getDate("date_end").toLocalDate());
            return project;
        });
    }

    public List<Project> getFilteredProjects(LocalDate from_date, LocalDate to_date) {
        String sql = "SELECT * FROM project WHERE project.date_start >= ? AND project.date_end <= ?";

        return jdbcTemplate.query(sql, projectMapper, from_date, to_date);
    }

    public List<Project> getAllProjects() {
        String sql = "SELECT * FROM project";
        return template.query(sql, projectMapper);
    }
}
