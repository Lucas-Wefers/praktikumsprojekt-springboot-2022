drop table if exists student, klausurreferenz, urlaubstermin;

create table urlaubstermin
(
    datum   date         not null,
    von     time         not null,
    bis     time         not null,
    student bigint not null,
    student_key integer
);

create table student
(
    github_id BIGINT not null,
    handle varchar(255) not null,
    constraint student_pk
        primary key (github_id)
);

create table klausurreferenz
(
    student_key integer,
    student BIGINT not null,
    id      BIGINT       not null
);