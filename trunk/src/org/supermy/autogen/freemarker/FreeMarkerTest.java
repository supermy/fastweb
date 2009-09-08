package org.supermy.autogen.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerTest {

	public static void main(String[] args) {
		FreeMarkerTest test = new FreeMarkerTest();
		test.genFile();
		test.getFile(Locale.JAPAN);
	}

	public void genFile() {
		Configuration freemarkerCfg = new Configuration();
		freemarkerCfg.setClassForTemplateLoading(this.getClass(), "/");
		freemarkerCfg.setEncoding(Locale.getDefault(), "UTF-8");
		Template template;
		try {

			template = freemarkerCfg.getTemplate("test.ftl");
			template.setEncoding("UTF-8");
			File htmlFile = new File("test.html");
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(htmlFile), "UTF-8"));
			HashMap propMap = new HashMap();
			propMap.put("we", "我们");
			propMap.put("user", "hermit");
			propMap.put("hello", "hello");
			template.process(propMap, out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getFile(Locale loc) {
		Configuration freemarkerCfg = new Configuration();
		freemarkerCfg.setClassForTemplateLoading(this.getClass(), "/");
		freemarkerCfg.setEncoding(Locale.getDefault(), "UTF-8");
		Template template;
		try {
			template = freemarkerCfg.getTemplate("test.ftl", loc);
			template.setEncoding("UTF-8");
			File htmlFile = new File("test_" + loc.getLanguage() + "_"
					+ loc.getCountry() + ".html");
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(htmlFile), "UTF-8"));
			HashMap propMap = new HashMap();
			propMap.put("we", "我们");
			propMap.put("user", "hermit");
			propMap.put("hello", "hello");
			
			template.process(propMap, out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
