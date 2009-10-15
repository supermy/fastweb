package com.gogo.comix.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.supermy.core.test.TestBaseService;

import com.gogo.comix.domain.MyTiger;
import com.gogo.comix.service.IMyTigerService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/app-springs.xml" })
public class MyTigerCRUDServiceTest extends TestBaseService {

	@Autowired
	private IMyTigerService myTigerService;


	@Test
	public void addMytiger() {
		MyTiger r = new MyTiger();
		r.setMyEmail("a@a.com");
		r.setMyTiger("my tiger");
		myTigerService.getMyTigerUtil().save(r);
		flush();
	}


}
