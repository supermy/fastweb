package org.supermy.core.test;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;

public abstract class TestCase extends junit.framework.TestCase {
	public Log log = LogFactory.getLog(getClass());

	private static SessionFactory sessions;

	private static AnnotationConfiguration cfg;

	private static Dialect dialect;

	private static Class lastTestClass;

	private Session session;

	public TestCase() {
		super();
	}

	public TestCase(String x) {
		super(x);
	}

	protected void buildSessionFactory(Class[] classes, String[] packages,
			String[] xmlFiles) throws Exception {

		if (getSessions() != null)
			getSessions().close();
		try {
			setCfg(new AnnotationConfiguration());
			configure(cfg);
			if (recreateSchema()) {
				cfg.setProperty(Environment.HBM2DDL_AUTO, "update");// create-drop
			}
			for (int i = 0; i < packages.length; i++) {
				getCfg().addPackage(packages[i]);
			}
			for (int i = 0; i < classes.length; i++) {
				getCfg().addAnnotatedClass(classes[i]);
			}
			for (int i = 0; i < xmlFiles.length; i++) {
				InputStream is = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream(xmlFiles[i]);
				getCfg().addInputStream(is);
			}

			// File hbm=new
			// File("/home/supermy/open_source/newmovonet/src/context/WEB-INF/lib/hbm.jar");
			// getCfg().addJar(hbm);

			setDialect(Dialect.getDialect());
			setSessions(getCfg()
					.buildSessionFactory( /* new TestInterceptor() */));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	protected void setUp() throws Exception {
		if (getSessions() == null || lastTestClass != getClass()) {
			buildSessionFactory(getMappings(), getAnnotatedPackages(),
					getXmlFiles());
			lastTestClass = getClass();
		}
	}

	protected void runTest() throws Throwable {
		try {
			super.runTest();
			if (session != null && session.isOpen()) {
				if (session.isConnected())
					session.connection().rollback();
				session.close();
				session = null;
				fail("unclosed session");
			} else {
				session = null;
			}
		} catch (Throwable e) {
			try {
				if (session != null && session.isOpen()) {
					if (session.isConnected())
						session.connection().rollback();
					session.close();
				}
			} catch (Exception ignore) {
			}
			try {
				if (sessions != null) {
					sessions.close();
					sessions = null;
				}
			} catch (Exception ignore) {
			}
			throw e;
		}
	}

	public Session openSession() throws HibernateException {
		session = getSessions().openSession();
		return session;
	}

	protected abstract Class[] getMappings();

	protected String[] getAnnotatedPackages() {
		return new String[] {};
	}

	protected String[] getXmlFiles() {
		return new String[] {};
	}

	private void setSessions(SessionFactory sessions) {
		TestCase.sessions = sessions;
	}

	protected SessionFactory getSessions() {
		return sessions;
	}

	private void setDialect(Dialect dialect) {
		TestCase.dialect = dialect;
	}

	protected Dialect getDialect() {
		return dialect;
	}

	protected static void setCfg(AnnotationConfiguration cfg) {
		TestCase.cfg = cfg;
	}

	protected static AnnotationConfiguration getCfg() {
		return cfg;
	}

	protected void configure(Configuration cfg) {
		// cfg.setNamingStrategy( AlternativeNamingStrategy.INSTANCE );
		// cfg.getSessionEventListenerConfig().setFlushEventListener( new
		// EJB3FlushEventListener() );
		// cfg.getSessionEventListenerConfig().setAutoFlushEventListener( new
		// EJB3AutoFlushEventListener() );
	}

	protected boolean recreateSchema() {
		return true;
	}

}
