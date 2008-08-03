package org.supermy.core.web;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.supermy.core.domain.*;
import org.supermy.core.service.IUserService;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version 创建时间：2008-7-23 上午11:54:04 类说明
 */
@Controller
public class WelcomeController {
	static Log log = LogFactory.getLog(WelcomeController.class);
	@Autowired
	private IUserService us;

	@RequestMapping("/welcome.do")
	public void welcomeHandler() {
		Set users=new HashSet();
		for (int i = 0; i < 20; i++) {
			User u = new User();
			u.setName("活着" + i);
			u.setPasswd("test");
			u.setEmail(i+"my@my.com");
			users.add(u);
		}
		us.saveAll(users);

		User u=(User)us.load(User.class,1);
		Role r=new Role();
		r.setName("admin");
		r.setUser(u);
		us.save(r);
		Role r1=new Role();
		r1.setName("superadmin");
		r1.setUser(u);
		us.save(r1);

		log.debug("add users 20 ");
	}

}
