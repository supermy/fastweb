package org.supermy.core.listener;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;
import org.supermy.core.service.IUserService;


public class InitUserRoleDataListener implements
		ServletContextListener {
	IUserService us = null;

	private static final Log log = LogFactory
			.getLog(InitUserRoleDataListener.class);

	public void contextInitialized(ServletContextEvent event) {
		if (log.isDebugEnabled()) {
			log.debug("initializing context...");
		}

		// call Spring's context ContextLoaderListener to initialize
		// all the context files specified in web.xml
		
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);

		us = (IUserService) ctx.getBean("userService");
		addUserByRole();
		//context.setAttribute("user","admin-admin  my-ROLE_ANONYMOUS user-user");
		log.debug("initialization complete [OK]");
	}

	private void addUserByRole() {
		Set users = new HashSet();
		User u1 = new User();
		u1.setName("admin");
		u1.setPasswd("admin");
		u1.setEmail("admin@my.com");
		u1.setMd5Passwd();

		//Md5PasswordEncoder md=new Md5PasswordEncoder();
		//u.setPassword(md.encodePassword(username,null));

		users.add(u1);
		
		for (int i = 0; i < 20; i++) {
			User u = new User();
			u.setName("活着" + i);
			u.setPasswd("test");
			u.setEmail(i + "my@my.com");
			u.setMd5Passwd();
			users.add(u);
		}
		us.saveAll(users);

		User u = (User) us.load(User.class, 1);
		Role r = new Role();
		r.setName("admin");
		r.setUser(u);
		us.save(r);
		Role r1 = new Role();
		r1.setName("superadmin");
		r1.setUser(u);
		us.save(r1);

		log.debug("add users 20 ");

	}

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

}
