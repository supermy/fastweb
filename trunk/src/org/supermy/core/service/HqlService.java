package org.supermy.core.service;

import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.BaseDomain;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2008-7-30 下午05:17:27
 * 
 */
@Transactional
@Service
public class HqlService extends BaseService implements IHqlService {

	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	private FastwebTemplate<BaseDomain, Long> util;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		util = new FastwebTemplate<BaseDomain, Long>(sessionFactory, null,
				BaseDomain.class);
	}

	/**
	 * @return the userUtil
	 */
	public FastwebTemplate<BaseDomain, Long> getUtil() {
		return util;
	}

}
