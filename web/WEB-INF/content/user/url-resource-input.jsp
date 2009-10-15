<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="urlResourceDetail.title"/></title>
    <meta name="heading" content="<s:text name='urlResourceDetail.heading'/>"/>

	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>
	<%@ include file="/common/js.jsp"%>
	<%@ include file="/common/js-validate.jsp"%>
	<script>
		$().ready(function(){
			//聚焦第一个输入框
			//$("#email").focus();
			//为inputForm注册validate函数
			$("#urlresourceInputForm").validate();
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
				id="urlresourceInputForm" 
				action="url-resource.action" 
				method="post">
			
		    <s:token />

			<s:hidden	name="id" />

			<s:hidden 	name="pageuser.pageRequest"/>
				
			<s:checkboxlist 
				label="%{getText('urlResource.authorityList')}"  
				name="authorityListId"
				list="authorityListAll"
				listKey="id" listValue="name"
				cssClass="{debug:false}"
				/>
									
			<s:textarea
				label="%{getText('urlResource.desc')}"  
				name="desc"  
				id="desc"  
				cols="20"
				rows="3" 
				cssClass="{debug:false,maxlength:250,minlength:0,required: true}"/>

			<s:select
				label="%{getText('urlResource.manager')}"  
			    list="managerList"
			    name="manager.id"
			    listKey="id"
			    listValue="name"
			    emptyOption="false"
			    headerKey="None"
			    headerValue="None"
				cssClass=" {debug:false,digits:true}"
			    />
								
			<s:textfield
				label="%{getText('urlResource.position')}"  
				name="position"  
				id="position"  
				size="30" 
				cssClass="{debug:false,number:true}"/>

			<s:textfield
				label="%{getText('urlResource.resourceType')}"  
				name="resourceType"  
				id="resourceType"  
				size="30" 
				cssClass="{debug:false,maxlength:20,minlength:0,required: true}"/>

			<s:textarea
				label="%{getText('urlResource.value')}"  
				name="value"  
				id="value"  
				cols="20"
				rows="3" 
				cssClass="{debug:false,maxlength:250,minlength:0,required: true}"/>

			
			<security:authorize 
					ifAnyGranted="AUTH_SAVE_URLRESOURCE">
						
			    	<s:submit 
			    		cssClass="button" 
			    		method="save"  
			    		key="common.domain.save"/>
		            		
				</security:authorize>
	
			<c:if test="${not empty id}">			        
			<security:authorize 
				ifAnyGranted="AUTH_DELETE_URLRESOURCE">
						
			    <s:submit 
			    	cssClass="button" 
			    	method="delete" 
			    	key="common.domain.delete"
			        onclick="return confirmDelete('UrlResource')" 
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
					<th><s:text name="urlResource.create"/></th>
					<td>
				            ${create}					
					</td>
				</tr>
				<tr>
					<th><s:text name="urlResource.createBy"/></th>
					<td>
				            ${createBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="urlResource.update"/></th>
					<td>
				            ${update}					
					</td>
				</tr>
				<tr>
					<th><s:text name="urlResource.updateBy"/></th>
					<td>
				            ${updateBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="urlResource.enabled"/></th>
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