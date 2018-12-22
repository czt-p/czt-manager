-- 资助政策表 --
create table t_subsidy_policy(id int not null,name varchar(250) not null,region varchar(50) not null,register_department varchar(250) not null,content varchar(2048) not null,create_time datetime,modify_time datetime,primary key (id));
insert into t_id_generator(id_key,id_value) values ('subsidyPolicy',100);