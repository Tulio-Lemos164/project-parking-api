insert into users (id, username, password, role) values (100, 'ted@gmail.com', '$2a$12$QmknttCEFcDrNFN/UqeI/.6S3E2do2QTEJ3Z6nM03U9rnDcpA/yzK', 'ROLE_CLIENT');
insert into users (id, username, password, role) values (200, 'barney@gmail.com', '$2a$12$y2NiNLrctZXCazF.M.KZf.KKCjcI4.ZAl78CqMYmsgvYEWY4Xednm', 'ROLE_ADMIN');
insert into users (id, username, password, role) values (150, 'robindcanadian@gmail.com', '$2a$12$X1/tQwVnxzQWBbCE5Ca5w.JgEF6FmtVhdPadQZxprl9goy3C6xCiG', 'ROLE_CLIENT');
insert into users (id, username, password, role) values (99, 'lily@gmail.com', '$2a$12$8kLHD91SqIAC1GE8q2I3H.q1Ww6Wvek0PBMWQ3gCKBbKuYWFliSOa', 'ROLE_CLIENT');

insert into clients (id, name, cpf, id_user) values (10, 'Ted Mosby', '65712430088', 100);
insert into clients (id, name, cpf, id_user) values (15, 'Robin Scherbatsky', '34560737045', 150);

insert into spaces (id, code, status) values (1000, 'A-01', 'OCCUPIED');
insert into spaces (id, code, status) values (2000, 'A-02', 'OCCUPIED');
insert into spaces (id, code, status) values (3000, 'A-03', 'OCCUPIED');
insert into spaces (id, code, status) values (4000, 'A-04', 'FREE');
insert into spaces (id, code, status) values (5000, 'A-05', 'FREE');

insert into clients_has_spaces (receipt_number, license_plate, brand, model, color, entry_date, id_client, id_space) values ('20230313-101300', 'FIT-1020', 'FIAT', 'PALIO', 'GREEN', '2023-03-13 10:15:00', 10, 1000);
insert into clients_has_spaces (receipt_number, license_plate, brand, model, color, entry_date, id_client, id_space) values ('20230314-101400', 'SIE-1020', 'FIAT', 'SIENA', 'WHITE', '2023-03-14 10:15:00', 15, 2000);
insert into clients_has_spaces (receipt_number, license_plate, brand, model, color, entry_date, id_client, id_space) values ('20230315-101500', 'FIT-1020', 'FIAT', 'PALIO', 'GREEN', '2023-03-14 10:15:00', 10, 3000);