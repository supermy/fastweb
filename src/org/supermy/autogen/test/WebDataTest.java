package org.supermy.autogen.test;

import org.supermy.autogen.WebDataConvert;

import com.gogo.comix.domain.MyTiger;


/**
 * @author 莫勇
 */
public class WebDataTest {
	
	public static void main(String[] args) throws NoSuchFieldException {
		WebDataConvert t=new WebDataConvert();
		System.out.println(MyTiger.class);
		System.out.println(t.fieldHasType(MyTiger.class,"java.util.Date"));
	}

}
