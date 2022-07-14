CREATE SEQUENCE IF NOT EXISTS user_generator START with 100 INCREMENT BY 1;

CREATE TABLE users
(
    id integer primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    user_name varchar(50) not null,
    password varchar(50) not null,
    email varchar(50) not null,
    country varchar(50) not null,
    city varchar(50) not null,
    gender varchar(6) not null,
   -- birth_date date not null,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    package varchar(20) not null
)
