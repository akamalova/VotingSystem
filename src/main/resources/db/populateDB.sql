DELETE FROM USER_ROLES;
DELETE FROM USERS_VOTE;
DELETE FROM MENU;
DELETE FROM DISHES;
DELETE FROM USERS;
DELETE FROM RESTAURANTS;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO USERS (NAME, EMAIL, PASSWORD, REGISTERED) VALUES
  ('User', 'user@yandex.ru', '{noop}password', '2015-05-30'),
  ('AnUser', 'anuser@yandex.ru', '{noop}password', '2014-05-30'),
  ('Admin', 'admin@gmail.com', '{noop}password', '2013-05-30');

INSERT INTO USER_ROLES (ROLE, USER_ID) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_USER', 100002),
  ('ROLE_ADMIN', 100002);

INSERT INTO RESTAURANTS(NAME, DESCRIPTION) VALUES
  ('FirstRestaurant', 'Swedish'),
  ('SecondRestaurant', 'Asian'),
  ('ThirdRestaurant', 'Russian'),
  ('FourthRestaurant', 'Japanese'),
  ('FifthRestaurant', 'Uzbek');

INSERT INTO MENU(DATE, RESTAURANT_ID) VALUES
  ('2017-05-30 10:00:00', 100003),
  ('2015-05-30 10:00:00', 100003),
  ('2015-05-30 10:00:00', 100004),
  ('2015-05-30 10:00:00', 100005),
  ('2015-05-30 10:00:00', 100006),
  ('2015-05-30 10:00:00', 100007);

INSERT INTO DISHES (NAME, PRICE, MENU_ID) VALUES
  ('Soup', '25.30', 100008),
  ('Meat', '60.50', 100008),
  ('Chicken', '15.90', 100009),
  ('bacon and eggs', '1.50', 100010),
  ('biscuits', '3.00', 100010),
  ('cauliflower', '120.60', 100011),
  ('chips', '15.3', 100011),
  ('eel', '26.70', 100012),
  ('fresh milk', '7.60', 100013),
  ('baked beans', '10.90', 100013);

INSERT INTO USERS_VOTE(USER_ID, DATE, RESTAURANT_ID) VALUES
  (100000, '2015-05-30 10:00:00', 100003),
  (100000, '2015-05-29 10:00:00', 100005),
  (100001, '2015-05-30 10:00:00', 100003),
  (100001, '2015-05-29 10:00:00', 100004),
  (100001, '2015-05-28 10:00:00', 100007),
  (100002, '2015-05-30 10:00:00', 100004),
  (100002, '2015-05-29 10:00:00', 100003),
  (100002, '2015-05-28 10:00:00', 100007);


