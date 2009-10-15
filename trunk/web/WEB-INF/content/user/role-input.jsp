<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="roleDetail.title"/></title>
    <meta name="heading" content="<s:text name='roleDetail.heading'/>"/>

	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>
	<%@ include file="/common/js.jsp"%>
	<%@ include file="/common/js-validate.jsp"%>
	<script>
		$().ready(function(){
			//聚焦第一个输入框
			//$("#email").focus();
			//为inputForm注册validate函数
			$("#roleInputForm").validate();
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
      			<s:text name="urlResourceDetail.title"/>
      		</legend>
      		
			<s:form  
				validate="false" 
				theme="xhtml"  
				id="roleInputForm" 
				action="role.action" 
				method="post">
			
			<input 
				type="hidden" 
				name="id" 
				value="${id}" />

			<input 
				type="hidden" 
				name="pagerole.pageRequest" 
				value="${pagerole.pageRequest}"/>
				
							<s:checkboxlist 
								label="%{getText('role.auths')}"  
								name="authsId"
								list="authsAll"
								listKey="id" listValue="name"
								cssClass="{debug:false}"
								/>
									
		            		        <s:textfield
										label="%{getText('role.name')}"  
						            	name="name"  
						            	id="name"  
						            	size="30" 
						            	cssClass="{debug:false,maxlength:20,minlength:0,required: true}"/>
			
					<security:authorize 
							ifAnyGranted="AUTH_SAVE_ROLE">
							
			            	<s:submit 
			            		cssClass="button" 
			            		method="save"  
			            		key="common.domain.save"/>
			            		
					</security:authorize>
	
			        <c:if test="${not empty id}">			        
						<security:authorize 
							ifAnyGranted="AUTH_DELETE_ROLE">
							
				            <s:submit 
				            	cssClass="button" 
				            	method="delete" 
				            	key="common.domain.delete"
				                onclick="return confirmDelete('Role')" 
				                />
				                
						</security:authorize>
			        </c:if>
				        
		            <s:submit 
		            	cssClass="button" 
		            	key="common.domain.cancel" 
		            	onclick="history.back()"/>
		            	
			</s:form>
      	</fieldset>		
		
	</div>
	<div class="column span-7 last">
		<s:actionmessage/>
		
		<table>
		<tbody>
				<tr class="even">
					<th><s:text name="role.create"/></th>
					<td>
				            ${create}					
					</td>
				</tr>
				<tr>
					<th><s:text name="role.createBy"/></th>
					<td>
				            ${createBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="role.update"/></th>
					<td>
				            ${update}					
					</td>
				</tr>
				<tr>
					<th><s:text name="role.updateBy"/></th>
					<td>
				            ${updateBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="role.enabled"/></th>
					<td>
				            ${enabled}					
					</td>
				</tr>
		</tbody>		
		</table>

	</div>

	<%@ include file="/common/footer.jsp"%>
	
</div>
</body>
</html>