package org.supermy.core.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.domain.BaseDomain;
import org.supermy.core.service.IHqlService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * 测试HQL查询语句专用
 * 
 * 
 */
public class HqlAction extends ActionSupport {

	@Autowired
	private IHqlService hqlService;

	// 能够查询所有的对象
	private List<BaseDomain> page = new ArrayList<BaseDomain>();

	public List<BaseDomain> getPage() {
		return page;
	}

	/**
	 * 根据属性过滤条件搜索用户.
	 */
	public String search() throws Exception {
		String search = Struts2Utils.getRequest().getParameter("search");
		page = hqlService.getUtil().find(search);
		addActionMessage(" ["+search+" ] "+"执行查询成功");
		return SUCCESS;
	}

}
