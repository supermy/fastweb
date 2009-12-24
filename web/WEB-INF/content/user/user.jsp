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
				<!--
				<s:if test="pageuser.hasPre">
					<a href="user.action?pageuser.pageNo=${pageuser.prePage}&pageuser.orderBy=${pageuser.orderBy}&pageuser.order=${pageuser.order}"><s:text name="common.page.pre"/></a>
				</s:if>
				<s:if test="pageuser.hasNext">
					<a href="user.action?pageuser.pageNo=${pageuser.nextPage}&pageuser.orderBy=${pageuser.orderBy}&pageuser.order=${pageuser.order}"><s:text name="common.page.next"/></a>
				</s:if>
				-->
				
				<s:property value="pageuser.genNav('user.action?','pageuser',4)"  escape="false"/>
				<br />
			</div>
		</c:set>
		<c:out value="${buttons}" escapeXml="false" />
		<table>
			<thead>
			<tr>
				        	<th>
							<a href="user.action?pageuser.orderBy=id&pageuser.order=
							<s:if test="pageuser.orderBy=='id'">${pageuser.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="user.id"/></a>
				</th>
	        	<th>
							<a href="user.action?pageuser.orderBy=accountNonExpired&pageuser.order=
							<s:if test="pageuser.orderBy=='accountNonExpired'">${pageuser.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="user.accountNonExpired"/></a>
				</th>
	        	<th>
							<a href="user.action?pageuser.orderBy=accountNonLocked&pageuser.order=
							<s:if test="pageuser.orderBy=='accountNonLocked'">${pageuser.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="user.accountNonLocked"/></a>
				</th>
	        	<th>
							<a href="user.action?pageuser.orderBy=credentialsNonExpired&pageuser.order=
							<s:if test="pageuser.orderBy=='credentialsNonExpired'">${pageuser.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="user.credentialsNonExpired"/></a>
				</th>
	        	<th>
							<a href="user.action?pageuser.orderBy=email&pageuser.order=
							<s:if test="pageuser.orderBy=='email'">${pageuser.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="user.email"/></a>
				</th>
	        	<th>
							<a href="user.action?pageuser.orderBy=intro&pageuser.order=
							<s:if test="pageuser.orderBy=='intro'">${pageuser.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="user.intro"/></a>
				</th>
	        	<th>
							<a href="user.action?pageuser.orderBy=name&pageuser.order=
							<s:if test="pageuser.orderBy=='name'">${pageuser.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="user.name"/></a>
				</th>
	        	<th>
							<a href="user.action?pageuser.orderBy=passwd&pageuser.order=
							<s:if test="pageuser.orderBy=='passwd'">${pageuser.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="user.passwd"/></a>
				</th>
	        	<th>
							<a href="user.action?pageuser.orderBy=salary&pageuser.order=
							<s:if test="pageuser.orderBy=='salary'">${pageuser.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="user.salary"/></a>
				</th>
			<th>manager</th>
			</tr>
			</thead>


			<s:iterator value="pageuser.result" status='st'>
			<tbody>		
			    <s:if test="#st.Odd">
				<tr  class="odd">
			    </s:if>

			    <s:if test="#st.Even">
				<tr  class="even">
			    </s:if>
				
						<td>
								<a href="user!input.action?id=${id}&pageuser.pageRequest=${pageuser.pageRequest}">
									${id}
								</a>
					</td>
						<td>
					                 <input 
					                 	type="checkbox" 
					                 	disabled="disabled" 
					                 	<c:if test="${accountNonExpired}">checked="checked"</c:if>/>
					</td>
						<td>
					                 <input 
					                 	type="checkbox" 
					                 	disabled="disabled" 
					                 	<c:if test="${accountNonLocked}">checked="checked"</c:if>/>
					</td>
						<td>
					                 <input 
					                 	type="checkbox" 
					                 	disabled="disabled" 
					                 	<c:if test="${credentialsNonExpired}">checked="checked"</c:if>/>
					</td>
						<td>
									${email}
					</td>
						<td>
									${intro}
					</td>
						<td>
									${name}
					</td>
						<td>
									${passwd}
					</td>
						<td>
									${salary}
					</td>
					<security:authorize ifAnyGranted="AUTH_EDIT_USER">
						<td>
							<a href="user!input.action?id=${id}&pageuser.pageRequest=${pageuser.pageRequest}">
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
						name="filter_LIKE_name|passwd|email|intro" 
						value="${param['filter_LIKE_name|passwd|email|intro']}" 
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
				action="user.action" 
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
