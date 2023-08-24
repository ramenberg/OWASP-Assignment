INSERT INTO roles VALUES (1, 'admin');
INSERT INTO roles VALUES (2, 'user');
INSERT INTO users VALUES (1, 'admin@test.se', 'admin', 'admin', '1234');
INSERT INTO users VALUES (2, 'pelle@test.se', 'Pelle', 'Larsson', '1234');
INSERT INTO users_roles VALUES (1, 1);
INSERT INTO users_roles VALUES (2, 2);