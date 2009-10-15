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

	<link rel="stylesheet" href="${'$'}{css}/css/table/iqcontent.css" type="text/css" media="screen, projection"/>

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
			<div>
				<s:text name="common.page.by"/>${'$'}{page${pojo.shortName.toLowerCase()}.pageNo}<s:text name="common.page.page"/>, <s:text name="common.page.total"/>${'$'}{page${pojo.shortName.toLowerCase()}.totalPages}<s:text name="common.page.page"/> 
				<s:if test="page${pojo.shortName.toLowerCase()}.hasPre">
					<a href="${util.build(pojo.shortName)}.action?page${pojo.shortName.toLowerCase()}.pageNo=${'$'}{page${pojo.shortName.toLowerCase()}.prePage}&page${pojo.shortName.toLowerCase()}.orderBy=${'$'}{page${pojo.shortName.toLowerCase()}.orderBy}&page${pojo.shortName.toLowerCase()}.order=${'$'}{page${pojo.shortName.toLowerCase()}.order}"><s:text name="common.page.pre"/></a>
				</s:if>
				<s:if test="page${pojo.shortName.toLowerCase()}.hasNext">
					<a href="${util.build(pojo.shortName)}.action?page${pojo.shortName.toLowerCase()}.pageNo=${'$'}{page${pojo.shortName.toLowerCase()}.nextPage}&page${pojo.shortName.toLowerCase()}.orderBy=${'$'}{page${pojo.shortName.toLowerCase()}.orderBy}&page${pojo.shortName.toLowerCase()}.order=${'$'}{page${pojo.shortName.toLowerCase()}.order}"><s:text name="common.page.next"/></a>
				</s:if>
				<br />
			</div>
		</c:set>
		<c:out value="${'$'}{buttons}" escapeXml="false" />
			<#include "list-view-custom.ftl"/>
			<!--#include "list-view-displaytag.ftl"/-->
		<c:out value="${'$'}{buttons}" escapeXml="false" />		
		
	</div>
	
	<!-- 布局  右边列-->
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
		<security:authorize 
			ifAnyGranted="AUTH_EDIT_${pojo.shortName.toUpperCase()}">
			<a  class="button" 
				href="${util.build(pojo.shortName)}!input.action?page${pojo.shortName.toLowerCase()}.pageRequest=${'$'}{page${pojo.shortName.toLowerCase()}.pageRequest}">
				<s:text name="common.domain.create"/>
			</a>
		</security:authorize>
		<hr class="space"/>
		<div id="filter">
			<form 
				action="${util.build(pojo.shortName)}.action" 
				method="post">
				<input type="text" 
						name="filter_LIKE_${webdata.concatFieldByString(clazz.getMappedClass())}" 
						value="${'$'}{param['filter_LIKE_${webdata.concatFieldByString(clazz.getMappedClass())}']}" 
						size="10"/>
				<s:submit 
					cssClass="button" 
					method="search"  
					key="common.domain.search" 
					/>
			</form>
		</div> 
	</div>
		
	<!-- 布局  底 -->
	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>
