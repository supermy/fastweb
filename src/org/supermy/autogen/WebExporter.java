package org.supermy.autogen;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import org.apache.struts2.convention.SEOActionNameBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2x.GenericExporter;
import org.hibernate.tool.hbm2x.pojo.POJOClass;
import org.hibernate.util.StringHelper;

public class WebExporter extends GenericExporter {
	
	@Override
	public void start() {
		getProperties().put("util", new SEOActionNameBuilder("true","-"));
		super.start();
	}
	
	//类名第一个字母小写
	protected String resolveFilename(POJOClass element) {
		String classNameForFile = getClassNameForFile( element );
		classNameForFile=classNameForFile.substring(0,1).toLowerCase()+classNameForFile.substring(1);
		classNameForFile=new SEOActionNameBuilder("true","-").build(classNameForFile);
		
		String filename = StringHelper.replace(getFilePattern(), "{class-name}", classNameForFile); 
		
		String packageNameForFile = getPackageNameForFile( element );
		packageNameForFile=packageNameForFile.substring(0,packageNameForFile.lastIndexOf("."));
		packageNameForFile=packageNameForFile.substring(packageNameForFile.lastIndexOf("."));
		String packageLocation = StringHelper.replace(packageNameForFile,".", "/");
		if(StringHelper.isEmpty(packageLocation)) {
			packageLocation = "."; // done to ensure default package classes doesn't end up in the root of the filesystem when outputdir=""
		}
				
		filename = StringHelper.replace(filename, "{package-name}", packageLocation);
		return filename;
	}	
	    	
}
