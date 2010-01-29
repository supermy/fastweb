<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="doubleColorBallDetail.title"/></title>
    <meta name="heading" content="<s:text name='doubleColorBallDetail.heading'/>"/>

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
			$("#doublecolorballInputForm").validate();
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
      			<s:text name="doubleColorBallDetail.title"/>
      		</legend>
      		
			<s:form  
				validate="false" 
				theme="xhtml"  
				id="doublecolorballInputForm" 
				action="double-color-ball.action" 
				method="post">
			
		    <s:token />

			<s:hidden	name="id" />

			<s:hidden 	name="pagedoublecolorball.pageRequest"/>

			<s:textfield
				label="%{getText('doubleColorBall.num')}"  
				name="num"  
				id="num"  
				size="30" 
				cssClass="{debug:false,maxlength:5,minlength:4,required: true}"/>

			<s:textfield
				label="%{getText('doubleColorBall.lotteryDates')}"  
				name="lotteryDates"  
				id="lotteryDates"  
				size="30" 
				cssClass="{debug:false,date:true}"/>
			<script type="text/javascript">
			$(function() {
				$("#lotteryDates").datepicker(
					{
						dateFormat: "yy-mm-dd",
						changeMonth: true,
						changeYear: true,
						//numberOfMonths: 3,
						//minDate: -20, maxDate: '+1M +10D',//最大日期 最小日期
						showButtonPanel: true									
					}
				);
			});
			</script>							            
			<s:textfield
				label="%{getText('doubleColorBall.redBlueBall')}"  
				name="redBlueBall"  
				id="redBlueBall"  
				size="30" 
				cssClass="{debug:false,maxlength:14,minlength:14}"/>

			<td colspan="2">按摇奖次序</td>

			<s:textfield
				label="%{getText('doubleColorBall.sequence1')}"  
				name="sequence1"  
				id="sequence1"  
				size="30" 
				cssClass="{debug:false,maxlength:2,minlength:2,required: true}"/>

			<s:textfield
				label="%{getText('doubleColorBall.sequence2')}"  
				name="sequence2"  
				id="sequence2"  
				size="30" 
				cssClass="{debug:false,maxlength:2,minlength:2,required: true}"/>

			<s:textfield
				label="%{getText('doubleColorBall.sequence3')}"  
				name="sequence3"  
				id="sequence3"  
				size="30" 
				cssClass="{debug:false,maxlength:2,minlength:2,required: true}"/>

			<s:textfield
				label="%{getText('doubleColorBall.sequence4')}"  
				name="sequence4"  
				id="sequence4"  
				size="30" 
				cssClass="{debug:false,maxlength:2,minlength:2,required: true}"/>

			<s:textfield
				label="%{getText('doubleColorBall.sequence5')}"  
				name="sequence5"  
				id="sequence5"  
				size="30" 
				cssClass="{debug:false,maxlength:2,minlength:2,required: true}"/>

			<s:textfield
				label="%{getText('doubleColorBall.sequence6')}"  
				name="sequence6"  
				id="sequence6"  
				size="30" 
				cssClass="{debug:false,maxlength:2,minlength:2,required: true}"/>

			<td colspan="2">按大小排序</td>

			<s:textfield
				label="%{getText('doubleColorBall.size1')}"  
				name="size1"  
				id="size1"  
				size="30" 
				cssClass="{debug:false,maxlength:2,minlength:2,required: true}"/>

			<s:textfield
				label="%{getText('doubleColorBall.size2')}"  
				name="size2"  
				id="size2"  
				size="30" 
				cssClass="{debug:false,maxlength:2,minlength:2,required: true}"/>

			<s:textfield
				label="%{getText('doubleColorBall.size3')}"  
				name="size3"  
				id="size3"  
				size="30" 
				cssClass="{debug:false,maxlength:2,minlength:2,required: true}"/>

			<s:textfield
				label="%{getText('doubleColorBall.size4')}"  
				name="size4"  
				id="size4"  
				size="30" 
				cssClass="{debug:false,maxlength:2,minlength:2,required: true}"/>

			<s:textfield
				label="%{getText('doubleColorBall.size5')}"  
				name="size5"  
				id="size5"  
				size="30" 
				cssClass="{debug:false,maxlength:2,minlength:2,required: true}"/>

			<s:textfield
				label="%{getText('doubleColorBall.size6')}"  
				name="size6"  
				id="size6"  
				size="30" 
				cssClass="{debug:false,maxlength:2,minlength:2,required: true}"/>

				
			<s:textfield
				label="%{getText('doubleColorBall.blueBall')}"  
				name="blueBall"  
				id="blueBall"  
				size="30" 
				cssClass="{debug:false,maxlength:2,minlength:2,required: true}"/>


			
			<security:authorize 
					ifAnyGranted="AUTH_SAVE_DOUBLECOLORBALL">
						
			    	<s:submit 
			    		cssClass="button" 
			    		method="save"  
			    		key="common.domain.save"/>
		            		
				</security:authorize>
	
			<c:if test="${not empty id}">			        
			<security:authorize 
				ifAnyGranted="AUTH_DELETE_DOUBLECOLORBALL">
						
			    <s:submit 
			    	cssClass="button" 
			    	method="delete" 
			    	key="common.domain.delete"
			        onclick="return confirmDelete('DoubleColorBall')" 
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
					<th><s:text name="doubleColorBall.create"/></th>
					<td>
				            ${create}					
					</td>
				</tr>
				<tr>
					<th><s:text name="doubleColorBall.createBy"/></th>
					<td>
				            ${createBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="doubleColorBall.update"/></th>
					<td>
				            ${update}					
					</td>
				</tr>
				<tr>
					<th><s:text name="doubleColorBall.updateBy"/></th>
					<td>
				            ${updateBy}					
					</td>
				</tr>
				<tr  class="even">
					<th><s:text name="doubleColorBall.enabled"/></th>
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