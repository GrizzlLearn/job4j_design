INSERT INTO roles_table(role_name) VALUES ('role_name_0');
INSERT INTO roles_table(role_name) VALUES ('role_name_1');

INSERT INTO rules_table(rule_name) VALUES ('rule_name_0');
INSERT INTO rules_table(rule_name) VALUES ('rule_name_1');

INSERT INTO categories_table(category_name) VALUES ('category_name_0');
INSERT INTO categories_table(category_name) VALUES ('category_name_1');

INSERT INTO states_table(state_name) VALUES ('state_name_0');
INSERT INTO states_table(state_name) VALUES ('state_name_1');

INSERT INTO roles_rules_table(role_id, rule_id)
SELECT s1.role_id, s2.rule_id
FROM roles_table s1
INNER JOIN rules_table s2 ON s2.rule_id = s1.role_id;

INSERT INTO users_table(user_name)
VALUES ('user_name_0');

UPDATE users_table
SET role_id = (SELECT role_id FROM roles_table WHERE role_name = 'role_name_1')
WHERE user_name = 'user_name_0';

INSERT INTO users_table(user_name)
VALUES ('user_name_1');

UPDATE users_table
SET role_id = (SELECT role_id FROM roles_table WHERE role_name = 'role_name_0')
WHERE user_name = 'user_name_1';

INSERT INTO items_table(item_key, item_summary, item_description)
VALUES ('TEST-1', 'TEST SUMMARY 1', 'TEST DESCRIPTION 1');

INSERT INTO items_table(item_key, item_summary, item_description)
VALUES ('TEST-2', 'TEST SUMMARY 2', 'TEST DESCRIPTION 2');

UPDATE items_table
SET item_category = (SELECT category_id FROM categories_table WHERE category_name = 'category_name_0'),
    item_status = (SELECT state_id FROM states_table WHERE state_name = 'state_name_1'),
    user_id = (SELECT user_id FROM users_table WHERE user_name = 'user_name_0')
WHERE item_key = 'TEST-1';

UPDATE items_table
SET item_category = (SELECT category_id FROM categories_table WHERE category_name = 'category_name_1'),
    item_status = (SELECT state_id FROM states_table WHERE state_name = 'state_name_0'),
    user_id = (SELECT user_id FROM users_table WHERE user_name = 'user_name_1')
WHERE item_key = 'TEST-2';

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
