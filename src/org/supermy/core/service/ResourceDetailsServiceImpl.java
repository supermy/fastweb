package org.supermy.core.service;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.UrlResource;

/**
 * 从数据库查询URL--授权定义Map的实现类.
 */
@Transactional(readOnly = true)
public class ResourceDetailsServiceImpl implements ResourceDetailsService {
	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService  userService;

	/**
	 * @see ResourceDetailsService#getRequestMap()
	 */
	public LinkedHashMap<String, String> getRequestMap() throws Exception {
		log.debug("getRequestMap start:");
		HashSet<UrlResource> resourceList = userService.getUrlResourceWithAuthorities();

		LinkedHashMap<String, String> requestMap = new LinkedHashMap<String, String>(resourceList.size());
		for (UrlResource resource : resourceList) {
			log.debug("url resource key:{} , value:{}", resource.getValue(),resource.getAuthorityListName());
			requestMap.put(resource.getValue(), resource.getAuthorityListName());
		}
		log.debug("url resource:{}", requestMap);
		return requestMap;
	}
}
