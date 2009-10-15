# -- ${pojo.shortName}-START
# ${util.build(pojo.shortName)}.action  // ${webdata.getComment(clazz)}

<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<#assign pojoNameDesc = webdata.getFieldDescription(pojo.shortName)>
<#assign pojoNameDesc = webdata.getComment(clazz)>
<#assign pojoNameDetail = webdata.getCommentDesc(clazz)>

${pojoNameLower}.name=${pojoNameDesc}
${pojoNameLower}.desc=${pojoNameDetail}

<#foreach field in pojo.getAllPropertiesIterator()>
<#if c2h.isCollection(field)>
		<#lt/>${pojoNameLower}.${field.name}=${webdata.getFieldDescription(field.name)}
	<#elseif c2h.isManyToOne(field)>
		<#lt/>${pojoNameLower}.${field.name}=${webdata.getFieldDescription(field.name)}
	<#elseif c2j.isComponent(field)>
	<#else>
		<#assign fieldComment = webdata.getComment(field)>
		<#assign fieldCommentDesc = webdata.getCommentDesc(field)>
		<#if fieldComment!="">
			<#lt/>${pojoNameLower}.${field.name}=${fieldComment}
			<#if fieldCommentDesc!="">
	    <#lt/>${pojoNameLower}.${field.name}.desc=${fieldCommentDesc}
			</#if>		    
		<#else>
		    <#lt/>${pojoNameLower}.${field.name}=${webdata.getFieldDescription(field.name)}
		</#if>
</#if>
</#foreach>

${pojoNameLower}.added=创建 [${pojoNameDesc}]成功.
${pojoNameLower}.updated=更新[${pojoNameDesc}] 成功.
${pojoNameLower}.deleted=删除[${pojoNameDesc}] 成功.
${pojoNameLower}.searchtxt=[${pojoNameDesc}]不能为空.

# -- ${pojoNameLower} list page --
${pojoNameLower}List.title=${pojoNameDesc} 列表
${pojoNameLower}List.heading=${pojoNameDesc} ${webdata.getComment(clazz)} ${webdata.getCommentDesc(clazz)}

${pojoNameLower}List.${pojoNameLower}=${pojoNameDesc}
${pojoNameLower}List.${pojoNameLower}s=${pojoNameDesc} 列表 

# -- ${pojoNameLower} detail page --
${pojoNameLower}Detail.title=${pojoNameDesc} 内容
${pojoNameLower}Detail.heading=${pojoNameDesc} 信息 ${webdata.getComment(clazz)} ${webdata.getCommentDesc(clazz)}
# -- ${pojo.shortName}-END

