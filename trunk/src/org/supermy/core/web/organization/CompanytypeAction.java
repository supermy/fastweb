package org.supermy.core.web.organization;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.domain.CompanyType;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.User;
import org.supermy.core.service.OrganizationService;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.workflow.domain.TaskItem;
import org.supermy.workflow.service.WorkflowService;

/**
 * 工作流方面的查询，分页的话需要重写查询语句.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 
 */
/**
 * @author my
 * 
 */
@Results( { @Result(name = BaseActionSupport.RELOAD, location = "companytype.action", type = "redirect") })
public class CompanytypeAction extends BaseActionSupport<CompanyType> {

	
	@Autowired
	private OrganizationService organizationService;

	// 基本属性
	private CompanyType companyType;
	private Long id;
	private List<CompanyType> companyTypes;	

	// 基本属性访问函数 //
	public CompanyType getModel() {
		return companyType;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			companyType = organizationService.getCompanyTypeUtil().get(id);
			log.debug("company type  name:{}", companyType.getName());
		} else {
			companyType = new CompanyType();
		}
	}

	@Override
	protected void prepareModelSave() throws Exception {
		prepareModel();
		
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the companyTypes
	 */
	public List<CompanyType> getCompanyTypes() {
		return companyTypes;
	}

	// CRUD Action 函数 //

	@Override
	public String list() throws Exception {
		companyTypes=organizationService.getCompanyTypeUtil().getAll();
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		organizationService.getCompanyTypeUtil().save(companyType);

		addActionMessage("保存公司类型成功");
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			organizationService.getCompanyTypeUtil().delete(id);
			addActionMessage("删除公司类型成功");
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
			addActionMessage(e.getMessage());
		}
		return RELOAD;
	}



}
