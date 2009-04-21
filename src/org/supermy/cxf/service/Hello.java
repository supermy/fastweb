package org.supermy.cxf.service;

import javax.jws.WebService;
import javax.xml.ws.WebServiceProvider;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value="helloWebService")
@WebService
public class Hello implements IHello {
	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	/* (non-Javadoc)
	 * @see org.supermy.cxf.service.IHello#say(java.lang.String)
	 */
	public String say(String name){
		log.debug("call by client:"+name);
		return "hello"+name;
	}
	public Man say1(Man m){
		m.setName("hi,"+m.getName());
		log.debug("call by client:"+m.getName());
		return m;
	}

}
