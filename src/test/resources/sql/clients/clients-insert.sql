insert into users (id, username, password, role) values (100, 'ted@gmail.com', '$2a$12$QmknttCEFcDrNFN/UqeI/.6S3E2do2QTEJ3Z6nM03U9rnDcpA/yzK', 'ROLE_CLIENT');
insert into users (id, username, password, role) values (200, 'barney@gmail.com', '$2a$12$y2NiNLrctZXCazF.M.KZf.KKCjcI4.ZAl78CqMYmsgvYEWY4Xednm', 'ROLE_ADMIN');
insert into users (id, username, password, role) values (150, 'robindcanadian@gmail.com', '$2a$12$X1/tQwVnxzQWBbCE5Ca5w.JgEF6FmtVhdPadQZxprl9goy3C6xCiG', 'ROLE_CLIENT');
insert into users (id, username, password, role) values (99, 'lily@gmail.com', '$2a$12$8kLHD91SqIAC1GE8q2I3H.q1Ww6Wvek0PBMWQ3gCKBbKuYWFliSOa', 'ROLE_CLIENT');

insert into clients (id, name, cpf, id_user) values (10, 'Ted Mosby', '65712430088', 100);
insert into clients (id, name, cpf, id_user) values (15, 'Robin Scherbatsky', '34560737045', 150);