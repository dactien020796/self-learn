insert into role(id, name, description, created_date, modified_date) value (1, 'ROLE_ADMIN', 'Administrator role of the system', '2021-11-19 14:38:56', '2021-11-19 14:38:56');
insert into role(id, name, description, created_date, modified_date) value (2, 'ROLE_SELLER', 'Seller', '2021-11-19 14:38:56', '2021-11-19 14:38:56');
insert into role(id, name, description, created_date, modified_date) value (3, 'ROLE_BUYER', 'Buyer', '2021-11-19 14:38:56', '2021-11-19 14:38:56');

-- Default ADMIN account of the system, password = 0207
insert INTO user (id, created_date, modified_date, full_name, password, username) VALUES (1, '2021-11-19 15:00:52', '2021-11-19 15:00:52', 'Tien Le', '$2a$10$kjkn0NhkN9KKEWYH2gM6n.UWQyWz3tNkbYtyrCUoX4nJiuJbSLtEu', 'tienle020796');

insert INTO user_role (user_id, role_id) VALUES (1, 1);