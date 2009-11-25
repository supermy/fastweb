<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>资源管理</title>

<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/css.jsp"%>


</head>

<body>
<div class="container"><%@ include file="/common/tools.jsp"%>

<%@ include file="/common/nav.jsp"%>

<div class="span-17 prepend-1 colborder">
<table width=480 border=1>
	<tr>
		<td colspan="6">流程定义</td>
	</tr>
	<tr>
		<td>ID</td>
		<td>Key</td>
		<td>名称</td>
		<td>描述</td>
		<td>版本</td>
		<td>操作</td>
	</tr>
	<s:iterator value="pdresults">
		<tr>
			<td>${id}</td>
			<td>${key}</td>
			<td>${name}</td>
			<td>${description}</td>
			<td>${version}</td>
			<td><a href="workflow!workflowImage.action?id=${id}">查看流程图</a>、
			<a href="workflow!delete.action?id=${id}">删除发布</a></td>
		</tr>
	</s:iterator>
</table>
<br />

<table width=480 border=1>
	<tr>
		<td colspan="5">流程实例</td>
	</tr>
	<tr>
		<td>ID</td>
		<td>Key</td>
		<td>状态</td>
		<td>操作</td>
	</tr>
	<c:forEach var="piresults" items="${pi}">
		<tr>
			<td>${pi.id}</td>
			<td>${pi.key}</td>
			<td>${pi.state}</td>
			<td><a href="workflow!exec.action?pid=${pi.id }">执行</a></td>
		</tr>
	</c:forEach>
</table>

<div>第${page.pageNo}页, 共${page.totalPages}页 <s:if
	test="page.hasPre">
	<a
		href="workflow.action?page.pageNo=${page.prePage}&page.orderBy=${page.orderBy}&page.order=${page.order}">上一页</a>
</s:if> <s:if test="page.hasNext">
	<a
		href="workflow.action?page.pageNo=${page.nextPage}&page.orderBy=${page.orderBy}&page.order=${page.order}">下一页</a>
</s:if> <br />
</div>


<hr />

</div>

<div class="column span-5 last"><s:actionmessage theme="mytheme" />
<hr class="space" />
</div>


<%@ include file="/common/footer.jsp"%></div>
</body>

</html>
