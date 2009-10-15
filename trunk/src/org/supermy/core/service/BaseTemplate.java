package org.supermy.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.supermy.core.domain.BaseDomain;

/**
 * @author tiger
 * 
 * @param <T>
 * @param <IdT>
 */
public class BaseTemplate<T extends BaseDomain, IdT extends Serializable> {

	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;

	protected Session session;

	protected Class<T> domainClass;

	public BaseTemplate(final SessionFactory sessionFactory,
			final Session session, final Class<T> domainClass) {
		this.domainClass = domainClass;
		this.sessionFactory = sessionFactory;
		// this.session = sessionFactory.openSession();
		// this.session = session;
	}

	public Session getSession() {
		// return this.session;
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
		log.debug("before save:{}" + obj);
		getSession().saveOrUpdate(obj);
		getSession().flush();// oracle must required
		log.debug("after save:{}" + obj);
	}

	public void save(final List<T> objs) {
		for (T t : objs) {
			save(t);
		}
		log.debug("save:{}", objs);
	}

	/**
	 * @param entity
	 */
	public void delete(final T obj) {
		getSession().delete(obj);
		getSession().flush();
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

		// getSession().flush();
	}

	public void deleteAll() {
		String hql = " delete " + domainClass.getName();
		log.debug("hql:{}", hql);
		Query q = createQuery(hql);
		int n = q.executeUpdate();
		log.debug("delete:{}", n);
		Session session = getSession();
		session.setFlushMode(FlushMode.COMMIT);
		session.flush();
	}

	/**
	 * 按id删除对象.
	 */

	public void delete(final IdT id) {
		// T obj = (T) getSession().load(domainClass, id);
		// getSession().delete(obj);
		delete(get(id));
		// getSession().flush();
		log.debug("delete:" + id);
	}

	/**
	 * 按id获取对象.
	 */
	@Transactional(readOnly = true)
	public T get(final IdT id) {
		return (T) getSession().load(domainClass, id);
	}

	/**
	 * 
	 * 按类名和ID获取对象
	 * 
	 * @param className
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public Object get(final String className,final IdT id) {
		return  getSession().load(className, id);
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

		criteria.setCacheable(true);

		return criteria;
	}

	/**
	 * 查询
	 * 
	 * @param criterions
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findByCriteria(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	@Transactional(readOnly = true)
	public List<T> findByProperty(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return findByCriteria(criterion);
	}

	@Transactional(readOnly = true)
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

		query.setCacheable(true);

		return query;
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询唯一对象.
	 */
	@Transactional(readOnly = true)
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

		query.setCacheable(true);

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

		query.setCacheable(true);

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

		query.setCacheable(true);

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

		query.setCacheable(true);

		return query;
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findBySQL(final String sql, final Object... values) {
		return createSqlQuery(sql, values).list();
	}

	@Transactional(readOnly = true)
	public List<Map> findBySQL4ListMap(final String sql, final Object... values) {
		return createSqlQuery4ListMap(sql, values).list();
	}

	@Transactional(readOnly = true)
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

	/**
	 * 提取集合中的对象的属性,组合成List.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertityName
	 *            要提取的属性名.
	 */
	public List<Object> propertyToList(final Collection<T> collection,
			final String propertyName) throws Exception {
		List<Object> list = new ArrayList<Object>();
		for (T obj : collection) {
			list.add(PropertyUtils.getProperty(obj, propertyName));
		}
		return list;
	}

	public List<Long> propertyToListLong(final Collection<T> collection,
			final String propertyName) throws Exception {
		List<Long> list = new ArrayList<Long>();
		for (T obj : collection) {
			list.add((Long) PropertyUtils.getProperty(obj, propertyName));
		}
		return list;
	}

	public List<String> propertyToListString(final Collection<T> collection,
			final String propertyName) throws Exception {
		List<String> list = new ArrayList<String>();
		for (T obj : collection) {
			list.add((String) PropertyUtils.getProperty(obj, propertyName));
		}
		return list;
	}

	/**
	 * 提取集合中的对象的属性,组合成由分割符分隔的字符串.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertityName
	 *            要提取的属性名.
	 * @param separator
	 *            分隔符.
	 */
	@SuppressWarnings("unchecked")
	public String propertyToString(final Collection<T> collection,
			final String propertyName, final String separator) throws Exception {
		List list = propertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	public void mergeCollection(final Collection<T> srcObjects,
			final Collection<IdT> checkedIds) {

		// 参数校验
		Assert.notNull(srcObjects);
		// Assert.hasText(idName);
		String idName = "id";

		// 目标ID集合为空,删除源集合中所有对象后直接返回.
		if (checkedIds == null) {
			srcObjects.clear();
			return;
		}

		// 遍历源集合,如果其id不在目标ID集合中的对象,进行删除.
		// 同时,在目标ID集合中删除已在源集合中的id,使得目标ID集合中剩下的id均为源集合中没有的ID.
		Iterator<T> srcIterator = srcObjects.iterator();

		try {
			while (srcIterator.hasNext()) {
				T element = srcIterator.next();
				Object id = PropertyUtils.getProperty(element, idName);
				if (!checkedIds.contains(id)) {
					srcIterator.remove();
				} else {
					checkedIds.remove(id);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		// ID集合目前剩余的id均不在源集合中,创建对象,为id属性赋值并添加到源集合中.
		try {
			for (IdT id : checkedIds) {
				T obj = domainClass.newInstance();
				PropertyUtils.setProperty(obj, idName, id);
				srcObjects.add(obj);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
