DROP DATABASE IF EXISTS keyboards;

CREATE DATABASE keyboards;

DROP TABLE IF EXISTS keys_num CASCADE;
DROP TABLE IF EXISTS keyboards_type;

CREATE TABLE keys_num(
    id serial primary key,
    keys_num int
);

CREATE TABLE keyboards_type(
    id serial primary key,
    keyboards_type char(255),
    keys_num_id int references keys_num(id)
);

INSERT INTO keys_num(keys_num) VALUES (104);
INSERT INTO keys_num(keys_num) VALUES (87);
INSERT INTO keys_num(keys_num) VALUES (84);

INSERT INTO keyboards_type(keyboards_type, keys_num_id) VALUES ('ANSI', 1);
INSERT INTO keyboards_type(keyboards_type, keys_num_id) VALUES ('TKL', 2);
INSERT INTO keyboards_type(keyboards_type, keys_num_id) VALUES ('75%', 3);

SELECT kt.keyboards_type AS "Тип Клавиатуры", kn.keys_num AS "Количество клавиш"
FROM keyboards_type AS kt
    INNER JOIN keys_num
        AS kn
        ON kt.id = kn.id;

SELECT kt.keyboards_type AS "Тип Клавиатуры", kn.keys_num AS "Количество клавиш"
FROM keyboards_type AS kt
    INNER JOIN keys_num
        AS kn
        ON kt.id = kn.id
WHERE kt.keyboards_type = '75%';

SELECT kt.keyboards_type AS "Тип Клавиатуры", kn.keys_num AS "Количество клавиш"
FROM keyboards_type AS kt
    INNER JOIN keys_num
        AS kn
        ON kt.id = kn.id
WHERE kn.keys_num = 104;
