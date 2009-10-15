insert into c_users (id_,name_,passwd_,email,enabled_,accountNonExpired,credentialsNonExpired,accountNonLocked) values(1,'Admin','e10adc3949ba59abbe56e057f20f883e','springclick@gmail.com',1,1,1,1);
insert into c_users (id_,name_,passwd_,email,enabled_,accountNonExpired,credentialsNonExpired,accountNonLocked) values(2,'User','e10adc3949ba59abbe56e057f20f883e','user@super.com',1,1,1,1);
insert into c_users (id_,name_,passwd_,email,enabled_,accountNonExpired,credentialsNonExpired,accountNonLocked) values(3,'manager','e10adc3949ba59abbe56e057f20f883e','manager@super.com',1,1,1,1);
insert into c_users (id_,name_,passwd_,email,enabled_,accountNonExpired,credentialsNonExpired,accountNonLocked) values(4,'boss','e10adc3949ba59abbe56e057f20f883e','boss@super.com',1,1,1,1);
insert into c_users (id_,name_,passwd_,email,enabled_,accountNonExpired,credentialsNonExpired,accountNonLocked) values(5,'cat','e10adc3949ba59abbe56e057f20f883e','cat@super.com',1,1,1,1);
insert into c_users (id_,name_,passwd_,email,enabled_,accountNonExpired,credentialsNonExpired,accountNonLocked) values(6,'dog','e10adc3949ba59abbe56e057f20f883e','dog@super.com',1,1,1,1);

insert into c_roles (id_,name_,enabled_) values(1,'管理员',1);
insert into c_roles (id_,name_,enabled_) values(2,'用户',1);
insert into c_roles (id_,name_,enabled_) values(3,'员工',1);
insert into c_roles (id_,name_,enabled_) values(4,'主管',1);
insert into c_roles (id_,name_,enabled_) values(5,'副主管',1);
insert into c_roles (id_,name_,enabled_) values(6,'经理',1);
insert into c_roles (id_,name_,enabled_) values(7,'副经理',1);

insert into c_authors (id_,name_,nickname_,enabled_) values(1,'AUTH_VIEW_USER','用户',1);
insert into c_authors (id_,name_,nickname_,enabled_) values(2,'AUTH_MODIFY_USER','用户修改',1);
insert into c_authors (id_,name_,nickname_,enabled_) values(3,'AUTH_VIEW_ROLE','角色',1);
insert into c_authors (id_,name_,nickname_,enabled_) values(4,'AUTH_MODIFY_ROLE','角色修改',1);
insert into c_authors (id_,name_,nickname_,enabled_) values(5,'AUTH_DELETE_USER','删除用户',1);
insert into c_authors (id_,name_,nickname_,enabled_) values(6,'AUTH_DELETE_ROLE','删除角色',1);
insert into c_authors (id_,name_,nickname_,enabled_) values(7,'AUTH_MYTASK','我的任务',1);
insert into c_authors (id_,name_,nickname_,enabled_) values(8,'roleAnonymous','游客',1);

insert into c_url_resources (id_,resource_type,desc_,value_,position_,enabled_) values(1,'url','保存用户','/user/user!save*',1,1);
insert into c_url_resources (id_,resource_type,desc_,value_,position_,enabled_) values(2,'url','删除用户','/user/user!delete*',2,1);
insert into c_url_resources (id_,resource_type,desc_,value_,position_,enabled_) values(3,'url','保存角色','/user/role!save*',3,1);
insert into c_url_resources (id_,resource_type,desc_,value_,position_,enabled_) values(4,'url','删除角色','/user/role!delete*',4,1);
insert into c_url_resources (id_,resource_type,desc_,value_,position_,enabled_) values(5,'url','我的任务','/workflow/mytask*',5,1);

insert into c_resource_authority (resource_id,authority_id) values(1,2);
insert into c_resource_authority (resource_id,authority_id) values(2,5);
insert into c_resource_authority (resource_id,authority_id) values(3,4);
insert into c_resource_authority (resource_id,authority_id) values(4,6);
insert into c_resource_authority (resource_id,authority_id) values(5,7);

insert into user_roles values(1,1);
insert into user_roles values(1,2);
insert into user_roles values(2,2);
insert into user_roles values(3,2);
insert into user_roles values(4,2);
insert into user_roles values(5,2);
insert into user_roles values(6,2);
insert into user_roles values(1,3);
insert into user_roles values(2,3);
insert into user_roles values(3,4);
insert into user_roles values(4,6);

insert into role_auths values(1,1);
insert into role_auths values(1,2);
insert into role_auths values(1,3);
insert into role_auths values(1,4);
insert into role_auths values(2,1);
insert into role_auths values(2,3);