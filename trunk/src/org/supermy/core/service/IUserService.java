package org.supermy.core.service;

import java.util.Set;

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

	public abstract void saveUser(User user);

	public abstract User loadUser(Long userId);

	public abstract void delUser(Long userId);

	public abstract void saveUsers(Set<User> users);

}