package org.supermy.cxf.service.test;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.supermy.cxf.service.IPojoHello;
import org.supermy.cxf.service.Man;

public class PojoClient {
	public static void main(String[] args) {
		// web("http://localhost:8080/hello");
		web("http://localhost:8080/fastweb/webservices/hello");
	}

	private static void web(String url) {
		ClientProxyFactoryBean factoryProxy = new ClientProxyFactoryBean();
		factoryProxy.setServiceClass(IPojoHello.class);
		factoryProxy.setAddress(url);
		
//		factoryProxy.getServiceFactory().setDataBinding(new AegisDatabinding());

		IPojoHello hello = (IPojoHello) factoryProxy.create();
		System.out.println("client:" + hello.say("james"));
		System.out.println("client:" + hello.say(new Man("pen")));
	}
}
