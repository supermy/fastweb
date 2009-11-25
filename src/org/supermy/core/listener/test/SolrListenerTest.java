package org.supermy.core.listener.test;

import java.io.IOException;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.domain.User;
import org.supermy.core.service.IUserService;
import org.supermy.core.service.Page;
import org.supermy.core.test.TestBaseService;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2009-11-05 10:28:33
 * @version update time: 2009-11-09 9:24
 * 
 */
public class SolrListenerTest extends TestBaseService {

	private final org.slf4j.Logger log = LoggerFactory
			.getLogger(SolrListenerTest.class);

	@Autowired
	private IUserService userService;

	@Autowired
	private SolrServer client;

	private Page<User> page = new Page<User>(10);

	@Test
	// @Rollback(false)
	public void DomainCRUDListener() throws SolrServerException {
		User u = new User();
		u.setEmail("test@sina.com");
		u.setName(u.getEmail());
		u.setIntro("intro 君不见，黄河之水天上来；东流到海不复回。");
		u.setPasswd("123456");
		u.setSalary(10000.00);
		// create
		userService.getUserUtil().save(u);
		u = userService.getUserUtil().get(u.getId());
		// update
		u.setPasswd("654321");
		userService.getUserUtil().save(u);
		// ...

		Page<User> fullltext = userService.getUserUtil().fullltext(page, "黄河",
				client);
		log.debug(" full text domain :{}", fullltext.getResult());

		// delete
		Assert.assertNotNull(u.getId());
		userService.getUserUtil().delete(u.getId());
	}

	@Test
	// @Rollback(false)
	public void addCustomBuildIndex() throws SolrServerException, IOException {
		log.debug("full text search add start:{}", client);

		// 增加索引
		SolrInputDocument sid = new SolrInputDocument();
		sid.addField("id", 1);
		sid.addField("name", "struts+hibernate+spring 开发大全");
		sid.addField("intro_t", "intro 个人介绍");
		client.add(sid);
		client.commit();

		SolrInputDocument sid1 = new SolrInputDocument();
		sid1.addField("id", 1);
		sid1.addField("name", "struts+hibernate+spring 开发大全 update");
		sid1.addField("intro_t", "intro 个人介绍  update");
		client.add(sid1);
		client.commit();

		SolrQuery solrQuery = new SolrQuery().setQuery("个人");
		QueryResponse rsp = client.query(solrQuery);

		SolrDocumentList results = rsp.getResults();
		log.debug("------------------------begin");
		for (SolrDocument solrDocument : results) {
			Collection<String> fieldNames = solrDocument.getFieldNames();
			for (String name : fieldNames) {
				Object fieldValue = solrDocument.getFieldValue(name);
				log.debug("name={},value={}", name, fieldValue);
			}
			log.debug("------------------------");
		}
		log.debug("------------------------end");

		// client.deleteByQuery("intro_t:个人");
		// client.commit();
	}

	@Test
	// @Rollback(false)
	public void deleteByQueryIndex() throws SolrServerException, IOException {
		log.debug("full text search delete start:{}", client);
		// client.deleteByQuery("name:sina");
		client.deleteByQuery("intro_t:黄河");
		client.commit();
	}

	@Test
	// @Rollback(false)
	public void deleteByIdIndex() throws SolrServerException, IOException {
		log.debug("full text search delete start:{}", client);
		String id = "User-38";
		SolrInputDocument sid = new SolrInputDocument();
		client.deleteById(id);
		client.commit();
	}

	@Test
	// @Rollback(false)
	public void updateCustomBuildIndex() throws SolrServerException,
			IOException {
		log.debug("full text search update start:{}", client);

		// 更新索引
		UpdateRequest updateRequest = new UpdateRequest();
		SolrInputDocument usid = new SolrInputDocument();
		usid.addField("id", 1);
		usid.addField("name", "my china 万里长城永不倒");
		updateRequest.setAction(UpdateRequest.ACTION.COMMIT, false, false);
		updateRequest.add(usid);
		UpdateResponse updateResponse = updateRequest.process(client);

	}

	@Test
	// @Rollback(false)
	public void oprimizeCustomBuildIndex() throws SolrServerException,
			IOException {
		log.debug("full text oprimize start:{}", client);
		client.optimize();// 索引文件手动优化
	}

	@Test
	// @Rollback(false)
	public void fulltextSearchQuery() throws SolrServerException, IOException {
		log.debug("full text search solrquery start:{}", client);

		// 另外一种查询方式
		SolrQuery solrQuery = new SolrQuery().setQuery("intro_t:黄河").setFacet(
				true).setFacetMinCount(1).setFacetLimit(8);
		// .addFacetField("id").addFacetField("name");
		QueryResponse rsp = client.query(solrQuery);
		System.out.println("solr query =================response = " + rsp);

		// search
		// SolrQuery query = new SolrQuery().setHighlight(true);
		// String condition="";
		// Integer start=0;
		// Integer rows=4;
		// query.setQuery(condition);
		// query.setStart(start);
		// query.setRows(rows);

	}

	@Test
	// @Rollback(false)
	public void fulltextSearchParam() throws SolrServerException, IOException {
		log.debug("full text search param start:{}", client);
		addCustomBuildIndex();
		updateCustomBuildIndex();

		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("qt", "/spellCheckCompRH");
		// 查询字符串
		params.set("q", "永不倒");
		params.set("spellcheck", "on");// spellCheckCompRH 是开启solr的拼写检查
		params.set("spellcheck.build", "true");// spellcheck 可以实现输入“错误”提示

		QueryResponse response = client.query(params);
		System.out.println("params =================response = " + response);

		// 总记录数量
		// QueryResponse rsp = client.query( query );
		// SolrDocumentList docs = rsp.getResults();
		// long num = docs.getNumFound(); //getNumFound获得的是整个的查询数量
		// System.out.println("================total = {}"+ num);

		// ·········10········20········30········40········50
		// SolrDocumentList sdl = response.getResults();
		// Integer len = (int) sdl.getNumFound();
		// System.out.println(len);
		// int perPage=10;
		// for(int i = 0; i < perPage; i++)
		// {
		// SolrDocument d = sdl.get(i);
		// for(Iterator<Map.Entry<String, Object>> j = d.iterator();
		// j.hasNext(); )
		// {
		// hits.add((String)( j.next().getValue() ) );
		// }
		// }

	}

}
