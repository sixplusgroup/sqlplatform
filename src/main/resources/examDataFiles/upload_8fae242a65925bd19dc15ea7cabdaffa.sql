create table address_data
(
    name    varchar(255) not null,
    address text         null,
    constraint address_data_name_uindex
        unique (name)
);

INSERT INTO sqlexamexec.address_data (name, address) VALUES ('Goofy', '2543  Stratford Park');
INSERT INTO sqlexamexec.address_data (name, address) VALUES ('Jerry', '3412  Colonial Drive');
INSERT INTO sqlexamexec.address_data (name, address) VALUES ('Tom', '4458  Hinkle Deegan Lake Road');

create table blank_data
(
    id     int auto_increment
        primary key,
    amount double default 0 null,
    name   varchar(255)     not null
);

INSERT INTO sqlexamexec.blank_data (id, amount, name) VALUES (1, 100.5, 'Tom');
INSERT INTO sqlexamexec.blank_data (id, amount, name) VALUES (2, 23.6, 'Jerry');
INSERT INTO sqlexamexec.blank_data (id, amount, name) VALUES (3, 20, 'Goofy');