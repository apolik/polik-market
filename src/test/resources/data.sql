INSERT INTO shop_unit(id, date, name, price, type, parent_id)
VALUES
    ('069cb8d7-bbdd-47d3-ad8f-82ef4c269df1', '2022-02-03T15:00:00.00', 'Товары', null, 'CATEGORY', null),
    ('d515e43f-f3f6-4471-bb77-6b455017a2d2', '2022-02-02T12:00:00.00', 'Смартфоны', null, 'CATEGORY', '069cb8d7-bbdd-47d3-ad8f-82ef4c269df1'),
    ('1cc0129a-2bfe-474c-9ee6-d435bf5fc8f2', '2022-02-03T15:00:00.00', 'Телевизоры', null, 'CATEGORY', '069cb8d7-bbdd-47d3-ad8f-82ef4c269df1'),
    ('863e1a7a-1304-42ae-943b-179184c077e3', '2022-02-02T12:00:00.00', 'jPhone 13', 79999, 'OFFER', 'd515e43f-f3f6-4471-bb77-6b455017a2d2'),
    ('b1d8fd7d-2ae3-47d5-b2f9-0f094af800d4', '2022-02-02T12:00:00.00', 'Xomiа Readme 10', 59999, 'OFFER', 'd515e43f-f3f6-4471-bb77-6b455017a2d2'),
    ('98883e8f-0507-482f-bce2-2fb306cf6483', '2022-02-03T12:00:00.00', 'Samson 70" LED UHD Smart', 32999, 'OFFER', '1cc0129a-2bfe-474c-9ee6-d435bf5fc8f2'),
    ('74b81fda-9cdc-4b63-8927-c978afed5cf4', '2022-02-03T12:00:00.00', 'Phyllis 50" LED UHD Smarter', 49999, 'OFFER', '1cc0129a-2bfe-474c-9ee6-d435bf5fc8f2'),
    ('73bc3b36-02d1-4245-ab35-3106c9ee1c65', '2022-02-03T15:00:00.00', 'Goldstar 65" LED UHD LOL Very Smart', 69999, 'OFFER', '1cc0129a-2bfe-474c-9ee6-d435bf5fc8f2');