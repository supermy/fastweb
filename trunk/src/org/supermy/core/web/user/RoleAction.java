package org.supermy.core.web.user;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.domain.Authority;
import org.supermy.core.domain.Role;
import org.supermy.core.service.IUserService;
import org.supermy.core.web.BaseActionSupport;

/**
 * 角色管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 
 */
@SuppressWarnings("serial")
@Results( { @Result(name = BaseActionSupport.RELOAD, location = "role.action", type = "redirect") })
public class RoleAction extends BaseActionSupport<Role> {

	
	@Autowired
	private IUserService roleService;

	// 基本属性
	private Role role;
	private Long id;
	private List<Role> allRoles;

	// 权限相关属性
	private List<Authority> allAuths; // 全部可选权限列表
	private List<Long> checkedAuthIds;// 页面中钩选的权限id列表

	// 基本属性访问函数 //

	public Role getModel() {
		return role;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			role = roleService.getRoleUtil().get(id);
		} else {
			role = new Role();
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Role> getAllRoles() {
		return allRoles;
	}

	// CRUD Action 函数 //

	@Override
	public String list() throws Exception {
		allRoles = roleService.getRoleUtil().getAll();
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		allAuths = roleService.getAuthUtil().getAll();
		checkedAuthIds = roleService.getAuthUtil().propertyToListLong(
				role.getAuths(), "id");
		return INPUT;
	}
	

	@Override
	public String save() throws Exception {
		// 根据页面上的checkbox 整合Role的Authorities Set.
		roleService.getAuthUtil().mergeCollection(role.getAuths(),
				checkedAuthIds);

		roleService.getRoleUtil().save(role);
		addActionMessage("保存角色成功");
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			roleService.getRoleUtil().delete(id);
			addActionMessage("删除角色成功");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			addActionMessage(e.getMessage());
		}
		return RELOAD;
	}

	// 其他属性访问函数及Action函数 //

	public List<Authority> getAllAuths() {
		return allAuths;
	}

	public List<Long> getCheckedAuthIds() {
		return checkedAuthIds;
	}

	public void setCheckedAuthIds(List<Long> checkedAuthIds) {
		this.checkedAuthIds = checkedAuthIds;
	}
}