<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>用户管理</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${css}/css/default.css" type="text/css" rel="stylesheet">
</head>

<body>
<div id="menu">
	<h3>
	<a href="${ctx}/user/user.action">用户列表</a>
	<a href="${ctx}/user/role.action">角色列表</a> 
	<a href="${ctx}/j_spring_security_logout">退出登录</a>
	</h3> 
</div>


	<div id="message"><s:actionmessage theme="mytheme"/></div>
    
<div id="filter">
<form action="user!search.action" method="post">
	你好,<%=SecurityUtils.getCurrentUserName()%>.&nbsp;&nbsp;<br/>
 	登录名: <input type="text" name="filter_EQ_name" value="${param['filter_EQ_ame']}"  size="8"/> 
          姓名或Email: <input type="text" name="filter_LIKE_name|email" value="${param['filter_LIKE_name|email']}" size="8"/>
	<input type="submit" value="搜索" />
</form>
</div> 


<div id="listContent">

///////////////////////////////////////////////////////////////////////

<link rel="stylesheet" type="text/css" href="${js}/ext/ext-all.css" />

<script src="http://www.google-analytics.com/urchin.js" type="text/javascript"> 
	<link rel="stylesheet" type="text/css" href="${js}/ext/xtheme-aero.css" /><!-- LIBS -->
	<script type="text/javascript" src="${js}/ext/ext-base.js"></script>

	<!-- ENDLIBS -->

<script type="text/javascript" src="${js}/ext/ext-all.js"></script>

<script type="text/javascript" src="${js}/ext/array-grid.js"></script>
<link rel="stylesheet" type="text/css" href="${js}/ext/grid-examples.css" />

<!-- Common Styles for the examples -->
<link rel="stylesheet" type="text/css" href="${js}/ext/examples.css" />

</head>
<body>
<script type="text/javascript" src="${js}/ext/examples.js"></script>

<link rel="stylesheet" type="text/css" href="../lib.css" /><div id="lib-bar" class="x-layout-panel-hd">


<div id="grid-example"></div>

///////////////////////////////////////////////////////////////////////////

<display:table 
	name="page.result" 
	requestURI ="user.action"
	pagesize="3" partialList ="true" size ="page.totalCount" 
	export="true" >
	
	<display:column property="id"    	title="ID" 		class="idcol"/>
	<display:column property="email" 	title="登录名称" autolink="true"/>
	<display:column property="name"  	title="姓名" url="#" paramId="email" paramProperty="email"/>
	<display:column property="roleNames"title="角色"/>
	<display:column property="intro"  	title="个人介绍"/>
</display:table>


<table>
	<tr>
		<th><a href="user.action?page.orderBy=email&page.order=
		<s:if test="page.orderBy=='email'">${page.inverseOrder}</s:if><s:else>desc</s:else>">
		<b>登录名</b></a></th>
		<th><a href="user.action?page.orderBy=name&page.order=
		<s:if test="page.orderBy=='name'">${page.inverseOrder}</s:if><s:else>desc</s:else>">
		<b>姓名</b></a></th>
		<th><b>角色</b></th>
		<th><b>操作</b></th>
	</tr>

	<s:iterator value="page.result">
		<tr>
			<td>${email}&nbsp;</td>
			<td>${name}&nbsp;</td>
			<td>${roleNames}&nbsp;</td>
			<td>&nbsp; 
				<security:authorize ifAnyGranted="AUTH_MODIFY_USER">
					<a href="user!input.action?id=${id}&page.pageRequest=${page.pageRequest}">修改</a>、
					<a href="user!delete.action?id=${id}&page.pageRequest=${page.pageRequest}">删除</a>
				</security:authorize>
			</td>
		</tr>
	</s:iterator>
</table>
</div>

<div id="footer">
	第${page.pageNo}页, 共${page.totalPages}页 
	<s:if test="page.hasPre">
		<a href="user.action?page.pageNo=${page.prePage}&page.orderBy=${page.orderBy}&page.order=${page.order}">上一页</a>
	</s:if>
	<s:if test="page.hasNext">
		<a href="user.action?page.pageNo=${page.nextPage}&page.orderBy=${page.orderBy}&page.order=${page.order}">下一页</a>
	</s:if>
	<br />
	<security:authorize ifAnyGranted="AUTH_MODIFY_USER">
		<a href="user!input.action">增加新用户</a>
	</security:authorize>
</div>

<div id="comment">TODO</div>
</body>

</html>
