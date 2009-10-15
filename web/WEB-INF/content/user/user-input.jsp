<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="userDetail.title"/></title>
    <meta name="heading" content="<s:text name='userDetail.heading'/>"/>

	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>
	<%@ include file="/common/js.jsp"%>
	<%@ include file="/common/js-validate.jsp"%>
	<script>
		$().ready(function(){
			//聚焦第一个输入框
			//$("#email").focus();
			//为inputForm注册validate函数
			$("#userInputForm").validate();
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
				id="userInputForm" 
				action="user.action" 
				method="post">
			
		    <s:token />

			<s:hidden	name="id" />

			<s:hidden 	name="pageuser.pageRequest"/>
				
			<s:checkbox 
				label="%{getText('user.accountNonExpired')}"  
				key="accountNonExpired" 
				cssClass="{debug:false}"/>
			<s:checkbox 
				label="%{getText('user.accountNonLocked')}"  
				key="accountNonLocked" 
				cssClass="{debug:false}"/>
			<s:checkbox 
				label="%{getText('user.credentialsNonExpired')}"  
				key="credentialsNonExpired" 
				cssClass="{debug:false}"/>
			<s:textfield
				label="%{getText('user.email')}"  
				name="email"  
				id="email"  
				size="30" 
				cssClass="{debug:false,email:true,required: true,maxlength:80}"/>

			<s:textarea
				label="%{getText('user.intro')}"  
				name="intro"  
				id="intro"  
				cols="20"
				rows="3" 
				cssClass="{debug:false}"/>

			<s:textfield
				label="%{getText('user.name')}"  
				name="name"  
				id="name"  
				size="30" 
				cssClass="{debug:false,maxlength:80,minlength:2,required: true}"/>

			<s:textfield
				label="%{getText('user.passwd')}"  
				name="passwd"  
				id="passwd"  
				size="30" 
				cssClass="{debug:false,maxlength:32,minlength:2,required: true}"/>

			<s:checkboxlist 
				label="%{getText('user.roles')}"  
				name="rolesId"
				list="rolesAll"
				listKey="id" listValue="name"
				cssClass="{debug:false}"
				/>
									
			<s:textfield
				label="%{getText('user.salary')}"  
				name="salary"  
				id="salary"  
				size="30" 
				cssClass="{debug:false,number:true}"/>

			
			<security:authorize 
					ifAnyGranted="AUTH_SAVE_USER">
						
			    	<s:submit 
			    		cssClass="button" 
			    		method="save"  
			    		key="common.domain.save"/>
		            		
				</security:authorize>
	
			<c:if test="${not empty id}">			        
			<security:authorize 
				ifAnyGranted="AUTH_DELETE_USER">
						
			    <s:submit 
			    	cssClass="button" 
			    	method="delete" 
			    	key="common.domain.delete"
			        onclick="return confirmDelete('User')" 
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
					<th><s:text name="user.create"/></th>
					<td>
				            ${create}					
					</td>
				</tr>
				<tr>
					<th><s:text name="user.createBy"/></th>
					<td>
				            ${createBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="user.update"/></th>
					<td>
				            ${update}					
					</td>
				</tr>
				<tr>
					<th><s:text name="user.updateBy"/></th>
					<td>
				            ${updateBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="user.enabled"/></th>
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