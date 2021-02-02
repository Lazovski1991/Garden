DELETE FROM user_roles;
DELETE FROM fertilizer;
DELETE FROM users;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@gmail.com', 'password'),
       ('Admin', 'admin@gmail.com', 'password');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2);

INSERT INTO fertilizer (date_time, name, status, user_id)
VALUES ('2020-05-30', 'красные', true, 1),
       ('2020-06-08', 'синие', false, 1),
       ('2020-07-04', 'зеленые', false, 1),
       ('2020-05-30', 'админ зеленые', true, 2),
       ('2020-06-03', 'админ красные', false, 2),
       ('2020-07-04', 'админ синие', false, 2)