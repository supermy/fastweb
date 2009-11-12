package org.supermy.core.listener;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.domain.BaseDomain;

/**
 * 
 * 自动在entity创建和更新的时候，添加检索信息到solr.
 * 
 * 在hibernate执行saveOrUpdate()时,自动添加检索信息到solr.
 * 
 */
@SuppressWarnings("serial")
public class SolrCreateListener implements PostInsertEventListener {
	boolean running = false;
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Autowired
	private SolrServer client;
	
	@Override
	public void onPostInsert(PostInsertEvent event) {
		if (running) {
			try {
				client.addBean(event.getEntity());
				client.commit();
			} catch (SolrServerException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

}
