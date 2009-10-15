package org.supermy.core.service;

import java.util.HashSet;
import java.util.List;

import javax.jws.WebService;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.Authority;
import org.supermy.core.domain.Role;
import org.supermy.core.domain.UrlResource;
import org.supermy.core.domain.User;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2008-7-30 下午05:17:27
 * 
 */
@Transactional
@Service
@WebService
public class UserService extends BaseService implements IUserService {

	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	private FastwebTemplate<User, Long> userUtil;

	private FastwebTemplate<Role, Long> roleUtil;

	private FastwebTemplate<Authority, Long> authUtil;

	private FastwebTemplate<UrlResource, Long> urlResourceUtil;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		// Session openSession = sessionFactory.openSession();//
		// 保证是同一个session,才能在相互调用方法。
		// Session openSession = sessionFactory.getCurrentSession();

		userUtil = new FastwebTemplate<User, Long>(sessionFactory, null,
				User.class);

		roleUtil = new FastwebTemplate<Role, Long>(sessionFactory, null,
				Role.class);

		authUtil = new FastwebTemplate<Authority, Long>(sessionFactory, null,
				Authority.class);

		urlResourceUtil = new FastwebTemplate<UrlResource, Long>(sessionFactory, null,
				UrlResource.class);
	}

	/**
	 * @return the userUtil
	 */
	public FastwebTemplate<User, Long> getUserUtil() {
		return userUtil;
	}

	/**
	 * @return the roleUtil
	 */
	public FastwebTemplate<Role, Long> getRoleUtil() {
		return roleUtil;
	}

	/**
	 * @return the authUtil
	 */
	public FastwebTemplate<Authority, Long> getAuthorityUtil() {
		return authUtil;
	}

	public FastwebTemplate<UrlResource, Long> getUrlResourceUtil() {
		return urlResourceUtil;
	}

	//@Override
	@Transactional(readOnly = true)
	public boolean isUniqueByEMail(String email) {
		Long count = (Long) getUserUtil().findUnique(
				"select count(obj.id) from " + User.class.getName()
						+ " obj where obj.email=?", email);
		return count.intValue() <= 0;
	}

	/**
	 * 查找URL类型的资源并初始化可访问该资源的授权.
	 */
	@Transactional(readOnly = true)
	public HashSet<UrlResource> getUrlResourceWithAuthorities() {
		//String QUERY_BY_RESOURCETYPE_WITH_AUTHORITY = "from UrlResource r left join fetch r.authorityList WHERE r.resourceType=? ORDER BY r.position ASC";
		String QUERY_BY_RESOURCETYPE_WITH_AUTHORITY = "from UrlResource r WHERE r.resourceType=? ORDER BY r.position ASC";
		Query query =urlResourceUtil.createQuery(QUERY_BY_RESOURCETYPE_WITH_AUTHORITY, UrlResource.URL_TYPE);
		//query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		HashSet set = new HashSet<UrlResource>(query.list());
		
		return set;

	}

	/**
	 * 保存数据
	 * @param urlResource
	 * @param authorityListId
	 */
	public void saveUrlResource(UrlResource urlResource,List<Long> authorityListId) {
		getAuthorityUtil().mergeCollection(urlResource.getAuthorityList(),authorityListId);
		getUrlResourceUtil().save(urlResource);
	}
	
}
