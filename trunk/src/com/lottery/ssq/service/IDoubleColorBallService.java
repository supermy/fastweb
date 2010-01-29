
package com.lottery.ssq.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.supermy.core.service.FastwebTemplate;

import com.lottery.ssq.domain.DoubleColorBall;

@WebService
public interface IDoubleColorBallService {

	/**
	 * @return the $(pojoNameLower)Util
	 */
	public abstract FastwebTemplate<DoubleColorBall, Long> getDoubleColorBallUtil();
	
	public List<Map<String, String>> trendline(Integer location, Integer limit) ;
	
	public List<Map<String, Object>> trendlinexml(Integer location, Integer limit) ;

}