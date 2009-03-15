package org.supermy.core.util;

/**
 * 利用正则表达式分解 与组装
 * 
 * @author my
 * 
 */
public class HqlUtil {
	String hql1 = " select order.id, sum(price.amount), count(item)"
			+ " from Order as order" + "    join order.lineItems as item"
			+ "    join item.product as product," + "    Catalog as catalog"
			+ "    join catalog.prices as price"  +
					"where order.paid = false"
			+ "    and order.customer = :customer"
			+ "    and price.product = product"
			+ "   and catalog.effectiveDate < sysdate"
			+ "    and catalog.effectiveDate >= all ("
			+ "        select cat.effectiveDate"
			+ "        from Catalog as cat"
			+ "        where cat.effectiveDate < sysdate" + "    )"
			+ " group by order" + " having sum(price.amount) > :minAmount"
			+ " order by sum(price.amount) desc";
	//注意where嵌套问题
}
