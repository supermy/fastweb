<%@ taglib prefix="s" uri="/struts-tags"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.opensymphony.com/oscache" prefix="cache" %>

<!-- 生产机时候使用不同的域名 -->
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="resource" value="${pageContext.request.contextPath}"/>
<c:set var="css" value="${pageContext.request.contextPath}"/>
<c:set var="image" value="${pageContext.request.contextPath}"/>
<c:set var="js" value="${pageContext.request.contextPath}"/>
