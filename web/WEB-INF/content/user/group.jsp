<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="groupList.title"/></title>
    <meta name="heading" content="<s:text name='groupList.heading'/>"/>
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
				<s:text name="common.page.by"/>${pagegroup.pageNo}<s:text name="common.page.page"/>, <s:text name="common.page.total"/>${pagegroup.totalPages}<s:text name="common.page.page"/> 
				<!--
				<s:if test="pagegroup.hasPre">
					<a href="group.action?pagegroup.pageNo=${pagegroup.prePage}&pagegroup.orderBy=${pagegroup.orderBy}&pagegroup.order=${pagegroup.order}"><s:text name="common.page.pre"/></a>
				</s:if>
				<s:if test="pagegroup.hasNext">
					<a href="group.action?pagegroup.pageNo=${pagegroup.nextPage}&pagegroup.orderBy=${pagegroup.orderBy}&pagegroup.order=${pagegroup.order}"><s:text name="common.page.next"/></a>
				</s:if>
				-->
				
				<s:property value="pagegroup.genNav('group.action?','pagegroup',4)"  escape="false"/>
				<br />
			</div>
		</c:set>
		<c:out value="${buttons}" escapeXml="false" />
		<table>
			<thead>
			<tr>
				        	<th>
							<a href="group.action?pagegroup.orderBy=id&pagegroup.order=
							<s:if test="pagegroup.orderBy=='id'">${pagegroup.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="group.id"/></a>
				</th>
	        	<th>
							<a href="group.action?pagegroup.orderBy=code&pagegroup.order=
							<s:if test="pagegroup.orderBy=='code'">${pagegroup.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="group.code"/></a>
				</th>
	        	<th>
							<a href="group.action?pagegroup.orderBy=email&pagegroup.order=
							<s:if test="pagegroup.orderBy=='email'">${pagegroup.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="group.email"/></a>
				</th>
	        	<th>
							<a href="group.action?pagegroup.orderBy=intro&pagegroup.order=
							<s:if test="pagegroup.orderBy=='intro'">${pagegroup.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="group.intro"/></a>
				</th>
	        	<th>
							<a href="group.action?pagegroup.orderBy=name&pagegroup.order=
							<s:if test="pagegroup.orderBy=='name'">${pagegroup.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="group.name"/></a>
				</th>
	        	<th>
							<s:text name="group.parent"/>
				</th>
	        	<th>
							<a href="group.action?pagegroup.orderBy=type&pagegroup.order=
							<s:if test="pagegroup.orderBy=='type'">${pagegroup.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="group.type"/></a>
				</th>
			<th>manager</th>
			</tr>
			</thead>


			<s:iterator value="pagegroup.result" status='st'>
			<tbody>		
			    <s:if test="#st.Odd">
				<tr  class="odd">
			    </s:if>

			    <s:if test="#st.Even">
				<tr  class="even">
			    </s:if>
				
						<td>
								<a href="group!input.action?id=${id}&pagegroup.pageRequest=${pagegroup.pageRequest}">
									${id}
								</a>
					</td>
						<td>
									${code}
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
									${parent.name}
					</td>
						<td>
									${type}
					</td>
					<security:authorize ifAnyGranted="AUTH_EDIT_GROUP">
						<td>
							<a href="group!input.action?id=${id}&pagegroup.pageRequest=${pagegroup.pageRequest}">
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
			ifAnyGranted="AUTH_EDIT_GROUP">
			<a  class="button" 
				href="group!input.action?pagegroup.pageRequest=${pagegroup.pageRequest}">
				<s:text name="common.domain.create"/>
			</a>
		</security:authorize>
		<hr class="space"/>
		<div id="filter">
			<form 
				action="group.action" 
				method="post">
				<input type="text" 
						name="filter_LIKE_name|code|email|type|intro" 
						value="${param['filter_LIKE_name|code|email|type|intro']}" 
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
				action="group.action" 
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
