package org.supermy.cxf.service;

import org.slf4j.LoggerFactory;

public class PojoHello implements IPojoHello {
	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	/* (non-Javadoc)
	 * @see org.supermy.cxf.service.IHello#say(java.lang.String)
	 */
	public String say(String name){
		log.debug("call by client:"+name);
		return "hello,"+name;
	}
	
	public Man say(Man m){
		m.setName("hi,"+m.getName());
		log.debug("call by client:"+m.getName());
		return m;
	}
	

}
