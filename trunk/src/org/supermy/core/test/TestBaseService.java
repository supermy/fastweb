package org.supermy.core.test;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
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
@ContextConfiguration(locations = { "/app-springs.xml"})
public class TestBaseService extends
		AbstractTransactionalJUnit4SpringContextTests {
	
	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 刷新sessionFactory,强制Hibernate执行SQL以验证ORM配置.
	 * 
	 * sessionFactory名默认为"sessionFactory".
	 * 
	 * @see #flush(String)
	 */
	protected void flush() {
		flush("sessionFactory");
	}

	/**
	 * 刷新sessionFactory,强制Hibernate执行SQL以验证ORM配置.
	 * 因为没有执行commit操作,不会更改测试数据库.
	 * 
	 * @param sessionFactoryName applicationContext中sessionFactory的名称.
	 */
	protected void flush(final String sessionFactoryName) {
		Session currentSession = ((SessionFactory) applicationContext.getBean(sessionFactoryName)).getCurrentSession();
		currentSession.flush();
	}
}
