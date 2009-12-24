package org.supermy.core.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.supermy.core.domain.Authority;
import org.supermy.core.domain.Group;
import org.supermy.core.domain.GroupUser;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.UrlResource;
import org.supermy.core.domain.User;
import org.supermy.core.service.IUserService;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version update time：2009-10-4 下午10:38:33
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/app-springs.xml" })
public class DbServiceTest extends TestBaseService {

	@Autowired
	private IUserService userService;

	@Test
	// 如果你需要真正插入数据库,将Rollback设为false
	@Rollback(false)
	public void dbInit() {
		// 手动执行sql语句
		// CommandUtil.loadFastweb();
		// 删除数据
		deleteData();
		authInitData();
	}

	private void deleteData() {
		// 清理关联数据
		List<UrlResource> resources = userService.getUrlResourceUtil().getAll();
		for (UrlResource urlResource : resources) {
			urlResource.getAuthorityList().clear();
			userService.getUrlResourceUtil().save(urlResource);
		}

		List<Authority> authoritys = userService.getAuthorityUtil().getAll();
		for (Authority authority : authoritys) {
		}

		List<Role> roles = userService.getRoleUtil().getAll();
		for (Role role : roles) {
			role.getAuths().clear();
			userService.getRoleUtil().save(role);
		}
		List<User> users = userService.getUserUtil().getAll();

		// 删除数据
		userService.getUrlResourceUtil().deleteAll();
		userService.getAuthorityUtil().deleteAll();
		userService.getRoleUtil().deleteAll();
		userService.getGroupUserUtil().deleteAll();
		userService.getUserUtil().deleteAll();
		userService.getGroupUtil().deleteAll();

	}

	// 权限数据初始化
	private void authInitData() {
		Group superadmin = genGroup("超级管理员", "t1", "springclick@gmail.com");
		Group admin = genGroup("管理员", "t2", "user@user.com");
		Group common = genGroup("普通用户", "t3", "guest@guest.com");

		User springclick = genUser("springclick@gmail.com", "123456");
		User user = genUser("user@user.com", "123456");
		User guest = genUser("guest@guest.com", "123456");

		Role manager = genRole("管理员");
		Role authManager = genRole("权限管理员");
		// Role userRole = genRole("用户");
		Role guestRole = genRole("游客");

		genGroupUser(springclick, superadmin, "管理员");
		genGroupUser(user, admin, "权限管理员");
		genGroupUser(guest, common, "游客");

		auth2Role(manager, authManager, guestRole, User.class.getSimpleName()
				.toUpperCase(), "/user/user");
		auth2Role(manager, authManager, guestRole, Group.class.getSimpleName()
				.toUpperCase(), "/user/group");
		auth2Role(manager, authManager, guestRole, GroupUser.class.getSimpleName()
				.toUpperCase(), "/user/group-user");
		auth2Role(manager, authManager, guestRole, Role.class.getSimpleName()
				.toUpperCase(), "/user/role");
		auth2Role(manager, authManager, guestRole, UrlResource.class
				.getSimpleName().toUpperCase(), "/user/url-resource");
		auth2Role(manager, authManager, guestRole, Authority.class
				.getSimpleName().toUpperCase(), "/user/authority");

		// user register
		UrlResource listUrlRes = genUrlResource("menu", "注册-register",
				"/user/user!reigster.action*");

	}

