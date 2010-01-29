package org.supermy.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.cfg.annotations.Comment;
import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.supermy.core.util.MD5;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2009-7-30 下午04:30:58 <br>
 *          用户m2o->（user:角色:group）<-m2o组织机构<br>
 *          角色 角色可以给个人(个人和岗位关联)、也可以给岗位(岗位直接和组织机构关联) <br>
 *          管理员-所有的权限 <br>
 *          功能管理员-功能模块的所有权限 (某个功能模块的阅读、删除、编辑、审查等权限) <br>
 *          用户-自己相关的所有权限<br>
 *          游客-所有的阅读权限<br>
 */
@Comment("用户")
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Table(name = "c_users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @Proxy(lazy = false)
public class User extends BaseDomain implements org.jbpm.api.identity.User {
	@Field
	@Comment("用户名")
	@NotEmpty
	@Index(name = "i_name_pwd", columnNames = { "name_", "passwd_" })
	@Length(min = 2)
	@Column(name = "name_", nullable = false, unique = true, length = 80)
	private String name;

	@Comment("口令")
	@NotEmpty
	@Length(min = 2)
	@Column(name = "passwd_", nullable = false, length = 32)
	private String passwd;

	/**
	 * 口令二次验证使用
	 */
	@Transient
	private String passwd2;

	@Comment("账户没有过期")
	@Column(name = "accountNonExpired")
	private boolean accountNonExpired = true;
	@Comment("账户信任")
	@Column(name = "credentialsNonExpired")
	private boolean credentialsNonExpired = true;
	@Comment("账户未被锁定")
	@Column(name = "accountNonLocked")
	private boolean accountNonLocked = true;

	@Field("email_t")
	@Comment(value = "Email", desc = "使用Email作为用户的ID,方便用户，获取有效用户")
	@Email
	@NotEmpty
	@Index(name = "i_email_pwd", columnNames = { "email", "passwd_" })
	@Column(name = "email", nullable = false, unique = true, length = 80)
	private String email;

	@Field("intro_t")
	// 动态字段 *_t ,解决domain属性的全文检索在schema中需要定义的问题
	@Comment(value = "简介", desc = "个人简单介绍")
	//@Lob
	@Column(name = "intro_")
	private String intro;

	@Field("salary_d")
	@Comment("薪水")
	@Column(name = "salary_", precision = 2)
	private Double salary;// 薪水 两位小数

	// @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	// @JoinTable(name = "c_user_role", joinColumns = { @JoinColumn(name =
	// "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	// @Fetch(FetchMode.SUBSELECT)
	// // @Fetch(FetchMode.JOIN)
	// @LazyCollection(LazyCollectionOption.FALSE)
	// @OrderBy("create")
	// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	// private Set<Role> roles = new LinkedHashSet<Role>();

	// public String getRolesName() throws Exception {
	// return ListUtils.propertyToString(new ArrayList<Object>(roles), "name",
	// ", ");
	// }

	// public List<String> getRoleNameList() {
	// return ListUtils.propertyToListString(new ArrayList(roles), "name");
	// }

	// public List<Long> getRolesId() {
	// return ListUtils.propertyToListLong(new ArrayList(roles), "id");
	// }

	/**
	 * 
	 * @return
	 */
	public boolean passwordCheck() {
		return passwd.equals(passwd2);
	}

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
	 * @return the salary
	 */
	public Double getSalary() {
		return salary;
	}

	/**
	 * @param salary
	 *            the salary to set
	 */
	public void setSalary(Double salary) {
		this.salary = salary;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * //123456//e10adc3949ba59abbe56e057f20f883e
	 * 
	 * @param passwd
	 *            the passwd to set
	 */
	public void setPasswd(String passwd) {
		if (passwd.length() < 32) {
			this.passwd = MD5.getMd5(passwd);
		} else
			this.passwd = passwd;
	}

	/**
	 * @return the passwd2
	 */
	public String getPasswd2() {
		return passwd2;

	}

	/**
	 * @param passwd2
	 *            the passwd2 to set
	 */
	public void setPasswd2(String passwd2) {
		// this.passwd2 = MD5.getMd5(passwd);
		if (passwd2.length() < 32) {
			this.passwd2 = MD5.getMd5(passwd2);
		} else
			this.passwd2 = passwd2;
	}

	// /**
	// * @return the roles
	// */
	// public Set<Role> getRoles() {
	// return roles;
	// }
	//
	// /**
	// * @param roles
	// * the roles to set
	// */
	// public void setRoles(Set<Role> roles) {
	// this.roles = roles;
	// }

	/**
	 * @return the accountNonExpired
	 */
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * @param accountNonExpired
	 *            the accountNonExpired to set
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * @return the credentialsNonExpired
	 */
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * @param credentialsNonExpired
	 *            the credentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * @return the accountNonLocked
	 */
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * @param accountNonLocked
	 *            the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	
	@Override
	public String getBusinessEmail() {
		return email;
	}

	@Override
	public String getFamilyName() {
		return name;
	}

	@Override
	public String getGivenName() {
		return email;
	}

	@Override
	public String getSId() {
		return getId().toString();
	}

}
