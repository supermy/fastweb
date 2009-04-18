<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/jbpm.tld" prefix="jbpm" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>流程实例管理</title>
	<%@ include file="/common/meta.jsp"%>
</head>

<body>
	<jbpm:processimage task="${param.taskId}"/>
</body>

</html>