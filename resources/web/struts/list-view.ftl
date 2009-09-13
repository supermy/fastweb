<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<#assign basepackage = pojo.packageName.substring(0,pojo.packageName.lastIndexOf("."))>
<#assign basepackage = basepackage.substring(basepackage.lastIndexOf(".")+1)>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="${pojoNameLower}List.title"/></title>
    <meta name="heading" content="<s:text name='${pojoNameLower}List.heading'/>"/>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>
</head>
<body>
<!-- 布局 容器  -->
<div class="container">
	<!-- 布局  顶部工具条-->
	<%@ include file="/common/tools.jsp"%>
	<!-- 布局  顶部导航栏目-->
	<%@ include file="/common/nav.jsp"%>
	<!-- 布局  左边列-->
	<div class="span-17 prepend-1 colborder">
		<c:set var="buttons">
			<jsp:include flush="false" page="/common/pagenav.jsp"> 
				<jsp:param   name="pagenav" value="page${pojo.shortName.toLowerCase()}"/>  
			</jsp:include> 			
		</c:set>
		<c:out value="${'$'}{buttons}" escapeXml="false" />
		<display:table 
			id="${pojoNameLower}List" name="page${pojo.shortName.toLowerCase()}.result" 
			sort="external" class="table" requestURI="" export="false">
			<#foreach field in pojo.getAllPropertiesIterator()>
				<#if field.equals(pojo.identifierProperty)>
				    <display:column property="${field.name}" 
				    	sortable="false"
				    	sortName="${field.name}" 
				    	href="${util.build(pojo.shortName)}!input.action?page${pojo.shortName.toLowerCase()}.pageRequest=${'$'}{page${pojo.shortName.toLowerCase()}.pageRequest}" media="html"
				        paramId="${field.name}" 
				        paramProperty="${field.name}" 
				        titleKey="${pojoNameLower}.${field.name}"/>
				    <display:column 
				    	property="${field.name}" 
				    	media="csv excel xml pdf"
				    	titleKey="${pojoNameLower}.${field.name}"/>
				    	
				<#elseif !c2h.isCollection(field) && !c2h.isManyToOne(field) && !c2j.isComponent(field)>
				
				    <#if field.value.typeName == "java.util.Date">
			            <display:column property="${field.name}"  format="{0,date,yyyy-MM-dd}" sortProperty="${field.name}" sortable="false" sortName="${field.name}" titleKey="${pojoNameLower}.${field.name}"/>
				    <#elseif field.value.typeName == "java.lang.Double" || field.value.typeName == "java.lang.Number" || field.value.typeName == "java.math.BigDecimal">
			             <display:column sortProperty="${field.name}" format="{0,number, 0,000,000.00}" sortable="false" sortName="${field.name}" titleKey="${pojoNameLower}.${field.name}"/>
				    <#elseif field.value.typeName == "boolean" || field.value.typeName == "java.lang.Boolean">
			             <display:column sortProperty="${field.name}" sortable="false" sortName="${field.name}" titleKey="${pojoNameLower}.${field.name}">
			                 <input type="checkbox" disabled="disabled" <c:if test="${'$'}{${pojoNameLower}List.${field.name}}">checked="checked"</c:if>/>
			             </display:column>
				    <#else>
		        	    <display:column property="${field.name}" sortable="false" sortName="${field.name}" titleKey="${pojoNameLower}.${field.name}"/>
				    
				    </#if>
				</#if>
			</#foreach>
			
		    <display:setProperty name="paging.banner.item_name"><s:text name="${pojoNameLower}List.${pojoNameLower}"/></display:setProperty>
		    <display:setProperty name="paging.banner.items_name"><s:text name="${pojoNameLower}List.${pojoNameLower}s"/></display:setProperty>
		
		    <display:setProperty name="export.excel.filename"><s:text name="${pojoNameLower}List.title"/>.xls</display:setProperty>
		    <display:setProperty name="export.csv.filename"><s:text name="${pojoNameLower}List.title"/>.csv</display:setProperty>
		    <display:setProperty name="export.pdf.filename"><s:text name="${pojoNameLower}List.title"/>.pdf</display:setProperty>
		</display:table>
		<c:out value="${'$'}{buttons}" escapeXml="false" />		
		
	</div>
	
	<!-- 布局  右边列-->
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
		<security:authorize ifAnyGranted="AUTH_MODIFY_USER">
			<a href="${util.build(pojo.shortName)}!input.action?page${pojo.shortName.toLowerCase()}.pageRequest=${'$'}{page${pojo.shortName.toLowerCase()}.pageRequest}"><s:text name="common.domain.create"/></a>
		</security:authorize>
		<hr class="space"/>
		<div id="filter">
			<form action="${pojo.shortName}!search.action" method="post">
				<input type="text" 
						name="filter_LIKE_name|email" 
						value="${'$'}{param['filter_LIKE_name|email']}" 
						size="10"/>
				<input type="submit" value="<s:text name='common.domain.search'/>" />
			</form>
		</div> 
	</div>
		
	<!-- 布局  底-->
	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>