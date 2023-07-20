drop database if exists one_to_one;

create database one_to_one;

drop table if exists pictures_owners;
drop table if exists owners;
drop table if exists pictures;


create table pictures(
    pictures_id serial primary key,
    title char(255),
    inventory_id int unique
);

create table owners(
    owners_id serial primary key,
    pseudonym char(255)
);

create table pictures_owners (
    pictures_inventory_id int references pictures(inventory_id) unique ,
    owners_id int references owners(owners_id) unique
);

INSERT INTO pictures(title, inventory_id) VALUES ('picture_name_0', 123456);
INSERT INTO owners(pseudonym) VALUES ('owner_pseudonym_0');

INSERT INTO pictures_owners(pictures_inventory_id, owners_id)
SELECT s1.inventory_id, s2.owners_id
FROM pictures s1
INNER JOIN owners s2 ON s2.owners_id = s1.pictures_id;

select * from pictures_owners;
