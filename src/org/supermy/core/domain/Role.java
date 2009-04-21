package org.supermy.core.domain;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.supermy.core.util.ListUtils;

/**
 * @author my
 *
 */
@Entity
@Table(name = "c_roles")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy=false)
public class Role extends BaseDomain {

	@NotNull
	@Column(name = "name",unique = true, length = 20)
	@Length(max = 20)
	private String name;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.EAGER)
	@JoinTable(name = "c_role_auths", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "authority_id") })
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Authority> auths = new LinkedHashSet<Authority>();

	public String getAuthNames() throws Exception {
		return ListUtils.propertyToString(new ArrayList<Object>(auths), "nickName", ", ");
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
	 * @return the auths
	 */
	public Set<Authority> getAuths() {
		return auths;
	}

	/**
	 * @param auths the auths to set
	 */
	public void setAuths(Set<Authority> auths) {
		this.auths = auths;
	}

	

}
