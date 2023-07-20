drop database if exists many_to_one;

create database many_to_one;

drop table if exists operators;
drop table if exists lathes;

create table lathes(
    id serial primary key,
    name char(255),
    inventory_id int,
    part_number char(255)
);

create table operators(
    id serial primary key,
    name char(255),
    experience_in_years smallint,
    lathe_id int references lathes(id)
);

insert into lathes(name, inventory_id, part_number) VALUES ('lathe_name_0', 123456, 'part_number_0');
insert into operators(name, experience_in_years, lathe_id) VALUES ('operator_name_0', 10, 1);

select * from lathes;
select * from operators where id in (select id from lathes);
