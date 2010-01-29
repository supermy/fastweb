package org.supermy.core.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.cfg.annotations.Comment;
import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2009-7-28 上午11:27:58
 * 
 *          父子结构递归循环； 编码对象->树结构（Struts2 tree标签，Flex 组织机构图）
 */
@Comment("组")
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Table(name = "c_groups")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @Proxy(lazy = false)
public class Group extends BaseDomain implements org.jbpm.api.identity.Group {

	@Field(value = "name_t")
	@Comment("组名")
	@NotEmpty
	@Length(min = 2, max = 80)
	@Index(name = "name_i")
	@Column(name = "name_", unique = false, length = 80)
	private String name;

	/**
	 * 01010101 (高层 部门 科室 岗位)
	 */
	@Field(value = "code_t")
	@Comment("组代码")
	@Length(min = 2, max = 80)
	@Index(name = "code_i")
	@Column(name = "code_", length = 20)
	private String code;

	@Field(value = "email_t")
	@Comment("EMail")
	@Email
	@NotEmpty
	@Length(max = 80)
	@Index(name = "email_i")
	@Column(name = "email_", unique = false, length = 80)
	private String email;

	@Comment("叶子")
	@Index(name = "leaf_i")
	@Column(name = "leaf_")
	private boolean leaf;

	/**
	 * 分类
	 */
	@Field(value = "type_t")
	@Comment("类型")
	@NotEmpty
	@Length(max = 80)
	@Index(name = "type_i")
	@Column(name = "type_", unique = false, length = 80)
	private String type;

	@Field(value = "intro_t")
	@Comment("介绍")
	@Length(max = 250)
	@Column(name = "intro_")
	private String intro;

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "company_id")
	// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	// private Company company;

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "type_id")
	// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	// private GroupType groupType;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Group parent;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "parent_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)//FIXME 缓存清除的时机   obj 变更的时候，关联的cache清除
	private Set<Group> children;

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the name
	 */

	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the intro
	 */
	public String getIntro() {
		return intro;
	}

	/**
	 * @param intro
	 *            the intro to set
	 */
	public void setIntro(String intro) {
		this.intro = intro;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the parent
	 */
	public Group getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(Group parent) {
		this.parent = parent;
	}

	// /**
	// * @return the company
	// */
	// public Company getCompany() {
	// return company;
	// }
	//
	// /**
	// * @param company
	// * the company to set
	// */
	// public void setCompany(Company company) {
	// this.company = company;
	// }

	// /**
	// * @return the type
	// */
	// public GroupType getGroupType() {
	// return groupType;
	// }
	//
	// /**
	// * @param type
	// * the type to set
	// */
	// public void setGroupType(GroupType type) {
	// this.groupType = type;
	// }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	public Set<Group> getChildren() {
		return children;
	}

	public void setChildren(Set<Group> children) {
		this.children = children;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	@Override
	public String getSId() {
		return getId().toString();
	}

	/**
	 * 
	 * ExtJS node
	 * 
	 * @return
	 */
	public String getCls() {
		return isLeaf() ? "file" : "folder";
	}

	public List<Map<String,Object>> treeNode() {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for (Group group : getChildren()) {
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("id", group.getId());
			obj.put("text", group.getName());
			if (group.isLeaf()) {
				obj.put("leaf", group.isLeaf());
			}
			obj.put("cls", group.getCls());
			result.add(obj);
		}
		return result;
	}
}
