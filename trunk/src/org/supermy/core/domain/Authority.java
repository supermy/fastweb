package org.supermy.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

@Entity
@Table(name = "c_authors")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
 @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Authority extends BaseDomain {

	@NotEmpty
	@Length(min = 2)
	@Column(name = "c_name",unique = true, length = 20)
	private String name;

	@NotEmpty
	@Length(min = 2)
	@Column(name = "c_nickname", length = 20)
	private String nickName;

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
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
