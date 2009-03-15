package org.supermy.core.test;

import org.hibernate.SessionFactory;
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
// ,"/security.xml"
public class BaseServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	public void flush() {
		flush("sessionFactory");
	}
	public void flush(String sessionFactoryName) {
		((SessionFactory) applicationContext.getBean(sessionFactoryName)).getCurrentSession().flush();
	}

}
