create sequence group_id
    increment by 1
    start with 1
    cache 1;

create type form_of_education as enum (
    'DISTANCE_EDUCATION',
    'FULL_TIME_EDUCATION',
    'EVENING_CLASSES'
    );

create type semester as enum (
    'FIRST',
    'SECOND',
    'FIFTH',
    'SIXTH',
    'SEVENTH'
    );

create type color as enum (
    'RED',
    'BLACK',
    'BLUE',
    'ORANGE',
    'BROWN'
    );

create table study_groups (
                              search_key text unique not null,
                              group_id integer not null default nextval('group_id') primary key,
                              group_name text not null,
                              creation_date date not null,
                              student_counts bigint not null,
                              expelled_students bigint not null,
                              form_of_education form_of_education,
                              semester semester not null,
                              creator text not null
);

alter table study_groups
    add constraint check_students_count
        check (student_counts > 0);

alter table study_groups
    add constraint check_expelled_students
        check (expelled_students > 0);


create table group_coordinates (
                                   coordinate_id serial primary key,
                                   group_id integer unique not null,
                                   coordinate_x integer not null,
                                   coordinate_y integer not null,
                                   foreign key (group_id) references study_groups(group_id) on delete cascade
);

alter table group_coordinates
    add constraint check_x
        check (coordinate_x > -678);

alter table group_coordinates
    add constraint check_y
        check (coordinate_y > -438);

create table group_admins (
                              admin_id serial primary key,
                              group_id integer unique not null,
                              admin_name text,
                              birthday date,
                              weight integer,
                              eye_color color,
                              foreign key (group_id) references study_groups(group_id) on delete cascade
);

alter table group_admins
    add constraint check_weight
        check (weight is null or weight > 0);

create table admin_locations (
                                 location_id serial primary key,
                                 admin_id integer unique not null,
                                 x bigint not null,
                                 y integer not null,
                                 z integer not null,
                                 place text,
                                 foreign key (admin_id) references group_admins(admin_id) on delete cascade
);


