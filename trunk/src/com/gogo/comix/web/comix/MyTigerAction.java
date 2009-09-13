
package com.gogo.comix.web.comix;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.gogo.comix.domain.MyTiger;
import com.gogo.comix.service.IMyTigerService;
import org.supermy.core.service.Page;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.core.web.Struts2Utils;

@Results( { @Result(name = BaseActionSupport.RELOAD, 
	location = "my-tiger.action?pagemytiger.pageRequest=${pagemytiger.pageRequest}", 
	type = "redirect") })
public class MyTigerAction extends BaseActionSupport<MyTiger> {

	@Autowired
	private IMyTigerService myTigerService;

	// 基本属性
	private MyTiger myTiger;
	private Long id;
	private Page<MyTiger> pagemytiger = new Page<MyTiger>(5);

	// 基本属性访问函数 //
	public MyTiger getModel() {
		return myTiger;
	}


	/**
	 * @return the pageMyTiger
	 */
	public Page<MyTiger> getPagemytiger() {
		return pagemytiger;
	}


	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			myTiger = myTigerService.getMyTigerUtil().get(id);
		} else {
			myTiger = new MyTiger();
		}
	}

	public void setId(Long id) {
		this.id = id;
	}



	// CRUD Action 函数 //

	@Override
	public String list() throws Exception {
		pagemytiger = myTigerService.getMyTigerUtil().get(pagemytiger);
		log.debug("find :{}", pagemytiger.getResult());
		log.debug("find myTiger by page:" + pagemytiger.getResult().size());
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		myTigerService.getMyTigerUtil().save(myTiger);
		addActionMessage(getText("myTiger.updated"));
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			myTigerService.getMyTigerUtil().delete(id);
			addActionMessage(getText("myTiger.deleted"));
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
		pagemytiger.setPageSize(Page.MAX_PAGESIZE);

		Map<String, Object> filters = Struts2Utils.buildPropertyFilters("filter_");
		if (filters.size() <= 0) {
			addActionMessage(getText("myTiger.searchtxt"));
		}
		pagemytiger = myTigerService.getMyTigerUtil().search(pagemytiger, filters);
		return SUCCESS;
	}

}