package org.supermy.core.web.user;

import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import org.supermy.core.domain.UrlResource;
import org.supermy.core.service.IUserService;
import org.supermy.core.service.Page;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.core.web.Struts2Utils;

import org.supermy.core.domain.Authority;
import java.util.Collection;
import org.supermy.core.domain.User;


@Results( { @Result(name = BaseActionSupport.RELOAD, 
	location = "url-resource.action?pageurlresource.pageRequest=${pageurlresource.pageRequest}", 
	type = "redirect") })
@Namespace("/user")
public class UrlResourceAction extends BaseActionSupport<UrlResource> {

	@Autowired
	private IUserService urlResourceService;

	// 基本属性
	private UrlResource urlResource;
	private Long id;
	private Page<UrlResource> pageurlresource = new Page<UrlResource>(5);

	private Collection<Authority> authorityListAll;
	private java.util.List<Long> authorityListId;
	private java.util.List<User> managerList;

	// 基本属性访问函数 //
	public UrlResource getModel() {
		return urlResource;
	}


	/**
	 * @return the pageUrlResource
	 */
	public Page<UrlResource> getPageurlresource() {
		return pageurlresource;
	}


	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			urlResource = urlResourceService.getUrlResourceUtil().get(id);
		} else {
			urlResource = new UrlResource();
		}
	}

	@Override
	protected void prepareModelSave() throws Exception {
		prepareModel();
		
		urlResource.setManager(new User());
	}

	public void setId(Long id) {
		this.id = id;
	}

	// CRUD Action 函数 //
	// 其他属性访问函数与Action函数 //

	public Collection<Authority> getAuthorityListAll() {
		return authorityListAll;
	}
	public List<Long> getAuthorityListId() {
		return authorityListId;
	}
	public void setAuthorityListId(List<Long> authorityListId) {
		this.authorityListId = authorityListId;
	}
	public java.util.List<User> getManagerList() {
		return managerList;
	}


	@Override
	public String list() throws Exception {
		pageurlresource = urlResourceService.getUrlResourceUtil().get(pageurlresource);
		log.debug("find :{}", pageurlresource.getResult());
		log.debug("find urlResource by page:" + pageurlresource.getResult().size());
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		authorityListAll= urlResourceService.getAuthorityUtil().getAll();
		authorityListId=urlResource.getAuthorityListId();
		managerList= urlResourceService.getUserUtil().getAll();
	
		return INPUT;
	}

	@Override
	public String save() throws Exception {
	
		urlResourceService.getAuthorityUtil().mergeCollection(urlResource.getAuthorityList(),authorityListId);
		//managerList= urlResourceService.getUserUtil().getAll();

		urlResourceService.getUrlResourceUtil().save(urlResource);
		addActionMessage(getText("urlResource.updated"));
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			urlResourceService.getUrlResourceUtil().delete(id);
			addActionMessage(getText("urlResource.deleted"));
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
		pageurlresource.setPageSize(Page.MAX_PAGESIZE);

		Map<String, Object> filters = Struts2Utils.buildPropertyFilters("filter_");
		if (filters.size() <= 0) {
			addActionMessage(getText("urlResource.searchtxt"));
		}
		pageurlresource = urlResourceService.getUrlResourceUtil().search(pageurlresource, filters);
		return SUCCESS;
	}

}