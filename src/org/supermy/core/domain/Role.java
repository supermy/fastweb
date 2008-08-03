package org.supermy.core.domain;


import javax.persistence.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;
//admin and common
@Entity
@Table(name = "c_role")
@org.hibernate.annotations.Entity(dynamicUpdate = true,dynamicInsert = true)
public class Role extends BaseDomain {

	@Column(name = "name", length = 20)
	@Length(max = 20)
	private String name;
	/**
	 * ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE}) 
	 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Transient
	private String[] roles;
	/**
	 * get the value of roles
	 * @return the value of roles
	 */
	public String[] getRoles(){
		return this.roles;
	}
	/**
	 * set a new value to roles
	 * @param roles the new value to be used
	 */
	public void setRoles(String[] roles) {
		this.roles=roles;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	

}
