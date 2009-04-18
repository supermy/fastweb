<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>流程实例管理</title>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>
</head>

<body>

<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>

	<div class="span-15 prepend-1 colborder">
	
		<h2>流程实例管理</h2>
		<table>
			<tr>
				<th><b>创建人</b></th>
				<th><b>名称</b></th>
				<th><b>金额</b></th>
				<th><b>完成</b></th>
				<th><b>操作</b></th>
			</tr>
		
			<s:iterator value="page.result">
				<tr>
					<td>${userName}</td>
					<td>${title}</td>
					<td>${money}</td>
					<td>${task}</td>
					<td>&nbsp; 
							<a href="taskitem!input.action?id=${id}">修改</a> 
							<a href="${ctx}/workflow/workflowplan.action?taskId=${task.id}" target="_blank">进度</a>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>

	<div class="column span-7 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
	</div>
	
	
	
	<%@ include file="/common/footer.jsp"%>
	
</div>

</body>
</html>