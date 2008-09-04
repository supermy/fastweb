package org.supermy.core.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.Address;
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
	static Log log = LogFactory.getLog(UserService.class);

	/**
	 * spring mvc 提交直接拿到 map的bean,可以直接查询
	 * 
	 * @param m
	 */
	@Transactional(readOnly = false)
	public void addRole(Map<String, Object> m) {
		StringBuffer insert_roles = new StringBuffer(
				"insert into c_role (name) values(:name)");
		// 直接把map的参数绑定到map中
		MapSqlParameterSource bean2param = new MapSqlParameterSource(m);
		jdbcTemplate.update(new String(insert_roles), bean2param);
	}

	/*
	 * 适用于插入并且返回主键的情况
	 * 
	 * @see org.supermy.core.service.IUserService#addRole(java.util.Map)
	 */
	@Transactional(readOnly = false)
	public void insertRole(Map<String, Object> m) {
		SimpleJdbcInsert exec = insertActor.withTableName("c_role")
				.usingGeneratedKeyColumns("c_id");
		Number key = exec.executeAndReturnKey(m);
		m.put("c_id", key);
	}

	/**
	 * 返回的list中都是Map,可以直接使用${o.name}
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findRoles() {
		StringBuffer roles = new StringBuffer("select name from c_role");
		return jdbcTemplate.queryForList(new String(roles));
	}

	public void deleteRoles(String name) {
		StringBuffer roles = new StringBuffer("delete from c_role where name=?");
		jdbcTemplate.update(new String(roles), name);
	}

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

	@Override
	public Set<Role> findRolesByUserId(long userId, int numPage, int sizePage) {
		StringBuffer selectHql = new StringBuffer("");
		StringBuffer otherHql = new StringBuffer("from Role r where r.user.id="
				+ userId);
		return findByPage(selectHql, otherHql, numPage, sizePage);
	}

	@Override
	public User getUserByName(String name) {
		List o = hibernateTemplate.findByNamedParam(
				"from User u where u.name=:name", "name", name);
		if (o.size() == 1) {
			return (User) o.get(0);
		} else {
			return null;
		}

	}

	@Override
	public Address getAddressByUserId(long userId) {
		List o = hibernateTemplate.findByNamedParam(
				"from Address a where a.user.id=:id", "id", userId);
		if (o.size() >= 1) {
			return (Address) o.get(0);
		} else {
			return null;
		}
	}

	public User login(String email, String passwd) {
		List o = hibernateTemplate.findByNamedParam(
				"from User u where u.email=:email and u.passwd=:passwd",
				new String[] { "email", "passwd" }, new String[] { email,
						passwd });
		if (o.size() == 1) {
			return (User) o.get(0);
		} else {
			return null;
		}

	}

	@Override
	public String[] findRoleNamesByUserId(long userId) {
		List roleNames = hibernateTemplate.findByNamedParam(
				"select o.name from Role o where o.user.id=:userId", "userId",
				userId);
		String[] results = new String[roleNames.size()];
		roleNames.toArray(results);
		return results;
		// return roleNames;
	}
}
