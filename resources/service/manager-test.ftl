<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<#assign basepackage = pojo.packageName.substring(0,pojo.packageName.lastIndexOf("."))>

package ${basepackage}.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import ${basepackage}.service.I${pojo.shortName}Service;
import ${pojo.packageName}.${pojo.shortName};
import org.supermy.core.test.TestBaseService;
import org.supermy.core.service.Page;

public class ${pojo.shortName}ServiceTest extends TestBaseService {

	@Autowired
	private I${pojo.shortName}Service ${pojoNameLower}Service;

	private Page<${pojo.shortName}> page = new Page<${pojo.shortName}>(10);

	@Test
	public void getSession() {
		Assert.assertNotNull(${pojoNameLower}Service.get${pojo.shortName}Util().getSessionFactory());
		log.debug(" session:{}", ${pojoNameLower}Service.get${pojo.shortName}Util().getSessionFactory());
	}

	@Test
	@Rollback(true)//don't put in db 	
	public void CRUD() {
		${pojo.shortName} u=new ${pojo.shortName}();
		${pojoNameLower}Service.get${pojo.shortName}Util().save(u);
		u=${pojoNameLower}Service.get${pojo.shortName}Util().get(u.getId());
		Assert.assertNotNull(u.getId());
		${pojoNameLower}Service.get${pojo.shortName}Util().delete(u.getId());
	}
}