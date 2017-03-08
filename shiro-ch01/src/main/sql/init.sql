create database shiro_au;

use shiro_au;

create table sys_user(
  id bigint auto_increment,
  user_name varchar(32),
  password varchar(32),
  salt varchar(32),
  locked bool default false,
  constraint pk_sys_user primary key(id)
)ENGINE=InnoDB CHARSET=utf8 comment = '用户信息表'


create table sys_role(
  id bigint auto_increment,
  role varchar(32),
  description varchar(32),
  available bool default false,
  primary key(id)
)ENGINE=InnoDB CHARSET=utf8 comment='角色信息表'

create table sys_permission(
    id bigint auto_increment,
    permission varchar(32),
    descption varchar(32),
    available bool default false,
    primary key(id)
)ENGINE=InnoDB CHARSET=utf8 comment='权限信息表'


create table sys_user_role(
  user_id bigint,
  role_id bigint,
  PRIMARY key(user_id,role_id)
)ENGINE=InnoDB CHARSET=utf8 comment='用户角色信息表'

create table sys_role_permission(
  role_id bigint ,
  permission_id bigint,
  primary key(role_id,permission_id)
)ENGINE=InnoDB CHARSET=utf8 comment='角色权限信息表'