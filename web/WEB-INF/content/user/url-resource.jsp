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
	<!-- 布局  顶部工具杄1�7-->
	<%@ include file="/common/tools.jsp"%>
	<!-- 布局  顶部导航栏目-->
	<%@ include file="/common/nav.jsp"%>
	<!-- 布局  左边刄1�7-->
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
		<table>
			<thead>
			<tr>
				        	<th>
							<a href="url-resource.action?pageurlresource.orderBy=id&pageurlresource.order=
							<s:if test="pageurlresource.orderBy=='id'">${pageurlresource.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="urlResource.id"/></a>
				</th>
	        	<th>
							<s:text name="urlResource.authorityList"/>
				</th>
	        	<th>
							<a href="url-resource.action?pageurlresource.orderBy=desc&pageurlresource.order=
							<s:if test="pageurlresource.orderBy=='desc'">${pageurlresource.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="urlResource.desc"/></a>
				</th>
	        	<th>
							<s:text name="urlResource.manager"/>
				</th>
	        	<th>
							<a href="url-resource.action?pageurlresource.orderBy=position&pageurlresource.order=
							<s:if test="pageurlresource.orderBy=='position'">${pageurlresource.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="urlResource.position"/></a>
				</th>
	        	<th>
							<a href="url-resource.action?pageurlresource.orderBy=resourceType&pageurlresource.order=
							<s:if test="pageurlresource.orderBy=='resourceType'">${pageurlresource.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="urlResource.resourceType"/></a>
				</th>
	        	<th>
							<a href="url-resource.action?pageurlresource.orderBy=value&pageurlresource.order=
							<s:if test="pageurlresource.orderBy=='value'">${pageurlresource.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="urlResource.value"/></a>
				</th>
			<th>manager</th>
			</tr>
			</thead>


			<s:iterator value="pageurlresource.result" status='st'>
			<tbody>		
			    <s:if test="#st.Odd">
				<tr  class="odd">
			    </s:if>

			    <s:if test="#st.Even">
				<tr  class="even">
			    </s:if>
				
						<td>
								<a href="url-resource!input.action?id=${id}&pageurlresource.pageRequest=${pageurlresource.pageRequest}">
									${id}
								</a>
					</td>
						<td>
								<a href="authority!mtolist.action?id=${id}&class=org.supermy.core.domain.UrlResource&property=authorityList">
									${authorityListName}
								</a>
					</td>
						<td>
									${desc}
					</td>
						<td>
									${managerName}
					</td>
						<td>
									${position}
					</td>
						<td>
									${resourceType}
					</td>
						<td>
									${value}
					</td>
					<security:authorize ifAnyGranted="AUTH_EDIT_URLRESOURCE">
						<td>
							<a href="url-resource!input.action?id=${id}&pageurlresource.pageRequest=${pageurlresource.pageRequest}">
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
			ifAnyGranted="AUTH_EDIT_URLRESOURCE">
			<a  class="button" 
				href="url-resource!input.action?pageurlresource.pageRequest=${pageurlresource.pageRequest}">
				<s:text name="common.domain.create"/>
			</a>
		</security:authorize>
		<hr class="space"/>
		<div id="filter">
			<form 
				action="url-resource.action" 
				method="post">
				<input type="text" 
						name="filter_LIKE_resourceType|desc|value" 
						value="${param['filter_LIKE_resourceType|desc|value']}" 
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
