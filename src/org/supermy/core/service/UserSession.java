package org.supermy.core.service;

import java.util.List;

import org.jbpm.api.identity.Group;
import org.jbpm.api.identity.User;
import org.jbpm.pvm.internal.identity.spi.IdentitySession;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.domain.GroupUser;

public class UserSession implements IdentitySession {

	@Autowired
	private UserService userService;

	public String createUser(String userName, String givenName,
			String familyName, String businessEmail) {

		org.supermy.core.domain.User user = new org.supermy.core.domain.User();
		user.setEmail(businessEmail);
		user.setName(userName);

		userService.getUserUtil().save(user);

		return user.getId().toString();
	}

	public User findUserById(String userId) {
		return userService.getUserUtil().get(userId);
	}

	public List<User> findUsersById(String... userIds) {
		List users = userService.getUserUtil().findBy(userIds);
		return users;
	}

	public List<User> findUsers() {
		List all = userService.getUserUtil().getAll();
		return all;
	}

	public void deleteUser(String userId) {
		List<GroupUser> findByProperty = userService.getGroupUserUtil()
				.findByProperty("user.id", userId);
		userService.getGroupUserUtil().delete(findByProperty);
		userService.getUserUtil().delete(userId);
	}

	public String createGroup(String groupName, String groupType,
			String parentGroupId) {
		org.supermy.core.domain.Group group = new org.supermy.core.domain.Group();
		// FIXME
		//		
		// String groupId = groupType != null ? groupType + "." + groupName
		// : groupName;
		// group.setId(groupId);
		group.setName(groupName);
		group.setType(groupType);

		if (parentGroupId != null) {
			org.supermy.core.domain.Group parentGroup = new org.supermy.core.domain.Group();
			parentGroup.setId(Long.getLong(parentGroupId));
			group.setParent(parentGroup);
		}

		userService.getGroupUtil().save(group);

		return group.getId().toString();
	}

	public List<User> findUsersByGroup(String groupId) {
		String hql = "select distinct obj.user from GroupUser obj where obj.group.id=?";
		return userService.getGroupUserUtil().findForProperty(hql, groupId);
	}

	public Group findGroupById(String groupId) {
		return userService.getGroupUtil().get(groupId);
	}

	public List<Group> findGroupsByUserAndGroupType(String userId,
			String groupType) {

		String hql = "select distinct m.group" + " from "
				+ GroupUser.class.getName() + " as m where m.user.id = :userId"
				+ " and m.group.type = :groupType";
		return userService.getGroupUserUtil().findForProperty(hql, userId,
				groupType);
	}

	public List<Group> findGroupsByUser(String userId) {
		String hql = "select distinct m.group" + " from "
				+ GroupUser.class.getName() + " as m where m.user.id = :userId";
		return userService.getGroupUserUtil().findForProperty(hql, userId);
	}

	public List<Group> findGroups() {
		List all = userService.getGroupUtil()
				.getAll();
		return all;
	}

	public void deleteGroup(String groupId) {
		List<GroupUser> findByProperty = userService.getGroupUserUtil()
				.findByProperty("group.id", groupId);
		userService.getGroupUserUtil().delete(findByProperty);
		userService.getGroupUtil().delete(groupId);
	}

	public void createMembership(String userId, String groupId, String role) {

		org.supermy.core.domain.User user = new org.supermy.core.domain.User();
		user.setId(Long.valueOf(userId));
		org.supermy.core.domain.Group group = new org.supermy.core.domain.Group();
		group.setId(Long.valueOf(groupId));

		GroupUser membership = new GroupUser();
		membership.setUser(user);
		membership.setGroup(group);
		membership.setName(role);
		userService.getGroupUserUtil().save(membership);
	}

	public void deleteMembership(String userId, String groupId, String role) {
		String hql = "from GroupUser obj where obj.user.id=? and obj.group.id=? and role=?";
		List findForProperty = userService.getGroupUserUtil().findForProperty(
				hql, userId, groupId, role);
		userService.getGroupUserUtil().delete(findForProperty);
	}

}