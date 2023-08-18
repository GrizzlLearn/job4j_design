create table products (
    id serial primary key,
    name varchar(50),
    producer varchar(50),
    count integer default 0,
    price integer
);

/* Процедура */

create or replace procedure delete_data(max_price integer)
    language 'plpgsql'
as $$
declare
    inner_max_price int;
BEGIN
    select MAX(price) into inner_max_price from products;
    if inner_max_price > max_price THEN
        delete from products WHERE price = inner_max_price;
    end if;
END;
$$;

call delete_data(120);

/* Функция */

create or replace function f_delete_data(max_price integer)
    returns integer
    language 'plpgsql'
as
$$
declare
    result integer;
    inner_max_price int;
begin
    select MAX(price) into inner_max_price from products;
    if inner_max_price > max_price THEN
        result = inner_max_price;
        delete from products where price = inner_max_price;
    end if;
    return result;
end;
$$;

select f_delete_data(155);
