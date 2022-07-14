CREATE TABLE download_permission(
    file_id integer not null,
    user_id integer not null,
    CONSTRAINT fk_file_id FOREIGN KEY (file_id) REFERENCES file (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);
