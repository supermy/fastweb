package org.supermy.core.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.hibernate.validator.NotEmpty;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.supermy.core.domain.Address;
import org.supermy.core.domain.BaseDomain;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;
import org.supermy.core.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/springs.xml" })
public class UserServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	static private Log log = LogFactory.getLog(UserServiceTest.class);
	@Autowired
	private IUserService us;

	@Test
	public void findUsersByPage() {
		Set<User> result = us.findUsers(1, 10);
		for (User line : result) {
			log.debug("u:" + line.getName());
		}
	}

	@Test
	public void updateUser() {
		User u = us.getUserByName("奥运5");
		// User u = us.loadUser(new Long(1));
		Assert.assertNotNull(u);
		u.setName("update");
		us.save(u);
	}

	@Test
	public void findRolesByUser() {
		User u = us.getUserByName("奥运1");
		// User u = us.loadUser(new Long(1));
		Assert.assertNotNull(u);
		Set<Role> result = us.findRolesByUserId(u.getId(), 1, 10);
		for (Role line : result) {
			log.debug("u:" + line.getName());
		}
	}

	@Test
	public void addRoleByUser() {
		log.debug("add role by user ...");
		User u = us.getUserByName("奥运1");
		Set<Role> roles1 = us.findRolesByUserId(u.getId(), 0, 40);
		Assert.assertNotNull(u);
		Role r = new Role();
		r.setName("quick");
		r.setUser(u);
		us.save(r);
		log.debug("roles 1:"+roles1.size());
		Set<Role> roles2 = us.findRolesByUserId(u.getId(), 0, 40);
		log.debug("roles 2:"+roles2.size());
		Assert.assertEquals(roles2.size()-roles1.size(), 1);
		Assert.assertTrue(roles2.contains(r));
		
	}

	@Test
	public void findAddressByUser() {
		log.debug("get address by user id ... ");
		User u = us.getUserByName("奥运1");
		// User u = us.loadUser(new Long(1));
		Assert.assertNotNull(u);
		Address a = (Address)us.getAddressByUserId(u.getId());
		a.setQq("111111");
		us.save(a);
	}

	@Test
	public void validator() {
		User u = new User();
		StringBuffer m = getErrorMsg(u);
		log.debug("============================error messages:" + m);
	}

	private StringBuffer getErrorMsg(BaseDomain u) {
		Annotation[] annotations = u.getClass().getAnnotations();

		for (Annotation annotation : annotations) {
			log.debug(annotation.annotationType().getName());
		}

		Field[] fs = u.getClass().getDeclaredFields();
		for (Field field : fs) {
			log.debug("field " + field.getName() + ":");
			log.debug(field.isAnnotationPresent(NotEmpty.class));
		}

		ClassValidator v = new ClassValidator(u.getClass());
		InvalidValue[] msgs = v.getInvalidValues(u);
		StringBuffer errorMsg = new StringBuffer("");
		for (InvalidValue line : msgs) {
			errorMsg.append(line.getPropertyPath()).append(":").append(
					line.getMessage()).append("  ");
		}
		return errorMsg;
	}

	@Before
	public void addData() {
		log.debug("init data ... ...");
		addUsers();
		addRoles();
		addAddress();
	}

	private void addUsers() {
		log.debug("add users");

		Set<User> users = new HashSet<User>();
		for (int i = 0; i < 20; i++) {
			User u = new User();
			u.setName("奥运" + i);
			u.setEmail("my@my.com");
			u.setPasswd("test");
			users.add(u);
		}
		us.saveAll(users);
		HashSet results = us.loadAll(User.class);
		Assert.assertEquals(results.size(), users.size());
	}

	private void addRoles() {
		log.debug("add roles");

		Set<Role> roles = new HashSet<Role>();
		User u = us.getUserByName("奥运1");
		// User u = us.loadUser(new Long(1));
		Assert.assertNotNull(u);
		for (int i = 0; i < 20; i++) {
			Role r = new Role();
			r.setName("admin" + i);
			r.setUser(u);
			roles.add(r);
		}
		us.saveAll(roles);
		HashSet results = us.loadAll(Role.class);
		Assert.assertEquals(results.size(), roles.size());
	}

	private void addAddress() {
		log.debug("add address");
		Set<Address> address = new HashSet<Address>();
		User u = us.getUserByName("奥运1");
		// User u = us.loadUser(new Long(1));
		Assert.assertNotNull(u);
		for (int i = 0; i < 20; i++) {
			Address r = new Address();
			r.setMsn(i + "msn@msn.com");
			r.setQq("" + i * 100);
			r.setPhone("123456");
			r.setUser(u);
			address.add(r);
		}
		us.saveAll(address);
		HashSet results = us.loadAll(Address.class);
		Assert.assertEquals(results.size(), address.size());
	}

	@After
	public void destoryData() {
		log.debug("destroy data ... ...");
		delAddress();
		delRoles();
		delUsers();
	}

	private void delUsers() {
		us.delAll(User.class);
	}

	private void delRoles() {
		us.delAll(Role.class);
	}

	private void delAddress() {
		us.delAll(Address.class);
	}

}
