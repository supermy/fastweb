package com.lottery.ssq.service.test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.supermy.core.service.Page;
import org.supermy.core.test.TestBaseService;

import com.lottery.ssq.domain.DoubleColorBall;
import com.lottery.ssq.service.DoubleColorBallService;

public class DoubleColorBallServiceTest extends TestBaseService {

	@Autowired
	private DoubleColorBallService doubleColorBallService;

	private Page<DoubleColorBall> page = new Page<DoubleColorBall>(10);

	@Test
	public void getSession() {
		Assert.assertNotNull(doubleColorBallService.getDoubleColorBallUtil()
				.getSessionFactory());
		log.debug(" session:{}", doubleColorBallService
				.getDoubleColorBallUtil().getSessionFactory());
	}

	@Test
	@Rollback(true)
	// 是否写入数据库
	public void trendlinexml() {
		Integer location = 1;
		Integer limit = 30;
		List<Map<String, Object>> trendline = doubleColorBallService.trendlinexml(location, limit);
		for (Map<String, Object> map : trendline) {
			System.out.println(map);
		}
	}

	@Test
	@Rollback(true)
	// 是否写入数据库
	public void trendline() {
		processTrendLine(1, 10);
		processTrendLine(2, 10);
		// processTrendLine(3, 10);
		// processTrendLine(4, 10);
		// processTrendLine(5, 10);
		// processTrendLine(6, 10);
		// processTrendLine(7, 10);
	}

	private void processTrendLine(int location, int limit) {
		List<Map<String, String>> trendline = doubleColorBallService.trendline(
				location, limit);
		System.out
				.println("[010203040506070809101112131415161718192021222324252627282930313233]");
		for (Map<String, String> map : trendline) {
			// System.out.println("count:"+map.values().size());
			StringBuffer sb = new StringBuffer("[");
			for (String obj : map.values()) {
				obj = obj.isEmpty() ? "--" : obj;
				sb.append(obj).append("");
			}
			sb.append("]");
			System.out.println(sb);
		}
	}

	@Test
	@Rollback(true)
	// 是否写入数据库
	public void CRUD() {
		DoubleColorBall u = new DoubleColorBall();
		// 10009 2010-1-21 21 13 9 24 32 1
		// 1 9 13 21 24 32 6

		u.setNum("10009");
		u.setLotteryDates(new Date(2010 - 1 - 21));
		u.setSequence1("21");
		u.setSequence2("13");
		u.setSequence3("09");
		u.setSequence4("24");
		u.setSequence5("32");
		u.setSequence6("01");

		u.setSize1("01");
		u.setSize2("09");
		u.setSize3("13");
		u.setSize4("21");
		u.setSize5("24");
		u.setSize6("32");

		u.setBlueBall("06");

		u.setRedBlueBall();

		try {
			doubleColorBallService.getDoubleColorBallUtil().save(u);
			u = doubleColorBallService.getDoubleColorBallUtil().get(u.getId());
			Assert.assertTrue(u.getRedBlueBall().equals("01091321243206"));
			Assert.assertNotNull(u.getId());
			doubleColorBallService.getDoubleColorBallUtil().delete(u.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}