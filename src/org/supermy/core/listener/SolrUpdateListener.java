package org.supermy.core.listener;

import java.io.IOException;
import java.util.Date;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.hibernate.HibernateException;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.domain.BaseDomain;
import org.supermy.core.security.SecurityUtils;

/**
 * 
 * 自动在entity创建和更新的时候，添加检索信息到solr.
 * 
 * 在hibernate执行saveOrUpdate()时,自动添加检索信息到solr.
 * 
 * TODO  创建的时候需要获取到id更新到索引中去；
 * 
 */
@SuppressWarnings("serial")
public class SolrUpdateListener implements PostUpdateEventListener {
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
	public void onPostUpdate(PostUpdateEvent event) {
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
