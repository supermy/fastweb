<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>我的任务</title>

	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>
	
</head>

<body>
<div class="container">

	<%@ include file="/common/tools.jsp"%>
	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-17 prepend-1 colborder">
		
		<h2>我的任务</h2>
		<table >
			<tr>
				<th>所有者</th>
				<th>备注</th>
				<th>名称</th>
				<th>开始时间</th>
				<th>流程图(位置)</th>
				<th>操作</th>
			</tr>
		
			<s:iterator value="mytasks">
				<tr >
					<td >${actorId}&nbsp;</td>
					<td >${description}&nbsp;</td>
					<td >${name}&nbsp;</td>
					<td >${create}&nbsp;</td>
					<td >${token.name}&nbsp;</td>
					<td >&nbsp; 
							<a href="TODOTODOworkflow!workFlowImage.action?id=${id}">查看流程图</a>、
					</td>
				</tr>
			</s:iterator>
		</table>
		
		<h2>待处理的任务</h2>
		<table >
			<tr>
				<th>备注</th>
				<th>名称</th>
				<th>开始时间</th>
				<th>流程图(位置)</th>
				<th>操作</th>
			</tr>
		
			<s:iterator value="todotasks">
				<tr >
					<td >${description}&nbsp;</td>
					<td >${name}&nbsp;</td>
					<td >${create}&nbsp;</td>
					<td >${token.name}&nbsp;</td>
					<td >&nbsp; 
					<a href="workflow!workFlowImage.action?id=${id}">我来处理</a>、
					<a href="TODOTODOworkflow!workFlowImage.action?id=${id}">查看流程图</a>、
					</td>
				</tr>
			</s:iterator>
		</table>
		

		<hr/>
		
	</div>
	
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
	</div>
		

	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>
