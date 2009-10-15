package org.supermy.core.domain;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.cfg.annotations.Comment;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.supermy.core.util.ListUtils;

/**
 * @author my
 *
 */
@Comment("角色")
@Entity
@Table(name = "c_roles")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends BaseDomain {

	@Comment("角色名称")
	@NotNull
	@Length(max = 20)
	@Index(name="i_name")
	@Column(name = "name_",nullable=false,unique = true, length = 20)
	private String name;
	
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "c_role_authority", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "authority_id") })
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)  	
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Authority> auths = new LinkedHashSet<Authority>();

	public String getAuthsName() throws Exception {
		return ListUtils.propertyToString(new ArrayList<Object>(auths), "nickName", ", ");
	}

	public List<Long> getAuthsId() {
		return ListUtils.propertyToListLong(new ArrayList(auths), "id");
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
