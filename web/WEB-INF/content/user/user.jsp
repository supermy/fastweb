<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="userList.title"/></title>
    <meta name="heading" content="<s:text name='userList.heading'/>"/>
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
				<s:text name="common.page.by"/>${pageuser.pageNo}<s:text name="common.page.page"/>, <s:text name="common.page.total"/>${pageuser.totalPages}<s:text name="common.page.page"/> 
				<s:if test="pageuser.hasPre">
					<a href="user.action?pageuser.pageNo=${pageuser.prePage}&pageuser.orderBy=${pageuser.orderBy}&pageuser.order=${pageuser.order}"><s:text name="common.page.pre"/></a>
				</s:if>
				<s:if test="pageuser.hasNext">
					<a href="user.action?pageuser.pageNo=${pageuser.nextPage}&pageuser.orderBy=${pageuser.orderBy}&pageuser.order=${pageuser.order}"><s:text name="common.page.next"/></a>
				</s:if>
				<br />
			</div>
		</c:set>
		<c:out value="${buttons}" escapeXml="false" />
		<display:table 
			id="userList" 
			name="pageuser.result" 
			sort="external" 
			class="table" 
			requestURI="" 
			export="false">
				    <display:column property="id" 
				    	sortable="false"
				    	sortName="id" 
				    	href="user!input.action?pageuser.pageRequest=${pageuser.pageRequest}" media="html"
				        paramId="id" 
				        paramProperty="id" 
				        titleKey="user.id"/>
				    <display:column 
				    	property="id" 
				    	media="csv excel xml pdf"
				    	titleKey="user.id"/>
				    	
			             <display:column 
			             	sortProperty="accountNonExpired" 
			             	sortable="false" 
			             	sortName="accountNonExpired" 
			             	titleKey="user.accountNonExpired">
			                 <input 
			                 	type="checkbox" 
			                 	disabled="disabled" 
			                 	<c:if test="${userList.accountNonExpired}">checked="checked"</c:if>/>
			             </display:column>
			             <display:column 
			             	sortProperty="accountNonLocked" 
			             	sortable="false" 
			             	sortName="accountNonLocked" 
			             	titleKey="user.accountNonLocked">
			                 <input 
			                 	type="checkbox" 
			                 	disabled="disabled" 
			                 	<c:if test="${userList.accountNonLocked}">checked="checked"</c:if>/>
			             </display:column>
			             <display:column 
			             	sortProperty="credentialsNonExpired" 
			             	sortable="false" 
			             	sortName="credentialsNonExpired" 
			             	titleKey="user.credentialsNonExpired">
			                 <input 
			                 	type="checkbox" 
			                 	disabled="disabled" 
			                 	<c:if test="${userList.credentialsNonExpired}">checked="checked"</c:if>/>
			             </display:column>
		        	    <display:column 
		        	    	property="email" 
		        	    	sortable="false" 
		        	    	sortName="email" 
		        	    	titleKey="user.email"/>
		        	    <display:column 
		        	    	property="intro" 
		        	    	sortable="false" 
		        	    	sortName="intro" 
		        	    	titleKey="user.intro"/>
		        	    <display:column 
		        	    	property="name" 
		        	    	sortable="false" 
		        	    	sortName="name" 
		        	    	titleKey="user.name"/>
		        	    <display:column 
		        	    	property="passwd" 
		        	    	sortable="false" 
		        	    	sortName="passwd" 
		        	    	titleKey="user.passwd"/>
		        		<display:column 
		        			property="rolesName" 
		        			sortable="false" 
		        			sortName="roles" 
		        			titleKey="user.roles"
		        			href="role!mtolist.action?class=org.supermy.core.domain.User&property=roles" media="html"
					        paramId="id"
					        paramProperty="id" 
		        			/>
			             <display:column 
			             	sortProperty="salary" 
			             	format="{0,number, 0,000,000.00}" 
			             	sortable="false" 
			             	sortName="salary" 
			             	titleKey="user.salary"/>
			<security:authorize ifAnyGranted="AUTH_EDIT_USER">
				<display:column 
					value="manager"
					titleKey="common.domain.manager" 	
					href="user!input.action?pageuser.pageRequest=${pageuser.pageRequest}" media="html"
					paramId="id" 
					paramProperty="id"/>
			</security:authorize>
			
		    <display:setProperty 
		    	name="paging.banner.item_name">
		    	<s:text name="userList.user"/>
		    </display:setProperty>
		    <display:setProperty 
		    	name="paging.banner.items_name">
		    	<s:text name="userList.users"/>
		    </display:setProperty>
		
		    <display:setProperty 
		    	name="export.excel.filename">
		    	<s:text name="userList.title"/>.xls
		    </display:setProperty>
		    <display:setProperty 
		    	name="export.csv.filename">
		    	<s:text name="userList.title"/>.csv
		    </display:setProperty>
		    <display:setProperty 
		    	name="export.pdf.filename">
		    	<s:text name="userList.title"/>.pdf
		    </display:setProperty>
		</display:table>
		<c:out value="${buttons}" escapeXml="false" />		
		
	</div>
	
	<!-- 布局  右边列-->
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
		<security:authorize 
			ifAnyGranted="AUTH_EDIT_USER">
			<a  class="button" 
				href="user!input.action?pageuser.pageRequest=${pageuser.pageRequest}">
				<s:text name="common.domain.create"/>
			</a>
		</security:authorize>
		<hr class="space"/>
		<div id="filter">
			<form 
				action="user.action" 
				method="post">
				<input type="text" 
						name="filter_LIKE_myTiger|myEmail" 
						value="${param['filter_LIKE_myTiger|myEmail']}" 
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
