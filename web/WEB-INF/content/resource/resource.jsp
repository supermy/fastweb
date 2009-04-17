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

		
		<h2>资源管理</h2>
		<table >
			<tr>
				<th ><a href="resource.action?page.orderBy=name&page.order=
				<s:if test="page.orderBy=='name'">${page.inverseOrder}</s:if><s:else>desc</s:else>">
				<b>名称</b></a></th>
				<th ><a href="resource.action?page.orderBy=fileType&page.order=
				<s:if test="page.orderBy=='fileType'">${page.inverseOrder}</s:if><s:else>desc</s:else>">
				<b>文件类别</b></a></th>
				<th ><a href="resource.action?page.orderBy=type&page.order=
				<s:if test="page.orderBy=='type'">${page.inverseOrder}</s:if><s:else>desc</s:else>">
				<b>类型</b></a></th>
				<th><b>完成</b></a></th>
				<th ><b>操作</b></th>
			</tr>
		
			<s:iterator value="page.result">
				<tr >
					<td ><a href="${resource}/${path}">${name}</a>&nbsp;</td>
					<td >${fileType}&nbsp;</td>
					<td >${type}&nbsp;</td>
					<td >${done}&nbsp;</td>
					<td >&nbsp; 
							<a href="resource!input.action?id=${id}&page.pageRequest=${page.pageRequest}">修改</a>、
							<a href="resource!delete.action?id=${id}&page.pageRequest=${page.pageRequest}">删除</a>、
							<s:if test="type=='工作流'">
								<a href="resource!deployProcess.action?id=${id}&page.pageRequest=${page.pageRequest}">发布</a>
							</s:if>
					</td>
				</tr>
			</s:iterator>
		</table>

		<div>
			第${page.pageNo}页, 共${page.totalPages}页 
			<s:if test="page.hasPre">
				<a href="resource.action?page.pageNo=${page.prePage}&page.orderBy=${page.orderBy}&page.order=${page.order}">上一页</a>
			</s:if>
			<s:if test="page.hasNext">
				<a href="resource.action?page.pageNo=${page.nextPage}&page.orderBy=${page.orderBy}&page.order=${page.order}">下一页</a>
			</s:if>
			<br />
		</div>
		
		
		<hr/>
		
	</div>
	
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
		<a href="resource!input.action">上传新文件</a>
	</div>
		

	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>
