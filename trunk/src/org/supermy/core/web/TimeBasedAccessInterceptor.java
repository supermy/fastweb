package org.supermy.core.web;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TimeBasedAccessInterceptor extends HandlerInterceptorAdapter {

	private int openingTime;
	private int closingTime;

	public void setOpeningTime(int openingTime) {
		this.openingTime = openingTime;
	}

	public void setClosingTime(int closingTime) {
		this.closingTime = closingTime;
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		Calendar cal = Calendar.getInstance();
		// int hour = cal.get(HOUR_OF_DAY);
		int hour = cal.get(24);
		if (openingTime <= hour && hour < closingTime) {
			return true;
		} else {
			response.sendRedirect("http://host.com/outsideOfficeHours.html");
			return false;
		}
	}
}