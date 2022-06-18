create table resume
(
    uuid      char(36) not null primary key,
    full_name text     not null
);

create table contract
(
    id          serial
        constraint contract_pk primary key,
    type        text     not null,
    value       text     not null,
    resume_uuid char(36) not null
        constraint contract_resume_uuid_fk references resume on update restrict on delete cascade
);

create unique index contract_resume_uuid_type on contract (resume_uuid, type);
