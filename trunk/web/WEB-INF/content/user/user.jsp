<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>用户管理</title>

	<%@ include file="/common/meta.jsp"%>
	
	<%@ include file="/common/css.jsp"%>
	
	<!--ext需要的部件，如果不用ext效果，不要加入，很费资源
	<%@ include file="/common/extjshead.jsp"%>
	<script type="text/javascript" src="${extjs}/ext/paging.js"></script>
	<script type="text/javascript" src="${extjs}/ext/edit-grid.js"></script>

	<link rel="stylesheet" type="text/css" href="${extjs}/ext/grid-examples.css" />
	-->
	
</head>

<body>
<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-17 prepend-1 colborder">

	<%@ taglib prefix="s" uri="/struts-tags"%>
	<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

	<sj:tabbedpanel id="localtabs">
	      <sj:tab id="tab1" target="tone" label="Local Tab One"/>
	      <sj:tab id="tab2" target="ttwo" label="Local Tab Two"/>
	      <sj:tab id="tab3" target="tthree" label="Local Tab Three"/>
	      <sj:tab id="tab4" target="tfour" label="Local Tab Four"/>
	      <div id="tone">Mauris mauris ante, blandit et, ultrices a, suscipit eget, quam. Integer ut neque. Vivamus nisi metus, molestie vel, gravida in, condimentum sit amet, nunc. Nam a nibh. Donec suscipit eros. Nam mi. Proin viverra leo ut odio. Curabitur malesuada. Vestibulum a velit eu ante scelerisque vulputate.</div>
	      <div id="ttwo">Sed non urna. Donec et ante. Phasellus eu ligula. Vestibulum sit amet purus. Vivamus hendrerit, dolor at aliquet laoreet, mauris turpis porttitor velit, faucibus interdum tellus libero ac justo. Vivamus non quam. In suscipit faucibus urna.</div>
	      <div id="tthree">Nam enim risus, molestie et, porta ac, aliquam ac, risus. Quisque lobortis. Phasellus pellentesque purus in massa. Aenean in pede. Phasellus ac libero ac tellus pellentesque semper. Sed ac felis. Sed commodo, magna quis lacinia ornare, quam ante aliquam nisi, eu iaculis leo purus venenatis dui.</div>
	      <div id="tfour">Cras dictum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean lacinia mauris vel est. Suspendisse eu nisl. Nullam ut libero. Integer dignissim consequat lectus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.</div>
	    </sj:tabbedpanel>


	<h2>用户列表</h2>

	<c:set var="buttons">
		<div>
			第${page.pageNo}页, 共${page.totalPages}页 
			<s:if test="page.hasPre">
				<a href="user.action?page.pageNo=${page.prePage}&page.orderBy=${page.orderBy}&page.order=${page.order}">上一页</a>
			</s:if>
			<s:if test="page.hasNext">
				<a href="user.action?page.pageNo=${page.nextPage}&page.orderBy=${page.orderBy}&page.order=${page.order}">下一页</a>
			</s:if>
			<br />
		</div>
	</c:set>
	
	
	
	<c:out value="${buttons}" escapeXml="false" />
	<display:table 
		name="page.result" 		
		requestURI =""
		export="flase" >
		
		<display:column property="id"    	title="ID" 	    />
		<display:column property="email" 	title="登录名称"   autolink="true"/>
		<display:column property="name"  	title="姓名"     />
		<display:column property="roleNames"title="角色"     />
		<display:column property="intro"  	title="个人介绍"  />
		<display:column value="修改"   	title="修改" 	url="/user/user!input.action?page.pageRequest=${page.pageRequest}" paramId="id" paramProperty="id"/>
		<display:column value="删除"   	title="删除" 	url="/user/user!delete.action?page.pageRequest=${page.pageRequest}" paramId="id" paramProperty="id"/>
		
	</display:table>
	<c:out value="${buttons}" escapeXml="false" />
	
	<br/>
	
	
		
		<h2>手工展示数据</h2>
		<c:out value="${buttons}" escapeXml="false" />
		<table >
			<tr>
				<th ><a href="user.action?page.orderBy=email&page.order=
				<s:if test="page.orderBy=='email'">${page.inverseOrder}</s:if><s:else>desc</s:else>">
				<b>登录名</b></a></th>
				<th ><a href="user.action?page.orderBy=name&page.order=
				<s:if test="page.orderBy=='name'">${page.inverseOrder}</s:if><s:else>desc</s:else>">
				<b>姓名</b></a></th>
				<th ><b>角色</b></th>
				<th ><b>操作</b></th>
			</tr>
		
			<s:iterator value="page.result">
				<tr >
					<td >${email}&nbsp;</td>
					<td >${name}&nbsp;</td>
					<td >${roleNames}&nbsp;</td>
					<td >&nbsp; 
						<security:authorize ifAnyGranted="AUTH_MODIFY_USER">
							<a href="user!input.action?id=${id}&page.pageRequest=${page.pageRequest}">修改</a>、
							<a href="user!delete.action?id=${id}&page.pageRequest=${page.pageRequest}">删除</a>
						</security:authorize>
					</td>
				</tr>
			</s:iterator>
		</table>
		<c:out value="${buttons}" escapeXml="false" />

		
		<hr/>
		
		<!--
		<hr/>
		<h2>EXT JS展示数据<h2>
		
		<div id="editor-grid"></div>
		<div id="user-page-grid"></div>
		-->
		
		<hr/>
		
		

		
		
	</div>
	
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<div id="filter">
			<form action="user!search.action" method="post">
				EMail或姓名: <input type="text" 
						name="filter_LIKE_name|email" 
						value="${param['filter_LIKE_name|email']}" 
						size="10"/>
				<input type="submit" value="搜索" />
			</form>
		</div> 
		<hr class="space"/>
		<security:authorize ifAnyGranted="AUTH_MODIFY_USER">
		<a href="user!input.action">增加新用户</a>
		</security:authorize>
	</div>
		

	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>
