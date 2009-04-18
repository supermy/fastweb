<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>资源管理</title>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>
	<link href="${css}/js/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
	<script src="${js}/js/jquery.js" type="text/javascript"></script>
	<script src="${js}/js/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${js}/js/validate/messages_cn.js" type="text/javascript"></script>
</head>

<body>

<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-15 prepend-1 colborder">
		<h3><s:if test="id == null">创建</s:if><s:else>修改</s:else>资源</h3>
		<form id="inputForm" action="resource!save.action" method="post" enctype ="multipart/form-data">
		<input type="hidden" name="id" value="${id}" />
		<input type="hidden" name="page.pageRequest" value="${page.pageRequest}" />
		<table class="inputView">
			<tr>
				<td>资源名称:</td>
				<td><input type="text" name="name" size="40" value="${name}" /></td>
			</tr>
			<tr>
				<td>资源类型:</td>
				<td>
					<select name="type" value="${type}">
						<option <s:if test="type=='文档'">SELECTED</s:if>>文档</option>
						<option <s:if test="type=='图片'">SELECTED</s:if>>图片</option>
						<option <s:if test="type=='工作流'">SELECTED</s:if>>工作流</option>
						<option <s:if test="type=='其他'">SELECTED</s:if>>其他</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>文件:</td>
				<td><input type="file" name="upload" size="50"/></td>
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
	<div class="column span-7 last">
	</div>

	<%@ include file="/common/footer.jsp"%>
	
</div>
</body>
</html>