package org.supermy.core.service;

import java.util.List;
import java.util.Map;

import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version 创建时间：2008-7-23 下午01:07:03 类说明
 */
public interface IUserService {

	public FastwebTemplate<User, Long> getUserUtil();

	public FastwebTemplate<Role, Long> getRoleUtil();

	public void addRole(Role obj);

	public List<Map> findRoles();

	public void deleteRoles(String name);

	/**
	 * find user by page
	 * 
	 * @param i
	 * @return
	 */
	public abstract List<User> findUsers(Page<User> page);

	public abstract List<Role> findRolesByUserId(Page<Role> page, String userId);

	public abstract User getUserByName(String name);

	public abstract User login(String email, String passwd);

	// public abstract String[] findRoleNamesByUserId(long userId);

}
