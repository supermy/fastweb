package org.supermy.cxf.service.test;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.supermy.cxf.service.IHello;
import org.supermy.cxf.service.Man;

public class Client {
	public static void main(String[] args) {
		//web("http://localhost:8080/hello");
		web("http://localhost:8080/fastweb/webservices/hello");
	}

	private static void web(String url) {
		JaxWsProxyFactoryBean factoryProxy=new JaxWsProxyFactoryBean();
		factoryProxy.setAddress(url);
		factoryProxy.setServiceClass(IHello.class);
		
//		factoryProxy.getServiceFactory().setDataBinding(new AegisDatabinding());
	
		IHello hello=(IHello)factoryProxy.create();
		System.out.println("client:"+hello.say("james"));
		System.out.println("client:"+hello.say1(new Man("pen")));
	}
}
