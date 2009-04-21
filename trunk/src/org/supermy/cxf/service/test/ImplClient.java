package org.supermy.cxf.service.test;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.supermy.cxf.service.IPojoHello;

public class ImplClient {
	public static void main(String[] args) {
		// web("http://localhost:8080/hello");
		web("http://localhost:8080/fastweb/webservices/hello");
	}

	private static void web(String url) {
		ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
		factory.setServiceClass(IPojoHello.class);
		factory.setAddress(url);
		// factory.setWsdlLocation(url+"?wsdl");
		 factory.getServiceFactory().setDataBinding(new AegisDatabinding());
		IPojoHello hello = (IPojoHello) factory.create();
		System.out.println("client:" + hello.say("james"));
	}
}
