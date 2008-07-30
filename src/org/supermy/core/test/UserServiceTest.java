package org.supermy.core.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.supermy.core.domain.Address;
import org.supermy.core.domain.BaseDomain;
import org.supermy.core.domain.User;
import org.supermy.core.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/springs.xml" })
public class UserServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private IUserService us;

	@Test
	public void userService() {
		Set<User> result = us.findUsers();
		for (User user : result) {
			System.out.println("u:" + user.getName());
		}
	}

	@Test
	public void updateUser() {
		Set<User> result = us.findUsers();
		for (User user : result) {
			user.setName("update");
			us.save(user);
		}
	}

	@Test
	public void validator() {
		User u = new User();
		StringBuffer m = getErrorMsg(u);
		System.out.println("============================error messages:" + m);
	}

	public StringBuffer getErrorMsg(BaseDomain u) {
		Annotation[] annotations = u.getClass().getAnnotations();

		for (Annotation annotation : annotations) {
			System.out.println(annotation.annotationType().getName());
		}

		Field[] fs = u.getClass().getDeclaredFields();
		for (Field field : fs) {
			System.out.println("field " + field.getName() + ":");
			System.out.println(field.isAnnotationPresent(NotEmpty.class));
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
	public void addUsers() {
		for (int i = 0; i < 20; i++) {
			User u = new User();
			u.setName("奥运" + i);
			u.setEmail("my@my.com");
			u.setPasswd("test");
			Address a = new Address();
			a.setQq("123456");
			a.setMsn("msn@msn.com");
			us.save(a);
			u.setAddress(a);
			System.out.println("jdbc insert after get key id ...begin");
			us.saveUser(u);
			System.out.println(u.getId());
			System.out.println("jdbc insert after get key id ...end");
		}
	}

	@After
	public void delUsers() {
		Set<User> users = us.findUsers();
		for (User user : users) {
			us.delUser(user.getId());
		}
	}

}
