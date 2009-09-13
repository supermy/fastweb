<%@ page contentType="text/html;charset=UTF-8" %>

<div class="span-24 cmdtd">
	<!-- 定义选项卡按钮，注意最外层的class定义为tabs --> 
		<a href="${ctx}/">HOME</a>
		<a href="${ctx}/user/user.action"><s:text name="user.manager"/></a>
		<a href="${ctx}/user/role.action"><s:text name="role.manager"/></a>
		<a href="${ctx}/organization/companytype.action"><s:text name="compnay.type.manager"/></a> 
		<a href="${ctx}/resource/resource.action"><s:text name="resource.manager"/></a>
		<a href="${ctx}/workflow/workflow.action"><s:text name="workflow.manager"/></a>
		<a href="${ctx}/workflow/taskitem.action"><s:text name="workflow.taskitem.manager"/></a> 
		<a href="${ctx}/workflow/mytask.action"><s:text name="workflow.mytask"/></a>
		<hr />
</div>
