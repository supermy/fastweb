package org.supermy.core.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2008-8-2 下午10:38:33
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/springs.xml" })
public class UserJdbcServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	/*@Autowired
	private IUserService us;

	@Test
	public void findUsersByPage() {
		log.debug("find user by page ... ");
		String sql = "select * from c_user;";
		SimpleJdbcTemplate jdbc = us.getJdbcTemplate();
		List<Map<String, Object>> results = jdbc.queryForList(sql);
		log.debug("query result count:" + results.size());
		for (Map<String, Object> map : results) {
			log.debug(map.get("C_NAME"));
		}

		// Assert.assertEquals(6,result.size());
	}
	
	@Test
	public void insertRole() {
		log.debug("insert role ... ");
		//spring mvc 表单输入得到map对
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("name", "name");
		//
		us.addRole(m);
		System.out.println(m);
		us.insertRole(m);
		System.out.println(m);
		// Assert.assertEquals(6,result.size());
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
			u.setEmail(i + "my@my.com");
			u.setPasswd("test");
			u.setMd5Passwd();
			users.add(u);
		}
		us.saveAll(users);
		HashSet results = us.loadAll(User.class);
		Assert.assertEquals(results.size(), 20);
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
	}*/

}
