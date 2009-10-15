package org.supermy.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2009-7-28 上午11:27:58
 * 
 * 公司类型：党、政、工、青、团
 * 
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Table(name = "c_company_types")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@Proxy(lazy = false)
public class CompanyType extends BaseDomain {

	@NotEmpty
	@Length(min = 2)
	@Column(name = "name_", unique = true, length = 20)
	private String name;


	@Column(name = "intro_",length=250)
	private String intro;


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
