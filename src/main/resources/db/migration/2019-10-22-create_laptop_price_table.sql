CREATE TABLE IF NOT EXISTS laptop_price(
id BIGINT NOT NULL PRIMARY KEY,
laptop_code VARCHAR(150) NOT NULL,
currency VARCHAR(40),
price BIGINT,
FOREIGN KEY (laptop_code) REFERENCES laptop(code);