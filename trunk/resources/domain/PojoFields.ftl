<#foreach field in pojo.getAllPropertiesIterator()><#rt/>
<#if pojo.getMetaAttribAsBool(field, "gen-property", true)><#rt/>
    <#if !(field.name.equals("id")|field.name.equals("Cid") | field.name.equals("enabled") | field.name.equals("create"))><#rt/>
	    <#include "GetFieldAnnotation.ftl"/>
	${pojo.getFieldModifiers(field)} ${pojo.getJavaTypeName(field, jdk5)} ${field.name}<#if pojo.hasFieldInitializor(field, jdk5)> = ${pojo.getFieldInitialization(field, jdk5)}</#if>;
    </#if><#rt/>
</#if><#rt/>
</#foreach><#rt/>