package org.supermy.core.service;

import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.Resource;

/**
 * @author my
 * 
 */
@Transactional
@Service
public class ResourceService extends BaseService {

	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	private FastwebTemplate<Resource, Long> resourceUtil;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		// Session openSession = sessionFactory.openSession();//
		// 保证是同一个session,才能在相互调用方法。
		// Session openSession = sessionFactory.getCurrentSession();

		resourceUtil = new FastwebTemplate<Resource, Long>(sessionFactory,
				null, Resource.class);
	}

	/**
	 * @return the resourceUtil
	 */
	public FastwebTemplate<Resource, Long> getResourceUtil() {
		return resourceUtil;
	}

}
