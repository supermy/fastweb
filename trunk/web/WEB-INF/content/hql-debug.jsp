<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>HQL语句测试</title>
	<link href="${css}/css/default.css" type="text/css" rel="stylesheet">

</head>

<body>
<style type="text/css"> 

.SpanHeight{line-height:1.0} 

</style>

    
<div id="filter">
<form action="hql!search.action" method="post">
 	HQL语句:
 	<br/> 
    <textarea name="search" cols="80" rows="10" class=SpanHeight>
    	select obj  from User obj </textarea> 
	<br/>
    <input type="submit" value="搜索" />
</form>
</div> 

<div id="message"><s:actionmessage theme="mytheme"/></div>

<display:table name="page"/>

</body>

</html>
