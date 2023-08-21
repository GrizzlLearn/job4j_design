create table products_2 (
    id serial primary key,
    name varchar(50),
    producer varchar(50),
    count integer default 0,
    price integer
);

insert into products_2 (name, producer, count, price) VALUES ('product_1', 'producer_1', 3, 50);
insert into products_2 (name, producer, count, price) VALUES ('product_2', 'producer_2', 15, 32);
insert into products_2 (name, producer, count, price) VALUES ('product_3', 'producer_3', 8, 115);

begin transaction;
insert into products_2 (name, producer, count, price) VALUES ('product_4', 'producer_4', 11, 64);
commit transaction;
select * from products_2;


/* Rollback */

begin transaction;
delete from products_2;
drop table products_2;
rollback transaction;
select * from products_2;

/* Savepoint 0 */

begin transaction;
insert into products_2 (name, producer, count, price) VALUES ('product_5', 'producer_5', 17, 45);
savepoint first_savepoint;
delete from products_2 where price = 115;
update products_2 set price = 75 where name = 'product_1';
savepoint second_savepoint;
insert into products_2 (name, producer, count, price) VALUES ('product_6', 'producer_6', 110, 640);
select * from products_2;
rollback to second_savepoint;
select * from products_2;
rollback to first_savepoint;
select * from products_2;
rollback transaction;

/* Savepoint 1 */

begin transaction;
insert into products_2 (name, producer, count, price) VALUES ('product_5', 'producer_5', 17, 45);
savepoint first_savepoint;
delete from products_2 where price = 115;
update products_2 set price = 75 where name = 'product_1';
savepoint second_savepoint;
insert into products_2 (name, producer, count, price) VALUES ('product_6', 'producer_6', 110, 640);
select * from products_2;
rollback to first_savepoint;
select * from products_2;
rollback to second_savepoint;
/*
ERROR:  savepoint "second_savepoint" does not exist
*/
select * from products_2;
/*
 ERROR:  current transaction is aborted, commands ignored until end of transaction block
*/
rollback transaction;
