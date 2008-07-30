package org.supermy.core.service;

import java.util.Set;

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

//	public abstract void saveUser(User user);
//
//	public abstract User loadUser(Long userId);

//	public abstract void delUser(Long userId);

//	public abstract void saveUsers(Set<User> users);

//	public abstract void saveRoles(Set<Role> roles);

//	public abstract void saveAddresss(Set<Address> address);

	public abstract Set<Role> findRolesByUserId(long userId, int pageNum, int pageSize);

	public abstract User getUserByName(String name);

	public abstract Address getAddressByUserId(long userId);

//	public abstract void saveAddress(Address a);

}