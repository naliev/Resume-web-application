create table resume
(
    uuid      char(36) not null primary key,
    full_name text     not null
);

create table contact
(
    id          serial
        constraint contact_pk primary key,
    type        text     not null,
    value       text     not null,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk references resume on update restrict on delete cascade
);

create unique index contact_resume_uuid_type on contact (resume_uuid, type);

create table section
(
    id          serial constraint section_pk primary key,
    type        text        not null,
    value       text        not null,
    resume_uuid char(36)    not null
        constraint section_resume_uuid_fk references resume on update restrict on delete cascade
);

create unique index section_resume_uuid_type on section (resume_uuid, type);