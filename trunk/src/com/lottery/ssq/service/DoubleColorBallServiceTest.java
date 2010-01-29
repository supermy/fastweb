
package com.lottery.ssq.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.ssq.service.IDoubleColorBallService;
import com.lottery.ssq.domain.DoubleColorBall;
import org.supermy.core.test.TestBaseService;
import org.supermy.core.service.Page;

public class DoubleColorBallServiceTest extends TestBaseService {

	@Autowired
	private IDoubleColorBallService doubleColorBallService;

	private Page<DoubleColorBall> page = new Page<DoubleColorBall>(10);

	@Test
	public void getSession() {
		Assert.assertNotNull(doubleColorBallService.getDoubleColorBallUtil().getSessionFactory());
		log.debug(" session:{}", doubleColorBallService.getDoubleColorBallUtil().getSessionFactory());
	}

	@Test
	public void CRUD() {
		// DoubleColorBall u=new DoubleColorBall();
		// doubleColorBallService.getDoubleColorBallUtil().save(u);
		// u=doubleColorBallService.getDoubleColorBallUtil().get(u.getId());
		// Assert.assertNotNull(u.getId());
		// doubleColorBallService.getDoubleColorBallUtil().delete(u.getId());
	}
}