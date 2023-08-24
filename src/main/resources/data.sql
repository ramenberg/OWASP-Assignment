INSERT INTO roles VALUES (1, 'admin');
INSERT INTO roles VALUES (2, 'user');
INSERT INTO users (email, firstname, lastname, password) VALUES ('admin@test.se', 'admin', 'admin', '1234');
INSERT INTO users (email, firstname, lastname, password) VALUES ('pelle@test.se', 'Pelle', 'Larsson', '1234');
INSERT INTO users_roles VALUES (1, 1);
INSERT INTO users_roles VALUES (2, 2);