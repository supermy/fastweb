<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<?xml version="1.0" encoding="UTF-8"?>
<chart>
	<series>
		<s:iterator value="trendlinexml" status='st'>
			<value xid="${st.index}"><s:date name="year" format="yyyy-MM-dd"/></value>
		</s:iterator>
	</series>
	<graphs>
		<graph gid="1">
		<s:iterator value="trendlinexml" status='st'>
				<value xid="${st.index}">${ball}</value>
		</s:iterator>
	</graphs>
</chart>
