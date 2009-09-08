

package com.gogo.comix.service;

import com.gogo.comix.domain.MyTiger;
import org.supermy.core.service.FastwebTemplate;
import org.supermy.core.service.BaseService;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class MyTigerService extends BaseService implements IMyTigerService{

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(MyTigerService.class);

	private FastwebTemplate<MyTiger, Long> myTigerUtil;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		myTigerUtil = new FastwebTemplate<MyTiger, Long>(sessionFactory,null, MyTiger.class);
	}

	/**
	 * @return the resourceUtil
	 */
	public FastwebTemplate<MyTiger, Long> getMyTigerUtil() {
		return myTigerUtil;
	}
}