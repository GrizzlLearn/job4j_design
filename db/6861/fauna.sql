DROP DATABASE IF EXISTS fauna;
CREATE DATABASE fauna;

DROP TABLE IF EXISTS fauna;

CREATE TABLE fauna (
   id serial primary key,
   name text,
   avg_age int,
   discovery_date date
);

INSERT INTO fauna(name, avg_age, discovery_date)
VALUES ('name_1', 1, '1910-01-01');

INSERT INTO fauna(name, avg_age, discovery_date)
VALUES ('name_2', 15000, '1920-01-02');

INSERT INTO fauna(name, avg_age, discovery_date)
VALUES ('name_3', 3, '1930-01-03');

INSERT INTO fauna(name, avg_age, discovery_date)
VALUES ('a_fish_4', 4, '1940-01-04');

INSERT INTO fauna(name, avg_age, discovery_date)
VALUES ('name_5', 5, '1950-01-05');

INSERT INTO fauna(name, avg_age, discovery_date)
VALUES ('name_6', 6, '1960-01-06');

INSERT INTO fauna(name, avg_age, discovery_date)
VALUES ('b_fish_7', 7, null);

INSERT INTO fauna(name, avg_age, discovery_date)
VALUES ('name_8', 20000, '1980-01-08');

/*
    Запросы из задания
 */

SELECT * FROM fauna WHERE name LIKE '%fish%';
SELECT * FROM fauna WHERE avg_age > 10000 AND avg_age < 21000;
SELECT * FROM fauna WHERE discovery_date IS NULL;
SELECT * FROM fauna WHERE discovery_date < '01.01.1950';
