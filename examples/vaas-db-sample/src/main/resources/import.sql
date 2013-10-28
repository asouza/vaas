--alter table User_Role drop constraint FK_tc5k40i3kit8944syrd366vy1
--alter table User_Role drop constraint FK_dv4w2xni693cg4ibi3fo1fkk6
--
--drop table Role if exists;
--drop table User if exists;
--drop table User_Role if exists;
--drop table URLAccessRole if exists;
--drop table URLAccessRole_Role if exists;

--create table Role (id bigint generated by default as identity (start with 1), name varchar(255), primary key (id));
--create table User (id bigint generated by default as identity (start with 1), login varchar(255), userName varchar(255), password varchar(255), primary key (id));
--create table User_Role (User_id bigint not null, roles_id bigint not null);
--create table URLAccessRole (id bigint generated by default as identity (start with 1), url varchar(255), primary key (id));
--create table URLAccessRole_Role (URLAccessRole_id bigint not null, allowedRoles_id bigint not null);

--alter table User_Role add constraint FK_tc5k40i3kit8944syrd366vy1 foreign key (roles_id) references Role;
--alter table User_Role add constraint FK_dv4w2xni693cg4ibi3fo1fkk6 foreign key (User_id) references User;
--create table URLAccessRole (id bigint generated by default as identity (start with 1), url varchar(255), primary key (id));
--create table URLAccessRole_Role (URLAccessRole_id bigint not null, allowedRoles_id bigint not null);

insert into Role (id, name) values (default, 'ROLE_ADMIN'); --1
insert into Role (id, name) values (default, 'ROLE_USER'); --2
insert into User (id, login, userName, password) values (default, 'admin', 'The Admin', 'admin'); --1
insert into User (id, login, userName, password) values (default, 'user', 'The User', 'user'); --2

insert into User_Role (User_id, roles_id) values (1, 1);
insert into User_Role (User_id, roles_id) values (1, 2);
insert into User_Role (User_id, roles_id) values (2, 2);

insert into URLAccessRole(id, url) values(default,'/main');       --1
insert into URLAccessRole(id, url) values(default,'/user-page');  --2
insert into URLAccessRole(id, url) values(default,'/admin-page'); --3

insert into URLAccessRole_Role(URLAccessRole_id,allowedRoles_id) values(1,1);
insert into URLAccessRole_Role(URLAccessRole_id,allowedRoles_id) values(1,2);
insert into URLAccessRole_Role(URLAccessRole_id,allowedRoles_id) values(2,1);
insert into URLAccessRole_Role(URLAccessRole_id,allowedRoles_id) values(2,2);
insert into URLAccessRole_Role(URLAccessRole_id,allowedRoles_id) values(3,2);
