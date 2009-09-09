<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/jbpm.tld" prefix="jbpm" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>流程实例管理</title>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>
	<link href="${css}/js/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
	<script src="${js}/js/jquery.js" type="text/javascript"></script>
	<script src="${js}/js/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${js}/js/validate//localization/messages_cn.js" type="text/javascript"></script>
	<script>
		$(document).ready(function(){
			//聚焦第一个输入框
			$("#title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: { 
	        		title: "required",
	        		url: "required",
				},
				messages: {
				}
			});
		});
	</script>
</head>

<body>

<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-15 prepend-1 colborder">
		<h3>绑定流程</h3>
		<form id="inputForm" action="taskitem!save.action" method="post">
		<input type="hidden" name="id" value="${id}" />
		<input type="hidden" name="processDefinitionId" value="${processDefinitionId}" />
		<input type="hidden" name="page.pageRequest" value="${page.pageRequest}" />
		<table class="inputView">
			<tr>
				<td>单据地址:</td>
				<td><input type="text" name="url" size="50" value="${url}" /></td>
			</tr>
			<tr>
				<td>标题:</td>
				<td><input type="text" name="title" size="30" value="${title}" /></td>
			</tr>
			<tr>
				<td>金额:</td>
				<td><input type="text" name="money" size="12" value="${money}" /></td>
			</tr>
			<tr>
				<td>备注:</td>
				<td>
				    <textarea name="remark" cols="46" rows="4" value="${remark}"></textarea> 
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="2">
					<input type="submit" value="保存" />&nbsp; 
					<input type="button" value="取消" onclick="history.back()"/>
				</td>
			</tr>
		</table>
		</form>

	</div>
	<div class="column span-7 last">

		<s:if test="taskId == null">
			<a href="workflow!workflowImage.action?id=${processDefinitionId}" target="_blank" title="点击看原图">
			<img src="workflow!workflowImage.action?id=${processDefinitionId}" width="250" />
			</a>
			<br/>
			<a href="workflow!workflowImage.action?id=${processDefinitionId}" target="_blank">点击看原图</a>
		</s:if><s:else>
			<a href="${ctx}/workflow/workflowplan.action?taskId=${taskId}" target="_blank" title="点击看原图">
			<img src="workflow!workflowImage.action?id=${processDefinitionId}" width="250" />
			</a>
			<br/>
			<a href="${ctx}/workflow/workflowplan.action?taskId=${taskId}" target="_blank">点击看进度图</a>
		</s:else>
		
			
	</div>

	<%@ include file="/common/footer.jsp"%>
	
</div>
</body>
</html>