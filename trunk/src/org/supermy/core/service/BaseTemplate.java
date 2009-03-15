package org.supermy.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.FlushModeType;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.BaseDomain;

/**
 * @author my
 * 
 * @param <T>
 * @param <IdT>
 */
@Transactional
public class BaseTemplate<T extends BaseDomain, IdT extends Serializable> {

	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;

	protected Class<T> domainClass;

	public BaseTemplate(final SessionFactory sessionFactory,
			final Class<T> domainClass) {
		this.domainClass = domainClass;
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 保存新增或修改的对象.
	 */
	public void save(final T obj) {
		getSession().saveOrUpdate(obj);
		log.debug("save:{}" + obj);
	}

	public void save(final List<T> objs) {
		for (T t : objs) {
			getSession().saveOrUpdate(t);
		}
		log.debug("save:{}", objs);
	}

	/**
	 * @param entity
	 */
	public void delete(final T obj) {
		getSession().delete(obj);
		log.debug("delete :{}", obj);
	}

	public void delete(final List<T> list) {

		if (list.size() <= 0) {
			return;
		}
		List<Long> ids = new ArrayList<Long>();// FIXME
		for (T t : list) {
			ids.add(t.getId());
		}

		StringBuffer hql = new StringBuffer(" delete ").append(
				domainClass.getName()).append(" where id in (:ids) ");
		log.debug("hql:{}", hql);
		Query q = createQuery(hql.toString());
		q.setParameterList("ids", ids);
		int n = q.executeUpdate();
		log.debug("delete:{}", n);

		//getSession().flush();
	}

	public void deleteAll() {
		String hql = " delete " + domainClass.getName();
		log.debug("hql:{}", hql);
		Query q = createQuery(hql);
		int n = q.executeUpdate();
		log.debug("delete:{}", n);
		getSession().setFlushMode(FlushMode.COMMIT);
		getSession().flush();
	}

	/**
	 * 按id删除对象.
	 */
	public void delete(final IdT id) {
		delete(get(id));
		log.debug("delete:" + id);
	}

	/**
	 * 按id获取对象.
	 */
	public T get(final IdT id) {
		return (T) getSession().load(domainClass, id);
	}

	/**
	 * 创建查询 类型固定
	 * 
	 * @param criterions
	 * @return
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(domainClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 查询
	 * 
	 * @param criterions
	 * @return
	 */
	public List<T> findByCriteria(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	public List<T> findByProperty(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return findByCriteria(criterion);
	}

	public T findUniqueByProperty(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	// //////////////////////////////////////
	/**
	 * 根据查询HQL与参数列表创建Query对象. 类型不固定
	 * 
	 * @param queryString
	 * @param values
	 * @return
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public List<T> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询唯一对象.
	 */
	public Object findUnique(final String hql, final Object... values) {
		return createQuery(hql, values).uniqueResult();
	}

	public Query createSqlQuery(final String queryString,
			final Object... values) {
		Query query = getSession().createSQLQuery(queryString);

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 单表查询返回对象 建议直接使用hibernate
	 * 
	 * @param queryString
	 * @param values
	 * @return
	 */
	public Query createSqlQuery4Class(final String queryString,
			final Object... values) {
		Query query = getSession().createSQLQuery(queryString).addEntity(
				domainClass);

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 返回一个map对象 <br>
	 * 
	 * Map map = (Map)list.get[i];<br>
	 * map.get("id");map.get("name");<br>
	 * 
	 * 来取值。按你的SQL语句select后的字段名来作为map的Key， 但这个key必须与数据库中的字段名一模一样。
	 * 
	 * @param queryString
	 * @param values
	 * @return
	 */
	public Query createSqlQuery4Map(final String queryString,
			final Object... values) {
		Query query = getSession().createSQLQuery(queryString).addScalar(
				getIdName(), getIdType()).addEntity(TreeMap.class);
		// .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * @param queryString
	 * @param values
	 * @return
	 */
	public Query createSqlQuery4ListMap(final String queryString,
			final Object... values) {
		Query query = getSession().createSQLQuery(queryString)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public List<T> findBySQL(final String sql, final Object... values) {
		return createSqlQuery(sql, values).list();
	}

	public List<Map> findBySQL4ListMap(final String sql, final Object... values) {
		return createSqlQuery4ListMap(sql, values).list();
	}

	public Map<IdT, Map> findBySQL4Map(final String sql, final Object... values) {
		return (Map<IdT, Map>) createSqlQuery4Map(sql, values).list().get(0);
	}

	/**
	 * 取得对象的主键名.
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(domainClass);
		return meta.getIdentifierPropertyName();
	}

	public Type getIdType() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(domainClass);
		return meta.getIdentifierType();
	}

	public String getDomainName() {
		return domainClass.getSimpleName();
	}

}
