<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="authorityList.title"/></title>
    <meta name="heading" content="<s:text name='authorityList.heading'/>"/>
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
				<s:text name="common.page.by"/>${pageauthority.pageNo}<s:text name="common.page.page"/>, <s:text name="common.page.total"/>${pageauthority.totalPages}<s:text name="common.page.page"/> 
				<s:if test="pageauthority.hasPre">
					<a href="authority.action?pageauthority.pageNo=${pageauthority.prePage}&pageauthority.orderBy=${pageauthority.orderBy}&pageauthority.order=${pageauthority.order}"><s:text name="common.page.pre"/></a>
				</s:if>
				<s:if test="pageauthority.hasNext">
					<a href="authority.action?pageauthority.pageNo=${pageauthority.nextPage}&pageauthority.orderBy=${pageauthority.orderBy}&pageauthority.order=${pageauthority.order}"><s:text name="common.page.next"/></a>
				</s:if>
				<br />
			</div>
		</c:set>
		<c:out value="${buttons}" escapeXml="false" />
		<table>
			<thead>
			<tr>
				        	<th>
							<a href="authority.action?pageauthority.orderBy=id&pageauthority.order=
							<s:if test="pageauthority.orderBy=='id'">${pageauthority.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="authority.id"/></a>
				</th>
	        	<th>
							<a href="authority.action?pageauthority.orderBy=name&pageauthority.order=
							<s:if test="pageauthority.orderBy=='name'">${pageauthority.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="authority.name"/></a>
				</th>
	        	<th>
							<a href="authority.action?pageauthority.orderBy=nickName&pageauthority.order=
							<s:if test="pageauthority.orderBy=='nickName'">${pageauthority.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="authority.nickName"/></a>
				</th>
			<th>manager</th>
			</tr>
			</thead>


			<s:iterator value="pageauthority.result" status='st'>
			<tbody>		
			    <s:if test="#st.Odd">
				<tr  class="odd">
			    </s:if>

			    <s:if test="#st.Even">
				<tr  class="even">
			    </s:if>
				
						<td>
								<a href="authority!input.action?id=${id}&pageauthority.pageRequest=${pageauthority.pageRequest}">
									${id}
								</a>
					</td>
						<td>
									${name}
					</td>
						<td>
									${nickName}
					</td>
					<security:authorize ifAnyGranted="AUTH_EDIT_AUTHORITY">
						<td>
							<a href="authority!input.action?id=${id}&pageauthority.pageRequest=${pageauthority.pageRequest}">
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
			ifAnyGranted="AUTH_EDIT_AUTHORITY">
			<a  class="button" 
				href="authority!input.action?pageauthority.pageRequest=${pageauthority.pageRequest}">
				<s:text name="common.domain.create"/>
			</a>
		</security:authorize>
		<hr class="space"/>
		<div id="filter">
			<form 
				action="authority.action" 
				method="post">
				<input type="text" 
						name="filter_LIKE_name|nickName" 
						value="${param['filter_LIKE_name|nickName']}" 
						size="10"/>
				<s:submit 
					cssClass="button" 
					method="search"  
					key="common.domain.search" 
					/>
			</form>
		</div> 
	</div>
		
	<!-- 布局  庄1�7 -->
	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>
