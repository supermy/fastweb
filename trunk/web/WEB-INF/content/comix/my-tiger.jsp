<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="myTigerList.title"/></title>
    <meta name="heading" content="<s:text name='myTigerList.heading'/>"/>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>

	<link rel="stylesheet" href="${css}/css/table/bluedream.css" type="text/css" media="screen, projection"/>


</head>
<body>
<!-- 布局 容器  -->
<div class="container">
	<!-- 布局  顶部工具杄1�7-->
	<%@ include file="/common/tools.jsp"%>
	<!-- 布局  顶部导航栏目-->
	<%@ include file="/common/nav.jsp"%>
	<!-- 布局  左边刄1�7-->
	<div class="span-17 prepend-1 colborder">
		<c:set var="buttons">
			<div>
				<s:text name="common.page.by"/>${pagemytiger.pageNo}<s:text name="common.page.page"/>, <s:text name="common.page.total"/>${pagemytiger.totalPages}<s:text name="common.page.page"/> 
				<s:if test="pagemytiger.hasPre">
					<a href="my-tiger.action?pagemytiger.pageNo=${pagemytiger.prePage}&pagemytiger.orderBy=${pagemytiger.orderBy}&pagemytiger.order=${pagemytiger.order}"><s:text name="common.page.pre"/></a>
				</s:if>
				<s:if test="pagemytiger.hasNext">
					<a href="my-tiger.action?pagemytiger.pageNo=${pagemytiger.nextPage}&pagemytiger.orderBy=${pagemytiger.orderBy}&pagemytiger.order=${pagemytiger.order}"><s:text name="common.page.next"/></a>
				</s:if>
				<br />
			</div>
		</c:set>
		<c:out value="${buttons}" escapeXml="false" />
		<display:table 
			id="myTigerList" name="pagemytiger.result" 
			sort="external" class="table" requestURI="" export="false">
				    <display:column property="id" 
				    	sortable="false"
				    	sortName="id" 
				    	href="my-tiger!input.action?pagemytiger.pageRequest=${pagemytiger.pageRequest}" media="html"
				        paramId="id" 
				        paramProperty="id" 
				        titleKey="myTiger.id"/>
				    <display:column 
				    	property="id" 
				    	media="csv excel xml pdf"
				    	titleKey="myTiger.id"/>
				    	
				
			            <display:column property="audit"  format="{0,date,yyyy-MM-dd}" sortProperty="audit" sortable="false" sortName="audit" titleKey="myTiger.audit"/>
				
		        	    <display:column property="myEmail" sortable="false" sortName="myEmail" titleKey="myTiger.myEmail"/>
				    
				
		        	    <display:column property="myTiger" sortable="false" sortName="myTiger" titleKey="myTiger.myTiger"/>
				    
			
		    <display:setProperty name="paging.banner.item_name"><s:text name="myTigerList.myTiger"/></display:setProperty>
		    <display:setProperty name="paging.banner.items_name"><s:text name="myTigerList.myTigers"/></display:setProperty>
		
		    <display:setProperty name="export.excel.filename"><s:text name="myTigerList.title"/>.xls</display:setProperty>
		    <display:setProperty name="export.csv.filename"><s:text name="myTigerList.title"/>.csv</display:setProperty>
		    <display:setProperty name="export.pdf.filename"><s:text name="myTigerList.title"/>.pdf</display:setProperty>
		</display:table>
		<c:out value="${buttons}" escapeXml="false" />		
		
	</div>
	
	<!-- 布局  右边刄1�7-->
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
		<security:authorize ifAnyGranted="AUTH_MODIFY_USER">
			<a  class="button" href="my-tiger!input.action?pagemytiger.pageRequest=${pagemytiger.pageRequest}"><s:text name="common.domain.create"/></a>
		</security:authorize>
		<hr class="space"/>
		<div id="filter">
			<form action="my-tiger.action" method="post">
				<input type="text" 
						name="filter_LIKE_myTiger|myEmail" 
						value="${param['filter_LIKE_myTiger|myEmail']}" 
						size="10"/>
				<s:submit cssClass="button" method="search"  key="common.domain.search" theme="simple"/>
			</form>
		</div> 
	</div>
		
	<!-- 布局  庄1�7 -->
	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>
