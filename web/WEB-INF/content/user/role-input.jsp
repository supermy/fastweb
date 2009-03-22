<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>角色管理</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${css}/css/default.css" type="text/css" rel="stylesheet">
	<link href="${css}/js/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
	<script src="${js}/js/jquery.js" type="text/javascript"></script>
	<script src="${js}/js/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${js}/js/validate/messages_cn.js" type="text/javascript"></script>

	<script>
		$(document).ready(function(){
			//聚焦第一个输入框
			$("#name").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</head>

<body>
<h3><s:if test="id == null">创建</s:if><s:else>修改</s:else>角色</h3>
<div id="inputContent">
<form id="inputForm" action="role!save.action" method="post">
<input type="hidden" name="id" value="${id}" />
<table>
	<tr>
		<td>角色名:</td>
		<td><input type="text" name="name" size="40" value="${name}" class="required"/></td>
	</tr>
	<tr>
		<td>授权:</td>
		<td>
			<div style="word-break:break-all;width:300px; overflow:auto; ">
				<s:checkboxlist name="checkedAuthIds"  list="allAuths"  listKey="id" listValue="nickName" theme="simple"/>
			</div>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="提交" />&nbsp; 
			<input type="button" value="取消" onclick="history.back()"/>
		</td>
	</tr>
</table>
</form>
</div>
</body>
</html>