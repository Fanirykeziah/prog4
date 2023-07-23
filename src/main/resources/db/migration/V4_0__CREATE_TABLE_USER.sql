create table if not exists "userr" (
   id BIGSERIAL primary key,
   username varchar not null,
   password varchar not null,
   role varchar check(role in('ADMIN','EMPLOYE'))
);