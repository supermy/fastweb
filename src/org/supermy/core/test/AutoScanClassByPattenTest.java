package org.supermy.core.test;

import java.io.IOException;

import javax.persistence.Entity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.supermy.core.domain.User;

public class AutoScanClassByPattenTest {

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

	@Test
	public void autoScanDomainClass() throws IOException, ClassNotFoundException {

		String classPattern = "**/domain/*.class";
		ResourcePatternResolver rl = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(   
		        rl); 
		Resource[] resources = rl.getResources(classPattern);
		for (Resource resource : resources) {
			MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);   
            String className = metadataReader.getClassMetadata().getClassName(); 
            Class clazz = ClassUtils.forName(className);   
            if (clazz.isAnnotationPresent(Entity.class)) {
				System.out.println("add class"+clazz.getName());
			}
		}

		System.out.println("user class ... ... "
				+ User.class.isAnnotationPresent(Entity.class));
	}
}
