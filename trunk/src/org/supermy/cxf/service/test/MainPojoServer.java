package org.supermy.cxf.service.test;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.supermy.cxf.service.IPojoHello;
import org.supermy.cxf.service.PojoHello;

/**
 * @author my
 *CXF2支持的两种绑定方式jaxb2和aegis
 */
public class MainPojoServer {
	public static void main(String[] args) throws InterruptedException {
		ServerFactoryBean jwsf = new ServerFactoryBean();
		jwsf.setServiceClass(IPojoHello.class);
		jwsf.setAddress("http://localhost:8080/fastweb/webservices/hello");
		
		jwsf.setServiceBean(new PojoHello());//必须有，否则测试错误
		
//		jwsf.getServiceFactory().setDataBinding(new AegisDatabinding());
		
		jwsf.getInInterceptors().add(new LoggingInInterceptor());
		jwsf.getOutInterceptors().add(new LoggingOutInterceptor());
		Server server = jwsf.create();
		server.start();
	}

}
