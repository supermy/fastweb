package org.supermy.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.cfg.annotations.Comment;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

/**
 * 
 * 某个用户以某种角色加入某个组
 * 
 * @author 莫勇
 * 
 */
@Comment("用户组")
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Table(name = "c_group_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @Proxy(lazy = false)
public class GroupUser extends BaseDomain {

	@Comment("角色")
	@NotEmpty
	@Length(min = 2, max = 80)
	@Column(name = "name_", unique = true, length = 80)
	private String name;

	@Comment("职位说明")
	@Length(max = 250)
	@Column(name = "intro_")
	private String intro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Group group;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
