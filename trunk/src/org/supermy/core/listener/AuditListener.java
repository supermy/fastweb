package org.supermy.core.listener;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;
import org.supermy.core.domain.BaseDomain;
import org.supermy.core.security.SecurityUtils;

/**
 * 在自动为entity添加审计信息的Hibernate EventListener.
 * 
 * 在hibernate执行saveOrUpdate()时,自动为AuditableEntity的子类添加审计信息.
 * 
 */
@SuppressWarnings("serial")
public class AuditListener implements SaveOrUpdateEventListener {

	public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
		Object object = event.getObject();

		//如果对象是AuditableEntity子类,添加审计信息.
		if (object instanceof BaseDomain) {
			BaseDomain entity = (BaseDomain) object;

			if (entity.isnew()) {
				//创建新对象
				entity.setCreate(new Date());
				entity.setCreateBy(SecurityUtils.getCurrentUserName());
			} else {
				//修改旧对象
				entity.setUpdate(new Date());
				entity.setUpdateBy(SecurityUtils.getCurrentUserName());
			}
		}
	}
}
