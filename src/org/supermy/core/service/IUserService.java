package org.supermy.core.service;


import java.util.HashSet;
import java.util.List;

import javax.jws.WebService;

import org.supermy.core.domain.Authority;
import org.supermy.core.domain.Group;
import org.supermy.core.domain.GroupUser;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.UrlResource;
import org.supermy.core.domain.User;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version 创建时间：2008-7-23 下午01:07:03 类说明
 */
@WebService
public interface IUserService {

	public FastwebTemplate<User, Long> getUserUtil();
	public FastwebTemplate<Group, Long> getGroupUtil();
	public FastwebTemplate<GroupUser, Long> getGroupUserUtil();

	public FastwebTemplate<Role, Long> getRoleUtil();
	
	public FastwebTemplate<Authority, Long> getAuthorityUtil();
	public FastwebTemplate<UrlResource, Long> getUrlResourceUtil() ;
	/**
	 * 检测email是否已经注册,或者说是否唯一
	 * 
	 * @param email
	 * @return
	 */
	public boolean isUniqueByEMail(String email);
	public HashSet<UrlResource> getUrlResourceWithAuthorities();
	public void saveUrlResource(UrlResource urlResource,List<Long> authorityListId) ;

	public void moveTreeNode(String id, String parentId);

	
}
