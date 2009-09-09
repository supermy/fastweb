

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="myTigerDetail.title"/></title>
    <meta name="heading" content="<s:text name='myTigerDetail.heading'/>"/>

	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>

	<link href="${css}/js/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
	<script src="${js}/js/jquery.js" type="text/javascript"></script>
	<script src="${js}/js/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${js}/js/validate/localization/messages_cn.js" type="text/javascript"></script>
	<script>
		$(document).ready(function(){
			//聚焦第一个输入框
			$("#email").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {					            
					            
	                    		myEmail:{
		                    		email:true,
			    					required: true,
			    					maxlength:100
			    				},			    									            
	                    		myTiger:{
		                    		
			    					maxlength:200
			    				},			    									            								
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
		<h3><s:if test="id == null">创建</s:if><s:else>修改</s:else><s:text name="myTigerDetail.title"/></h3>


		<form id="inputForm" action="my-tiger!save.action" method="post">
		<input type="hidden" name="id" value="${id}" />
		<input type="hidden" name="pagemytiger.pageRequest" value="${pagemytiger.pageRequest}" />
		<table class="inputView">
				<tr>
					<td><s:text name="myTiger.create"/></td>
					<td>
				            ${create}					
					</td>
				</tr>
				<tr>
					<td><s:text name="myTiger.enabled"/></td>
					<td>
		             		<s:checkbox key="enabled" cssClass="checkbox" theme="simple"/>
					</td>
				</tr>
				<tr>
					<td><s:text name="myTiger.myEmail"/></td>
					<td>
				            <input type="text" name="myEmail" size="30" value="${myEmail}" />
					</td>
				</tr>
				<tr>
					<td><s:text name="myTiger.myTiger"/></td>
					<td>
				            <input type="text" name="myTiger" size="30" value="${myTiger}" />
					</td>
				</tr>
		
			<tr>
				<td colspan="2">
					<input type="submit" value="提交" />&nbsp; 
			        <c:if test="${not empty id}">			        
			            <s:submit cssClass="button" method="delete" key="button.delete"
			                onclick="return confirmDelete('MyTiger')" theme="simple"/>
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