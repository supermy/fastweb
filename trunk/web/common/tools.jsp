<jsp:directive.page  language="java"   pageEncoding="UTF-8"/>
<%@ page import="org.supermy.core.security.SecurityUtils" %>

<div class="span-6 righttd">
	<img src="${image}/images/fast.jpg"/>
</div>

<div class="span-10">
	<br/>
	<br/>
	<h4><B>FastWeb</B></h4>
	Beta1
</div>

<div class="span-8 last" >
	<br/>
	<div class="cmdtd" >		
			你好,<%=SecurityUtils.getCurrentUserName()%>.&nbsp;&nbsp;
-			<a href="${ctx}/j_spring_security_logout">退出</a> -
		 	<a href="${ctx}/login.action" target="_self">登录</a> 
	</div>		

</div>    

