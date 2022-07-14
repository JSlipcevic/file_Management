CREATE SEQUENCE IF NOT EXISTS file_generator START with 100 INCREMENT BY 1;

CREATE TABLE file (

    id integer primary key,
    file_name varchar (50) not null,
    description varchar (255),
    document_type varchar (20) not null,
    document_size integer not null,
    upload_date timestamptz not null,
    time_to_live timestamptz not null,
    updated_at timestamptz not null,
    downloaded_at timestamptz,
    download_counter integer not null default 0,
    user_id integer not null,
    CONSTRAINT fk_userid_to_file_table FOREIGN KEY (user_id) REFERENCES users (id)
)