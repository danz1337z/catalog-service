create table book
(
    id                 serial primary key  not null,
    isbn               varchar(255) unique not null,
    title              varchar(255)        not null,
    author             varchar(255)        not null,
    price              float8              not null,
    created_time       timestamp           not null,
    last_modified_time timestamp           not null,
    version            integer             not null
);