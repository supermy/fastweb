
package org.supermy.core.web.user;


import java.util.List;
import java.util.HashSet;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import org.supermy.core.domain.Authority;

import org.supermy.core.service.IUserService;
import org.supermy.core.service.Page;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.core.web.Struts2Utils;



@Results( { @Result(name = BaseActionSupport.RELOAD, 
	location = "authority.action?pageauthority.pageRequest=${pageauthority.pageRequest}", 
	type = "redirect") })
@Namespace("/user")
public class AuthorityAction extends BaseActionSupport<Authority> {

	@Autowired
	private IUserService authorityService;

	// 基本属性
	private Authority authority;
	private Long id;
	private Page<Authority> pageauthority = new Page<Authority>(5);


	// 基本属性访问函数 //
	public Authority getModel() {
		return authority;
	}


	/**
	 * @return the pageAuthority
	 */
	public Page<Authority> getPageauthority() {
		return pageauthority;
	}
	
	public void setPageauthority(Page<Authority> pageauthority) {
		this.pageauthority=pageauthority;
	}


	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			authority = authorityService.getAuthorityUtil().get(id);
		} else {
			authority = new Authority();
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



	@Override
	public String list() throws Exception {
		pageauthority = authorityService.getAuthorityUtil().get(pageauthority);
		log.debug("find :{}", pageauthority.getResult());
		log.debug("find authority by page:" + pageauthority.getResult().size());
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
	
		return INPUT;
	}

	@Override
	public String save() throws Exception {
	

		authorityService.getAuthorityUtil().save(authority);
		addActionMessage(getText("authority.updated"));
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			authorityService.getAuthorityUtil().delete(id);
			addActionMessage(getText("authority.deleted"));
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
		pageauthority.setPageSize(Page.MAX_PAGESIZE);

		Map<String, Object> filters = Struts2Utils.buildPropertyFilters("filter_");
		if (filters.size() <= 0) {
			addActionMessage(getText("authority.searchtxt"));
		}
		pageauthority = authorityService.getAuthorityUtil().search(pageauthority, filters);
		return SUCCESS;
	}

}