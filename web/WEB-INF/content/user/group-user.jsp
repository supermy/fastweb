<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="groupUserList.title"/></title>
    <meta name="heading" content="<s:text name='groupUserList.heading'/>"/>
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
				<s:text name="common.page.by"/>${pagegroupuser.pageNo}<s:text name="common.page.page"/>, <s:text name="common.page.total"/>${pagegroupuser.totalPages}<s:text name="common.page.page"/> 
				<!--
				<s:if test="pagegroupuser.hasPre">
					<a href="group-user.action?pagegroupuser.pageNo=${pagegroupuser.prePage}&pagegroupuser.orderBy=${pagegroupuser.orderBy}&pagegroupuser.order=${pagegroupuser.order}"><s:text name="common.page.pre"/></a>
				</s:if>
				<s:if test="pagegroupuser.hasNext">
					<a href="group-user.action?pagegroupuser.pageNo=${pagegroupuser.nextPage}&pagegroupuser.orderBy=${pagegroupuser.orderBy}&pagegroupuser.order=${pagegroupuser.order}"><s:text name="common.page.next"/></a>
				</s:if>
				-->
				
				<s:property value="pagegroupuser.genNav('group-user.action?','pagegroupuser',4)"  escape="false"/>
				<br />
			</div>
		</c:set>
		<c:out value="${buttons}" escapeXml="false" />
		<table>
			<thead>
			<tr>
				        	<th>
							<a href="group-user.action?pagegroupuser.orderBy=id&pagegroupuser.order=
							<s:if test="pagegroupuser.orderBy=='id'">${pagegroupuser.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="groupUser.id"/></a>
				</th>
	        	<th>
							<s:text name="groupUser.group"/>
				</th>
	        	<th>
							<a href="group-user.action?pagegroupuser.orderBy=intro&pagegroupuser.order=
							<s:if test="pagegroupuser.orderBy=='intro'">${pagegroupuser.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="groupUser.intro"/></a>
				</th>
	        	<th>
							<a href="group-user.action?pagegroupuser.orderBy=name&pagegroupuser.order=
							<s:if test="pagegroupuser.orderBy=='name'">${pagegroupuser.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="groupUser.name"/></a>
				</th>
	        	<th>
							<s:text name="groupUser.user"/>
				</th>
			<th>manager</th>
			</tr>
			</thead>

			<s:iterator value="pagegroupuser.result" status='st'>
			<tbody>		
			    <s:if test="#st.Odd">
				<tr  class="odd">
			    </s:if>

			    <s:if test="#st.Even">
				<tr  class="even">
			    </s:if>
				
						<td>
								<a href="group-user!input.action?id=${id}&pagegroupuser.pageRequest=${pagegroupuser.pageRequest}">
									${id}
								</a>
					</td>
						<td>
									${group.name}
					</td>
						<td>
									${intro}
					</td>
						<td>
									${name}
					</td>
						<td>
									${user.name}
					</td>
					<security:authorize ifAnyGranted="AUTH_EDIT_GROUPUSER">
						<td>
							<a href="group-user!input.action?id=${id}&pagegroupuser.pageRequest=${pagegroupuser.pageRequest}">
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
	
	<!-- 布局  右边列-->
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
		<security:authorize 
			ifAnyGranted="AUTH_EDIT_GROUPUSER">
			<a  class="button" 
				href="group-user!input.action?pagegroupuser.pageRequest=${pagegroupuser.pageRequest}">
				<s:text name="common.domain.create"/>
			</a>
		</security:authorize>
		<hr class="space"/>
		<div id="filter">
			<form 
				action="group-user.action" 
				method="post">
				<input type="text" 
						name="filter_LIKE_name|intro" 
						value="${param['filter_LIKE_name|intro']}" 
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
				action="group-user.action" 
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
		
	<!-- 布局  底 -->
	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>
