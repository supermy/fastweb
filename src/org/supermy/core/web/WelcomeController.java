package org.supermy.core.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.supermy.core.domain.Address;
import org.supermy.core.domain.User;
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
		for (int i = 0; i < 20; i++) {
			Address a = new Address();
			a.setQq("123456");
			a.setMsn("msn@msn.com");
			us.save(a);
			
			User u = new User();
			u.setName("活着" + i);
			u.setPasswd("test");
			u.setEmail("my@my.com");
			u.setAddress(a);
			us.saveUser(u);
		}
	}

}
