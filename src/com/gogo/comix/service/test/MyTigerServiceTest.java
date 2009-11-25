
package com.gogo.comix.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gogo.comix.domain.MyTiger;
import com.gogo.comix.service.IMyTigerService;
import org.supermy.core.test.TestBaseService;
import org.supermy.core.service.Page;

public class MyTigerServiceTest extends TestBaseService {

	@Autowired
	private IMyTigerService myTigerService;

	private Page<MyTiger> page = new Page<MyTiger>(10);

	@Test
	public void getSession() {
		Assert.assertNotNull(myTigerService.getMyTigerUtil().getSessionFactory());
		log.debug(" session:{}", myTigerService.getMyTigerUtil().getSessionFactory());
	}

	@Test
	public void CRUD() {
		MyTiger u=new MyTiger();
		myTigerService.getMyTigerUtil().save(u);
		u=myTigerService.getMyTigerUtil().get(u.getId());
		Assert.assertNotNull(u.getId());
		myTigerService.getMyTigerUtil().delete(u.getId());
	}
}