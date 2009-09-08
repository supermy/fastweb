
# -- ${pojo.shortName}-START
<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<#foreach field in pojo.getAllPropertiesIterator()>
<#if !c2h.isCollection(field) && !c2h.isManyToOne(field) && !c2j.isComponent(field)>
    
</#if>
</#foreach>

${pojoNameLower}.added=${pojo.shortName} has been added successfully.
${pojoNameLower}.updated=${pojo.shortName} has been updated successfully.
${pojoNameLower}.deleted=${pojo.shortName} has been deleted successfully.

# -- ${pojoNameLower} list page --
${pojoNameLower}List.title=${pojo.shortName} List
${pojoNameLower}List.heading=${pojo.shortName} List

${pojoNameLower}List.${pojoNameLower}=${pojoNameLower}


# -- ${pojoNameLower} detail page --
${pojoNameLower}Detail.title=${pojo.shortName} Detail
${pojoNameLower}Detail.heading=${pojo.shortName} Information
# -- ${pojo.shortName}-END