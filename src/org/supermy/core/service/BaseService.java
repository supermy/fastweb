package org.supermy.core.service;

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

//JdbcDaoSupport

//@Transactional 
//extends HibernateDaoSupport
public class BaseService implements IBaseService {

	// @Autowired
	// public SessionFactory sessionFactory;

	@Autowired
	public HibernateTemplate hibernateTemplate;

	// 分页查询 1.查询数据总量；2.根据页面尺寸计算页面数；3.返回说要查询页面的数据
	// 获取记录总数
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

	// 分页查找
	public Set findByPage(final DetachedCriteria detachedCriteria,
			final int pageNum, final int pageSize) {
		int startResult = startResult(detachedCriteria, pageNum, pageSize);
		List result = findByConditions(detachedCriteria, startResult, pageSize);
		return new HashSet(result);
	}

	/**
	 * 获取记录总数
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	private Long getRecordCount(StringBuffer otherHql) {
		StringBuffer hql = new StringBuffer(" select count(*) ").append(otherHql);
		Long result = new Long(hibernateTemplate.find(hql.toString()).get(0)
				.toString());
		return result;
	}

	/**
	 * 查询指定范围的数据
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
	 * 计算开始记录
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
	 * 分页查找 分页查询 1.查询数据总量；2.根据页面尺寸计算页面数；3.返回说要查询页面的数据
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

	/* 内部测试专用
	 * @see org.supermy.core.service.IBaseService#save(org.supermy.core.domain.BaseDomain)
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BaseDomain o) {
		hibernateTemplate.saveOrUpdate(o);
	}

}
