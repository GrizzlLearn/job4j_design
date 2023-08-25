drop database if exists companies;
create database companies;

drop table if exists customers;

CREATE TABLE public.customers
(
    id         serial primary key,
    first_name text,
    last_name  text,
    age        int,
    country    text
);

INSERT INTO customers (first_name, last_name, age, country)
VALUES ('Alice', 'Smith', 25, 'Canada'),
       ('Emma', 'Brown', 28, 'UK'),
       ('Olivia', 'Davis', 35, 'Australia'),
       ('Sophia', 'Wilson', 23, 'Canada'),
       ('Liam', 'Taylor', 31, 'UK'),
       ('Ethan', 'Moore', 33, 'Canada'),
       ('Noah', 'Wilson', 23, 'UK'),
       ('Mia', 'Thomas', 24, 'UK'),
       ('Benjamin', 'Walker', 26, 'UK'),
       ('Isabella', 'Hall', 23, 'UK');

select first_name, last_name, age
from customers as c
where age = (select min(age) from customers);

CREATE TABLE orders
(
    id          serial primary key,
    amount      int,
    customer_id int references customers (id)
);

INSERT INTO orders (amount, customer_id)
VALUES (100, 1),
       (200, 2),
       (150, 3),
       (250, 5),
       (180, 6),
       (220, 7),
       (280, 9),
       (170, 10);

select *
from customers as c
where c.id not in (select customer_id from orders as o where o.customer_id = c.id);

