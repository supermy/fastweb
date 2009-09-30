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
	<%@ include file="/common/js.jsp"%>
	<%@ include file="/common/js-validate.jsp"%>
	<script src="${js}/js/jquery-ui-1.7.2/ui/minified/ui.core.min.js"  type="text/javascript"></script>
	<script src="${js}/js/jquery-ui-1.7.2/ui/minified/ui.datepicker.min.js"  type="text/javascript"></script>
	<link rel="stylesheet" href="${css}/css/jquery-ui-1.7.2/jquery-ui.css" type="text/css" media="screen, projection"/>
	<link rel="stylesheet" href="${css}/css/jquery-ui-1.7.2/ui.datepicker.css" type="text/css" media="screen, projection"/>
	<script>
		$().ready(function(){
			//聚焦第一个输入框
			//$("#email").focus();
			//为inputForm注册validate函数
			$("#mytigerInputForm").validate();
		});
	</script>

</head>

<body>
<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-15 prepend-1 colborder">
		<form id="mytigerInputForm" action="my-tiger.action" method="post">
      	<fieldset>
      		<legend><s:if test="id == null"><s:text name="common.label.create"/></s:if><s:else><s:text name="common.label.modify"/></s:else><s:text name="myTigerDetail.title"/></legend>
		<input type="hidden" name="id" value="${id}" />
		<input type="hidden" name="pagemytiger.pageRequest" value="${pagemytiger.pageRequest}" />
		<table class="inputView">
				<tr>
				<tbody>					
					<th><s:text name="myTiger.audit"/></th>
					<td>
				            <input type="text" name="audit"  id="audit"  size="30" value="${audit}" class="text {debug:false,date:true}"/> <em> </em>
							<script type="text/javascript">
							$(function() {
								$("#audit").datepicker(
									{
												dateFormat: "yy-mm-dd",
												changeMonth: true,
												changeYear: true,
												//numberOfMonths: 3,
												//minDate: -20, maxDate: '+1M +10D',//朄1�7大日朄1�7 朄1�7小日朄1�7
												showButtonPanel: true									
									}
								);
							});
							</script>
				            
					</td>
				</tbody>					
				</tr>
				<tr>
				<tbody>					
					<th><s:text name="myTiger.myEmail"/></th>
					<td>
				            <input type="text" name="myEmail" size="30" value="${myEmail}" class="text {debug:false,email:true,maxlength:100,minlength:0,required: true}"/> <em>*</em>
					</td>
				</tbody>					
				</tr>
				<tr>
				<tbody>					
					<th><s:text name="myTiger.myTiger"/></th>
					<td>
				            <input type="text" name="myTiger" size="30" value="${myTiger}" class="text {debug:false,maxlength:200,minlength:0}"/> <em> </em>
					</td>
				</tbody>					
				</tr>
		
			<tr>
			<tbody>
				<td></td>					
				<td>
		            <s:submit cssClass="button" method="save"  key="common.domain.save" theme="simple"/>
			        <c:if test="${not empty id}">			        
			            <s:submit cssClass="button" method="delete" key="common.domain.delete"
			                onclick="return confirmDelete('MyTiger')" theme="simple"/>
			        </c:if>
		            <s:submit cssClass="button" key="common.domain.cancel" 
		            	onclick="history.back()" theme="simple"/>
				</td>
			</tbody>					
			</tr>
		</table>

      	</fieldset>		
		</form>
		
	</div>
	<div class="column span-7 last">
		<s:actionmessage/>
		
		<table>
		<tbody>
				<tr class="even">
					<th><s:text name="myTiger.create"/></th>
					<td>
				            ${create}					
					</td>
				</tr>
				<tr>
					<th><s:text name="myTiger.createBy"/></th>
					<td>
				            ${createBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="myTiger.update"/></th>
					<td>
				            ${update}					
					</td>
				</tr>
				<tr>
					<th><s:text name="myTiger.updateBy"/></th>
					<td>
				            ${updateBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="myTiger.enabled"/></th>
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