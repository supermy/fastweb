package org.supermy.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;
import org.supermy.core.service.IUserService;
import org.supermy.core.service.Page;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2008-8-2 下午10:38:33
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/app-springs.xml" })
public class CRUDServiceTest extends TestBaseService {

	@Autowired
	private IUserService userService;

	private Page<User> page = new Page<User>(10);

	@Test
	public void addDelRole() {
		Role r = new Role();
		r.setName("superadmin");
		userService.getRoleUtil().save(r);

		r = userService.getRoleUtil().findUniqueByProperty("name",
				"superadmin");
		log.debug("find:{}", r);
		
		userService.getRoleUtil().delete(r.getId());
	}

	@Test
	public void validRole() {
		Role r = new Role();
		r.setName("superadmin");
		userService.getRoleUtil().save(r);

		r = userService.getRoleUtil().findUniqueByProperty("name",
				"superadmin");
		log.debug("find:{}", r);
		
		userService.getRoleUtil().delete(r.getId());
	}

}
