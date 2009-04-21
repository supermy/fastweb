package org.supermy.cxf.service;

import javax.jws.WebService;

@WebService
public interface IHello {
	public abstract String say(String name);
	public abstract Man say1(Man m);

}