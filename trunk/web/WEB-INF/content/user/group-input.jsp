<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="groupDetail.title"/></title>
    <meta name="heading" content="<s:text name='groupDetail.heading'/>"/>

	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>
	<%@ include file="/common/js.jsp"%>
	<%@ include file="/common/js-validate.jsp"%>
	<script>
		$().ready(function(){
			//聚焦第一个输入框
			//$("#email").focus();
			//为inputForm注册validate函数
			$("#groupInputForm").validate();
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
      			<s:text name="groupDetail.title"/>
      		</legend>
      		
			<s:form  
				validate="false" 
				theme="xhtml"  
				id="groupInputForm" 
				action="group.action" 
				method="post">
			
		    <s:token />

			<s:hidden	name="id" />

			<s:hidden 	name="pagegroup.pageRequest"/>
				
			<s:textfield
				label="%{getText('group.code')}"  
				name="code"  
				id="code"  
				size="30" 
				cssClass="{debug:false,maxlength:80,minlength:2}"/>

			<s:textfield
				label="%{getText('group.email')}"  
				name="email"  
				id="email"  
				size="30" 
				cssClass="{debug:false,email:true,maxlength:80,minlength:0,required: true}"/>

			<s:textarea
				label="%{getText('group.intro')}"  
				name="intro"  
				id="intro"  
				cols="20"
				rows="3" 
				cssClass="{debug:false,maxlength:250,minlength:0}"/>

			<s:textfield
				label="%{getText('group.name')}"  
				name="name"  
				id="name"  
				size="30" 
				cssClass="{debug:false,maxlength:80,minlength:2,required: true}"/>

			<s:select
				label="%{getText('group.parent')}"  
			    list="parentList"
			    name="parent.id"
			    listKey="id"
			    listValue="name"
			    emptyOption="false"
			    headerKey="0"
			    headerValue="None"
				cssClass=" {debug:false}"
			    />
								
			<s:textfield
				label="%{getText('group.type')}"  
				name="type"  
				id="type"  
				size="30" 
				cssClass="{debug:false,maxlength:80,minlength:0,required: true}"/>

			
			<security:authorize 
					ifAnyGranted="AUTH_SAVE_GROUP">
						
			    	<s:submit 
			    		cssClass="button" 
			    		method="save"  
			    		key="common.domain.save"/>
		            		
				</security:authorize>
	
			<c:if test="${not empty id}">			        
			<security:authorize 
				ifAnyGranted="AUTH_DELETE_GROUP">
						
			    <s:submit 
			    	cssClass="button" 
			    	method="delete" 
			    	key="common.domain.delete"
			        onclick="return confirmDelete('Group')" 
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
					<th><s:text name="group.create"/></th>
					<td>
				            ${create}					
					</td>
				</tr>
				<tr>
					<th><s:text name="group.createBy"/></th>
					<td>
				            ${createBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="group.update"/></th>
					<td>
				            ${update}					
					</td>
				</tr>
				<tr>
					<th><s:text name="group.updateBy"/></th>
					<td>
				            ${updateBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="group.enabled"/></th>
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