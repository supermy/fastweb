package org.supermy.core.web.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;
import org.supermy.core.service.IUserService;
import org.supermy.core.service.Page;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.core.web.Struts2Utils;

/**
 * 用户管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 
 */
@Results( { @Result(name = BaseActionSupport.RELOAD, location = "user.action?page.pageRequest=${page.pageRequest}", type = "redirect") })
public class UserAction extends BaseActionSupport<User> {

	@Autowired
	private IUserService userService;

	// 基本属性
	private User user;
	private Long id;
	private Page<User> pageUser = new Page<User>(5);
	private Page<Role> pageRole = new Page<Role>(30);
	// 角色相关属性
	private List<Role> allRoles; // 全部可选角色列表
	private List<Long> checkedRoleIds; // 页面中钩选的角色id列表

	// 基本属性访问函数 //
	public User getModel() {
		return user;
	}

	public User getUser() {
		return user;
	}

	/**
	 * @return the pageUser
	 */
	public Page<User> getPageUser() {
		return pageUser;
	}

	/**
	 * @return the pageRole
	 */
	public Page<Role> getPageRole() {
		return pageRole;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			user = userService.getUserUtil().get(id);
		} else {
			user = new User();
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Page<User> getPage() {
		return pageUser;
	}

	// CRUD Action 函数 //

	@Override
	public String list() throws Exception {
		pageUser = userService.getUserUtil().get(pageUser);
		log.debug("find :{}", pageUser.getResult());
		log.debug("find user by page:" + pageUser.getResult().size());

		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		allRoles = userService.getRoleUtil().getAll();
		checkedRoleIds = user.getRoleIds();
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		// 根据页面上的checkbox 整合User的Roles Set
		userService.getRoleUtil().mergeCollection(user.getRoles(),
				checkedRoleIds);
		userService.getUserUtil().save(user);
		addActionMessage("保存用户成功");
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			userService.getUserUtil().delete(id);
			addActionMessage("删除用户成功");
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
			addActionMessage(e.getMessage());
		}
		return RELOAD;
	}

	// 其他属性访问函数与Action函数 //

	public List<Role> getAllRoles() {
		return allRoles;
	}

	public List<Long> getCheckedRoleIds() {
		return checkedRoleIds;
	}

	public void setCheckedRoleIds(List<Long> checkedRoleIds) {
		this.checkedRoleIds = checkedRoleIds;
	}

	/**
	 * 根据属性过滤条件搜索用户.
	 */
	public String search() throws Exception {
		
		// 因为搜索时不保存分页参数,因此将页面大小设到最大.
		pageUser.setPageSize(Page.MAX_PAGESIZE);

		Map<String, Object> filters = buildPropertyFilters("filter_");
		if (filters.size()<=0) {
			addActionMessage("没有搜索条件");
		}
		pageUser = userService.getUserUtil().search(pageUser, filters);
		return SUCCESS;
	}

	/**
	 * 支持使用Jquery.validate Ajax检验用户名是否重复. 登录名称不允许修改
	 */
	public String checkLoginEMail() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String email = request.getParameter("email");

		if (userService.isUniqueByEMail(email)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		// 因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}
}
