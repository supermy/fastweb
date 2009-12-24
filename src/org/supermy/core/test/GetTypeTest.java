package org.supermy.core.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.supermy.core.domain.Role;

public class GetTypeTest {

	@Test
	public void getType() throws SecurityException, NoSuchFieldException {
		List<Role> value = new ArrayList<Role>();
		System.out.println(value.getClass());
		System.out.println(value.getClass().getSimpleName());
		System.out.println(value.iterator().getClass().getGenericInterfaces()[0]);
	}


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

}
