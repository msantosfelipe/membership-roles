CREATE TABLE IF NOT EXISTS roles(
   id           UUID PRIMARY KEY,
   role_code    VARCHAR(255) UNIQUE NOT NULL,
   name         VARCHAR(255) NOT NULL
);
