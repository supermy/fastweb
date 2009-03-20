insert into c_users (c_name,c_passwd,email,c_enabled,accountNonExpired,credentialsNonExpired,accountNonLocked) values('Admin','e10adc3949ba59abbe56
e057f20f883e','springclick@gmail.com',1,1,1,1);
insert into c_users (c_name,c_passwd,email,c_enabled,accountNonExpired,credentialsNonExpired,accountNonLocked) values('User','e10adc3949ba59abbe56
e057f20f883e','user@super.com',1,1,1,1);
insert into c_users (c_name,c_passwd,email,c_enabled,accountNonExpired,credentialsNonExpired,accountNonLocked) values('cat','e10adc3949ba59abbe56
e057f20f883e','cat@super.com',1,1,1,1);
insert into c_users (c_name,c_passwd,email,c_enabled,accountNonExpired,credentialsNonExpired,accountNonLocked) values('dog','e10adc3949ba59abbe56
e057f20f883e','dog@super.com',1,1,1,1);
insert into c_users (c_name,c_passwd,email,c_enabled,accountNonExpired,credentialsNonExpired,accountNonLocked) values('fish','e10adc3949ba59abbe56
e057f20f883e','fish@super.com',1,1,1,1);
insert into c_users (c_name,c_passwd,email,c_enabled,accountNonExpired,credentialsNonExpired,accountNonLocked) values('pig','e10adc3949ba59abbe56
e057f20f883e','pig@super.com',1,1,1,1);


insert into c_roles (NAME,c_enabled) values('管理员',1);
insert into c_roles (NAME,c_enabled) values('用户',1);

insert into c_authors (c_name,c_nickname,c_enabled) values('AUTH_VIEW_USER','用户',1);
insert into c_authors (c_name,c_nickname,c_enabled) values('AUTH_MODIFY_USER','用户修改',1);
insert into c_authors (c_name,c_nickname,c_enabled) values('AUTH_VIEW_ROLE','角色',1);
insert into c_authors (c_name,c_nickname,c_enabled) values('AUTH_MODIFY_ROLE','角色修改',1);

insert into c_user_roles values(1,1);
insert into c_user_roles values(1,2);
insert into c_user_roles values(2,2);

insert into c_user_roles values(3,2);
insert into c_user_roles values(4,2);
insert into c_user_roles values(5,2);
insert into c_user_roles values(6,2);

insert into c_role_auths values(1,1);
insert into c_role_auths values(1,2);
insert into c_role_auths values(1,3);
insert into c_role_auths values(1,4);
insert into c_role_auths values(2,1);
insert into c_role_auths values(2,3);
