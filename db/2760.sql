drop database if exists control_task;
create database control_task;

drop table if exists company cascade;
drop table if exists person cascade;

CREATE TABLE company (
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person (
    id integer NOT NULL,
    name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

INSERT INTO company (id, name)
VALUES
    (1, 'Company A'),
    (2, 'Company B'),
    (3, 'Company C'),
    (4, 'Company D'),
    (5, 'Company E');

INSERT INTO person (id, name, company_id)
VALUES
    (1, 'John', 1),
    (2, 'Jane', 2),
    (3, 'Alice', 3),
    (4, 'Bob', 1),
    (5, 'Charlie', 4),
    (6, 'David', 2),
    (7, 'Eve', 3),
    (8, 'Frank', 5),
    (9, 'Grace', 4),
    (10, 'Harry', 1),
    (11, 'Ivy', 1),
    (12, 'Jack', 2),
    (13, 'Kelly', 3),
    (14, 'Liam', 4),
    (15, 'Mia', 4);

/*
1. В одном запросе получить:

- имена всех person, которые не состоят в компании с id = 5;
- название компании для каждого человека.
*/

SELECT p.name, c.name FROM person AS p
        JOIN company AS c ON p.company_id = c.id
            WHERE p.company_id != 5
ORDER BY p.id;

/*
2. Необходимо выбрать название компании с максимальным количеством человек + количество человек в этой компании.
Нужно учесть, что таких компаний может быть несколько.
*/

WITH CompanyPersonCounts AS (
    SELECT
        c.name AS company_name,
        COUNT(p.id) AS person_count
    FROM
        company c
            LEFT JOIN
        person p ON c.id = p.company_id
    GROUP BY
        c.name
)
SELECT
    company_name,
    person_count
FROM
    CompanyPersonCounts
WHERE
        person_count = (SELECT MAX(person_count) FROM CompanyPersonCounts)
ORDER BY company_name;
