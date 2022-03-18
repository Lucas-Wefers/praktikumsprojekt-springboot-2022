drop table if exists student, student_klausur, urlaubstermine;

create table urlaubstermine
(
    datum   date         not null,
    von     time         not null,
    bis     time         not null,
    student varchar(255) not null
);

create table student
(
    handle varchar(255) not null,
    constraint student_pk
        primary key (handle)
);

create table student_klausur
(
    student varchar(255) not null,
    klausur BIGINT       not null,
    primary key (student, klausur),
    constraint `fk_klausur_student`
        FOREIGN KEY (student) REFERENCES student (handle),
        FOREIGN KEY (klausur) REFERENCES klausur (id)
);