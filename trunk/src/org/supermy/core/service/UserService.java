package org.supermy.core.service;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.BaseDomain;
import org.supermy.core.domain.User;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2008-7-30 下午05:17:27
 * 
 */
@Transactional
@Service
public class UserService extends BaseService implements IUserService {
	static Log log = LogFactory.getLog(UserService.class);
	/*
	 * 
	 * @see org.supermy.core.service.IUserService#findUsers()
	 */
	@Transactional(readOnly = true)
	public Set<User> findUsers(int numPage, int sizePage) {
		// DetachedCriteria query = DetachedCriteria.forClass(User.class);
		// //query.createCriteria("address");
		// return findByPage(query, 1, 10);
		StringBuffer selectHql = new StringBuffer("");
		StringBuffer otherHql = new StringBuffer("from User");
		return findByPage(selectHql, otherHql, numPage, sizePage);
	};

	public void saveUser(User u) {
		super.save(u);
	}

	@Override
	public User loadUser(Long userId) {
		return (User) super.load(User.class, userId);
	}

	@Override
	public void delUser(Long userId) {
		super.delete(User.class, userId);
	}

	@Override
	public void saveUsers(Set<User> users) {
		super.saveAll(users);
		
	};

}
