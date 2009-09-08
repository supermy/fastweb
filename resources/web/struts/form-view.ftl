<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>


<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="${pojoNameLower}Detail.title"/></title>
    <meta name="heading" content="<s:text name='${pojoNameLower}Detail.heading'/>"/>

	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>

	<link href="${'$'}{css}/js/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
	<script src="${'$'}{js}/js/jquery.js" type="text/javascript"></script>
	<script src="${'$'}{js}/js/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${'$'}{js}/js/validate/localization/messages_cn.js" type="text/javascript"></script>
	<script>
		${'$'}(document).ready(function(){
			//聚焦第一个输入框
			${'$'}("#email").focus();
			//为inputForm注册validate函数
			${'$'}("#inputForm").validate({
				rules: {<#rt/>
					<#foreach field in pojo.getAllPropertiesIterator()>					
					<#if field.equals(pojo.identifierProperty)>
						<#lt/><#rt/>
					<#elseif !c2h.isCollection(field) && !c2h.isManyToOne(field) && !c2j.isComponent(field)>
					    <#foreach column in field.getColumnIterator()>
					        <#if field.value.typeName == "java.util.Date">
					            <#if (!column.nullable)>${field.name}: "required",</#if>
					        <#elseif field.value.typeName == "boolean" || field.value.typeName == "java.lang.Boolean">
					            <#if (!column.nullable)>${field.name}: "required",</#if>
					        <#else>
								<#if ((column.length > 0) && (!column.nullable))>
	                    		${field.name}:{
		                    		<#if (field.name.toLowerCase().endsWith("email"))>email:true,</#if>
			    					required: true,
			    					maxlength:${column.length}
			    				},			    									            
						        <#elseif (column.length > 0)>
	                    		${field.name}:{
		                    		<#if (field.name.toLowerCase().endsWith("email"))>email:true,</#if>
			    					maxlength:${column.length}
			    				},			    									            								
						        <#elseif (!column.nullable)>
	                    		${field.name}:{
		                    		<#if (field.name.toLowerCase().endsWith("email"))>email:true,</#if>
			    					required: true
			    				},	    									            								
								</#if>
					        </#if>
					    </#foreach>
					<#elseif c2h.isManyToOne(field)>
					    <#lt/>
					<#foreach column in field.getColumnIterator()>
					    </#foreach>
					</#if>
					</#foreach>
				},
				messages: {
					
				}
			});
		});
		
	</script>
</head>

<body>
<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-15 prepend-1 colborder">
		<h3><s:if test="id == null">创建</s:if><s:else>修改</s:else><s:text name="${pojoNameLower}Detail.title"/></h3>


		<form id="inputForm" action="${util.build(pojo.shortName)}!save.action" method="post">
		<input type="hidden" name="id" value="${'$'}{id}" />
		<input type="hidden" name="page${pojo.shortName.toLowerCase()}.pageRequest" value="${'$'}{page${pojo.shortName.toLowerCase()}.pageRequest}" />
		<table class="inputView">
			<#foreach field in pojo.getAllPropertiesIterator()>
			<#if field.equals(pojo.identifierProperty)>
				<#lt/>
			<#elseif !c2h.isCollection(field) && !c2h.isManyToOne(field) && !c2j.isComponent(field)>
			    <#foreach column in field.getColumnIterator()>
				<tr>
					<td><s:text name="${pojoNameLower}.${field.name}"/></td>
					<td>
			        <#if field.name == "create">
				            ${'$'}{${field.name}}					
			        <#else>
				        <#if field.value.typeName == "java.util.Date">
				            <input type="text" name="${field.name}" size="30" value="${'$'}{${field.name}}" />
				        <#elseif field.value.typeName == "boolean" || field.value.typeName == "java.lang.Boolean">
		             		<s:checkbox key="${field.name}" cssClass="checkbox" theme="simple"/>
				        <#else>
				            <input type="text" name="${field.name}" size="30" value="${'$'}{${field.name}}" />
				        </#if>
			        </#if>
					</td>
				</tr>
			    </#foreach>
			<#elseif c2h.isManyToOne(field)>
			    <#foreach column in field.getColumnIterator()>
			    </#foreach>
			</#if>
			</#foreach>
		
			<tr>
				<td colspan="2">
					<input type="submit" value="提交" />&nbsp; 
			        <c:if test="${'$'}{not empty id}">			        
			            <s:submit cssClass="button" method="delete" key="button.delete"
			                onclick="return confirmDelete('${pojo.shortName}')" theme="simple"/>
			        </c:if>
					<input type="button" value="取消" onclick="history.back()"/>
				</td>
			</tr>
		</table>
		</form>
		
	</div>
	<div class="column span-7 last">
		
	</div>

	<%@ include file="/common/footer.jsp"%>
	
</div>
</body>
</html>