package org.supermy.core.service;

import java.util.Set;
import java.util.*;

import org.supermy.core.domain.Address;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;

/**
* @author supermy E-mail:springclick@gmail.com
* @version 创建时间：2008-7-23 下午01:07:03
* 类说明
*/
public interface IUserService extends IBaseService{

	/**
	 * find user by page
	 * @param i
	 * @return
	 */
	public abstract Set<User> findUsers(int numPage,int sizePage);

	public abstract Set<Role> findRolesByUserId(long userId, int pageNum, int pageSize);

	public abstract User getUserByName(String name);

	public abstract User login(String email,String passwd);

	public abstract Address getAddressByUserId(long userId);

	public abstract String[] findRoleNamesByUserId(long userId);

	


}
