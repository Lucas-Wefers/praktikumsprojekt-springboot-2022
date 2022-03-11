drop table if exists klausur;

create table klausur
(
    id bigint auto_increment,
    fach varchar(255) not null,
    datum date not null,
    von time not null,
    bis time not null,
    is_praesenz boolean not null,
    veranstaltungs_id bigint not null,
    constraint klausur_pk
        primary key (id)
);