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
import org.supermy.core.domain.Group;
import org.supermy.core.domain.GroupUser;
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
	private FastwebTemplate<Group, Long> groupUtil;
	private FastwebTemplate<GroupUser, Long> groupUserUtil;

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
		groupUtil = new FastwebTemplate<Group, Long>(sessionFactory, null,
				Group.class);
		groupUserUtil = new FastwebTemplate<GroupUser, Long>(sessionFactory,
				null, GroupUser.class);

		roleUtil = new FastwebTemplate<Role, Long>(sessionFactory, null,
				Role.class);

		authUtil = new FastwebTemplate<Authority, Long>(sessionFactory, null,
				Authority.class);

		urlResourceUtil = new FastwebTemplate<UrlResource, Long>(
				sessionFactory, null, UrlResource.class);
	}

	/**
	 * @return the userUtil
	 */
	public FastwebTemplate<User, Long> getUserUtil() {
		return userUtil;
	}

	public FastwebTemplate<Group, Long> getGroupUtil() {
		return groupUtil;
	}

	public FastwebTemplate<GroupUser, Long> getGroupUserUtil() {
		return groupUserUtil;
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

	// @Override
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
		// String QUERY_BY_RESOURCETYPE_WITH_AUTHORITY =
		// "from UrlResource r left join fetch r.authorityList WHERE r.resourceType=? ORDER BY r.position ASC"
		// ;
		String QUERY_BY_RESOURCETYPE_WITH_AUTHORITY = "from UrlResource r WHERE r.resourceType=? ORDER BY r.position ASC";
		Query query = urlResourceUtil.createQuery(
				QUERY_BY_RESOURCETYPE_WITH_AUTHORITY, UrlResource.URL_TYPE);
		//query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
		// ;
		HashSet set = new HashSet<UrlResource>(query.list());

		return set;

	}

	/**
	 * 保存数据
	 * 
	 * @param urlResource
	 * @param authorityListId
	 */
	public void saveUrlResource(UrlResource urlResource,
			List<Long> authorityListId) {
		getAuthorityUtil().mergeCollection(urlResource.getAuthorityList(),
				authorityListId);
		getUrlResourceUtil().save(urlResource);
	}

	/**
	 * 移动节点，修改父节点，修改是否叶子节点
	 * 
	 * @param id
	 * @param parentId
	 */
	public void moveTreeNode(String id, String parentId) {
		log.debug("node id:{}", id);
		log.debug("parent id:{}", parentId);

		Group parent;
		if ("root".equalsIgnoreCase(parentId)) {
			parent = getGroupUtil().findUniqueByProperty("type", "root");
		} else {
			parent = getGroupUtil().get(Long.parseLong(parentId));
		}
		//parent.setLeaf(false);ExtJS 不允许改变

		Group obj = getGroupUtil().get(Long.parseLong(id));
		// 原有的父节点，子节点全部被移除，是否成为还原为子节点 ExtJS 不允许改变
		Group sourceParent = obj.getParent();
		//if (sourceParent.getChildren().size() == 0) {
			//sourceParent.setLeaf(true);
		//}
		
		// 关联cache清除 
		sourceParent.getChildren().remove(obj);
		getGroupUtil().save(sourceParent);

		parent.getChildren().add(obj);
		getGroupUtil().save(parent);

		obj.setParent(parent);
		getGroupUtil().save(obj);
		
	}

}
