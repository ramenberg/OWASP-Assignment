INSERT INTO roles (name) VALUES ('admin');
INSERT INTO roles (name) VALUES ('user');
INSERT INTO users (email, firstname, lastname, password) VALUES ('admin@test.se', 'admin', 'admin', '$2a$10$tlAz96z/HBvv3Jk4nLlGfe.v6k/h38DIHJBxn6REX5B2CqicomDR2');
INSERT INTO users (email, firstname, lastname, password) VALUES ('user@test.se', 'user', 'user', '$2a$10$tlAz96z/HBvv3Jk4nLlGfe.v6k/h38DIHJBxn6REX5B2CqicomDR2');
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);