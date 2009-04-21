package org.supermy.core.service;

import javax.jws.WebService;

import org.supermy.core.domain.Authority;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version 创建时间：2008-7-23 下午01:07:03 类说明
 */
@WebService
public interface IUserService {

	public FastwebTemplate<User, Long> getUserUtil();

	public FastwebTemplate<Role, Long> getRoleUtil();

	public FastwebTemplate<Authority, Long> getAuthUtil();

	/**
	 * 检测email是否已经注册,或者说是否唯一
	 * 
	 * @param email
	 * @return
	 */
	public boolean isUniqueByEMail(String email);

}
