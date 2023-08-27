TRUNCATE TABLE products;
ALTER SEQUENCE products_id_seq RESTART WITH 1;

insert into products (name, count, price) VALUES ('product_1', 1, 5);
insert into products (name, count, price) VALUES ('product_2', 2, 10);
insert into products (name, count, price) VALUES ('product_3', 3, 15);
insert into products (name, count, price) VALUES ('product_4', 4, 20);
insert into products (name, count, price) VALUES ('product_5', 5, 25);
insert into products (name, count, price) VALUES ('product_6', 6, 30);
insert into products (name, count, price) VALUES ('product_7', 7, 35);
insert into products (name, count, price) VALUES ('product_8', 8, 40);
insert into products (name, count, price) VALUES ('product_9', 9, 45);
insert into products (name, count, price) VALUES ('product_10', 10, 50);
insert into products (name, count, price) VALUES ('product_11', 11, 55);
insert into products (name, count, price) VALUES ('product_12', 12, 60);
insert into products (name, count, price) VALUES ('product_13', 13, 65);
insert into products (name, count, price) VALUES ('product_14', 14, 70);
insert into products (name, count, price) VALUES ('product_15', 15, 75);
insert into products (name, count, price) VALUES ('product_16', 16, 80);
insert into products (name, count, price) VALUES ('product_17', 17, 85);
insert into products (name, count, price) VALUES ('product_18', 18, 90);
insert into products (name, count, price) VALUES ('product_19', 19, 95);
insert into products (name, count, price) VALUES ('product_20', 20, 100);

begin;

/* Объявление курсора */
DECLARE
    curs_prod scroll cursor for
        select * from products;

/* Переход в конец таблицы */
fetch last from curs_prod;

/* перемещение вверх по таблице на 15 строку */
move backward 5 from curs_prod;

/* перемещение вверх по таблице на 7 строку */
move backward 8 from curs_prod;

/* перемещение вверх по таблице на 2 строку */
move backward 5 from curs_prod;

/* перемещение вверх по таблице на 1 строку */
move backward from curs_prod;

/* Закрытие курсора */
CLOSE curs_prod;

/* Завершение транзакции */

commit;