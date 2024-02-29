CREATE TABLE IF NOT EXISTS project
(
    id           BIGSERIAL PRIMARY KEY,
    project_name VARCHAR(50) NOT NULL,
    description  TEXT,
    date_start   DATE,
    date_end     DATE
);

ALTER TABLE project
    ADD CHECK (date_end >= date_start);

CREATE TABLE IF NOT EXISTS task
(
    id            BIGSERIAL PRIMARY KEY,
    project_id    BIGINT      NOT NULL,
    task_name     VARCHAR(50) NOT NULL,
    description   TEXT,
    plan_date_end DATE,
    is_done       BOOLEAN     NOT NULL,

    FOREIGN KEY (project_id) REFERENCES project (id)
);