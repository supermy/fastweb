<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>用户管理</title>

	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>
	<!--ext需要的部件，如果不用ext效果，不要加入，很费资源-->
	<%@ include file="/common/extjshead.jsp"%>

	<script type="text/javascript" src="${extjs}/ext/paging.js"></script>
	<script type="text/javascript" src="${extjs}/ext/edit-grid.js"></script>

	<link rel="stylesheet" type="text/css" href="${extjs}/ext/grid-examples.css" />

</head>

<body>
<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-17 prepend-1 colborder">

		
		<h2>手工展示数据</h2>
		<table >
			<tr>
				<th ><a href="user.action?page.orderBy=email&page.order=
				<s:if test="page.orderBy=='email'">${page.inverseOrder}</s:if><s:else>desc</s:else>">
				<b>登录名</b></a></th>
				<th ><a href="user.action?page.orderBy=name&page.order=
				<s:if test="page.orderBy=='name'">${page.inverseOrder}</s:if><s:else>desc</s:else>">
				<b>姓名</b></a></th>
				<th ><b>角色</b></th>
				<th ><b>操作</b></th>
			</tr>
		
			<s:iterator value="page.result">
				<tr >
					<td >${email}&nbsp;</td>
					<td >${name}&nbsp;</td>
					<td >${roleNames}&nbsp;</td>
					<td >&nbsp; 
						<security:authorize ifAnyGranted="AUTH_MODIFY_USER">
							<a href="user!input.action?id=${id}&page.pageRequest=${page.pageRequest}">修改</a>、
							<a href="user!delete.action?id=${id}&page.pageRequest=${page.pageRequest}">删除</a>
						</security:authorize>
					</td>
				</tr>
			</s:iterator>
		</table>

		<div>
			第${page.pageNo}页, 共${page.totalPages}页 
			<s:if test="page.hasPre">
				<a href="user.action?page.pageNo=${page.prePage}&page.orderBy=${page.orderBy}&page.order=${page.order}">上一页</a>
			</s:if>
			<s:if test="page.hasNext">
				<a href="user.action?page.pageNo=${page.nextPage}&page.orderBy=${page.orderBy}&page.order=${page.order}">下一页</a>
			</s:if>
			<br />
		</div>
		
		<hr/>
		
		<hr/>
		<h2>EXT JS展示数据<h2>
		
		<div id="editor-grid"></div>
		<div id="user-page-grid"></div>
		
		
		<hr/>
		
		<h2>DisplayTLD展示数据<h2>
		
		<display:table 
			name="page.result" 
			
			requestURI ="user.action"
			export="true" >
			
			<display:column property="id"    	title="ID" 	    />
			<display:column property="email" 	title="登录名称"   autolink="true"/>
			<display:column property="name"  	title="姓名"      url="#" paramId="email" paramProperty="email"/>
			<display:column property="roleNames"title="角色"     />
			<display:column property="intro"  	title="个人介绍"  />
		</display:table>
		
		<br/>
		

		
		
	</div>
	
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<div id="filter">
			<form action="user!search.action" method="post">
				EMail或姓名: <input type="text" 
						name="filter_LIKE_name|email" 
						value="${param['filter_LIKE_name|email']}" 
						size="10"/>
				<input type="submit" value="搜索" />
			</form>
		</div> 
		<hr class="space"/>
		<security:authorize ifAnyGranted="AUTH_MODIFY_USER">
		<a href="user!input.action">增加新用户</a>
		</security:authorize>
	</div>
		

	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>
