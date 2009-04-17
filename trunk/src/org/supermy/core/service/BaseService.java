package org.supermy.core.service;

import org.hibernate.SessionFactory;
import org.springmodules.workflow.jbpm31.JbpmTemplate;

public abstract class BaseService {
	
	public abstract void setSessionFactory(SessionFactory sessionFactory);

}
