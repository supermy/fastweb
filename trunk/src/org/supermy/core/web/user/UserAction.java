package org.supermy.core.web.user;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;
import org.supermy.core.service.IUserService;
import org.supermy.core.service.Page;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.core.web.Struts2Utils;

@Results( { @Result(name = BaseActionSupport.RELOAD, location = "user.action?pageuser.pageRequest=${pageuser.pageRequest}", type = "redirect") })
@Namespace("/user")
public class UserAction extends BaseActionSupport<User> {

	@Autowired
	private IUserService userService;

	@Autowired
	private SolrServer client;

	// 基本属性
	private User user;
	private Long id;
	private Page<User> pageuser = new Page<User>(5);

	private Set<Role> rolesAll;
	private java.util.List<Long> rolesId;

	// 基本属性访问函数 //
	public User getModel() {
		return user;
	}

	/**
	 * @return the pageUser
	 */
	public Page<User> getPageuser() {
		return pageuser;
	}

	public void setPageuser(Page<User> pageuser) {
		this.pageuser = pageuser;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			user = userService.getUserUtil().get(id);
		} else {
			user = new User();
		}
	}

	@Override
	protected void prepareModelSave() throws Exception {
		prepareModel();
	}

	public void setId(Long id) {
		this.id = id;
	}

	// CRUD Action 函数 //
	// 其他属性访问函数与Action函数 //

	public Set<Role> getRolesAll() {
		return rolesAll;
	}

	public List<Long> getRolesId() {
		return rolesId;
	}

	public void setRolesId(List<Long> rolesId) {
		this.rolesId = rolesId;
	}

	@Override
	public String list() throws Exception {
		pageuser = userService.getUserUtil().get(pageuser);
		log.debug("find :{}", pageuser.getResult());
		log.debug("find user by page:" + pageuser.getResult().size());
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		rolesAll = new HashSet<Role>(userService.getRoleUtil().getAll());
		rolesId = user.getRolesId();

		return INPUT;
	}

	@Override
	public String save() throws Exception {

		userService.getRoleUtil().mergeCollection(user.getRoles(), rolesId);

		userService.getUserUtil().save(user);
		addActionMessage(getText("user.updated"));
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			userService.getUserUtil().delete(id);
			addActionMessage(getText("user.deleted"));
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
			addActionMessage(e.getMessage());
		}
		return RELOAD;
	}

	// 其他属性访问函数与Action函数 //

	/**
	 * 根据属性过滤条件搜索.
	 */
	public String search() throws Exception {

		// 因为搜索时不保存分页参数,因此将页面大小设到最大.
		pageuser.setPageSize(Page.MAX_PAGESIZE);

		Map<String, Object> filters = Struts2Utils
				.buildPropertyFilters("filter_");
		if (filters.size() <= 0) {
			addActionMessage(getText("user.searchtxt"));
		}
		pageuser = userService.getUserUtil().search(pageuser, filters);
		return SUCCESS;
	}

	/**
	 * solr方式全文检索对象的String属性
	 * 
	 * @return
	 * @throws Exception
	 */
	public String fulltext() throws Exception {

		// 因为搜索时不保存分页参数,因此将页面大小设到最大.
		pageuser.setPageSize(Page.MAX_PAGESIZE);

		String q = Struts2Utils.getRequest().getParameter("q");
		if (StringUtils.isBlank(q)) {
			addActionMessage(getText("fulltext.query.notblank"));
			return RELOAD;
		}
		addActionMessage(getText("user.searchtxt"));

		pageuser = userService.getUserUtil().fullltext(pageuser, q, client);
		return SUCCESS;
	}

	public String register() throws Exception {
		log.debug("register user:{}", user);
		return "register";
	}

	public void prepareRegistersave() throws Exception {
		log.debug("prepare register save 绑定 ");
		user = new User();
		log.debug("prepare register save 绑定:{}", user);
	}

	public String registersave() throws Exception {
		log.debug("save register user:{}", user);
		user.setName(user.getEmail());
		userService.getUserUtil().save(user);
		addActionMessage(getText("user.register"));
		return RELOAD;
	}

	public void checkLoginEMail() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String email = request.getParameter("email");

		if (userService.isUniqueByEMail(email)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
	}

}