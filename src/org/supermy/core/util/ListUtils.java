package org.supermy.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

/**
 * @author my
 */
public class ListUtils {
	private static org.slf4j.Logger log = LoggerFactory
			.getLogger(ListUtils.class);

	/**
	 * 提取集合中的对象的属性,组合成List.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertityName
	 *            要提取的属性名.
	 */
	public static List<Object> propertyToList(
			final Collection<Object> collection, final String propertyName)
			throws Exception {
		List<Object> list = new ArrayList<Object>();
		for (Object obj : collection) {
			list.add(PropertyUtils.getProperty(obj, propertyName));
		}
		return list;
	}

	public static List<Long> propertyToListLong(
			final Collection<Object> collection, final String propertyName)
			throws Exception {
		List<Long> list = new ArrayList<Long>();
		for (Object obj : collection) {
			list.add((Long) PropertyUtils.getProperty(obj, propertyName));
		}
		return list;
	}

	public static List<String> propertyToListString(
			final Collection<Object> collection, final String propertyName) {
		List<String> list = new ArrayList<String>();
		try {
			for (Object obj : collection) {
				list.add((String) PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new RuntimeException(e.getMessage());
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
	public static String propertyToString(final Collection<Object> collection,
			final String propertyName, final String separator) throws Exception {
		List list = propertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}
}
