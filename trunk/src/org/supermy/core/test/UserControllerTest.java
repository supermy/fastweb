package org.supermy.core.test;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.supermy.core.domain.Address;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;
import org.supermy.core.service.IUserService;
import org.supermy.core.web.UserController;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2008-7-31 上午10:14:21
 * 
 * MockHttpServletRequest,MockHttpSession
 * AbstractTransactionalJUnit4SpringContextTests<br>
 * AbstractModelAndViewTests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/springs.xml" })
public class UserControllerTest extends
		AbstractTransactionalDataSourceSpringContextTests {

	static private Log log = LogFactory.getLog(UserControllerTest.class);

	@Autowired
	private IUserService us;

	@Test
	public void hello() {
		log.debug("hello ... ... ");
	}

	// @Test
	// public void register() throws Exception {
	// @SuppressWarnings("serial")
	// DispatcherServlet servlet = new DispatcherServlet() {
	// protected WebApplicationContext createWebApplicationContext(
	// WebApplicationContext parent) {
	// GenericWebApplicationContext wac = new GenericWebApplicationContext();
	// wac.registerBeanDefinition("userController",
	// new RootBeanDefinition(UserController.class));
	// wac.refresh();
	// return wac;
	// }
	// };
	// servlet.init(new MockServletConfig());
	//
	// MockHttpServletRequest request = new MockHttpServletRequest("GET",
	// "/register.do");
	// request.addParameter("param1", "value1");
	// request.addParameter("param2", "2");
	// MockHttpServletResponse response = new MockHttpServletResponse();
	// servlet.service(request, response);
	//
	// Assert.assertEquals("register", response.getForwardedUrl());
	//
	// // ModelAndViewAssert.assertViewName(mav, expectedName)
	// // us.register(user, result, model, status)delUser();
	// }
	//
//	@Test
//	public void delUser() throws Exception {
//		@SuppressWarnings("serial")
//		DispatcherServlet servlet = new DispatcherServlet() {
//			protected WebApplicationContext createWebApplicationContext(
//					WebApplicationContext parent) {
//				GenericWebApplicationContext wac = new GenericWebApplicationContext();
//				wac.registerBeanDefinition("userController",
//						new RootBeanDefinition(UserController.class));
//				wac.refresh();
//				return wac;
//			}
//		};
//		servlet.init(new MockServletConfig());
//
//		MockHttpServletRequest request = new MockHttpServletRequest("GET",
//				"/delUser.do");
//		request.addParameter("userId", "2");
//		MockHttpServletResponse response = new MockHttpServletResponse();
//		servlet.service(request, response);
//
//		Assert.assertEquals("listUser", response.getForwardedUrl());
//		log.debug(response.getErrorMessage());
//		log.debug(response.getContentAsString());
//
//		// ModelAndViewAssert.assertViewName(mav, expectedName)
//		// us.register(user, result, model, status)delUser();
//	}

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
