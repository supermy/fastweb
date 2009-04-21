package org.supermy.core.service;

import javax.jws.WebService;

import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.Authority;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2008-7-30 下午05:17:27
 * 
 */
@Transactional
@Service
@WebService
public class UserService extends BaseService implements IUserService {

	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	private FastwebTemplate<User, Long> userUtil;

	private FastwebTemplate<Role, Long> roleUtil;

	private FastwebTemplate<Authority, Long> authUtil;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		// Session openSession = sessionFactory.openSession();//
		// 保证是同一个session,才能在相互调用方法。
		// Session openSession = sessionFactory.getCurrentSession();

		userUtil = new FastwebTemplate<User, Long>(sessionFactory, null,
				User.class);

		roleUtil = new FastwebTemplate<Role, Long>(sessionFactory, null,
				Role.class);

		authUtil = new FastwebTemplate<Authority, Long>(sessionFactory, null,
				Authority.class);
	}

	/**
	 * @return the userUtil
	 */
	public FastwebTemplate<User, Long> getUserUtil() {
		return userUtil;
	}

	/**
	 * @return the roleUtil
	 */
	public FastwebTemplate<Role, Long> getRoleUtil() {
		return roleUtil;
	}

	/**
	 * @return the authUtil
	 */
	public FastwebTemplate<Authority, Long> getAuthUtil() {
		return authUtil;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isUniqueByEMail(String email) {
		Long count = (Long) getUserUtil().findUnique(
				"select count(obj.id) from " + User.class.getName()
						+ " obj where obj.email=?", email);
		return count.intValue() <= 0;
	}

}
