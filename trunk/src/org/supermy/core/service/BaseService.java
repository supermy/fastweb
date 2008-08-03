package org.supermy.core.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.BaseDomain;
import org.supermy.core.domain.User;

@Transactional
public class BaseService implements IBaseService {

	@Autowired
	public HibernateTemplate hibernateTemplate;

	/**
	 * @param detachedCriteria
	 * @return
	 */
	private Long getRecordCount(final DetachedCriteria detachedCriteria) {
		return (Long) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(session);
				long count = Long.parseLong((criteria.setProjection(Projections
						.rowCount()).uniqueResult()).toString());
				criteria.setProjection(null);
				return Long.valueOf("" + count);
			}
		}, true);
	}

	// 查询指定范围的数据
	private List findByConditions(final DetachedCriteria detachedCriteria,
			final int startResult, final int limitResult) {

		return (List) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(session);
				criteria.setFirstResult(startResult);
				criteria.setMaxResults(limitResult);
				return criteria.list();
			}
		}, true);
	}

	// 计算开始记录
	private int startResult(final DetachedCriteria detachedCriteria,
			final long pageNum, final long pageSize) {
		Long recordCount = getRecordCount(detachedCriteria);
		Long result = recordCount / pageSize * pageNum;
		return result.intValue();
	}

	/**
	 * FIXME
	 * 
	 * @param detachedCriteria
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Set findByPage(final DetachedCriteria detachedCriteria,
			final int pageNum, final int pageSize) {
		int startResult = startResult(detachedCriteria, pageNum, pageSize);
		List result = findByConditions(detachedCriteria, startResult, pageSize);
		return new HashSet(result);
	}

	/**
	 * get total record
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	private Long getRecordCount(StringBuffer otherHql) {
		StringBuffer hql = new StringBuffer(" select count(*) ")
				.append(otherHql);
		Long result = new Long(hibernateTemplate.find(hql.toString()).get(0)
				.toString());
		return result;
	}

	/**
	 * 
	 * @param detachedCriteria
	 * @param startResult
	 * @param limitResult
	 * @return
	 */
	private List findByConditions(final StringBuffer hql,
			final int startResult, final int limitResult) {
		return (List) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				return session.createQuery(hql.toString()).setFirstResult(
						startResult).setMaxResults(limitResult).list();
			}
		}, true);
	}

	/**
	 * one page start record
	 * 
	 * @param detachedCriteria
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	private int startResult(StringBuffer otherHql, long pageNum, long pageSize) {
		Long recordCount = getRecordCount(otherHql);
		Long result = recordCount / pageSize * pageNum;
		return result.intValue();
	}

	/**
	 * 
	 * query by page 1.query data total lines；2.page number by page
	 * size；3.return page data
	 * 
	 * @param selectHql
	 * @param otherHql
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Set findByPage(StringBuffer selectHql, StringBuffer otherHql,
			int pageNum, int pageSize) {
		int startResult = startResult(otherHql, pageNum, pageSize);
		List result = findByConditions(selectHql.append(" ").append(otherHql),
				startResult, pageSize);
		return new HashSet(result);
	}

	/*
	 * @see org.supermy.core.service.IBaseService#save(org.supermy.core.domain.BaseDomain)
	 */
	@Override
	@Transactional(readOnly = false)
	public BaseDomain save(BaseDomain o) {
		if (o.isOld()) {
			o.setUpate(new Date());
		} else {
			o.setCreate(new Date());
			o.setUpate(new Date());
		}
		hibernateTemplate.saveOrUpdate(o);
		return o;
	}

	@Transactional(readOnly = false)
	public BaseDomain merge(BaseDomain o) {
		if (o.isOld()) {
			o.setUpate(new Date());
			o.setCreate(null);
		} else {
			o.setUpate(new Date());
			o.setCreate(new Date());
		}
		hibernateTemplate.merge(o);
		return o;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.supermy.core.service.IBaseService#delAll(org.supermy.core.domain.BaseDomain)
	 */
	@Override
	public void delAll(Class o) {
		hibernateTemplate.bulkUpdate(" delete from " + o.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.supermy.core.service.IBaseService#delete(org.supermy.core.domain.BaseDomain)
	 */
	@Override
	public void delete(Class o, long id) {
		hibernateTemplate.bulkUpdate(" delete from " + o.getName()
				+ " where id=", id);
	}

	@Override
	public HashSet loadAll(Class o) {
		List result = hibernateTemplate.loadAll(o);
		return new HashSet(result);
	}

	@Override
	public Object load(Class o, long id) {
		Object result = hibernateTemplate.load(o, id);
		return result;
	}

	public Set saveAll(Set lines) {
		hibernateTemplate.saveOrUpdateAll(lines);
		return lines;
	}

	@Override
	public void delSome(Set somes) {
		hibernateTemplate.deleteAll(somes);
	}

}
