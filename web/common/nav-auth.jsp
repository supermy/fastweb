<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 权限管理子菜单  -->
<div class="span-5" style="text-align:left;">
		<a href="${ctx}/user/user.action"  
			><s:text name="user.manager" /></a>
		<a href="${ctx}/user/role.action"  
			><s:text name="role.manager" /></a><br/>
		<a href="${ctx}/user/authority.action"  
			><s:text name="authorityList.title" /></a>
		<a href="${ctx}/user/url-resource.action"  
			><s:text name="urlResourceList.title" /></a>
</div>