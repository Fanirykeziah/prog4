create table if not exists "company_conf"(
   id BIGSERIAL primary key,
   name varchar not null,
   description varchar not null,
   slogan varchar not null,
   address varchar not null,
   email varchar not null,
   phone_numbers varchar(10) unique not null,
   nif varchar,
   stat varchar,
   logo varchar
 );