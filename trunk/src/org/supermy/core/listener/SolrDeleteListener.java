package org.supermy.core.listener;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;
import org.springframework.beans.factory.annotation.Autowired;

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
public class SolrDeleteListener implements PostDeleteEventListener {

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
	public void onPostDelete(PostDeleteEvent event) {
		if (running) {
			try {
				String id = event.getEntity().getClass().getSimpleName()+"-"+event.getId();
				System.out.println("delete fulltext domain id:"+id);
				client.deleteById(id);
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
