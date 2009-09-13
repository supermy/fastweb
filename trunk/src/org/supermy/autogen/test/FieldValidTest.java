package org.supermy.autogen.test;

import org.springframework.security.util.FieldUtils;
import org.supermy.autogen.WebDataConvert;

import com.gogo.comix.domain.MyTiger;

/**
 * @author 莫勇
 * 某個樹形是否具備 校驗 注解；說明注解成為躲過語言
 * 
 */
public class FieldValidTest {
	
	public static void main(String[] args) throws NoSuchFieldException {
		WebDataConvert t=new WebDataConvert();
		System.out.println(t.valid(FieldUtils.getField(MyTiger.class,"myEmail")));
	}

}
