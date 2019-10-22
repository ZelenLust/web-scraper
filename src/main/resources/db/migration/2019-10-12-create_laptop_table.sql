CREATE TABLE IF NOT EXISTS laptop(
  id BIGINT NOT NULL PRIMARY KEY,
  processor VARCHAR(70),
  screen_diagonal VARCHAR(25),
  amount_of_ram VARCHAR(25),
  amount_of_cores TINYINT(2),
  hdd VARCHAR(25),
  ssd VARCHAR(25),
  url VARCHAR(1000),
  code VARCHAR(150) UNIQUE NOT NULL,
  name VARCHAR(500);
