package org.supermy.core.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.supermy.core.domain.User;
import org.supermy.core.service.Page;

/**
 * @author 莫勇
 *
 */
public class PageTest {

	@Test
	public void gennav(){
		Page<User> obj=new Page<User>();
		obj.setPageNo(1);
		obj.setPageSize(10);
		obj.setTotalCount(200);
		String genNav = obj.genNav("test.action", "pageuser" ,8);
		System.out.println(genNav);
	}
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
