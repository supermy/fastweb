package org.supermy.core.test;

import org.hibernate.validator.AssertTrue;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.supermy.core.domain.UrlResource;
import org.supermy.core.domain.User;
import org.supermy.core.service.IUserService;
import org.supermy.core.service.Page;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2008-8-2 下午10:38:33
 * 
 */
public class UserServiceTest extends TestBaseService {
	
	private final org.slf4j.Logger log = LoggerFactory.getLogger(UserServiceTest.class);

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
			log.debug("user info:{}", u.toString());
		}

		Assert.assertNotNull(users.getResult());
		page.setPageNo(page.getNextPage());
		Page<User> users1 = userService.getUserUtil().find(page, "",
				" from " + User.class.getName());
		log.debug("find:{}", users1.getResult());

		// userService.getUserUtil().delete(users.getResult());
	}

	@Test
	public void CRUD() {
		User u = new User();
		userService.getUserUtil().save(u);
		u = userService.getUserUtil().get(u.getId());
		Assert.assertNotNull(u.getId());
		userService.getUserUtil().delete(u.getId());
	}

	@Test
	@Rollback(false)
	public void manytooneUpdate() {
		
//		User u = userService.getUserUtil().findUniqueByProperty("name",
//				"springclick@gmail.com");
//		u.setIntro("intro");
		
		UrlResource url = userService.getUrlResourceUtil()
				.findUniqueByProperty("value", "/user/user!save.action*");
		long id=14;
		//url.getManager().setId(id);
		User newu=new User();
		newu.setId(id);
		url.setManager(newu);
		
		userService.getUrlResourceUtil().save(url);
		log.debug("save after user id:{}",url.getManager().getId());
	}

}
