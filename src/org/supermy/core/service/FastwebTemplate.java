package org.supermy.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.supermy.core.domain.BaseDomain;

@Transactional
public class FastwebTemplate<T extends BaseDomain, IdT extends Serializable>
		extends BaseTemplate<T, IdT> {

	public FastwebTemplate(SessionFactory sessionFactory, Session session,
			Class<T> domainClass) {
		super(sessionFactory, session, domainClass);
	}

	// //////////////////////////////////////

	/**
	 * 按HQL分页查询. 不支持自动获取总结果数,需用户另行执行查询.
	 * 
	 * @return 分页查询结果,附带结果列表及所有查询时的参数.
	 */
	@Transactional(readOnly = true)
	public Page<T> find(final Page<T> page, final String selectHql,
			final String otherHql, final Object... values) {
		Assert.notNull(page);

		// 这是一种特殊情况
		StringBuffer orderHql = new StringBuffer();
		if (page.isOrderBySetted()) {
			orderHql.append(" order by ");

			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');

			Assert.isTrue(orderByArray.length == orderArray.length,
					"分页多重排序参数中,排序字段与排序方向的个数不相等");

			for (int i = 0; i < orderByArray.length; i++) {

				if (Page.ASC.equals(orderArray[i])) {
					// TODO 约定俗成 From Object as obj ,可以用正则表达式进一步改进
					if (i > 0) {
						orderHql.append(",");
					}
					orderHql.append(" obj").append(".").append(orderByArray[i])
							.append(" asc ");
				} else {
					if (i > 0) {
						orderHql.append(",");
					}
					orderHql.append(" obj ").append(".")
							.append(orderByArray[i]).append(" desc ");
				}
			}
		}

		String hql = new String(selectHql + otherHql + orderHql);
		log.debug(hql);

		Query q = createQuery(hql, values);

		if (page.isAutoTotal()) {
			int totalCount = getRecordCount(otherHql, values).intValue();
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);
		List result = q.list();
		page.setResult(result);
		if (log.isDebugEnabled()) {
			queryByCacheStats();
		}
		return page;
	}

	@Transactional(readOnly = true)
	public Page<T> search(final Page<T> page, final Map<String, Object> filters) {
		Assert.notNull(page);
		Assert.notNull(filters);

		String wherehql = " " + filters.get("hql").toString();
		filters.remove("hql");
		return find(page, "", " from " + domainClass.getName() + " obj "
				+ wherehql, filters.values().toArray());

	}

	@Transactional(readOnly = true)
	public Page<T> fullltext(final Page<T> page, final String q,
			final SolrServer client) {
		Assert.notNull(page);
		Assert.notNull(q);

		// 查询
		Integer start = page.getFirst();
		Integer rows = page.getPageSize();
		SolrQuery solrQuery = new SolrQuery().setQuery(
				q + " AND indextype_t:" + domainClass.getSimpleName()).setFields("id")
				.setStart(start).setRows(rows);
		// .addFacetField("id").addFacetField("name");
		QueryResponse rsp;
		try {
			// System.out.println("client:==================" + client);
			rsp = client.query(solrQuery);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		// 结果处理
		SolrDocumentList results = rsp.getResults();
		List<Long> ids = new ArrayList<Long>();
		for (SolrDocument solrDocument : results) {
			log.debug("find solrDocument:{}", solrDocument);
			String id = solrDocument.getFieldValue("id").toString();
			ids.add(Long.parseLong(StringUtils.substringAfterLast(id, "-")));
		}
		log.debug("ids:{}", ids);
		log.debug("total count:{}", results.getNumFound());
		// long2int
		page.setTotalCount(Integer.parseInt(results.getNumFound() + ""));

		page.setResult(find(ids));

		log.debug("find domain :{}", page.getResult());
		
		return page;
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameter(final Query q, final Page<T> page) {
		q.setFirstResult(page.getFirst());
		q.setMaxResults(page.getPageSize());

		return q;
	}

	/**
	 * @param otherHql
	 * @param values
	 * @return
	 */
	private Long getRecordCount(String otherHql, Object... values) {
		StringBuffer hql = new StringBuffer(" select count(*) ")
				.append(otherHql);
		return (Long) findUnique(hql.toString(), values);
	}

	@Transactional(readOnly = true)
	public Page<T> get(final Page<T> page) {
		return find(page, "", " from " + domainClass.getName() + " obj");
	}

	@Transactional(readOnly = true)
	public List<T> getAll() {
		return createQuery(" from " + domainClass.getName()).list();
	}

}
