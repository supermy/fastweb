<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<#assign basepackage = pojo.packageName.substring(0,pojo.packageName.lastIndexOf("."))>


package ${basepackage}.service;

import ${pojo.packageName}.${pojo.shortName};
import org.supermy.core.service.FastwebTemplate;
import org.supermy.core.service.BaseService;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class ${pojo.shortName}Service extends BaseService implements I${pojo.shortName}Service{

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(${pojo.shortName}Service.class);

	private FastwebTemplate<${pojo.shortName}, Long> ${pojoNameLower}Util;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		${pojoNameLower}Util = new FastwebTemplate<${pojo.shortName}, Long>(sessionFactory,null, ${pojo.shortName}.class);
	}

	/**
	 * @return the resourceUtil
	 */
	public FastwebTemplate<${pojo.shortName}, Long> get${pojo.shortName}Util() {
		return ${pojoNameLower}Util;
	}
}