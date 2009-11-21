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
		$().ready(
				function(){
					$("#userRegisterForm").validate({
						rules: {
								email: {
									required: true,
									minlength:6,
									email:true,
									remote: "user!checkLoginEMail.action?orgEMail="+encodeURIComponent('${email}')
								},
								
			    				passwd2: {
			    					equalTo:"#passwd"
			    				},								
						},

						messages: {
							email: {
								remote: "<s:text name='user.email.have'/>"
							},
							passwd2: {
								equalTo: "<s:text name='user.passwd2.tip'/>"
							}
						}
							
					});
				}
		  );
	</script>

</head>

<body>
<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-15 prepend-1 colborder">

      	<fieldset>
      	
      		<legend>
  				<s:text name="common.domain.register"/>
      		</legend>
      		
			<s:form
				validate="false" 
				theme="xhtml"  
				id="userRegisterForm" 
				action="user.action" 
				method="post">
			
		    <s:token />

			<s:hidden	name="id" />

			<s:hidden 	name="pageuser.pageRequest"/>
				
			<s:textfield
				label="%{getText('user.email')}"  
				name="email"  
				id="email"  
				size="30" 
				cssClass="{debug:false,required: true,maxlength:80}"/>

			<s:textfield
				label="%{getText('user.passwd')}"  
				name="passwd"  
				id="passwd"  
				size="30" 
				cssClass="{debug:false,maxlength:10,minlength:6,required: true}"/>

			<s:textfield
				label="%{getText('user.passwd2')}"  
				name="passwd2"  
				id="passwd2"  
				size="30" 
				cssClass="{debug:false,maxlength:10,minlength:6,required: true}"/>

	    	<s:submit 
	    		cssClass="button" 
	    		method="registersave"  
	    		key="common.domain.register"/>

			<s:submit 
				cssClass="button" 
				key="common.domain.cancel" 
				onclick="history.back()"/>
		            	
			</s:form>
      	</fieldset>		
		
	</div>
	<div class="column span-7 last">
		<s:actionmessage/>
	</div>

	<%@ include file="/common/footer.jsp"%>
	
</div>
</body>
</html>