insert into _users (_name,_passwd,email,_enabled,accountNonExpired,credentialsNonExpired,accountNonLocked) values('Admin','e10adc3949ba59abbe56e057f20f883e','springclick@gmail.com',1,1,1,1);
insert into _users (_name,_passwd,email,_enabled,accountNonExpired,credentialsNonExpired,accountNonLocked) values('User','e10adc3949ba59abbe56e057f20f883e','user@super.com',1,1,1,1);

insert into _users (_name,_passwd,email,_enabled,accountNonExpired,credentialsNonExpired,accountNonLocked) values('manager','e10adc3949ba59abbe56e057f20f883e','manager@super.com',1,1,1,1);
insert into _users (_name,_passwd,email,_enabled,accountNonExpired,credentialsNonExpired,accountNonLocked) values('boss','e10adc3949ba59abbe56e057f20f883e','boss@super.com',1,1,1,1);


insert into _users (_name,_passwd,email,_enabled,accountNonExpired,credentialsNonExpired,accountNonLocked) values('cat','e10adc3949ba59abbe56e057f20f883e','cat@super.com',1,1,1,1);
insert into _users (_name,_passwd,email,_enabled,accountNonExpired,credentialsNonExpired,accountNonLocked) values('dog','e10adc3949ba59abbe56e057f20f883e','dog@super.com',1,1,1,1);


insert into _roles (NAME,_enabled) values('管理员',1);
insert into _roles (NAME,_enabled) values('用户',1);

insert into _roles (NAME,_enabled) values('员工',1);
insert into _roles (NAME,_enabled) values('主管',1);
insert into _roles (NAME,_enabled) values('副主管',1);
insert into _roles (NAME,_enabled) values('经理',1);
insert into _roles (NAME,_enabled) values('副经理',1);


insert into _authors (_name,_nickname,_enabled) values('AUTH_VIEW_USER','用户',1);
insert into _authors (_name,_nickname,_enabled) values('AUTH_MODIFY_USER','用户修改',1);
insert into _authors (_name,_nickname,_enabled) values('AUTH_VIEW_ROLE','角色',1);
insert into _authors (_name,_nickname,_enabled) values('AUTH_MODIFY_ROLE','角色修改',1);

insert into _user_roles values(1,1);
insert into _user_roles values(1,2);
insert into _user_roles values(2,2);

insert into _user_roles values(3,2);
insert into _user_roles values(4,2);
insert into _user_roles values(5,2);
insert into _user_roles values(6,2);

insert into _user_roles values(1,3);
insert into _user_roles values(2,3);
insert into _user_roles values(3,4);
insert into _user_roles values(4,6);

insert into _role_auths values(1,1);
insert into _role_auths values(1,2);
insert into _role_auths values(1,3);
insert into _role_auths values(1,4);
insert into _role_auths values(2,1);
insert into _role_auths values(2,3);
