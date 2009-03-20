package org.supermy.core.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;
import org.supermy.core.service.IUserService;
import org.supermy.core.service.Page;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2008-8-2 下午10:38:33
 * 
 */
public class UserServiceTest extends BaseServiceTest {

	@Autowired
	private IUserService userService;

	private Page<User> page = new Page<User>(10);

	@Test
	public void getSession() {
		Assert.assertNotNull(userService.getUserUtil().getSessionFactory());
		log.debug(" session:{}", userService.getUserUtil().getSessionFactory());
	}

	/**
	 * 翻译查询，多个对象删除优化
	 */
	@Test
	public void findDeleteManySqlPage() {
		Page<User> users = userService.getUserUtil().find(page, "",
				" from " + User.class.getName());
		log.debug("find:{}", users.getResult());
		
		for (User u : users.getResult()) {
			log.debug("user info:{}",u.toString());
		}
		
		Assert.assertEquals(users.getResult().size(), 10);
		page.setPageNo(page.getNextPage());
		Page<User> users1 = userService.getUserUtil().find(page, "",
				" from " + User.class.getName());
		log.debug("find:{}", users1.getResult());

		userService.getUserUtil().delete(users.getResult());
	}

	/*
	 * @Test public void login() { User u = us.login("1my@my.com","test");
	 * Assert.assertNotNull(u); }
	 * 
	 * @Test public void updateUser() { User u = us.getUserByName("奥运5"); //
	 * User u = us.loadUser(new Long(1)); Assert.assertNotNull(u);
	 * u.setName("update"); us.save(u); }
	 * 
	 * @Test public void register() { User u = new User(); u.setName("qiaqia");
	 * u.setPasswd("12345"); u.setPasswd2("12345"); u.setEmail("qian@m.com");
	 * u.setMd5Passwd(); us.save(u); Assert.assertNotNull(u); }
	 * 
	 * 
	 * @Test public void findRolesByUser() { User u = us.getUserByName("奥运1");
	 * // User u = us.loadUser(new Long(1)); Assert.assertNotNull(u); Set<Role>
	 * result = us.findRolesByUserId(u.getId(), 1, 10); for (Role line : result)
	 * { log.debug("u:" + line.getName()); } }
	 * 
	 * @Test public void addRoleByUser() { log.debug("add role by user ...");
	 * User u = us.getUserByName("奥运1"); Set<Role> roles1 =
	 * us.findRolesByUserId(u.getId(), 0, 40); Assert.assertNotNull(u); Role r =
	 * new Role(); r.setName("quick"); r.setUser(u); us.save(r);
	 * log.debug("roles 1:"+roles1.size()); Set<Role> roles2 =
	 * us.findRolesByUserId(u.getId(), 0, 40);
	 * log.debug("roles 2:"+roles2.size());
	 * Assert.assertEquals(roles2.size()-roles1.size(), 1);
	 * Assert.assertTrue(roles2.contains(r));
	 * 
	 * }
	 * 
	 * @Test public void getAddressByUser() {
	 * log.debug("get address by user id ... "); User u =
	 * us.getUserByName("奥运1"); // User u = us.loadUser(new Long(1));
	 * Assert.assertNotNull(u); Address a =
	 * (Address)us.getAddressByUserId(u.getId()); a.setQq("111111"); us.save(a);
	 * }
	 * 
	 * @Test public void validator() { User u = new User(); StringBuffer m =
	 * getErrorMsg(u); log.debug("============================error messages:" +
	 * m); }
	 * 
	 * private StringBuffer getErrorMsg(BaseDomain u) { Annotation[] annotations
	 * = u.getClass().getAnnotations();
	 * 
	 * for (Annotation annotation : annotations) {
	 * log.debug(annotation.annotationType().getName()); }
	 * 
	 * Field[] fs = u.getClass().getDeclaredFields(); for (Field field : fs) {
	 * log.debug("field " + field.getName() + ":");
	 * log.debug(field.isAnnotationPresent(NotEmpty.class)); }
	 * 
	 * ClassValidator v = new ClassValidator(u.getClass()); InvalidValue[] msgs
	 * = v.getInvalidValues(u); StringBuffer errorMsg = new StringBuffer("");
	 * for (InvalidValue line : msgs) {
	 * errorMsg.append(line.getPropertyPath()).append(":").append(
	 * line.getMessage()).append("  "); } return errorMsg; }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * private void addAddress() { log.debug("add address"); Set<Address>
	 * address = new HashSet<Address>(); User u = us.getUserByName("奥运1"); //
	 * User u = us.loadUser(new Long(1)); Assert.assertNotNull(u); for (int i =
	 * 0; i < 20; i++) { Address r = new Address(); r.setMsn(i + "msn@msn.com");
	 * r.setQq("" + i 100); r.setPhone("123456"); r.setUser(u); address.add(r);
	 * } us.saveAll(address); HashSet results = us.loadAll(Address.class);
	 * Assert.assertEquals(results.size(), address.size()); }
	 */
	public void addUsers() {
		log.debug("add users");

		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 20; i++) {
			User u = new User();
			u.setName("奥运" + i);
			u.setEmail(i + "my@my.com");
			u.setPasswd("test");
			//u.setMd5Passwd();
			users.add(u);
		}
		userService.getUserUtil().save(users);
		Assert.assertEquals(users.size(), 20);
		Assert.assertEquals(users.size(), users.size());
	}

	public void addRoles() {
		log.debug("add roles");
		List<Role> roles = new ArrayList<Role>();
		for (int i = 0; i < 20; i++) {
			Role r = new Role();
			r.setName("admin" + i);
			roles.add(r);
		}
		userService.getRoleUtil().save(roles);
		Assert.assertEquals(roles.size(), 20);
	}

	@Before
	//@Test
	public void addData() {
		log.debug("init data ... ...");

//		page.setPageSize(10);
//		page.setAutoCount(true);

		addUsers();
		addRoles();
	}

	@After
	public void destoryData() {
		log.debug("destroy data ... ...");
//		delRoles();
//		delUsers();
	}

	public void delUsers() {
		userService.getUserUtil().deleteAll();
	}

	public void delRoles() {
		userService.getRoleUtil().deleteAll();
	}

}
