<#if ejb3?if_exists><#rt/>
    <#if pojo.isComponent()><#rt/>
    <#lt>@${pojo.importType("javax.persistence.Embeddable")}
    <#else><#rt/>
    <#if clazz.table.comment??>    
	    <#if clazz.table.comment!="">
	        <#lt/>@Comment("${clazz.table.comment}")
	    </#if><#rt/>
    </#if><#rt/>
    <#lt>@${pojo.importType("javax.persistence.Entity")}
    <#lt>@${pojo.importType("javax.persistence.Table")}(name="${clazz.table.name}"<#rt/>
    <#if clazz.table.schema?exists><#rt/>
        <#lt>,schema="${clazz.table.schema}"<#rt/>
        </#if><#if clazz.table.catalog?exists><#rt/>
            <#lt>,catalog="${clazz.table.catalog}"<#rt/>
        </#if><#rt/>
    <#assign uniqueConstraint=pojo.generateAnnTableUniqueConstraint()><#rt/>
        <#if uniqueConstraint?has_content><#rt/>
            <#lt/>, uniqueConstraints = ${uniqueConstraint} <#-- bug alert: two unique column names will cause invalid output --><#rt/>
        </#if><#lt/>)
    </#if>

@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
</#if>