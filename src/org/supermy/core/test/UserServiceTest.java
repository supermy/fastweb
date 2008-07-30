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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.supermy.core.domain.BaseDomain;
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
	public void userService() {

		Set<User> result = us.findUsers(1, 10);
		for (User user : result) {
			log.debug("u:" + user.getName());
		}
	}

	@Test
	public void updateUser() {
		User u = us.loadUser(new Long(1));
		u.setName("update");
		us.saveUser(u);
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
	public void addUsers() {
		Set<User> users = new HashSet<User>();
		for (int i = 0; i < 20; i++) {
			User u = new User();
			u.setName("奥运" + i);
			u.setEmail("my@my.com");
			u.setPasswd("test");
			users.add(u);
		}
		us.saveUsers(users);
	}

	@After
	public void delUsers() {
		us.delAll(User.class);
	}

}
