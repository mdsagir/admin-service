create table if not exists address
(
    id            bigint auto_increment
    primary key,
    address_line1 varchar(255) null,
    address_line2 varchar(255) null,
    country       varchar(255) null,
    pin_code      varchar(255) null,
    state         varchar(255) null
    );

create table if not exists hospital
(
    id            bigint auto_increment
    primary key,
    created_by    bigint       null,
    created_date  datetime(6)  null,
    flag          bit          null,
    modified_by   bigint       null,
    modified_date datetime(6)  null,
    status        bit          null,
    founded_at    varchar(255) null,
    hospital_name varchar(255) null,
    address_id    bigint       null,
    constraint FKsn8phpkxt2ywxycwicbybshun
    foreign key (address_id) references address (id)
    );

create table if not exists branch
(
    id            bigint auto_increment
    primary key,
    branch_name   varchar(255) null,
    created_by    bigint       null,
    created_date  datetime(6)  null,
    flag          bit          null,
    modified_by   bigint       null,
    modified_date datetime(6)  null,
    status        bit          null,
    address_id    bigint       null,
    hospital_id   bigint       null,
    constraint FKphsdwxh318gha38mlfallngwq
    foreign key (hospital_id) references hospital (id),
    constraint FKr5n331c13dyb3kbq1jlo53mh5
    foreign key (address_id) references address (id)
    );

create table if not exists person
(
    id           bigint       not null
    primary key,
    alternate_no varchar(255) null,
    email        varchar(255) null,
    first_name   varchar(255) null,
    last_name    varchar(255) null,
    mobile_no    varchar(255) null,
    surname      varchar(255) null,
    constraint FKg00t0xysertoyfh95u8d3nj01
    foreign key (id) references address (id)
    );

create table if not exists doctor
(
    id            bigint       not null
    primary key,
    created_by    bigint       null,
    created_date  datetime(6)  null,
    flag          bit          null,
    modified_by   bigint       null,
    modified_date datetime(6)  null,
    status        bit          null,
    degree        varchar(255) null,
    practice_name varchar(255) null,
    specialty     varchar(255) null,
    hospital_id   bigint       null,
    constraint FKml6eqq1qsctk0soclu7hyo2xi
    foreign key (hospital_id) references branch (id),
    constraint FKop6cku1inyqn8son2ki4cgqdh
    foreign key (id) references person (id)
    );