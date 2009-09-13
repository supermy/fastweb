package org.supermy.autogen;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import org.apache.struts2.convention.SEOActionNameBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2x.GenericExporter;
import org.hibernate.tool.hbm2x.pojo.POJOClass;
import org.hibernate.util.StringHelper;

/**
 */
public class ActionExporter extends GenericExporter {
	

	@Override
	public void start() {
		getProperties().put("util", new SEOActionNameBuilder("true","-"));
		getProperties().put("webdata", new WebDataConvert());
		super.start();
	}

	protected String resolveFilename(POJOClass element) {
		String packageNameForFile = getPackageNameForFile( element );
		//获取上一级的包路径
		packageNameForFile=packageNameForFile.substring(0, packageNameForFile.lastIndexOf("."));
		String webpath=packageNameForFile.substring(packageNameForFile.lastIndexOf(".")+1);
		
		String filename = StringHelper.replace(super.getFilePattern(), "{class-name}",webpath+"/"+ getClassNameForFile( element )); 
		
		String packageLocation = StringHelper.replace(packageNameForFile,".", "/");		
		if(StringHelper.isEmpty(packageLocation)) {
			packageLocation = "."; // done to ensure default package classes doesn't end up in the root of the filesystem when outputdir=""
		}
		filename = StringHelper.replace(filename, "{package-name}", packageLocation);
		return filename;
	}
	

}
