drop table if exists roles_rules_table;
drop table if exists items_table CASCADE;
drop table if exists users_table CASCADE;
drop table if exists comments_table;
drop table if exists attaches_table;
drop table if exists states_table;
drop table if exists categories_table;
drop table if exists rules_table CASCADE;
drop table if exists roles_table CASCADE;


CREATE TABLE roles_table(
    role_id serial PRIMARY KEY,
    role_name char(255) UNIQUE
);

CREATE TABLE rules_table(
    rule_id serial PRIMARY KEY,
    rule_name char(255) UNIQUE
);

CREATE TABLE categories_table(
    category_id serial PRIMARY KEY,
    category_name char(255) UNIQUE
);

CREATE TABLE states_table(
    state_id serial PRIMARY KEY,
    state_name char(255) UNIQUE
);

CREATE TABLE roles_rules_table(
    role_id int REFERENCES roles_table(role_id),
    rule_id int REFERENCES rules_table(rule_id)
);

CREATE TABLE users_table(
    user_id serial PRIMARY KEY,
    user_name char(255) UNIQUE,
    role_id int REFERENCES roles_table(role_id)
);

CREATE TABLE items_table(
    item_id serial PRIMARY KEY,
    item_key char(255) UNIQUE,
    item_summary VARCHAR,
    item_description TEXT,
    item_category int REFERENCES categories_table(category_id),
    item_status int REFERENCES states_table(state_id),
    user_id int REFERENCES users_table(user_id)
);

CREATE TABLE comments_table(
    comment_id serial PRIMARY KEY,
    comment_body TEXT,
    item_id int REFERENCES items_table(item_id)
);

CREATE TABLE attaches_table(
    attach_id serial PRIMARY KEY,
    item_id int REFERENCES items_table(item_id)
);
