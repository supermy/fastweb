<jsp:directive.page  language="java"   pageEncoding="UTF-8"/>
<%@ page import="org.supermy.core.security.SecurityUtils" %>

<div class="span-6 righttd">
	<img src="${image}/images/fast.jpg"/>
</div>

<div class="span-10">
	<br/>
	<h3><B>FastWeb</B></h3>
	Beta1
</div>

<div class="span-8 last" >
	<br/>
	<div class="cmdtd" >		
			<s:text name="common.hello"/>,<%=SecurityUtils.getCurrentUserName()%>.&nbsp;&nbsp;
-			<a href="${ctx}/j_spring_security_logout"><s:text name="common.exit"/></a> -
		 	<a href="${ctx}/login.action" target="_self"><s:text name="common.login"/></a> 
	</div>
</div>