package org.supermy.cxf.service.test;

import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.supermy.core.service.IUserService;
import org.supermy.cxf.service.Man;

public class UserServiceClient {
	public static void main(String[] args) {
		// web("http://localhost:8080/hello");
		web("http://localhost:8080/fastweb/webservices/UserService");
	}

	private static void web(String url) {
		ClientProxyFactoryBean factoryProxy = new ClientProxyFactoryBean();
		factoryProxy.setServiceClass(IUserService.class);
		factoryProxy.setAddress(url);

		// factoryProxy.getServiceFactory().setDataBinding(new
		// AegisDatabinding());

		IUserService u = (IUserService) factoryProxy.create();
		System.out.println("client:"
				+ u.getUserUtil().findUniqueByProperty("email",
						"springclick@gmail.com"));
	}
}
