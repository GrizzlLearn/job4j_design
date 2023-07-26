DROP DATABASE IF EXISTS devices;
CREATE DATABASE devices;

DROP TABLE IF EXISTS people CASCADE;
DROP TABLE IF EXISTS devices CASCADE;
DROP TABLE IF EXISTS devices_people;

create table devices(
    device_id serial primary key,
    name varchar(255),
    price float
);

create table people(
    people_id serial primary key,
    name varchar(255)
);

create table devices_people(
    id serial primary key,
    device_id int references devices(device_id),
    people_id int references people(people_id)
);

INSERT INTO people(name)
VALUES ('people_1'),
       ('people_2'),
       ('people_3'),
       ('people_4');

INSERT INTO devices(name, price)
VALUES ('device_name_1', 10.99),
       ('device_name_2', 14.99),
       ('device_name_3', 24.99),
       ('device_name_4', 39.99);

INSERT INTO devices_people(device_id, people_id)
VALUES (1, 4),
       (4, 4),
       (2, 3),
       (3, 2);

SELECT avg(price) FROM devices;

SELECT p.name, avg(d.price)
FROM people AS p
    JOIN devices_people
        AS dp
        ON p.people_id = dp.people_id
    JOIN devices
        AS d
        ON dp.device_id = d.device_id
GROUP BY p.name;

SELECT p.name, avg(d.price)
FROM people AS p
    JOIN devices_people
        AS dp
        ON p.people_id = dp.people_id
    JOIN devices
        AS d
        ON dp.device_id = d.device_id
GROUP BY p.name
HAVING avg(d.price) > 15;
