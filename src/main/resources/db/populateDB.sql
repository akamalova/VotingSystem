DELETE FROM USER_ROLES;
DELETE FROM USERS_VOTE;
DELETE FROM MENU;
DELETE FROM USERS;
DELETE FROM RESTAURANTS;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO USERS (NAME, EMAIL) VALUES
  ('User', 'user@yandex.ru'),
  ('AnUser', 'anuser@yandex.ru'),
  ('Admin', 'admin@gmail.com');

INSERT INTO USER_ROLES (ROLE, USER_ID) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_USER', 100002),
  ('ROLE_ADMIN', 100002);

INSERT INTO RESTAURANTS(NAME, DATE_TIME, DESCRIPTION) VALUES
  ('FirstRestaurant', '2015-05-30 10:00:00', 'Swedish'),
  ('SecondRestaurant', '2015-05-30 10:00:00', 'Asian'),
  ('ThirdRestaurant', '2015-05-30 10:00:00', 'Russian'),
  ('FourthRestaurant', '2015-05-30 10:00:00', 'Japanese'),
  ('FifthRestaurant', '2015-05-30 10:00:00', 'Uzbek');

INSERT INTO MENU(NAME, PRICE, RESTAURANT_ID) VALUES
  ('Soup', '25.30', 100003),
  ('Meat', '60.50', 100003),
  ('Chicken', '15.90', 100004),
  ('bacon and eggs', '1.50', 100004),
  ('biscuits', '3.00', 100005),
  ('cauliflower', '120.60', 100005),
  ('chips', '15.3', 100005),
  ('eel', '26.70', 100006),
  ('fresh milk', '7.60', 100007),
  ('baked beans', '10.90', 100007);

INSERT INTO USERS_VOTE(USER_ID, DATE_TIME, RESTAURANT_ID) VALUES
  (100000, '2015-05-30 10:00:00', 100003),
  (100000, '2015-05-29 10:00:00', 100005),
  (100001, '2015-05-30 10:00:00', 100003),
  (100001, '2015-05-29 10:00:00', 100004),
  (100001, '2015-05-28 10:00:00', 100007),
  (100002, '2015-05-30 10:00:00', 100004),
  (100002, '2015-05-29 10:00:00', 100003),
  (100002, '2015-05-28 10:00:00', 100007);


