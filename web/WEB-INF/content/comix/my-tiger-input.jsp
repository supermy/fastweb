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
	<script src="${js}/js/validate/jquery.metadata.js" type="text/javascript"></script>
	<script src="${js}/js/validate/localization/messages_cn.js" type="text/javascript"></script>
	<script>
		$().ready(function(){
			//聚焦第一个输入框
			//$("#email").focus();
			//为inputForm注册validate函数
			$("#mytigerInputForm").validate();
		});
	</script>
	
	<script type="text/javascript" src="${js}/js/jquery-ui-1.7.2/ui/minified/ui.core.min.js"></script>
	<script type="text/javascript" src="${js}/js/jquery-ui-1.7.2/ui/minified/ui.datepicker.min.js"></script>
	
	

</head>

<body>
<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>
	
	<div class="css-panes span-15 prepend-1 colborder">
	
	<script type="text/javascript" src="${js}/js/jquery.tools.min.js"></script>
	<link href="${css}/js/tools/tabs.css" type="text/css" rel="stylesheet" />

	
	<!-- tabs --> 
	<ul class="css-tabs"> 
	    <li><a href="ajax1.htm">Tab 1</a></li> 
	    <li><a href="ajax2.htm">Second tab</a></li> 
	    <li><a href="ajax3.htm">An ultra long third tab</a></li> 
	</ul> 
	 
	<!-- single pane --> 
	<div class="css-panes"> 
	    <div style="display:block"></div> 
	</div>

	<script>
		$(function() { 
		    // 将ul.tabs区域设定为选项卡，用来控制div.panes区域中最近一层的各div区域
		   // 注意tabs和panes与html中class的对应关系
    		$("ul.css-tabs").tabs("div.css-panes > div", {effect: 'ajax'}); 
		});	
	</script>
	
		<h3><s:if test="id == null"><s:text name="common.label.create"/></s:if><s:else><s:text name="common.label.modify"/></s:else><s:text name="myTigerDetail.title"/></h3>
		<form id="mytigerInputForm" action="my-tiger.action" method="post">
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
		             		<s:checkbox key="enabled" cssClass="checkbox" theme="simple" class="{debug:false}"/> <em> </em>
					</td>
				</tr>
				<tr>
					<td><s:text name="myTiger.myEmail"/></td>
					<td>
				            <input type="text" name="myEmail" size="30" value="${myEmail}" class="{debug:false,email:true,maxlength:100,minlength:0,required: true}"/> <em>*</em>
					</td>
				</tr>
				<tr>
					<td><s:text name="myTiger.myTiger"/></td>
					<td>
				            <input type="text" name="myTiger" id="myTiger"  size="30" value="${myTiger}" class="{debug:false,maxlength:200,minlength:0}"/> <em> </em>
				            <script type="text/javascript">
							$(function() {
								$('#myTiger').datepicker({
									changeMonth: true,
									changeYear: true
								});
							});
							</script>
					</td>
				</tr>
		
			<tr>
				<td colspan="2">
		            <s:submit cssClass="button" method="save"  key="common.domain.save" theme="simple"/>
			        <c:if test="${not empty id}">			        
			            <s:submit cssClass="button" method="delete" key="common.domain.delete"
			                onclick="return confirmDelete('MyTiger')" theme="simple"/>
			        </c:if>
		            <s:submit cssClass="button" key="common.domain.cancel" 
		            	onclick="history.back()" theme="simple"/>
				</td>
			</tr>
		</table>
		</form>
		
	</div>
	<div class="column span-7 last">
		to do list ... ...
	</div>

	<%@ include file="/common/footer.jsp"%>
	
</div>
</body>
</html>