DROP DATABASE IF EXISTS filters;
CREATE DATABASE filters;

DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS type;

CREATE TABLE type(
    id serial primary key,
    name text
);

CREATE TABLE product(
    id serial primary key,
    name text,
    type_id int REFERENCES type(id),
    expired_date date,
    price int
);

INSERT INTO type(name) VALUES ('СЫР'),
                              ('МОЛОКО'),
                              ('ТВОРОГ'),
                              ('МОРОЖЕНОЕ');

INSERT INTO product(name, type_id, expired_date, price) VALUES ('Сыр Обычный', 1, '2023-07-28', 100),
                                                               ('Сыр Жирный', 1, '2023-07-30', 110),
                                                               ('Сыр Диетический', 1, '2023-07-31', 120);

INSERT INTO product(name, type_id, expired_date, price) VALUES ('Молоко Обычное', 2, '2023-08-10', 200),
                                                               ('Молоко Жирное', 2, '2023-07-05', 210),
                                                               ('Молоко Диетическое_1', 2, '2023-08-02', 221),
                                                               ('Молоко Диетическое_2', 2, '2023-08-02', 222),
                                                               ('Молоко Диетическое_3', 2, '2023-08-02', 223);

INSERT INTO product(name, type_id, expired_date, price) VALUES ('Творог Обычный', 3, '2023-08-14', 300),
                                                               ('Творог Жирный', 3, '2023-08-09', 310),
                                                               ('Творог Диетический_1', 3, '2023-08-17', 421),
                                                               ('Творог Диетический_2', 3, '2023-08-17', 422),
                                                               ('Творог Диетический_3', 3, '2023-08-17', 423),
                                                               ('Творог Диетический_4', 3, '2023-08-17', 424),
                                                               ('Творог Диетический_5', 3, '2023-08-17', 425);

INSERT INTO product(name, type_id, expired_date, price) VALUES ('Мороженое Обычное', 4, '2023-06-14', 400),
                                                               ('Мороженое Жирное', 4, '2023-09-09', 410);

/* Получение всех продуктов с типом "СЫР" */
SELECT *
FROM product AS p
    JOIN type t
        ON p.type_id = t.id
WHERE t.name = 'СЫР';

/* Получения всех продуктов, у кого в имени есть слово "мороженое" */
SELECT *
FROM product
WHERE name LIKE '%Мороженое%';

/* Все продукты, срок годности которых уже истек */

SELECT *
FROM product
WHERE expired_date < CURRENT_DATE;

/* Самый дорогой продукт */

SELECT *
FROM product
WHERE price = (
    SELECT MAX(price)
    FROM product
);

/* Для каждого типа продукта количество продуктов к нему принадлежащих. В виде имя_типа, количество */

SELECT t.name AS "имя_типа", COUNT(*) AS "количество" FROM product AS p
    JOIN type t
        ON t.id = p.type_id
GROUP BY t.name;

/* Получение всех продуктов с типом "СЫР" и "МОЛОКО" */

SELECT * FROM product AS p
    JOIN type t
        ON p.type_id = t.id
WHERE t.name = 'СЫР' OR t.name = 'МОЛОКО'
ORDER BY t.name;

/* Тип продуктов, которых осталось меньше 5 штук */

SELECT t.name AS "имя_типа", COUNT(*) AS "количество" FROM product AS p
    JOIN type t
        ON t.id = p.type_id
GROUP BY t.name
HAVING COUNT(*) < 5;

/* Все продукты и их тип */

SELECT p.name, t.name FROM product AS p
    JOIN type t
        ON p.type_id = t.id;
