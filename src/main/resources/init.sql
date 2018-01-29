create table rbk_role
(
	id bigint not null auto_increment
		primary key,
	name varchar(32) null,
	code varchar(32) null
)
;

create table rbk_role_permission
(
	id bigint not null auto_increment
		primary key,
	role_id bigint null,
	permission int null,
	module varchar(32) null
)
;

create table rbk_user
(
	id bigint not null
		primary key,
	name varchar(32) null,
	password varchar(32) null,
	create_time datetime null,
	salt varchar(128) null,
	mobile_phone varchar(32) null
)
;

create table rbk_user_role
(
	id bigint not null auto_increment
		primary key,
	role_id bigint null,
	user_id bigint null
)
;

