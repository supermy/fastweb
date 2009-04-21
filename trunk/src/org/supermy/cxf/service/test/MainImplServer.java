package org.supermy.cxf.service.test;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.supermy.cxf.service.IPojoHello;
import org.supermy.cxf.service.PojoHello;

public class MainImplServer {
	
	public static void main(String[] args) throws InterruptedException {
		String url="http://localhost:8080/fastweb/webservices/hello";
		
		ServerFactoryBean sf = new ServerFactoryBean();
//		sf.setServiceClass(IPojoHello.class);
		sf.setServiceClass(PojoHello.class);
		sf.setAddress(url);
		sf.setServiceBean(new PojoHello());	
//		sf.getServiceFactory().setDataBinding(new AegisDatabinding());
		sf.getInInterceptors().add(new LoggingInInterceptor());
		sf.getOutInterceptors().add(new LoggingOutInterceptor());
		
		Server server = sf.create();
		server.start();
		
	}

}
