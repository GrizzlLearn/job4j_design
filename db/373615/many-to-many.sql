drop database if exists many_to_many;

create database many_to_many;

drop table if exists product_category;
drop table if exists product;
drop table if exists category;


create table category(
    category_id serial primary key,
    category_name char(255) unique
);

create table product(
    product_id serial primary key,
    product_name char(255),
    product_price char(255)
);

create table product_category (
    id serial primary key,
    category_name char(255) references category(category_name),
    product_id int references product(product_id)
);

insert into category(category_name) VALUES ('category_name_0');
insert into product(product_name, product_price) VALUES ('product_name_0', 'product_price_0');

insert into product_category(category_name, product_id)
SELECT s1.category_name, s2.product_id
FROM category s1
INNER JOIN product s2 ON s2.product_id = s1.category_id;

select * from product_category;
