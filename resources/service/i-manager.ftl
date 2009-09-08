<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<#assign basepackage = pojo.packageName.substring(0,pojo.packageName.lastIndexOf("."))>

package ${basepackage}.service;

import ${pojo.packageName}.${pojo.shortName};
import org.supermy.core.service.FastwebTemplate;
import javax.jws.WebService;

@WebService
public interface I${pojo.shortName}Service {

	/**
	 * @return the $(pojoNameLower)Util
	 */
	public abstract FastwebTemplate<${pojo.shortName}, Long> get${pojo.shortName}Util();

}