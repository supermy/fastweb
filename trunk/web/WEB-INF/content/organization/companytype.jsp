<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>公司类型管理</title>

	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>

	
</head>

<body>
<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-17 prepend-1 colborder">

		
		<h2>公司类型管理</h2>
		<table >
			<tr>
				<th ><b>名称</a></th>
				<th ><b>简介</a></th>
				<th ><b>操作</b></th>
			</tr>
		
			<s:iterator value="companyTypes">
				<tr >
					<td >${name}&nbsp;</td>
					<td >${intro}&nbsp;</td>
					<td >&nbsp; 
							<a href="companytype!input.action?id=${id}">修改</a>、
							<a href="companytype!delete.action?id=${id}">删除</a>、
					</td>
				</tr>
			</s:iterator>
		</table>

		<div>
			<br />
		</div>
		
		
		<hr/>
		
	</div>
	
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
		<a href="companytype!input.action">增加公司类型</a>
	</div>
		

	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>
