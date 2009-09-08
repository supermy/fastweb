
<#if ejb3><#rt/>
	    <#if c2h.isManyToOne(field)><#rt/>
	        <#--TODO support @OneToOne true and false-->
	        <#lt/>${pojo.generateManyToOneAnnotation(field)}
	        <#--TODO support optional and targetEntity-->
	        <#lt/>${pojo.generateJoinColumnsAnnotation(field)}
	        <#elseif c2h.isCollection(field)><#rt/>
	            <#lt/>${pojo.generateCollectionAnnotation(field, cfg)}
	        <#else><#rt/>
	        <#lt/>${pojo.generateBasicAnnotation(field)}
	        <#if !field.equals(clazz.identifierProperty)><#rt/>
			<#setting number_format="#">
		   <#foreach column in field.getColumnIterator()>
					<#assign type = column.value.typeName>
		                <#if !column.nullable>
			                <#lt/>  @NotNull
		                </#if><#rt/>
		                <#if type == "string">
							<#lt/>    @Length(max = ${column.length})
		                <#else>
		                </#if><#rt/>
		    </#foreach><#rt/>
	        <#lt/>${pojo.generateAnnColumnAnnotation(field)}
	        </#if><#rt/>
	    </#if><#rt/>
</#if><#rt/>