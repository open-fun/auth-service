CREATE TABLE users (
  display_name        VARCHAR(256),
  email               VARCHAR(256) PRIMARY KEY,
  password            VARCHAR(256) NOT NULL,
  enabled             BOOLEAN DEFAULT TRUE,
  account_expired     BOOLEAN DEFAULT FALSE,
  credentials_expired BOOLEAN DEFAULT FALSE,
  account_locked      BOOLEAN DEFAULT FALSE
);

CREATE TABLE user_authorities (
  user_email  VARCHAR(256) NOT NULL,
  authorities VARCHAR(256) NOT NULL,
  CONSTRAINT users_authorities_uq UNIQUE (user_email, authorities)
);