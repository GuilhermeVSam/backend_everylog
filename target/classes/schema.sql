CREATE TABLE IF NOT EXISTS USERS (
    username VARCHAR(255) NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

INSERT INTO USERS (username, email, password)
VALUES ('username', 'username@email.com', '$2b$12$8yBzPonipwEoVS0XU1MoluINazqu/eWPPdMMQ/5z.yy16R3MHiDWa')