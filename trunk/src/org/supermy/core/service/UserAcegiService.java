package org.supermy.core.service;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2008-7-30 下午05:17:27
 * 
 */
@Transactional
@Service
public class UserAcegiService  implements UserDetailsService {
	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUserService us;

	/*
	 * Acegi
	 * 
	 * @see org.springframework.security.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		User user = us.getUserByName(userName);
		if (user == null)
			throw new UsernameNotFoundException(userName + " not found");

		Set<Role> roles=null;// = us.findRolesByUserId(user.getId(), 0, 30);

		GrantedAuthority[] arrayAuths = new GrantedAuthority[roles.size()];
		int index = 0;
		for (Role role : roles)
			// 要求角色名以ROLE_为前缀.
			arrayAuths[index++] = new GrantedAuthorityImpl("ROLE_"
					+ role.getName());

		// User类中没有enabled, accountNonExpired,credentialsNonExpired,
		// accountNonLocked等属性
		// 暂时全部设为true,在需要时才添加这些属性.
		org.springframework.security.userdetails.User userdetail = new org.springframework.security.userdetails.User(
				user.getName(), user.getPasswd(), true, true, true, true,
				arrayAuths);

		return userdetail;
	}
}
