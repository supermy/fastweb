package org.supermy.core.service;

import org.hibernate.SessionFactory;

public abstract class BaseService {
	
	public abstract void setSessionFactory(SessionFactory sessionFactory);

}
