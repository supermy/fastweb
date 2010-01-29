package com.lottery.ssq.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.service.BaseService;
import org.supermy.core.service.FastwebTemplate;
import org.supermy.core.service.Page;

import com.lottery.ssq.domain.DoubleColorBall;

@Transactional
@Service
public class DoubleColorBallService extends BaseService implements
		IDoubleColorBallService {

	private static final org.slf4j.Logger log = LoggerFactory
			.getLogger(DoubleColorBallService.class);

	private FastwebTemplate<DoubleColorBall, Long> doubleColorBallUtil;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		doubleColorBallUtil = new FastwebTemplate<DoubleColorBall, Long>(
				sessionFactory, null, DoubleColorBall.class);
	}

	/**
	 * @return the resourceUtil
	 */
	public FastwebTemplate<DoubleColorBall, Long> getDoubleColorBallUtil() {
		return doubleColorBallUtil;
	}

	/*
	 * @see
	 * com.lottery.ssq.service.IDoubleColorBallService#trendline(java.lang.String
	 * , org.supermy.core.service.Page)
	 */
	public List<Map<String, String>> trendline(Integer location, Integer limit) {

		String columnname = "";
		if (location.intValue() == 7) {// 蓝色球
			columnname = "blueBall";
		} else {
			columnname = "sequence" + location;
		}

		String ballquery = "select obj." + columnname
				+ " from DoubleColorBall obj order by obj.id asc";

		String countquery = "select count(obj.id) from DoubleColorBall obj";
		Long total = (Long) doubleColorBallUtil.findUnique(countquery);

		List<String> findForProperty = doubleColorBallUtil.findForProperty(
				ballquery, total.intValue(), limit);

		List<Map<String, String>> result = new ArrayList<Map<String, String>>(
				limit);
		for (String obj : findForProperty) {
			Map<String, String> line = new TreeMap<String, String>();
			for (int i = 0; i <= 32; i++) {

				int jj = i + 1;
				String jkey = jj <= 9 ? "0" + jj : jj + "";

				if (Integer.parseInt(obj) == jj) {
					line.put("ball" + jkey, obj + "");
				} else {
					line.put("ball" + jkey, "");
				}
			}
			result.add(line);
		}
		return result;
	}

	public List<Map<String, Object>> trendlinexml(Integer location,
			Integer limit) {

		String columnname = "";
		if (location.intValue() == 7) {// 蓝色球
			columnname = "blueBall";
		} else {
			columnname = "sequence" + location;
		}

		// select new map( max(bodyWeight) as max, min(bodyWeight) as min,
		// count(*) as n )

		String ballquery = "select new map(obj.lotteryDates as year,obj."
				+ columnname + " as ball  ) "
				+ "from DoubleColorBall obj order by obj.id asc";

		String countquery = "select count(obj.id) from DoubleColorBall obj";
		Long total = (Long) doubleColorBallUtil.findUnique(countquery);

		return doubleColorBallUtil.findForProperty(ballquery, total.intValue(),
				limit);
	}

}