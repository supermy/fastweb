    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ${pojo.getDeclarationName()} pojo = (${pojo.getDeclarationName()}) o;

    <#list pojo.getAllPropertiesIterator() as property><#rt/>
        <#if !(property.name.equals("enabled") | property.name.equals("create")  | property.name.equals("class_"))><#rt/>   
        <#if !property.equals(clazz.identifierProperty)><#rt/>
        <#if pojo.getJavaTypeName(property, jdk5) == "boolean"><#rt/>
        if (${property.getName()} != pojo.${pojo.getGetterSignature(property)}()) return false;
        <#elseif !c2h.isCollection(property)><#rt/>
        if (${property.getName()} != null ? !${property.getName()}.equals(pojo.${pojo.getGetterSignature(property)}()) : pojo.${pojo.getGetterSignature(property)}() != null) return false;
        </#if><#rt/>
        </#if><#rt/>
        </#if><#rt/>
    </#list><#rt/>

        return true;
    }

    public int hashCode() {
        int result = 0;
    <#list pojo.getAllPropertiesIterator() as property><#rt/>
        <#if !property.equals(clazz.identifierProperty)><#rt/>
    <#if property_index == 1><#rt/>
        <#if pojo.getJavaTypeName(property, jdk5) == "boolean"><#rt/>
        result = (${property.getName()} ? 1 : 0);
        <#elseif pojo.getJavaTypeName(property, jdk5) == "char"><#rt/>
        
        <#elseif !c2h.isCollection(property)><#rt/>
        result = (${pojo.getGetterSignature(property)}() != null ? ${pojo.getGetterSignature(property)}().hashCode() : 0);
        </#if><#rt/>
    <#else><#rt/>
        <#if pojo.getJavaTypeName(property, jdk5) == "boolean"><#rt/>
        result = 31 * result + (${pojo.getGetterSignature(property)}() ? 1 : 0);
        <#elseif !c2h.isCollection(property)><#rt/>

        <#if !(property.name.equals("enabled") | property.name.equals("class_"))><#rt/>   
        result = 31 * result + (${pojo.getGetterSignature(property)}() != null ? ${pojo.getGetterSignature(property)}().hashCode() : 0);
        </#if><#rt/>
        </#if><#rt/>
    </#if><#rt/>
        </#if><#rt/>
    </#list><#rt/>

        return result;
    }