package org.supermy.autogen.test;

import java.lang.reflect.Field;

import org.apache.struts2.convention.SEOActionNameBuilder;

public class SeoNameBuildTest {
	public static void main(String[] args) {
		String seoName=new SEOActionNameBuilder("true","-").build("MyTigerAction");
		System.out.println("seo name:"+seoName);
//		Field fi=new Field();
//		fi.getDeclaredAnnotations();
//		fi.getAnnotations();
	}

}
