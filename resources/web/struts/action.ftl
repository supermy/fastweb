<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<#assign basepackage = pojo.packageName.substring(0,pojo.packageName.lastIndexOf("."))>

<#assign actionpath = basepackage.substring(basepackage.lastIndexOf(".")+1)>

package ${basepackage}.web.${actionpath};

${pojo.generateImports()}
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

import ${pojo.packageName}.${pojo.shortName};
import ${basepackage}.service.I${pojo.shortName}Service;
import org.supermy.core.service.Page;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.core.web.Struts2Utils;

<#foreach field in pojo.getAllPropertiesIterator()>
	<#if c2h.isCollection(field)>
		<#lt/>import ${field.value.element.type.returnedClass.name};
		<#lt/>import ${field.value.type.returnedClass.name};
	<#elseif c2h.isManyToOne(field)>
		<#lt/>import ${field.value.type.returnedClass.name};
	<#elseif c2j.isComponent(field)>
		<#lt/>	todo ... ...
	</#if>
</#foreach>


@Results( { @Result(name = BaseActionSupport.RELOAD, 
	location = "${util.build(pojo.shortName)}.action?page${pojo.shortName.toLowerCase()}.pageRequest=${'$'}{page${pojo.shortName.toLowerCase()}.pageRequest}", 
	type = "redirect") })
@Namespace("/${actionpath}")
public class ${pojo.shortName}Action extends BaseActionSupport<${pojo.shortName}> {

	@Autowired
	private I${pojo.shortName}Service ${pojoNameLower}Service;

	@Autowired
	private SolrServer client;

	// 基本属性
	private ${pojo.shortName} ${pojoNameLower};
	private Long id;
	private Page<${pojo.shortName}> page${pojo.shortName.toLowerCase()} = new Page<${pojo.shortName}>(5);

	<#foreach field in pojo.getAllPropertiesIterator()>
		<#if c2h.isCollection(field)>
			<#lt/>	${pojo.getFieldModifiers(field)} ${pojo.getJavaTypeName(field, true)} ${field.name}All;
			<#lt/>	${pojo.getFieldModifiers(field)} java.util.List<Long> ${field.name}Id;
		<#elseif c2h.isManyToOne(field)>
			<#lt/>	${pojo.getFieldModifiers(field)} java.util.List<${pojo.getJavaTypeName(field, true)}> ${field.name}List;
		<#elseif c2j.isComponent(field)>
			<#lt/>	todo ... ...
		</#if>
	</#foreach>

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
	
	public void setPage${pojo.shortName.toLowerCase()}(Page<${pojo.shortName}> page${pojo.shortName.toLowerCase()}) {
		this.page${pojo.shortName.toLowerCase()}=page${pojo.shortName.toLowerCase()};
	}


	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			${pojoNameLower} = ${pojoNameLower}Service.get${pojo.shortName}Util().get(id);
		} else {
			${pojoNameLower} = new ${pojo.shortName}();
		}
	}

	@Override
	protected void prepareModelSave() throws Exception {
		prepareModel();
		
		<#foreach field in pojo.getAllPropertiesIterator()>
			<#if c2h.isCollection(field)>
			<#elseif c2h.isManyToOne(field)>
				<#lt/>		${pojoNameLower}.set${field.name.substring(0,1).toUpperCase()+field.name.substring(1)}(new ${pojo.getJavaTypeName(field, true)}());
			<#elseif c2j.isComponent(field)>
				<#lt/>	todo ... ...
			</#if>
		</#foreach>
	}

	public void setId(Long id) {
		this.id = id;
	}

	// CRUD Action 函数 //
	// 其他属性访问函数与Action函数 //

	<#foreach field in pojo.getAllPropertiesIterator()>
		<#if c2h.isCollection(field)>
			<#lt/>	public ${pojo.getJavaTypeName(field, true)} get${field.name.substring(0,1).toUpperCase()+field.name.substring(1)}All() {
			<#lt/>		return ${field.name}All;
			<#lt/>	}
			<#lt/>	public List<Long> get${field.name.substring(0,1).toUpperCase()+field.name.substring(1)}Id() {
			<#lt/>		return ${field.name}Id;
			<#lt/>	}
			<#lt/>	public void set${field.name.substring(0,1).toUpperCase()+field.name.substring(1)}Id(List<Long> ${field.name}Id) {
			<#lt/>		this.${field.name}Id = ${field.name}Id;
			<#lt/>	}
		<#elseif c2h.isManyToOne(field)>
			<#lt/>	public java.util.List<${pojo.getJavaTypeName(field, true)}> get${field.name.substring(0,1).toUpperCase()+field.name.substring(1)}List() {
			<#lt/>		return ${field.name}List;
			<#lt/>	}
		<#elseif c2j.isComponent(field)>
			<#lt/>	todo ... ...
	    </#if>
	</#foreach>


	@Override
	public String list() throws Exception {
		page${pojo.shortName.toLowerCase()} = ${pojoNameLower}Service.get${pojo.shortName}Util().get(page${pojo.shortName.toLowerCase()});
		log.debug("find :{}", page${pojo.shortName.toLowerCase()}.getResult());
		log.debug("find ${pojoNameLower} by page:" + page${pojo.shortName.toLowerCase()}.getResult().size());
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
	<#foreach field in pojo.getAllPropertiesIterator()>
		<#if c2h.isCollection(field)>
			<#assign fieldtype = field.value.element.type.returnedClass.simpleName>
			<#lt/>		${field.name}All= new HashSet<${fieldtype}>(${pojoNameLower}Service.get${fieldtype}Util().getAll());
			<#lt/>		${field.name}Id=${pojoNameLower}.get${field.name.substring(0,1).toUpperCase()+field.name.substring(1)}Id();
		<#elseif c2h.isManyToOne(field)>
			<#lt/>		${field.name}List= ${pojoNameLower}Service.get${field.value.type.returnedClass.simpleName}Util().getAll();
		<#elseif c2j.isComponent(field)>
			<#lt/>todo ... ...
	    </#if>
	</#foreach>
	
		return INPUT;
	}

	@Override
	public String save() throws Exception {
	
	<#foreach field in pojo.getAllPropertiesIterator()>
		<#if c2h.isCollection(field)>
			<#lt/>		${pojoNameLower}Service.get${field.value.element.type.returnedClass.simpleName}Util().mergeCollection(${pojoNameLower}.get${field.name.substring(0,1).toUpperCase()+field.name.substring(1)}(),${field.name}Id);
		<#elseif c2h.isManyToOne(field)>
			<#lt/>		//${field.name}List= ${pojoNameLower}Service.get${field.value.type.returnedClass.simpleName}Util().getAll();
		<#elseif c2j.isComponent(field)>
			<#lt/>todo ... ...
	    </#if>
	</#foreach>

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

	/**
	 * solr方式全文检索对象的String属性
	 * 
	 * @return
	 * @throws Exception
	 */
	public String fulltext() throws Exception {

		// 因为搜索时不保存分页参数,因此将页面大小设到最大.
		page${pojo.shortName.toLowerCase()}.setPageSize(Page.MAX_PAGESIZE);

		String q = Struts2Utils.getRequest().getParameter("q");
		if (StringUtils.isBlank(q)) {
			addActionMessage(getText("fulltext.query.notblank"));
			return RELOAD;
		}
		addActionMessage(getText("common.domain.fulltext")+" ["+q+"] ");

		page${pojo.shortName.toLowerCase()} = ${pojoNameLower}Service.get${pojo.shortName}Util().fullltext(page${pojo.shortName.toLowerCase()}, q, client);

		return SUCCESS;
	}



}