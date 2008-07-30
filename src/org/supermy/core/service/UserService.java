package org.supermy.core.service;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.User;

/**
* @author supermy E-mail:springclick@gmail.com
* @version 创建时间：2008-7-23 下午01:06:49
* 类说明
*/
@Transactional
@Service
public class UserService extends BaseService implements IUserService {
static Log log=LogFactory.getLog(UserService.class);
	/*
	 * 最常用 TODO 分页查询
	 * 
	 * @see org.supermy.core.service.IUserService#findUsers()
	 */
	@Transactional(readOnly = true)
	public Set<User> findUsers() {
		// DetachedCriteria query = DetachedCriteria.forClass(User.class);
		// //query.createCriteria("address");
		// return findByPage(query, 1, 10);
		StringBuffer selectHql=new StringBuffer("");
		StringBuffer otherHql=new StringBuffer("from User");
		return findByPage(selectHql, otherHql, 2, 10);
	};

	public void saveUser(User u) {
		hibernateTemplate.saveOrUpdate(u);
	}

	@Override
	public User loadUser(Long userId) {
		return (User) hibernateTemplate.load(User.class, userId);
	}

	@Override
	public void delUser(Long userId) {
		int result = hibernateTemplate.bulkUpdate("delete from User where id='"+userId+"'");
		log.debug("刪除 user "+result);
	};

}
