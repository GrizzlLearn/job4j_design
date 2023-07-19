drop database if exists users;

create database users;

drop table if exists users_table;

create table users_table(
    id serial primary key,
    name char(255),
    age smallint,
    birthdate date
);

insert into users_table(name, age, birthdate) values ('Иван', 25, '1998-06-20');

select * from users_table;

update users_table set name = 'Николай', age = 15, birthdate = '2008-02-10';

select * from users_table;

delete from users_table;

select * from users_table;

