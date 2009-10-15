<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="urlResourceList.title"/></title>
    <meta name="heading" content="<s:text name='urlResourceList.heading'/>"/>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>

	<link rel="stylesheet" href="${css}/css/table/iqcontent.css" type="text/css" media="screen, projection"/>

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
				<s:text name="common.page.by"/>${pageurlresource.pageNo}<s:text name="common.page.page"/>, <s:text name="common.page.total"/>${pageurlresource.totalPages}<s:text name="common.page.page"/> 
				<s:if test="pageurlresource.hasPre">
					<a href="url-resource.action?pageurlresource.pageNo=${pageurlresource.prePage}&pageurlresource.orderBy=${pageurlresource.orderBy}&pageurlresource.order=${pageurlresource.order}"><s:text name="common.page.pre"/></a>
				</s:if>
				<s:if test="pageurlresource.hasNext">
					<a href="url-resource.action?pageurlresource.pageNo=${pageurlresource.nextPage}&pageurlresource.orderBy=${pageurlresource.orderBy}&pageurlresource.order=${pageurlresource.order}"><s:text name="common.page.next"/></a>
				</s:if>
				<br />
			</div>
		</c:set>
		<c:out value="${buttons}" escapeXml="false" />
		<display:table 
			id="urlResourceList" name="pageurlresource.result" 
			sort="external" class="table" requestURI="" export="false">
				    <display:column property="id" 
				    	sortable="false"
				    	sortName="id" 
				    	href="url-resource!input.action?pageurlresource.pageRequest=${pageurlresource.pageRequest}" media="html"
				        paramId="id" 
				        paramProperty="id" 
				        titleKey="urlResource.id"/>
				    <display:column 
				    	property="id" 
				    	media="csv excel xml pdf"
				    	titleKey="urlResource.id"/>
				    	
		        		<display:column property="authorityListName" sortable="false" sortName="authorityList" titleKey="urlResource.authorityList"/>
		        	    <display:column property="desc" sortable="false" sortName="desc" titleKey="urlResource.desc"/>
		        		<display:column property="manager.name" sortable="false" sortName="manager" titleKey="urlResource.manager"/>
		        	    <display:column property="position" sortable="false" sortName="position" titleKey="urlResource.position"/>
		        	    <display:column property="resourceType" sortable="false" sortName="resourceType" titleKey="urlResource.resourceType"/>
		        	    <display:column property="value" sortable="false" sortName="value" titleKey="urlResource.value"/>
			
		    <display:setProperty name="paging.banner.item_name"><s:text name="urlResourceList.urlResource"/></display:setProperty>
		    <display:setProperty name="paging.banner.items_name"><s:text name="urlResourceList.urlResources"/></display:setProperty>
		
		    <display:setProperty name="export.excel.filename"><s:text name="urlResourceList.title"/>.xls</display:setProperty>
		    <display:setProperty name="export.csv.filename"><s:text name="urlResourceList.title"/>.csv</display:setProperty>
		    <display:setProperty name="export.pdf.filename"><s:text name="urlResourceList.title"/>.pdf</display:setProperty>
		</display:table>
		<c:out value="${buttons}" escapeXml="false" />		
		
	</div>
	
	<!-- 布局  右边列-->
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
		<security:authorize ifAnyGranted="AUTH_EDIT_URLRESOURCE">
			<a  class="button" href="url-resource!input.action?pageurlresource.pageRequest=${pageurlresource.pageRequest}"><s:text name="common.domain.create"/></a>
		</security:authorize>
		<hr class="space"/>
		<div id="filter">
			<form action="url-resource.action" method="post">
				<input type="text" 
						name="filter_LIKE_myTiger|myEmail" 
						value="${param['filter_LIKE_myTiger|myEmail']}" 
						size="10"/>
				<s:submit cssClass="button" method="search"  key="common.domain.search" theme="simple"/>
			</form>
		</div> 
	</div>
		
	<!-- 布局  底 -->
	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>
