<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>FastWEB Demo</title>
	<%@ include file="/common/css.jsp"%>
</head>

<body>
<div class="container">

	<%@ include file="/common/tools.jsp"%>

	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-18 prepend-1 colborder">
		现有功能介绍：测试驱动方式开发，每个功能都有测试驱动的最佳用例;整合JBPM，支持自定义工作流；整合CXF,支持WebService<br/>
		一、完成一个页面的流程<br/>
			1、分析业务需求，完成快速原型和流程图；<br/>
			2、分析页面原型建立对象类图；<br/>
			   使用hibernate,同时完成数据表定义，数据验证要求，之后可以自动生成数据表<br/>
			3、根据页面原型和流程些业务逻辑，要求建立完成的逻辑junit测试，能够准确的生成对照数据；<br/>
			4、将页面原型与逻辑对接；<br/>
			5、熟练之后，整个开发过程可以按照从页面写道Domain类<br/>
			<br/>
		二、技术特点<br/>
			1、完成对象类图的同时，就做完了数据表定义，数据验证，数据缓存，全文检索(TODO)；<br/>
			2、逻辑程建立完成的测试，与业务数据作精准对照；<br/>
			3、全面采用注解，达到零配置；<br/>
			4、引入blueprint CSS框架，便于页面规范管理；(与ext-css冲突FIXME)<br/>
			5、引入ext js(ext-debug.html ext测试使用),非常方便的做后台管理程序时候。<br/>
			6、hql-debug.actin hql查询测试使用<br/>
			7、注重性能优化：同时有两套cache机制，单机建议使用oscache，多机集群建议使用memcached配置；
				关闭了struts的验证机制，服务器断采用hibernate的验证，页面采用jquery验证机制<br/>
			8、并建立了fastwebtemplate基类，完成了常用的分页等操作，逻辑程只需注重逻辑编写即可<br/>
			
			
			<br/>
			
			
	</div>
	
	<div class="column span-6 last">
	</div>
	
	<div id="comment" class="span-24">
		TODO:以此为基础，建立几个实际应用。
	</div>
	
	<%@ include file="/common/footer.jsp"%>
	
</div>
</body>
</html>