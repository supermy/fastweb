
package org.supermy.core.web.user;


import java.util.List;
import java.util.HashSet;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import org.supermy.core.domain.Group;
import org.supermy.core.service.IUserService;
import org.supermy.core.service.Page;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.core.web.Struts2Utils;

import org.supermy.core.domain.Group;


@Results( { @Result(name = BaseActionSupport.RELOAD, 
	location = "group.action?pagegroup.pageRequest=${pagegroup.pageRequest}", 
	type = "redirect") })
@Namespace("/user")
public class GroupAction extends BaseActionSupport<Group> {

	@Autowired
	private IUserService groupService;


	// 基本属性
	private Group group;
	private Long id;
	private Page<Group> pagegroup = new Page<Group>(5);

	private java.util.List<Group> parentList;

	// 基本属性访问函数 //
	public Group getModel() {
		return group;
	}


	/**
	 * @return the pageGroup
	 */
	public Page<Group> getPagegroup() {
		return pagegroup;
	}
	
	public void setPagegroup(Page<Group> pagegroup) {
		this.pagegroup=pagegroup;
	}


	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			group = groupService.getGroupUtil().get(id);
		} else {
			group = new Group();
		}
	}

	@Override
	protected void prepareModelSave() throws Exception {
		prepareModel();
		
		group.setParent(new Group());
	}

	public void setId(Long id) {
		this.id = id;
	}

	// CRUD Action 函数 //
	// 其他属性访问函数与Action函数 //

	public java.util.List<Group> getParentList() {
		return parentList;
	}


	@Override
	public String list() throws Exception {
		pagegroup = groupService.getGroupUtil().get(pagegroup);
		log.debug("find :{}", pagegroup.getResult());
		log.debug("find group by page:" + pagegroup.getResult().size());
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		parentList= groupService.getGroupUtil().getAll();
	
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		if (group.getParent().getId()==0) {
			group.setParent(null);
		}
		groupService.getGroupUtil().save(group);
		addActionMessage(getText("group.updated"));
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			groupService.getGroupUtil().delete(id);
			addActionMessage(getText("group.deleted"));
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
		pagegroup.setPageSize(Page.MAX_PAGESIZE);

		Map<String, Object> filters = Struts2Utils.buildPropertyFilters("filter_");
		if (filters.size() <= 0) {
			addActionMessage(getText("group.searchtxt"));
		}
		pagegroup = groupService.getGroupUtil().search(pagegroup, filters);
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
		pagegroup.setPageSize(Page.MAX_PAGESIZE);

		String q = Struts2Utils.getRequest().getParameter("q");
		if (StringUtils.isBlank(q)) {
			addActionMessage(getText("fulltext.query.notblank"));
			return RELOAD;
		}
		addActionMessage(getText("common.domain.fulltext")+" ["+q+"] ");

		pagegroup = groupService.getGroupUtil().fullltext(pagegroup, q, getClient());

		return SUCCESS;
	}



}