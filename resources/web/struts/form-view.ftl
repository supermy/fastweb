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
	<%@ include file="/common/js.jsp"%>
	<%@ include file="/common/js-validate.jsp"%>
<#if webdata.fieldHasType(clazz.getMappedClass(),"java.util.Date")>
	<script src="${'$'}{js}/js/jquery-ui-1.7.2/ui/minified/ui.core.min.js"  type="text/javascript"></script>
	<script src="${'$'}{js}/js/jquery-ui-1.7.2/ui/minified/ui.datepicker.min.js"  type="text/javascript"></script>
	<link rel="stylesheet" href="${'$'}{css}/css/jquery-ui-1.7.2/jquery-ui.css" type="text/css" media="screen, projection"/>
	<link rel="stylesheet" href="${'$'}{css}/css/jquery-ui-1.7.2/ui.datepicker.css" type="text/css" media="screen, projection"/>
</#if>
	<script>
		${'$'}().ready(function(){
			//聚焦第一个输入框
			//${'$'}("#email").focus();
			//为inputForm注册validate函数
			${'$'}("#${pojo.shortName.toLowerCase()}InputForm").validate();
		});
	</script>

</head>

<body>
<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-15 prepend-1 colborder">

      	<fieldset>
      	
      		<legend>
      			<s:if test="id == null">
      				<s:text name="common.label.create"/>
      			</s:if>
      			<s:else>
      				<s:text name="common.label.modify"/>
      			</s:else>
      			<s:text name="${pojoNameLower}Detail.title"/>
      		</legend>
      		
			<s:form  
				validate="false" 
				theme="xhtml"  
				id="${pojo.shortName.toLowerCase()}InputForm" 
				action="${util.build(pojo.shortName)}.action" 
				method="post">
			
		    <s:token />

			<s:hidden	name="id" />

			<s:hidden 	name="page${pojo.shortName.toLowerCase()}.pageRequest"/>
				
				<#foreach field in pojo.getAllPropertiesIterator()>
						<#if field.equals(pojo.identifierProperty)>
							<#lt/>
						<#elseif c2h.isCollection(field)>
							<#lt/>			<s:checkboxlist 
							<#lt/>				label="%{getText('${pojoNameLower}.${field.name}')}"  
							<#lt/>				name="${field.name}Id"
							<#lt/>				list="${field.name}All"
							<#lt/>				listKey="id" listValue="name"
							<#lt/>				cssClass="${webdata.HValidator2JQValidator(field)}"
							<#lt/>				/>
									
						<#elseif c2h.isManyToOne(field)>
							<#lt/>			<s:select
							<#lt/>				label="%{getText('${pojoNameLower}.${field.name}')}"  
						    <#lt/>			    list="${field.name}List"
						    <#lt/>			    name="${field.name}.id"
						    <#lt/>			    listKey="id"
						    <#lt/>			    listValue="name"
						    <#lt/>			    emptyOption="false"
						    <#lt/>			    headerKey="0"
						    <#lt/>			    headerValue="None"
							<#lt/>				cssClass=" ${webdata.HValidator2JQValidator(field)}"
						    <#lt/>			    />
								
						<#elseif c2j.isComponent(field)>
			
					    <#else>
					    <#foreach column in field.getColumnIterator()>
					        <#if field.name == "create" || field.name == "update" || field.name == "createBy" || field.name == "updateBy"  || field.name == "enabled">
					        <#else>
						        <#if field.value.typeName == "java.util.Date">
	            		        <#lt/>			<s:textfield
								<#lt/>				label="%{getText('${pojoNameLower}.${field.name}')}"  
					            <#lt/>				name="${field.name}"  
					            <#lt/>				id="${field.name}"  
					            <#lt/>				size="30" 
					            <#lt/>				cssClass="${webdata.HValidator2JQValidator(field)}"/>
								<#lt/>			<script type="text/javascript">
								<#lt/>			${'$'}(function() {
								<#lt/>				${'$'}("#${field.name}").datepicker(
								<#lt/>					{
								<#lt/>						dateFormat: "yy-mm-dd",
								<#lt/>						changeMonth: true,
								<#lt/>						changeYear: true,
								<#lt/>						//numberOfMonths: 3,
								<#lt/>						//minDate: -20, maxDate: '+1M +10D',//最大日期 最小日期
								<#lt/>						showButtonPanel: true									
								<#lt/>					}
								<#lt/>				);
								<#lt/>			});
								<#lt/>			</script>							            
						        <#elseif field.value.typeName == "boolean" || field.value.typeName == "java.lang.Boolean">
				             		<#lt/>			<s:checkbox 
				             		<#lt/>				label="%{getText('${pojoNameLower}.${field.name}')}"  
				             		<#lt/>				key="${field.name}" 
				             		<#lt/>				cssClass="${webdata.HValidator2JQValidator(field)}"/>
						        <#else>
			        			    <#foreach column in field.getColumnIterator()>
								        <#if (column.length>200)&& (field.value.typeName.contains("String")) >
				            		        <#lt/>			<s:textarea
											<#lt/>				label="%{getText('${pojoNameLower}.${field.name}')}"  
								            <#lt/>				name="${field.name}"  
								            <#lt/>				id="${field.name}"  
								            <#lt/>				cols="20"
            								<#lt/>				rows="3" 
								            <#lt/>				cssClass="${webdata.HValidator2JQValidator(field)}"/>
								        <#else>
				            		        <#lt/>			<s:textfield
											<#lt/>				label="%{getText('${pojoNameLower}.${field.name}')}"  
								            <#lt/>				name="${field.name}"  
								            <#lt/>				id="${field.name}"  
								            <#lt/>				size="30" 
								            <#lt/>				cssClass="${webdata.HValidator2JQValidator(field)}"/>
								        </#if>
							    	</#foreach>

						        </#if>
					        </#if>
				    	</#foreach>
						</#if>
				</#foreach>
			
				<#lt/>			<security:authorize 
				<#lt/>					ifAnyGranted="AUTH_SAVE_${pojo.shortName.toUpperCase()}">
						
		        <#lt/>			    	<s:submit 
		        <#lt/>			    		cssClass="button" 
		        <#lt/>			    		method="save"  
		        <#lt/>			    		key="common.domain.save"/>
		            		
				</security:authorize>
	
		        <#lt/>			<c:if test="${'$'}{not empty id}">			        
					<#lt/>			<security:authorize 
					<#lt/>				ifAnyGranted="AUTH_DELETE_${pojo.shortName.toUpperCase()}">
						
			        <#lt/>			    <s:submit 
			        <#lt/>			    	cssClass="button" 
			        <#lt/>			    	method="delete" 
			        <#lt/>			    	key="common.domain.delete"
			        <#lt/>			        onclick="return confirmDelete('${pojo.shortName}')" 
			        <#lt/>			        />
			                
					<#lt/>			</security:authorize>
		        <#lt/>			</c:if>
				        
	            <#lt/>			<s:submit 
	            <#lt/>				cssClass="button" 
	            <#lt/>				key="common.domain.cancel" 
	            <#lt/>				onclick="history.back()"/>
		            	
			</s:form>
      	</fieldset>		
		
	</div>
	<div class="column span-7 last">
		<s:actionmessage/>
		
		<table>
		<tbody>
				<tr class="even">
					<th><s:text name="${pojoNameLower}.create"/></th>
					<td>
				            ${'$'}{create}					
					</td>
				</tr>
				<tr>
					<th><s:text name="${pojoNameLower}.createBy"/></th>
					<td>
				            ${'$'}{createBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="${pojoNameLower}.update"/></th>
					<td>
				            ${'$'}{update}					
					</td>
				</tr>
				<tr>
					<th><s:text name="${pojoNameLower}.updateBy"/></th>
					<td>
				            ${'$'}{updateBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="${pojoNameLower}.enabled"/></th>
					<td>
				            ${'$'}{enabled}					
					</td>
				</tr>
		</tbody>		
		</table>

	</div>

	<%@ include file="/common/footer.jsp"%>
	
</div>
</body>
</html>