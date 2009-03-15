package org.supermy.core.service;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class FastwebTemplate<T, IdT extends Serializable> extends
		BaseTemplate<T, IdT> {

	public FastwebTemplate(SessionFactory sessionFactory, Class<T> domainClass) {
		super(sessionFactory, domainClass);
	}

	// //////////////////////////////////////

	/**
	 * 按HQL分页查询. 不支持自动获取总结果数,需用户另行执行查询.
	 * 
	 * @return 分页查询结果,附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
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
					orderHql.append(" obj ").append(".")
							.append(orderByArray[i]).append(" asc ");
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
			int totalCount = getRecordCount(otherHql);
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);
		List result = q.list();
		page.setResult(result);
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
	 * @return
	 */
	private Integer getRecordCount(String otherHql) {
		StringBuffer hql = new StringBuffer(" select count(*) ")
				.append(otherHql);
		return (Integer) findUnique(hql.toString());
	}

}
