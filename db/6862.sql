DROP DATABASE IF EXISTS departments;
CREATE DATABASE departments;

DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS teens;

CREATE TABLE departments(
    dep_id serial primary key,
    dep_name CHAR(255) unique
);

CREATE TABLE employee(
    emp_id serial primary key,
    emp_name CHAR(255),
    dep_id int references departments(dep_id)
);

CREATE TABLE teens(
    teen_id serial primary key,
    teen_name CHAR(255),
    teem_gender CHAR(1)
);

INSERT INTO departments (dep_name) VALUES ('Отдел разработки'),
                                          ('Отдел маркетинга'),
                                          ('Отдел финансов');

INSERT INTO employee (emp_name, dep_id) VALUES ('Иван Иванов', 1),
                                               ('Петр Петров', 1),
                                               ('Мария Сидорова', 2),
                                               ('Алексей Смирнов', 2);

INSERT INTO teens (teen_name, teem_gender) VALUES ('Анна', 'F'),
                                                  ('Иван', 'M'),
                                                  ('Мария', 'F'),
                                                  ('Петр', 'M'),
                                                  ('Елена', 'F'),
                                                  ('Алексей', 'M');

/* left, right, full, cross соединения */

SELECT *
FROM departments d
LEFT JOIN employee e
    ON d.dep_id = e.dep_id
WHERE e.dep_id = 2;

SELECT *
FROM employee e
RIGHT JOIN departments d
    ON d.dep_id = e.dep_id
WHERE d.dep_id = 1;

SELECT *
FROM departments d
FULL JOIN employee e
    ON d.dep_id = e.dep_id;

SELECT d.dep_id, d.dep_name, e.emp_id, e.emp_name
FROM departments d
CROSS JOIN employee e;

/*left join, департаменты без работников*/
SELECT *
FROM departments d
LEFT JOIN employee e
    ON d.dep_id = e.dep_id
WHERE e.dep_id IS NULL;

/* 1 left join и right join дают одинаковый результат*/

SELECT *
FROM departments d
LEFT JOIN employee e
    ON d.dep_id = e.dep_id
WHERE e.dep_id IS NOT NULL;

/* 2 left join и right join дают одинаковый результат*/

SELECT d.dep_id, d.dep_name, e.emp_id, e.emp_name, e.dep_id
FROM employee e
RIGHT JOIN departments d ON d.dep_id = e.dep_id
WHERE e.dep_id IS NOT NULL;

/* cross join, составить все возможные разнополые пары */

SELECT *
FROM teens t1
CROSS JOIN teens t2
WHERE t1.teem_gender != t2.teem_gender AND t1.teen_id < t2.teen_id
ORDER BY t1.teen_name;
