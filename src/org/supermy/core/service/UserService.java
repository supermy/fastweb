package org.supermy.core.service;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
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

	// /**
	// * spring mvc 提交直接拿到 map的bean,可以直接查询
	// *
	// * @param m
	// */
	// @Transactional(readOnly = false)
	// public void addRole(Role obj) {
	// roleUtil.save(obj);
	// }

	// public List<Map> findRoles() {
	// StringBuffer sql = new StringBuffer("select name from c_role");
	// return roleUtil.findBySQL4ListMap(sql.toString());
	// }

	// @Transactional(readOnly = false)
	// public void deleteRoles(String name) {
	// StringBuffer roles = new StringBuffer("delete from c_role where name=?");
	// roleUtil.createSqlQuery(roles.toString()).executeUpdate();
	// }

	// /*
	// *
	// * @see org.supermy.core.service.IUserService#findUsers()
	// */
	// public List<User> findUsers(Page<User> page) {
	// StringBuffer selectHql = new StringBuffer("");
	// StringBuffer otherHql = new StringBuffer("from User");
	// return userUtil.find(page, selectHql.toString(), otherHql.toString())
	// .getResult();
	// };

	// public List<Role> findRolesByUserId(Page<Role> page, String userId) {
	// StringBuffer selectHql = new StringBuffer("");
	// StringBuffer otherHql = new StringBuffer(
	// "from Role r where r.user.id=:userId");
	// return roleUtil.find(page, selectHql.toString(), otherHql.toString(),
	// userId).getResult();
	// }

	// public User getUserByName(String name) {
	// return userUtil.findUniqueByProperty("name", name);
	// }

	// public User login(String email, String passwd) {
	// String hql = "from User u where u.email=:email and u.passwd=:passwd";
	// return (User) userUtil.findUnique(hql, email, passwd);
	// }

	@Override
	public boolean isUniqueByEMail(String email) {
		Long count = (Long) getUserUtil().findUnique(
				"select count(obj.id) from " + User.class.getName()
						+ " obj where obj.email=?", email);
		return count.intValue() <= 0;
	}

}
