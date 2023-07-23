CREATE SEQUENCE employee_seq START WITH 1 INCREMENT BY 1;

create table if not exists "cin"(
    id BIGSERIAL primary key,
    number varchar,
    issue_date date,
    issue_location varchar
);


