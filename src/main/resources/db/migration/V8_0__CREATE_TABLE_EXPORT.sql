create table if not exists "export" (
    id BIGSERIAL primary key not null,
    img varchar,
    matricule varchar unique,
    name varchar(255) not null,
    first_name varchar(100) not null,
    age int not null,
    starting_date date not null,
    ending_date date,
    cnaps_number varchar,
    salary varchar,
    logo varchar,
    company_name varchar not null,
    nif varchar,
    stat varchar,
    company_address varchar not null,
    company_number varchar not null,
    email varchar not null
);


