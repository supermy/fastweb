package org.supermy.core.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.hibernate.SessionFactory;
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
import org.supermy.core.domain.Authority;
import org.supermy.core.domain.Group;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 */
@Transactional(readOnly = true)
@Service(value="UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
	private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	private FastwebTemplate<User, Long> userDao;
	private FastwebTemplate<Group, Long> groupDao;
	private FastwebTemplate<Role, Long> roleDao;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		userDao = new FastwebTemplate<User, Long>(sessionFactory, null,
				User.class);
		groupDao = new FastwebTemplate<Group, Long>(sessionFactory, null,
				Group.class);
		roleDao = new FastwebTemplate<Role, Long>(sessionFactory, null,
				Role.class);
	}

	/**
	 * 获取用户Detail信息的回调函数.
	 */
	public UserDetails loadUserByUsername(String userEmail)
			throws UsernameNotFoundException, DataAccessException {

		User user = userDao.findUniqueByProperty("email", userEmail);
		log.debug("find user:{}", user);
		if (user == null)
			throw new UsernameNotFoundException("用户" + userEmail + " 不存在");

		GrantedAuthority[] grantedAuths = obtainGrantedAuthorities(user);

		org.springframework.security.userdetails.User userdetail = new org.springframework.security.userdetails.User(
				user.getEmail(), user.getPasswd(), user.isEnabled(), user
						.isAccountNonExpired(), user.isCredentialsNonExpired(),
				user.isAccountNonLocked(), grantedAuths);

		return userdetail;
	}

	/**
	 * 获得用户所有角色的权限.
	 */
	public GrantedAuthority[] obtainGrantedAuthorities(User user) {
		log.debug("user:{}",user);
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		String findRoleByUser = "select obj.name from GroupUser obj where obj.user=?";
		log.debug("groupDao:{}",groupDao);
		List<String> roleNames = groupDao.findForProperty(findRoleByUser, user);
		String findAuthorityByRole = "from Role obj where obj.name in (:roles)";
		List<Role> roles = roleDao.find(findAuthorityByRole,new String[]{"roles"} ,roleNames);
		for (Role role : roles) {
			for (Authority authority : role.getAuths()) {
				authSet.add(new GrantedAuthorityImpl(authority.getName()));
			}
		}
		// for (Role role : user.getRoles()) {
		// for (Authority authority : role.getAuths()) {
		// authSet.add(new GrantedAuthorityImpl(authority.getName()));
		// }
		// }
		log.debug("auth size:{}", authSet.size());
		return authSet.toArray(new GrantedAuthority[authSet.size()]);
	}
}
