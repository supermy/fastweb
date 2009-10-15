		<!-- display tag  -->
		<display:table 
			id="${pojoNameLower}List" 
			name="page${pojo.shortName.toLowerCase()}.result" 
			sort="external" 
			class="table" 
			requestURI="" 
			export="false">
			<#foreach field in pojo.getAllPropertiesIterator()>
	        <#if field.name == "create" || field.name == "update" || field.name == "createBy" || field.name == "updateBy"  || field.name == "enabled">
	        <#else>	
				<#if field.equals(pojo.identifierProperty)>
				    <display:column property="${field.name}" 
				    	sortable="false"
				    	sortName="${field.name}" 
				    	href="${util.build(pojo.shortName)}!input.action?page${pojo.shortName.toLowerCase()}.pageRequest=${'$'}{page${pojo.shortName.toLowerCase()}.pageRequest}" media="html"
				        paramId="${field.name}" 
				        paramProperty="${field.name}" 
				        titleKey="${pojoNameLower}.${field.name}"/>
				    <display:column 
				    	property="${field.name}" 
				    	media="csv excel xml pdf"
				    	titleKey="${pojoNameLower}.${field.name}"/>
				    	
				<#elseif c2h.isCollection(field)>
						<#assign field_relation_type = field.value.element.type.returnedClass.simpleName.toLowerCase()>
		        		<display:column 
		        			property="${field.name}Name" 
		        			sortable="false" 
		        			sortName="${field.name}" 
		        			titleKey="${pojoNameLower}.${field.name}"
		        			href="${field_relation_type}!mtolist.action?class=${clazz.mappedClass.name}&property=${field.name}" media="html"
					        paramId="id"
					        paramProperty="id" 
		        			/>
				<#elseif c2h.isManyToOne(field)>
		        		<display:column 
		        			property="${field.name}.name" 
		        			sortable="false" 
		        			sortName="${field.name}" 
		        			titleKey="${pojoNameLower}.${field.name}"/>
				<#elseif c2j.isComponent(field)>
			    <#else>
				    <#if field.value.typeName == "java.util.Date">
			            <display:column 
			            	property="${field.name}"  
			            	format="{0,date,yyyy-MM-dd}" 
			            	sortProperty="${field.name}" 
			            	sortable="false" 
			            	sortName="${field.name}" 
			            	titleKey="${pojoNameLower}.${field.name}"/>
				    <#elseif field.value.typeName == "java.lang.Double" || field.value.typeName == "java.lang.Number" || field.value.typeName == "java.math.BigDecimal">
			             <display:column 
			             	sortProperty="${field.name}" 
			             	format="{0,number, 0,000,000.00}" 
			             	sortable="false" 
			             	sortName="${field.name}" 
			             	titleKey="${pojoNameLower}.${field.name}"/>
				    <#elseif field.value.typeName == "boolean" || field.value.typeName == "java.lang.Boolean">
			             <display:column 
			             	sortProperty="${field.name}" 
			             	sortable="false" 
			             	sortName="${field.name}" 
			             	titleKey="${pojoNameLower}.${field.name}">
			                 <input 
			                 	type="checkbox" 
			                 	disabled="disabled" 
			                 	<c:if test="${'$'}{${pojoNameLower}List.${field.name}}">checked="checked"</c:if>/>
			             </display:column>
				    <#else>
		        	    <display:column 
		        	    	property="${field.name}" 
		        	    	sortable="false" 
		        	    	sortName="${field.name}" 
		        	    	titleKey="${pojoNameLower}.${field.name}"/>
				    </#if>
				</#if>
			</#if>
			</#foreach>
			<security:authorize ifAnyGranted="AUTH_EDIT_${pojo.shortName.toUpperCase()}">
				<display:column 
					value="manager"
					titleKey="common.domain.manager" 	
					href="${util.build(pojo.shortName)}!input.action?page${pojo.shortName.toLowerCase()}.pageRequest=${'$'}{page${pojo.shortName.toLowerCase()}.pageRequest}" media="html"
					paramId="id" 
					paramProperty="id"/>
			</security:authorize>
			
		    <display:setProperty 
		    	name="paging.banner.item_name">
		    	<s:text name="${pojoNameLower}List.${pojoNameLower}"/>
		    </display:setProperty>
		    <display:setProperty 
		    	name="paging.banner.items_name">
		    	<s:text name="${pojoNameLower}List.${pojoNameLower}s"/>
		    </display:setProperty>
		
		    <display:setProperty 
		    	name="export.excel.filename">
		    	<s:text name="${pojoNameLower}List.title"/>.xls
		    </display:setProperty>
		    <display:setProperty 
		    	name="export.csv.filename">
		    	<s:text name="${pojoNameLower}List.title"/>.csv
		    </display:setProperty>
		    <display:setProperty 
		    	name="export.pdf.filename">
		    	<s:text name="${pojoNameLower}List.title"/>.pdf
		    </display:setProperty>
		</display:table>
