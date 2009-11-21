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
	<!-- 布局  顶部工具杄1�7-->
	<%@ include file="/common/tools.jsp"%>
	<!-- 布局  顶部导航栏目-->
	<%@ include file="/common/nav.jsp"%>
	<!-- 布局  左边刄1�7-->
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
		<table>
			<thead>
			<tr>
				        	<th>
							<a href="role.action?pagerole.orderBy=id&pagerole.order=
							<s:if test="pagerole.orderBy=='id'">${pagerole.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="role.id"/></a>
				</th>
	        	<th>
							<s:text name="role.auths"/>
				</th>
	        	<th>
							<a href="role.action?pagerole.orderBy=name&pagerole.order=
							<s:if test="pagerole.orderBy=='name'">${pagerole.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="role.name"/></a>
				</th>
			<th>manager</th>
			</tr>
			</thead>


			<s:iterator value="pagerole.result" status='st'>
			<tbody>		
			    <s:if test="#st.Odd">
				<tr  class="odd">
			    </s:if>

			    <s:if test="#st.Even">
				<tr  class="even">
			    </s:if>
				
						<td>
								<a href="role!input.action?id=${id}&pagerole.pageRequest=${pagerole.pageRequest}">
									${id}
								</a>
					</td>
						<td>
								<a href="authority!mtolist.action?id=${id}&class=org.supermy.core.domain.Role&property=auths">
									${authsName}
								</a>
					</td>
						<td>
									${name}
					</td>
					<security:authorize ifAnyGranted="AUTH_EDIT_ROLE">
						<td>
							<a href="role!input.action?id=${id}&pagerole.pageRequest=${pagerole.pageRequest}">
								manager
							</a>
						</td>
					</security:authorize>
					
				</tr>
			</tbody>		
			</s:iterator>
		</table>			<!--#include "list-view-displaytag.ftl"/-->
		<c:out value="${buttons}" escapeXml="false" />		
		
	</div>
	
	<!-- 布局  右边刄1�7-->
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
						name="filter_LIKE_name" 
						value="${param['filter_LIKE_name']}" 
						size="10"/>
				<s:submit 
					cssClass="button" 
					method="search"  
					key="common.domain.search" 
					/>
			</form>
		</div> 

		<div id="fulltext">
			<form 
				action="role.action" 
				method="post">
				<input type="text" 
						name="q" 
						value="${param['q']}" 
						size="10"/>
				<s:submit 
					cssClass="button" 
					method="fulltext"  
					key="common.domain.fulltext" 
					/>
			</form>
		</div> 

	</div>
		
	<!-- 布局  庄1�7 -->
	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>
