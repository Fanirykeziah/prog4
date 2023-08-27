do
$$
    begin
            create type csp as enum ('M1','M2','OS1','OS2','OS3','OP1');
    end
$$;

create table if not exists "employee" (
    id BIGSERIAL primary key not null,
    name varchar(255) not null,
    first_name varchar(100) not null,
    birthday date not null,
    cin_id int references cin(id) unique not null,
    sexe varchar(1) check (sexe in('H','F')),
    location varchar not null,
    function varchar not null,
    photos varchar,
    children_number int,
    email_perso varchar not null,
    email_pro varchar not null,
    phone_numbers varchar(13) unique,
    starting_date date not null,
    closing_date date,
    csp csp,
    cnaps varchar,
    matricule varchar(10) unique,
    salary varchar not null
);