	/**
	 * 权限初始化;权限赋予给角色
	 * 
	 * @param manager
	 * @param authManager
	 * @param guestRole
	 * @param name
	 * @param url
	 */
	private void auth2Role(Role manager, Role authManager, Role guestRole,
			String name, String url) {
		//权限
		Authority authReadUrlRes = genAuthority("AUTH_READ_" + name,
				"阅读AUTH_READ_" + name);
		Authority authEditUrlRes = genAuthority("AUTH_EDIT_" + name,
				"编辑AUTH_EDIT_" + name);
		Authority authSaveUrlRes = genAuthority("AUTH_SAVE_" + name,
				"保存AUTH_SAVE_" + name);
		Authority authDeleteUrlRes = genAuthority("AUTH_DELETE_" + name,
				"删除AUTH_DELETE_" + name);

		// 资源管理 ：权限角色关联
		manager.getAuths().add(authReadUrlRes);
		manager.getAuths().add(authEditUrlRes);
		manager.getAuths().add(authSaveUrlRes);
		manager.getAuths().add(authDeleteUrlRes);

		authManager.getAuths().add(authReadUrlRes);
		authManager.getAuths().add(authEditUrlRes);
		authManager.getAuths().add(authSaveUrlRes);
		authManager.getAuths().add(authDeleteUrlRes);

		guestRole.getAuths().add(authReadUrlRes);
		guestRole.getAuths().add(authEditUrlRes);

		//资源url
		UrlResource listUrlRes = genUrlResource("url", "列表-" + name, url+ ".action*");
		
		UrlResource editUrlRes = genUrlResource("url", "编辑-" + name, url
				+ "!input.action*");
		UrlResource saveUrlRes = genUrlResource("url", "保存-" + name, url
				+ "!save.action*");
		UrlResource deleteUrlRes = genUrlResource("url", "刪除-" + name, url
				+ "!delete.action*");

		// 资源与权限关联 
		listUrlRes.getAuthorityList().add(authReadUrlRes);//FIXME 此资源应该无需验证
		editUrlRes.getAuthorityList().add(authEditUrlRes);
		saveUrlRes.getAuthorityList().add(authSaveUrlRes);
		deleteUrlRes.getAuthorityList().add(authDeleteUrlRes);
	}

	/**
	 * 构造资源数据
	 * 
	 * @param resourceType
	 * @param desc
	 * @param value
	 * @param position
	 * @return
	 */
	private UrlResource genUrlResource(String resourceType, String desc,
			String value) {
		UrlResource u = new UrlResource();
		u.setResourceType(resourceType);
		u.setDesc(desc);
		u.setValue(value);
		// userService.getUrlResourceUtil().save(u);
		u.setPosition(1);
		userService.getUrlResourceUtil().save(u);
		return u;
	}

	/**
	 * 构造权限数据
	 * 
	 * @param name
	 * @param nickName
	 * @return
	 */
	private Authority genAuthority(String name, String nickName) {
		Authority a = new Authority();
		a.setName(name);
		a.setNickName(nickName);
		userService.getAuthorityUtil().save(a);
		return a;
	}

	/**
	 * 构造角色数据
	 * 
	 * @param name
	 * @return
	 */
	private Role genRole(String name) {
		Role r = new Role();
		r.setName(name);
		userService.getRoleUtil().save(r);
		return r;
	}

	/**
	 * 构造用户数据
	 * 
	 * @param name
	 * @param passwd
	 * @return
	 */
	private User genUser(String name, String passwd) {
		User u = new User();
		u.setName(name);
		u.setEmail(name);
		u.setPasswd(passwd);
		u.setCredentialsNonExpired(true);
		u.setAccountNonExpired(true);
		u.setAccountNonLocked(true);
		u.setCreateBy("autogen");
		userService.getUserUtil().save(u);
		return u;
	}

	private Group genGroup(String name, String type, String email) {
		Group g = new Group();
		g.setName(name);
		g.setCode("change");
		g.setType(type);
		g.setEmail(email);
		// g.setParent(null);
		userService.getGroupUtil().save(g);
		return g;
	}

	private GroupUser genGroupUser(User u, Group g, String rolename) {
		GroupUser gu = new GroupUser();
		gu.setName(rolename);
		gu.setGroup(g);
		gu.setUser(u);
		userService.getGroupUserUtil().save(gu);
		return gu;
	}

	@Test
	public void createGroup() {
		genGroup("abc", "y", "a@a.com");
		genUser("aa@aa.com", "bb");
	}

}
