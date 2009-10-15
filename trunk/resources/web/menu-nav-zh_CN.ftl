<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<#assign basepackage = pojo.packageName.substring(0,pojo.packageName.lastIndexOf("."))>
<#assign actionpath = basepackage.substring(basepackage.lastIndexOf(".")+1)>

<!--
#${webdata.getComment(clazz)} detail:${webdata.getCommentDesc(clazz)}
-->
<a href="${'$'}{ctx}/${actionpath}/${util.build(pojo.shortName)}.action" 
	alt="<s:text name="${pojoNameLower}.desc"/>"><s:text name="${pojoNameLower}.name"/></a>