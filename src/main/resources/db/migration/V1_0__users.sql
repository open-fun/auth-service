CREATE TABLE users (
  display_name VARCHAR(256),
  email        VARCHAR(256) PRIMARY KEY,
  password     VARCHAR(256),
  enabled      BOOLEAN
);

CREATE TABLE authorities (
  email     VARCHAR(256),
  authority VARCHAR(256),
  PRIMARY KEY (email, authority)
);