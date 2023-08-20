drop database if exists triggers;

create database triggers;

drop table if exists products cascade;

create table public.products (
    id serial primary key,
    name varchar(50),
    producer varchar(50),
    count integer default 0,
    price integer
);

insert into products (name, producer, count, price) VALUES ('product_3', 'producer_3', 8, 115);

insert into products (name, producer, count, price) VALUES ('product_1', 'producer_1', 3, 50);

/* 1) Триггер срабатывает после вставки данных, для любого товара и просто насчитывать налог на товар */

create or replace function apply_tax_after()
    returns trigger as
$$
BEGIN
    update products
    set price = price * 1.2
    where id in (select id from inserted) and count <= 5;
    return new;
END;
$$
    LANGUAGE 'plpgsql';

create trigger tax_trigger
    after insert on products
    referencing new table as inserted
    for each statement
execute procedure apply_tax_after();

/* 2) Триггер срабатывает до вставки данных и насчитывать налог на товар */

create or replace function apply_tax_before()
    returns trigger as
$$
BEGIN
    NEW.price = NEW.price * 1.2;
    return new;
END;
$$
    LANGUAGE 'plpgsql';

create trigger tax_trigger_before
    before insert on products
    for each row
execute procedure apply_tax_before();

/* Написать триггер на row уровне, который сразу после вставки продукта в таблицу products,
   будет заносить имя, цену и текущую дату в таблицу history_of_price */

create table history_of_price (
    id serial primary key,
    name varchar(50),
    price integer,
    date timestamp
);

create or replace function update_history_of_price()
    returns trigger as
$$
BEGIN
    insert into history_of_price(name, price, date)
    select p.name, p.price, now()
    from products as p
    where id = (select id from inserted);
    return new;
END;
$$
    LANGUAGE 'plpgsql';

create or replace trigger tax_trigger_before
    after insert on products
    referencing new table as inserted
    for each row
execute procedure update_history_of_price();
