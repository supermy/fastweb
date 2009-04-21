package org.supermy.cxf.service.test;

import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.supermy.cxf.service.Hello;

public class MainServer {
	public static void main(String[] args) throws InterruptedException {
		JaxWsServerFactoryBean jwsf = new JaxWsServerFactoryBean();
		jwsf.setServiceClass(Hello.class);
		jwsf.setAddress("http://localhost:8080/fastweb/webservices/hello");
		
//		jwsf.getServiceFactory().setDataBinding(new AegisDatabinding());
		
		jwsf.getInInterceptors().add(new LoggingInInterceptor());
		jwsf.getOutInterceptors().add(new LoggingOutInterceptor());
		Server server = jwsf.create();
		server.start();
	}

}
