<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>资源管理</title>

	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>

	
</head>

<body>
<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-17 prepend-1 colborder">

		
		<h2>流程管理</h2>
		<table >
			<tr>
				<th ><a href="workflow.action">
				<b>名称</b></a></th>
				<th ><a href="workflow.action">
				<b>版本号</b></a></th>
				<th ><a href="workflow.action">
				<b>备注</b></a></th>
				<th ><b>操作</b></th>
			</tr>
		
			<s:iterator value="results">
				<tr >
					<td >${name}&nbsp;</td>
					<td >${version}&nbsp;</td>
					<td >${description}&nbsp;</td>
					<td >&nbsp; 
							<!--img src="workflow!workflowImage.action?id=${id}" height="60"/-->
							<a href="workflow!workflowImage.action?id=${id}">查看流程图</a>、
							<a href="taskitem!input.action?processDefinitionId=${id}">启动一个流程</a>
					</td>
				</tr>
			</s:iterator>
		</table>

		<div>
			第${page.pageNo}页, 共${page.totalPages}页 
			<s:if test="page.hasPre">
				<a href="workflow.action?page.pageNo=${page.prePage}&page.orderBy=${page.orderBy}&page.order=${page.order}">上一页</a>
			</s:if>
			<s:if test="page.hasNext">
				<a href="workflow.action?page.pageNo=${page.nextPage}&page.orderBy=${page.orderBy}&page.order=${page.order}">下一页</a>
			</s:if>
			<br />
		</div>
		
		
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
