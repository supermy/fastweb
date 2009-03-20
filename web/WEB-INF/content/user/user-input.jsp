<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>用户管理</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${ctx}/css/default.css" type="text/css" rel="stylesheet">
	<link href="${ctx}/js/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script src="${ctx}/js/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${ctx}/js/validate/messages_cn.js" type="text/javascript"></script>
	<script>
		$(document).ready(function(){
			//聚焦第一个输入框
			$("#email").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: { 
					email: {
						required: true,
        				minlength:8,
						email:true
						<s:if test="id == null">
						,remote: "user!checkLoginEMail.action?orgEMail="+encodeURIComponent('${email}')
						</s:if>
    				},
            		name: "required",
            		passwd: {
    					required: true,
    					minlength:6
    				}, 
    				passwd2: {
    					equalTo:"#passwd"
    				},
				},
				messages: {
					email: {
						remote: "用户登录EMail已存在"
					},
					passwd2: {
						equalTo: "输入与上面相同的密码"
					}
				}
			});
		});
	</script>
</head>

<body>
<h3><s:if test="id == null">创建</s:if><s:else>修改</s:else>用户</h3>
<div id="inputContent">
<form id="inputForm" action="user!save.action" method="post">
<input type="hidden" name="id" value="${id}" />
<input type="hidden" name="page.pageRequest" value="${page.pageRequest}" />
<table class="inputView">
	<tr>
		<td>登录邮箱:</td>
		<td><input type="text" name="email" size="40" value="${email}" /></td>
	</tr>
	<tr>
		<td>用户名:</td>
		<td><input type="text" name="name" size="40" value="${name}" /></td>
	</tr>
	<tr>
		<td>密码:</td>
		<td><input type="password" id="passwd" name="passwd" size="40" value="${passwd}" /></td>
	</tr>
	<tr>
		<td>确认密码:${passwd}</td>
		<td><input type="password" name="passwd2" size="40" value="${passwd}" /></td>
	</tr>
	<tr>
		<td>角色:</td>
		<td>
			<div style="word-break:break-all;width:250px; overflow:auto; ">
				<s:checkboxlist name="checkedRoleIds"  list="allRoles"  listKey="id" listValue="name" theme="simple"/>
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