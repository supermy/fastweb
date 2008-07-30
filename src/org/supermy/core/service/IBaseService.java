package org.supermy.core.service;

import java.util.HashSet;
import java.util.Set;

import org.supermy.core.domain.BaseDomain;

/**
* @author supermy E-mail:springclick@gmail.com
* @version create time：2008-7-30 下午04:53:06
* 
*/
public interface IBaseService {
	/**
	 * @param o
	 * @param id
	 */
	public Object load(Class o,long id);
	/**
	 * @param o
	 */
	public HashSet loadAll(Class o);
	/**
	 * @param o
	 */
	public BaseDomain save(BaseDomain o);
	public Set saveAll(Set users) ;
	/**
	 * @param o
	 */
	public void delete(Class o,long id);
	/**
	 * @param o
	 */
	public void delAll(Class o);
}