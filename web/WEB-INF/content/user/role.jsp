<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>角色管理</title>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>
</head>

<body>

<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>

	<div class="span-15 prepend-1 colborder">
	
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

	<div class="column span-7 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
		<security:authorize ifAnyGranted="AUTH_MODIFY_ROLE">
		<a href="role!input.action">增加新角色</a>
		</security:authorize>
	</div>
	
	
	
	<%@ include file="/common/footer.jsp"%>
	
</div>

</body>
</html>