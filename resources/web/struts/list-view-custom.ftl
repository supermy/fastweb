		<table>
			<thead>
			<tr>
			<#foreach field in pojo.getAllPropertiesIterator()>
	        <#if field.name == "create" || field.name == "update" || field.name == "createBy" || field.name == "updateBy"  || field.name == "enabled">
	        <#else>	
	        	<th>
						<#if c2h.isCollection(field)>
							<s:text name="${pojoNameLower}.${field.name}"/>
						<#elseif c2h.isManyToOne(field)>
							<s:text name="${pojoNameLower}.${field.name}"/>
						<#elseif c2j.isComponent(field)>
							<s:text name="${pojoNameLower}.${field.name}"/>
					    <#else>
							<a href="${util.build(pojo.shortName)}.action?page${pojo.shortName.toLowerCase()}.orderBy=${field.name}&page${pojo.shortName.toLowerCase()}.order=
							<s:if test="page${pojo.shortName.toLowerCase()}.orderBy=='${field.name}'">${'$'}{page${pojo.shortName.toLowerCase()}.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="${pojoNameLower}.${field.name}"/></a>
						</#if>
				</th>
			</#if>
			</#foreach>
			<th>manager</th>
			</tr>
			</thead>


			<s:iterator value="page${pojo.shortName.toLowerCase()}.result" status='st'>
			<tbody>		
			    <s:if test="#st.Odd">
				<tr  class="odd">
			    </s:if>

			    <s:if test="#st.Even">
				<tr  class="even">
			    </s:if>
				
					<#foreach field in pojo.getAllPropertiesIterator()>
			        <#if field.name == "create" || field.name == "update" || field.name == "createBy" || field.name == "updateBy"  || field.name == "enabled">
			        <#else>	
						<td>
						<#if field.equals(pojo.identifierProperty)>
								<a href="${util.build(pojo.shortName)}!input.action?id=${'$'}{id}&page${pojo.shortName.toLowerCase()}.pageRequest=${'$'}{page${pojo.shortName.toLowerCase()}.pageRequest}">
									${'$'}{${field.name}}
								</a>
						<#elseif c2h.isCollection(field)>
								<#assign field_relation_type = field.value.element.type.returnedClass.simpleName.toLowerCase()>
								<a href="${field_relation_type}!mtolist.action?id=${'$'}{id}&class=${clazz.mappedClass.name}&property=${field.name}">
									${'$'}{${field.name}Name}
								</a>
						<#elseif c2h.isManyToOne(field)>
									${'$'}{${field.name}Name}
						<#elseif c2j.isComponent(field)>
					    <#else>
						    <#if field.value.typeName == "java.util.Date">
									${'$'}{${field.name}}
						    <#elseif field.value.typeName == "java.lang.Double" || field.value.typeName == "java.lang.Number" || field.value.typeName == "java.math.BigDecimal">
									${'$'}{${field.name}}
						    <#elseif field.value.typeName == "boolean" || field.value.typeName == "java.lang.Boolean">
					                 <input 
					                 	type="checkbox" 
					                 	disabled="disabled" 
					                 	<c:if test="${'$'}{${field.name}}">checked="checked"</c:if>/>
						    <#else>
									${'$'}{${field.name}}
						    </#if>
						</#if>
					</td>
					</#if>
					</#foreach>
					<security:authorize ifAnyGranted="AUTH_EDIT_${pojo.shortName.toUpperCase()}">
						<td>
							<a href="${util.build(pojo.shortName)}!input.action?id=${'$'}{id}&page${pojo.shortName.toLowerCase()}.pageRequest=${'$'}{page${pojo.shortName.toLowerCase()}.pageRequest}">
								manager
							</a>
						</td>
					</security:authorize>
					
				</tr>
			</tbody>		
			</s:iterator>
		</table>