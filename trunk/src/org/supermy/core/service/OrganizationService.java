package org.supermy.core.service;

import org.hibernate.SessionFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.Company;
import org.supermy.core.domain.CompanyType;
import org.supermy.core.domain.User;

/**
 * 组织机构的主要业务类
 * 
 * @author my
 * 
 */
@Transactional
@Service
public class OrganizationService extends BaseService {

	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	private FastwebTemplate<User, Long> userUtil;

	private FastwebTemplate<CompanyType, Long> companyTypeUtil;
	private FastwebTemplate<Company, Long> companyUtil;

	private SessionFactory sessionFactory;
	@Autowired
	private JbpmConfiguration jbpmConfiguration;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		userUtil = new FastwebTemplate<User, Long>(sessionFactory, null,
				User.class);
		companyUtil = new FastwebTemplate<Company, Long>(sessionFactory,
				null, Company.class);
		companyTypeUtil = new FastwebTemplate<CompanyType, Long>(sessionFactory,
				null, CompanyType.class);
		this.sessionFactory = sessionFactory;
	}

	public JbpmContext getJbpmContext() {
		log.debug("get jbpm context:");
		JbpmContext jbpmContext = jbpmConfiguration.getCurrentJbpmContext();
		if (jbpmContext == null) {
			log.debug("create jbpm context ... ... ");
			jbpmContext = jbpmConfiguration.createJbpmContext();
		}
		// 保持session 一致
		jbpmContext.setSessionFactory(sessionFactory);
		jbpmContext.setSession(sessionFactory.getCurrentSession());
		return jbpmContext;
	}

	/**
	 * 测试的时候使用 filter自动关闭
	 */
	public void closeJbpmContext() {
		log.debug("close context:");
		jbpmConfiguration.getCurrentJbpmContext().close();
	}

	/**
	 * @return the userUtil
	 */
	public FastwebTemplate<User, Long> getUserUtil() {
		return userUtil;
	}

	

	/**
	 * @return the companyTypeUtil
	 */
	public FastwebTemplate<CompanyType, Long> getCompanyTypeUtil() {
		return companyTypeUtil;
	}

	/**
	 * @return the companyUtil
	 */
	public FastwebTemplate<Company, Long> getCompanyUtil() {
		return companyUtil;
	}


}