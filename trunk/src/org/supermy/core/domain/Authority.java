package org.supermy.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.cfg.annotations.Comment;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

@Comment("权限")
@Entity
@Table(name = "c_authors")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
 @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@Proxy(lazy = false)
public class Authority extends BaseDomain {

	@Comment("权限名称")
	@NotEmpty
	@Length(max = 50)
	@Index(name="i_name")
	@Column(name = "name_",nullable=false,unique = true, length = 50)
	private String name;

	@Comment("长名称")
	@NotEmpty
	@Length(max = 80)
	@Column(name = "nickname_", nullable=false,length = 80)
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
