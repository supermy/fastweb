<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="roleList.title"/></title>
    <meta name="heading" content="<s:text name='roleList.heading'/>"/>
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
				<s:text name="common.page.by"/>${pagerole.pageNo}<s:text name="common.page.page"/>, <s:text name="common.page.total"/>${pagerole.totalPages}<s:text name="common.page.page"/> 
				<s:if test="pagerole.hasPre">
					<a href="role.action?pagerole.pageNo=${pagerole.prePage}&pagerole.orderBy=${pagerole.orderBy}&pagerole.order=${pagerole.order}"><s:text name="common.page.pre"/></a>
				</s:if>
				<s:if test="pagerole.hasNext">
					<a href="role.action?pagerole.pageNo=${pagerole.nextPage}&pagerole.orderBy=${pagerole.orderBy}&pagerole.order=${pagerole.order}"><s:text name="common.page.next"/></a>
				</s:if>
				<br />
			</div>
		</c:set>
		<c:out value="${buttons}" escapeXml="false" />
		<display:table 
			id="roleList" 
			name="pagerole.result" 
			sort="external" 
			class="table" 
			requestURI="" 
			export="false">
				    <display:column property="id" 
				    	sortable="false"
				    	sortName="id" 
				    	href="role!input.action?pagerole.pageRequest=${pagerole.pageRequest}" media="html"
				        paramId="id" 
				        paramProperty="id" 
				        titleKey="role.id"/>
				    <display:column 
				    	property="id" 
				    	media="csv excel xml pdf"
				    	titleKey="role.id"/>
				    	
		        		<display:column 
		        			property="authsName" 
		        			sortable="false" 
		        			sortName="auths" 
		        			titleKey="role.auths"
		        			href="authority!mtolist.action?class=org.supermy.core.domain.Role&property=auths" media="html"
					        paramId="id"
					        paramProperty="id" 
		        			/>
		        	    <display:column 
		        	    	property="name" 
		        	    	sortable="false" 
		        	    	sortName="name" 
		        	    	titleKey="role.name"/>
			<security:authorize ifAnyGranted="AUTH_EDIT_ROLE">
				<display:column 
					value="manager"
					titleKey="common.domain.manager" 	
					href="role!input.action?pagerole.pageRequest=${pagerole.pageRequest}" media="html"
					paramId="id" 
					paramProperty="id"/>
			</security:authorize>
			
		    <display:setProperty 
		    	name="paging.banner.item_name">
		    	<s:text name="roleList.role"/>
		    </display:setProperty>
		    <display:setProperty 
		    	name="paging.banner.items_name">
		    	<s:text name="roleList.roles"/>
		    </display:setProperty>
		
		    <display:setProperty 
		    	name="export.excel.filename">
		    	<s:text name="roleList.title"/>.xls
		    </display:setProperty>
		    <display:setProperty 
		    	name="export.csv.filename">
		    	<s:text name="roleList.title"/>.csv
		    </display:setProperty>
		    <display:setProperty 
		    	name="export.pdf.filename">
		    	<s:text name="roleList.title"/>.pdf
		    </display:setProperty>
		</display:table>
		<c:out value="${buttons}" escapeXml="false" />		
		
	</div>
	
	<!-- 布局  右边列-->
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
		<security:authorize 
			ifAnyGranted="AUTH_EDIT_ROLE">
			<a  class="button" 
				href="role!input.action?pagerole.pageRequest=${pagerole.pageRequest}">
				<s:text name="common.domain.create"/>
			</a>
		</security:authorize>
		<hr class="space"/>
		<div id="filter">
			<form 
				action="role.action" 
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
