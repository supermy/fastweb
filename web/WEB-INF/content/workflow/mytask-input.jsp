<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>处理任务</title>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>
	<link href="${css}/js/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
	<script src="${js}/js/jquery.js" type="text/javascript"></script>
	<script src="${js}/js/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${js}/js/validate//localization/messages_cn.js" type="text/javascript"></script>

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


<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-15 prepend-1 colborder">
		<h3>处理任务</h3>
		<form id="inputForm" action="mytask!save.action" method="post">
		<input type="hidden" name="id" value="${id}" />
		
		<table>
			<tr>
			<th>名称</th><td ><a href="${taskItem.url}" target="_blank">${taskItem.title}</a></td>
			</tr>
			<tr>
			<th>金额</th><td >${taskItem.money}&nbsp;</td>
			</tr>
			<tr>
			<th>备注</th><td >${taskItem.remark}&nbsp;</td>
			</tr>
			<tr>
				<th>名称</th><td >${name}&nbsp;</td>
				<th>开始时间</th><td >${create}&nbsp;</td>
			</tr>

			<tr>
				<th>审批意见</th><td colspan="4">
					<textarea name="remark" cols="46" rows="4" value="${remark}"></textarea> 
				</td>
			</tr>

			<tr>
				<th>审批结果</th><td>
				<s:radio theme="simple"  name="pass"  
						value="availableTransitions[0].name" 
						list="availableTransitions" 
						listValue="name" listKey="name"/>
				</td>
			</tr>

			<tr valign="middle">
				<td></td>
				<td colspan="6" align="center" valign="middel">
					<input type="submit" id="sendback" name="save" value="保存" />&nbsp; 
					<input type="button" value="暂不处理" onclick="history.back()"/>
				</td>
			</tr>
		</table>
		</form>
	</div>

	<div class="column span-7 last">
		<h3>流程图</h3>
			<a href="${ctx}/workflow/workflowplan.action?taskId=${id}" target="_blank" title="点击看原图">
			<img src="workflow!workflowImage.action?id=${processInstance.processDefinition.id}" width="250" />
			</a>
			<br/>
			<a href="${ctx}/workflow/workflowplan.action?taskId=${id}" target="_blank">点击看进度图</a>
	</div>

	<%@ include file="/common/footer.jsp"%>
	
</div>
</body>
</html>