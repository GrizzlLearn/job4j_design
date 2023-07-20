INSERT INTO roles_table(role_name) VALUES ('role_name_0');
INSERT INTO roles_table(role_name) VALUES ('role_name_1');

INSERT INTO rules_table(rule_name) VALUES ('rule_name_0');
INSERT INTO rules_table(rule_name) VALUES ('rule_name_1');

INSERT INTO categories_table(category_name) VALUES ('category_name_0');
INSERT INTO categories_table(category_name) VALUES ('category_name_1');

INSERT INTO states_table(state_name) VALUES ('state_name_0');
INSERT INTO states_table(state_name) VALUES ('state_name_1');

INSERT INTO roles_rules_table(role_name, rule_name)
SELECT s1.role_name, s2.rule_name
FROM roles_table s1
INNER JOIN rules_table s2 ON s2.rule_id = s1.role_id;

INSERT INTO items_table(item_key, item_summary, item_description)
VALUES ('TEST-1', 'TEST SUMMARY 1', 'TEST DESCRIPTION 1');

INSERT INTO items_table(item_key, item_summary, item_description)
VALUES ('TEST-2', 'TEST SUMMARY 2', 'TEST DESCRIPTION 2');

/*
 Не сообразил как за одну транзакцию сделать VALUES и SELECT, поэтому
 использовал обновление
 */
UPDATE items_table
SET item_category = (SELECT category_name FROM categories_table WHERE category_name = 'category_name_0'),
    item_status = (SELECT state_name FROM states_table WHERE state_name = 'state_name_1')
WHERE item_key = 'TEST-1';

UPDATE items_table
SET item_category = (SELECT category_name FROM categories_table WHERE category_name = 'category_name_1'),
    item_status = (SELECT state_name FROM states_table WHERE state_name = 'state_name_0')
WHERE item_key = 'TEST-2';

INSERT INTO users_table(user_name)
VALUES ('user_name_0');

UPDATE users_table
SET role_name = (SELECT role_name FROM roles_table WHERE role_name = 'role_name_1'),
    item_key = (SELECT item_key FROM items_table WHERE item_id = 1 )
WHERE user_name = 'user_name_0';

INSERT INTO users_table(user_name)
VALUES ('user_name_1');

UPDATE users_table
SET role_name = (SELECT role_name FROM roles_table WHERE role_name = 'role_name_0'),
    item_key = (SELECT item_key FROM items_table WHERE item_id = 2 )
WHERE user_name = 'user_name_1';

INSERT INTO comments_table(comment_body)
VALUES ('COMMENT BODY 0');

UPDATE comments_table
SET item_id = (SELECT item_id FROM items_table WHERE item_key = 'TEST-1')
WHERE comment_body = 'COMMENT BODY 0';

INSERT INTO comments_table(comment_body)
VALUES ('COMMENT BODY 1');

UPDATE comments_table
SET item_id = (SELECT item_id FROM items_table WHERE item_key = 'TEST-2')
WHERE comment_body = 'COMMENT BODY 1';

INSERT INTO attaches_table(item_id)
SELECT s1.item_id
FROM items_table s1
WHERE item_id = 1;

select * from attaches_table;
