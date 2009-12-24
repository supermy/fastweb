package org.supermy.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2009-7-28 上午11:27:58
 * 
 *          政：部门，科室，岗位 <br/>
 *          党：机关党支部，教学管理党支部
 *			工会：
 *			团委： 
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Table(name = "c_group_types")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @Proxy(lazy = false)
public class GroupType extends BaseDomain {

	@NotEmpty
	@Length(min = 2)
	@Column(name = "name_", unique = true, length = 220)
	private String name;

	@Column(name = "intro_",length=250)
	private String intro;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private CompanyType type;

	/**
	 * @return the type
	 */
	public CompanyType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(CompanyType type) {
		this.type = type;
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

}
