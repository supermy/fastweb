<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>角色管理</title>
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

<div id="filter">你好,<%=SecurityUtils.getCurrentUserName()%>.</div>

<div id="listContent">
<table>
	<tr>
		<th><b>名称</b></th>
		<th><b>授权</b></th>
		<th><b>操作</b></th>
	</tr>

	<s:iterator value="allRoles">
		<tr>
			<td>${name}</td>
			<td>${authNames}</td>
			<td>&nbsp; 
				<security:authorize ifAnyGranted="AUTH_MODIFY_ROLE">
					<a href="role!input.action?id=${id}">修改</a>、
					<a href="role!delete.action?id=${id}">删除</a>
				</security:authorize>
			</td>
		</tr>
	</s:iterator>
</table>
</div>

<div id="footer">
	<security:authorize ifAnyGranted="AUTH_MODIFY_ROLE">
		<a href="role!input.action">增加新角色</a>
	</security:authorize>
</div>

<div id="comment">TODO.</div>
</body>
</html>