CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL
);

CREATE TABLE users_roles (
                             user_id BIGINT,
                             role_id BIGINT,
                             FOREIGN KEY (user_id) REFERENCES users(id),
                             FOREIGN KEY (role_id) REFERENCES roles(id)
);
