
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

import org.supermy.core.domain.GroupUser;
import org.supermy.core.service.IUserService;
import org.supermy.core.service.Page;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.core.web.Struts2Utils;

import org.supermy.core.domain.Group;
import org.supermy.core.domain.User;


@Results( { @Result(name = BaseActionSupport.RELOAD, 
	location = "group-user.action?pagegroupuser.pageRequest=${pagegroupuser.pageRequest}", 
	type = "redirect") })
@Namespace("/user")
public class GroupUserAction extends BaseActionSupport<GroupUser> {

	@Autowired
	private IUserService groupUserService;


	// 基本属性
	private GroupUser groupUser;
	private Long id;
	private Page<GroupUser> pagegroupuser = new Page<GroupUser>(5);

	private java.util.List<Group> groupList;
	private java.util.List<User> userList;

	// 基本属性访问函数 //
	public GroupUser getModel() {
		return groupUser;
	}


	/**
	 * @return the pageGroupUser
	 */
	public Page<GroupUser> getPagegroupuser() {
		return pagegroupuser;
	}
	
	public void setPagegroupuser(Page<GroupUser> pagegroupuser) {
		this.pagegroupuser=pagegroupuser;
	}


	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			groupUser = groupUserService.getGroupUserUtil().get(id);
		} else {
			groupUser = new GroupUser();
		}
	}

	@Override
	protected void prepareModelSave() throws Exception {
		prepareModel();
		
		groupUser.setGroup(new Group());
		groupUser.setUser(new User());
	}

	public void setId(Long id) {
		this.id = id;
	}

	// CRUD Action 函数 //
	// 其他属性访问函数与Action函数 //

	public java.util.List<Group> getGroupList() {
		return groupList;
	}
	public java.util.List<User> getUserList() {
		return userList;
	}


	@Override
	public String list() throws Exception {
		pagegroupuser = groupUserService.getGroupUserUtil().get(pagegroupuser);
		log.debug("find :{}", pagegroupuser.getResult());
		log.debug("find groupUser by page:" + pagegroupuser.getResult().size());
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		groupList= groupUserService.getGroupUtil().getAll();
		userList= groupUserService.getUserUtil().getAll();
	
		return INPUT;
	}

	@Override
	public String save() throws Exception {
	
		//groupList= groupUserService.getGroupUtil().getAll();
		if (groupUser.getGroup().getId()==0) {
			groupUser.setGroup(null);
		}
		//userList= groupUserService.getUserUtil().getAll();
		if (groupUser.getUser().getId()==0) {
			groupUser.setUser(null);
		}

		groupUserService.getGroupUserUtil().save(groupUser);
		addActionMessage(getText("groupUser.updated"));
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			groupUserService.getGroupUserUtil().delete(id);
			addActionMessage(getText("groupUser.deleted"));
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
		pagegroupuser.setPageSize(Page.MAX_PAGESIZE);

		Map<String, Object> filters = Struts2Utils.buildPropertyFilters("filter_");
		if (filters.size() <= 0) {
			addActionMessage(getText("groupUser.searchtxt"));
		}
		pagegroupuser = groupUserService.getGroupUserUtil().search(pagegroupuser, filters);
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
		pagegroupuser.setPageSize(Page.MAX_PAGESIZE);

		String q = Struts2Utils.getRequest().getParameter("q");
		if (StringUtils.isBlank(q)) {
			addActionMessage(getText("fulltext.query.notblank"));
			return RELOAD;
		}
		addActionMessage(getText("common.domain.fulltext")+" ["+q+"] ");

		pagegroupuser = groupUserService.getGroupUserUtil().fullltext(pagegroupuser, q, getClient());

		return SUCCESS;
	}



}