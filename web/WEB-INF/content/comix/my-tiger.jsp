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
				<jsp:param   name="pagenav" value="pagemytiger"/>  
			</jsp:include> 			
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
				    	
				
			            <display:column property="create"  format="{0,date,yyyy-MM-dd}" sortProperty="create" sortable="false" sortName="create" titleKey="myTiger.create"/>
				
			             <display:column sortProperty="enabled" sortable="false" sortName="enabled" titleKey="myTiger.enabled">
			                 <input type="checkbox" disabled="disabled" <c:if test="${myTigerList.enabled}">checked="checked"</c:if>/>
			             </display:column>
				
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
	
	<!-- 布局  右边列-->
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
		<security:authorize ifAnyGranted="AUTH_MODIFY_USER">
			<a href="my-tiger!input.action?pagemytiger.pageRequest=${pagemytiger.pageRequest}"><s:text name="common.domain.create"/></a>
		</security:authorize>
		<hr class="space"/>
		<div id="filter">
			<form action="MyTiger!search.action" method="post">
				<input type="text" 
						name="filter_LIKE_name|email" 
						value="${param['filter_LIKE_name|email']}" 
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
