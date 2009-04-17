package org.supermy.core.spring;

import java.io.IOException;

import javax.persistence.Entity;

import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.util.ClassUtils;

/**
* @author supermy E-mail:springclick@gmail.com
* @version create time：2008-8-2 下午02:03:22
* 
*/
public class AutoScanAnnotationSessionFactoryBean extends
		AnnotationSessionFactoryBean {
	
	private ResourcePatternResolver rl = new PathMatchingResourcePatternResolver();
	private MetadataReaderFactory mrf = new CachingMetadataReaderFactory(
			this.rl);
	private String[] domainPackageNames;

	public void setDomainPackageNames(String[] packageNames) {
		this.domainPackageNames = packageNames;
	}
	
	@Override
	protected void postProcessAnnotationConfiguration(
			AnnotationConfiguration config) throws HibernateException {
		for (String basePackage : domainPackageNames) {
			String classPattern = "**/" + basePackage + "/*.class";
			Resource[] rs;
			try {
				rs = rl.getResources(classPattern);
				for (Resource resource : rs) {
					MetadataReader mr = mrf
							.getMetadataReader(resource);
					String className = mr.getClassMetadata().getClassName();
					Class clazz = ClassUtils.forName(className);
					if (clazz.isAnnotationPresent(Entity.class)) {
						config.addAnnotatedClass(clazz);
						System.out.println("add class" + clazz.getName());
					}
				}
			} catch (IOException e) {
				throw new IllegalStateException("scan hibernate annotation error:IOException", e);
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException("scan hibernate annotation error:ClassNotFoundException", e);
			} catch (LinkageError e) {
				throw new IllegalStateException("scan hibernate annotation error:LinkageError", e);
			}
		}
		super.postProcessAnnotationConfiguration(config);
	}

}

// 這個 class 的內容是以 Spring 2.5 的 ClassPathBeanDefinitionScanner 為骨幹而來的。原理很簡單，以
// PathMatchingResourcePatternResolver 去找到所以 basePackage 下的 classes，一一比對是否有
// annotate 了 @javax.persistence.Entity，如果有就加入到 session factory 的
// annotationClass。
//
//
// <bean id="sessionFactory"
// class="package.ClasspathScanningAnnotationSessionFactoryBean">
// <property name="dataSource" ref="dataSource"/>
// <property name="hibernateProperties">
// <props>
// <prop key="hibernate.dialect">${hibernate.dialect}</prop>
// </props>
// </property>
// <property name="basePackages">
// <list>
// <value>package.model</value>
// </list>
// </property>
// </bean>

