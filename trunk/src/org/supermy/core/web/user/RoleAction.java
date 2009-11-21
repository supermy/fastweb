
package org.supermy.core.web.user;


import java.util.List;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import org.supermy.core.domain.Role;

import org.supermy.core.service.IUserService;
import org.supermy.core.service.Page;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.core.web.Struts2Utils;

import org.supermy.core.domain.Authority;
import java.util.Set;


@Results( { @Result(name = BaseActionSupport.RELOAD, 
	location = "role.action?pagerole.pageRequest=${pagerole.pageRequest}", 
	type = "redirect") })
@Namespace("/user")
public class RoleAction extends BaseActionSupport<Role> {

	@Autowired
	private IUserService roleService;


	// 基本属性
	private Role role;
	private Long id;
	private Page<Role> pagerole = new Page<Role>(5);

	private Set<Authority> authsAll;
	private java.util.List<Long> authsId;

	// 基本属性访问函数 //
	public Role getModel() {
		return role;
	}


	/**
	 * @return the pageRole
	 */
	public Page<Role> getPagerole() {
		return pagerole;
	}
	
	public void setPagerole(Page<Role> pagerole) {
		this.pagerole=pagerole;
	}


	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			role = roleService.getRoleUtil().get(id);
		} else {
			role = new Role();
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

	public Set<Authority> getAuthsAll() {
		return authsAll;
	}
	public List<Long> getAuthsId() {
		return authsId;
	}
	public void setAuthsId(List<Long> authsId) {
		this.authsId = authsId;
	}


	@Override
	public String list() throws Exception {
		pagerole = roleService.getRoleUtil().get(pagerole);
		log.debug("find :{}", pagerole.getResult());
		log.debug("find role by page:" + pagerole.getResult().size());
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		authsAll= new HashSet<Authority>(roleService.getAuthorityUtil().getAll());
		authsId=role.getAuthsId();
	
		return INPUT;
	}

	@Override
	public String save() throws Exception {
	
		roleService.getAuthorityUtil().mergeCollection(role.getAuths(),authsId);

		roleService.getRoleUtil().save(role);
		addActionMessage(getText("role.updated"));
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			roleService.getRoleUtil().delete(id);
			addActionMessage(getText("role.deleted"));
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
		pagerole.setPageSize(Page.MAX_PAGESIZE);

		Map<String, Object> filters = Struts2Utils.buildPropertyFilters("filter_");
		if (filters.size() <= 0) {
			addActionMessage(getText("role.searchtxt"));
		}
		pagerole = roleService.getRoleUtil().search(pagerole, filters);
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
		pagerole.setPageSize(Page.MAX_PAGESIZE);

		String q = Struts2Utils.getRequest().getParameter("q");
		if (StringUtils.isBlank(q)) {
			addActionMessage(getText("fulltext.query.notblank"));
			return RELOAD;
		}
		addActionMessage(getText("common.domain.fulltext")+" ["+q+"] ");

		pagerole = roleService.getRoleUtil().fullltext(pagerole, q, getClient());

		return SUCCESS;
	}

	
}