CREATE TABLE items.laptop_price(
id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
date DATE,
laptop_code VARCHAR(150),
currency VARCHAR(40),
price BIGINT(20));