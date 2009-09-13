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

	<!-- validator-->
	<link href="${'$'}{css}/js/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
	<script src="${'$'}{js}/js/jquery.js" type="text/javascript"></script>
	<script src="${'$'}{js}/js/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${'$'}{js}/js/validate/jquery.metadata.js" type="text/javascript"></script>
	<script src="${'$'}{js}/js/validate/localization/messages_cn.js" type="text/javascript"></script>
	<script>
		${'$'}().ready(function(){
			//聚焦第一个输入框
			//${'$'}("#email").focus();
			//为inputForm注册validate函数
			${'$'}("#${pojo.shortName.toLowerCase()}InputForm").validate();
		});
	</script>
	
	<!-- ui -->
	<script type="text/javascript" src="${'$'}{js}/js/jquery-ui-1.7.2/ui/minified/ui.core.min.js"></script>
	<script type="text/javascript" src="${'$'}{js}/js/jquery-ui-1.7.2/ui/minified/ui.datepicker.min.js"></script>

	<script type="text/javascript" src="${js}/js/jquery.tools.min.js"></script>
	<link href="${css}/js/tools/tabs.css" type="text/css" rel="stylesheet" />
	
</head>

<body>
<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-15 prepend-1 colborder">
		<h3><s:if test="id == null"><s:text name="common.label.create"/></s:if><s:else><s:text name="common.label.modify"/></s:else><s:text name="${pojoNameLower}Detail.title"/></h3>
		<form id="${pojo.shortName.toLowerCase()}InputForm" action="${util.build(pojo.shortName)}.action" method="post">
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
				            <input type="text" name="${field.name}"  id="${field.name}"  size="30" value="${'$'}{${field.name}}" class="${webdata.HValidator2JQValidator(field)}"/> ${webdata.required(field)}

							<script type="text/javascript">
							${'$'}(function() {
								${'$'}('#${field.name}').datepicker({
									changeMonth: true,
									changeYear: true
								});
							});
							</script>
				            
				        <#elseif field.value.typeName == "boolean" || field.value.typeName == "java.lang.Boolean">
		             		<s:checkbox key="${field.name}" cssClass="checkbox" theme="simple" class="${webdata.HValidator2JQValidator(field)}"/> ${webdata.required(field)}
				        <#else>
				            <input type="text" name="${field.name}" size="30" value="${'$'}{${field.name}}" class="${webdata.HValidator2JQValidator(field)}"/> ${webdata.required(field)}
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
		            <s:submit cssClass="button" method="save"  key="common.domain.save" theme="simple"/>
			        <c:if test="${'$'}{not empty id}">			        
			            <s:submit cssClass="button" method="delete" key="common.domain.delete"
			                onclick="return confirmDelete('${pojo.shortName}')" theme="simple"/>
			        </c:if>
		            <s:submit cssClass="button" key="common.domain.cancel" 
		            	onclick="history.back()" theme="simple"/>
				</td>
			</tr>
		</table>
		</form>
		
	</div>
	<div class="column span-7 last">
		to do list ... ...
	</div>

	<%@ include file="/common/footer.jsp"%>
	
</div>
</body>
</html>