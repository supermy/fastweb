# -- ${pojo.shortName}-START
<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<#assign pojoNameDesc = webdata.getFieldDescription(pojo.shortName)>

${pojoNameLower}.name=${pojo.shortName}
${pojoNameLower}.desc=${pojoNameDesc}

<#foreach field in pojo.getAllPropertiesIterator()>
<#if c2h.isCollection(field)>
		<#lt/>${pojoNameLower}.${field.name}=${webdata.getFieldDescription(field.name)}
	<#elseif c2h.isManyToOne(field)>
		<#lt/>${pojoNameLower}.${field.name}=${webdata.getFieldDescription(field.name)}
	<#elseif c2j.isComponent(field)>
	<#else>
		<#lt/>${pojoNameLower}.${field.name}=${webdata.getFieldDescription(field.name)}
</#if>
</#foreach>

${pojoNameLower}.added=${pojoNameDesc} has been added successfully.
${pojoNameLower}.updated=${pojoNameDesc} has been updated successfully.
${pojoNameLower}.deleted=${pojoNameDesc} has been deleted successfully.
${pojoNameLower}.searchtxt=${pojoNameDesc} not empty.

# -- ${pojoNameLower} list page --
${pojoNameLower}List.title=${pojoNameDesc} List
${pojoNameLower}List.heading=${pojoNameDesc}

${pojoNameLower}List.${pojoNameLower}=${pojoNameDesc}
${pojoNameLower}List.${pojoNameLower}s=${pojoNameDesc} list 

# -- ${pojoNameLower} detail page --
${pojoNameLower}Detail.title=${pojoNameDesc} Detail
${pojoNameLower}Detail.heading=${pojoNameDesc} Information
# -- ${pojo.shortName}-END