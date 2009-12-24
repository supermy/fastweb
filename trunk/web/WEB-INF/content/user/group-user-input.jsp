<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="groupUserDetail.title"/></title>
    <meta name="heading" content="<s:text name='groupUserDetail.heading'/>"/>

	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>
	<%@ include file="/common/js.jsp"%>
	<%@ include file="/common/js-validate.jsp"%>
	<script>
		$().ready(function(){
			//聚焦第一个输入框
			//$("#email").focus();
			//为inputForm注册validate函数
			$("#groupuserInputForm").validate();
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
      			<s:text name="groupUserDetail.title"/>
      		</legend>
      		
			<s:form  
				validate="false" 
				theme="xhtml"  
				id="groupuserInputForm" 
				action="group-user.action" 
				method="post">
			
		    <s:token />

			<s:hidden	name="id" />

			<s:hidden 	name="pagegroupuser.pageRequest"/>
				
			<s:select
				label="%{getText('groupUser.group')}"  
			    list="groupList"
			    name="group.id"
			    listKey="id"
			    listValue="name"
			    emptyOption="false"
			    headerKey="0"
			    headerValue="None"
				cssClass=" {debug:false}"
			    />
								
			<s:textarea
				label="%{getText('groupUser.intro')}"  
				name="intro"  
				id="intro"  
				cols="20"
				rows="3" 
				cssClass="{debug:false,maxlength:250,minlength:0}"/>

			<s:textfield
				label="%{getText('groupUser.name')}"  
				name="name"  
				id="name"  
				size="30" 
				cssClass="{debug:false,maxlength:80,minlength:2,required: true}"/>

			<s:select
				label="%{getText('groupUser.user')}"  
			    list="userList"
			    name="user.id"
			    listKey="id"
			    listValue="name"
			    emptyOption="false"
			    headerKey="0"
			    headerValue="None"
				cssClass=" {debug:false}"
			    />
								
			
			<security:authorize 
					ifAnyGranted="AUTH_SAVE_GROUPUSER">
						
			    	<s:submit 
			    		cssClass="button" 
			    		method="save"  
			    		key="common.domain.save"/>
		            		
				</security:authorize>
	
			<c:if test="${not empty id}">			        
			<security:authorize 
				ifAnyGranted="AUTH_DELETE_GROUPUSER">
						
			    <s:submit 
			    	cssClass="button" 
			    	method="delete" 
			    	key="common.domain.delete"
			        onclick="return confirmDelete('GroupUser')" 
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
					<th><s:text name="groupUser.create"/></th>
					<td>
				            ${create}					
					</td>
				</tr>
				<tr>
					<th><s:text name="groupUser.createBy"/></th>
					<td>
				            ${createBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="groupUser.update"/></th>
					<td>
				            ${update}					
					</td>
				</tr>
				<tr>
					<th><s:text name="groupUser.updateBy"/></th>
					<td>
				            ${updateBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="groupUser.enabled"/></th>
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