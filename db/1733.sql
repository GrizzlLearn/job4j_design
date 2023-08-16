DROP DATABASE IF EXISTS cars;
CREATE DATABASE cars;

DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS car_transmissions;
DROP TABLE IF EXISTS car_engines;
DROP TABLE IF EXISTS car_bodies;

create table car_bodies(
    id serial primary key,
    name CHAR(255)
);

CREATE TABLE car_engines(
    id serial primary key,
    name CHAR(255)
);

CREATE TABLE car_transmissions(
    id serial primary key,
    name CHAR(255)
);

CREATE TABLE cars(
    id serial primary key,
    name CHAR(255),
    body_id INT REFERENCES car_bodies(id),
    engine_id INT REFERENCES car_engines(id),
    transmission_id INT REFERENCES car_transmissions(id)
);

INSERT INTO car_bodies (name) VALUES
                                  ('Седан'),
                                  ('Хэтчбек'),
                                  ('Купе'),
                                  ('Универсал'),
                                  ('Внедорожник'),
                                  ('Минивэн'),
                                  ('Кабриолет');

INSERT INTO car_engines (name) VALUES
                                   ('Бензиновый'),
                                   ('Дизельный'),
                                   ('Электрический'),
                                   ('Гибридный'),
                                   ('Турбированный бензиновый'),
                                   ('Турбированный дизельный'),
                                   ('Турбированный бензиновый супер');

INSERT INTO car_transmissions (name) VALUES
                                         ('Механическая'),
                                         ('Автоматическая'),
                                         ('Роботизированная'),
                                         ('Вариатор'),
                                         ('Гидромеханическая'),
                                         ('Автоматизированная с двойным сцеплением'),
                                         ('Гидромеханическая cупер');

INSERT INTO cars (name, body_id, engine_id, transmission_id) VALUES
                                                                 ('Toyota Corolla', 1, 1, 2),
                                                                 ('Honda Civic', 1, 1, 1),
                                                                 ('Ford Mustang', 3, 5, 6),
                                                                 ('Chevrolet Camaro', 3, 5, 6),
                                                                 ('BMW X5', 5, 2, 5),
                                                                 ('Mercedes-Benz GLC', 5, 2, 5),
                                                                 ('Volkswagen Golf', 2, 3, 3),
                                                                 ('Audi A3', 2, 3, 4),
                                                                 ('Nissan Rogue', 4, 1, 2),
                                                                 ('Hyundai Tucson', 4, 1, 2),
                                                                 ('Kia Seltos', 4, 1, 2),
                                                                 ('Jeep Wrangler', 5, 4, 6),
                                                                 ('Lexus ES', 1, 2, NULL),
                                                                 ('Mazda CX-5', 4, NULL, 3),
                                                                 ('Subaru Outback', NULL, 2, 4),
                                                                 ('Porsche 911', 3, 6, 6),
                                                                 ('Ford F-150', 6, NULL, 2),
                                                                 ('Chevrolet Silverado', NULL, 5, 2),
                                                                 ('Volvo XC90', 5, NULL, 5),
                                                                 ('Hyundai Sonata', 1, 1, 1);

/* Список всех машин и все привязанные к ним детали */

SELECT c.name "Марка/Модель", cb.name AS "Тип Кузова", ce.name AS "Тип Двигателя", ct.name AS "Тип трансмиссии" FROM cars c
LEFT JOIN car_bodies cb on cb.id = c.body_id
LEFT JOIN car_engines ce on ce.id = c.engine_id
LEFT JOIN car_transmissions ct on ct.id = c.transmission_id
ORDER BY c.name;

/* Кузова, которые не используются НИ в одной машине */

SELECT cb.name AS "Тип Кузова" FROM car_bodies cb
LEFT JOIN cars c on cb.id = c.body_id
WHERE c.body_id IS NULL
ORDER BY cb.name;

/* Двигатели, которые не используются НИ в одной машине */

SELECT ce.name AS "Тип Двигателя" FROM car_engines ce
LEFT JOIN cars c on ce.id = c.engine_id
WHERE c.engine_id IS NULL
ORDER BY ce.name;

/* Коробки передач, которые не используются НИ в одной машине */

SELECT ct.name AS "Тип трансмиссии" FROM car_transmissions ct
LEFT JOIN cars c on ct.id = c.transmission_id
WHERE c.transmission_id IS NULL
ORDER BY ct.name;
