<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<#assign basepackage = pojo.packageName.substring(0,pojo.packageName.lastIndexOf("."))>

<#assign actionpath = basepackage.substring(basepackage.lastIndexOf(".")+1)>

package ${basepackage}.web.${actionpath};

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import ${pojo.packageName}.${pojo.shortName};
import ${basepackage}.service.I${pojo.shortName}Service;
import org.supermy.core.service.Page;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.core.web.Struts2Utils;

@Results( { @Result(name = BaseActionSupport.RELOAD, 
	location = "${util.build(pojo.shortName)}.action?page${pojo.shortName.toLowerCase()}.pageRequest=${'$'}{page${pojo.shortName.toLowerCase()}.pageRequest}", 
	type = "redirect") })
public class ${pojo.shortName}Action extends BaseActionSupport<${pojo.shortName}> {

	@Autowired
	private I${pojo.shortName}Service ${pojoNameLower}Service;

	// 基本属性
	private ${pojo.shortName} ${pojoNameLower};
	private Long id;
	private Page<${pojo.shortName}> page${pojo.shortName.toLowerCase()} = new Page<${pojo.shortName}>(5);

	// 基本属性访问函数 //
	public ${pojo.shortName} getModel() {
		return ${pojoNameLower};
	}


	/**
	 * @return the page${pojo.shortName}
	 */
	public Page<${pojo.shortName}> getPage${pojo.shortName.toLowerCase()}() {
		return page${pojo.shortName.toLowerCase()};
	}


	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			${pojoNameLower} = ${pojoNameLower}Service.get${pojo.shortName}Util().get(id);
		} else {
			${pojoNameLower} = new ${pojo.shortName}();
		}
	}

	public void setId(Long id) {
		this.id = id;
	}



	// CRUD Action 函数 //

	@Override
	public String list() throws Exception {
		page${pojo.shortName.toLowerCase()} = ${pojoNameLower}Service.get${pojo.shortName}Util().get(page${pojo.shortName.toLowerCase()});
		log.debug("find :{}", page${pojo.shortName.toLowerCase()}.getResult());
		log.debug("find ${pojoNameLower} by page:" + page${pojo.shortName.toLowerCase()}.getResult().size());
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		${pojoNameLower}Service.get${pojo.shortName}Util().save(${pojoNameLower});
		addActionMessage(getText("${pojoNameLower}.updated"));
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			${pojoNameLower}Service.get${pojo.shortName}Util().delete(id);
			addActionMessage(getText("${pojoNameLower}.deleted"));
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
		page${pojo.shortName.toLowerCase()}.setPageSize(Page.MAX_PAGESIZE);

		Map<String, Object> filters = Struts2Utils.buildPropertyFilters("filter_");
		if (filters.size() <= 0) {
			addActionMessage(getText("${pojoNameLower}.searchtxt"));
		}
		page${pojo.shortName.toLowerCase()} = ${pojoNameLower}Service.get${pojo.shortName}Util().search(page${pojo.shortName.toLowerCase()}, filters);
		return SUCCESS;
	}

}