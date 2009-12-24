package org.supermy.core.test;

import java.util.Calendar;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;

/**
 * Role Domain Test
 * 
 * 运行成功，需要hibernate.property 文件
 * 
 */
public class RoleTest extends TestCase {
	String un = new Long(Calendar.getInstance().getTimeInMillis()).toString();

	public RoleTest(String x) {
		super(x);
	}

	public void testRole() throws Exception {

		Session s = openSession();
		Transaction tx = s.beginTransaction();

		Role r = new Role();
		r.setEnabled(true);
		s.persist(r);
		tx.commit();
		s.close();

		s = openSession();
		tx = s.beginTransaction();
		r = (Role) s.get(Role.class, r.getId());
		assertNotNull(r);
		s.delete(r);
		tx.commit();
		s.close();
	}

	/**
	 * @see org.supermy.core.test.test.annotations.TestCase#getMappings()
	 */
	protected Class[] getMappings() {
		return new Class[] { Role.class, User.class };
	}
}
