package org.supermy.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.Email;

/**
* @author supermy E-mail:springclick@gmail.com
* @version 创建时间：2008-7-23 下午01:06:00
* 类说明
*/
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true,dynamicInsert=true)
@Table(name = "c_user")
public class User extends BaseDomain {

	@NotEmpty
	@Length(min = 2)
	@Column(name = "c_name", length = 20)
	private String name;

	@NotEmpty
	@Length(min = 2)
	@Column(name = "c_passwd", length = 20)
	private String passwd;

	/**
	 * 	口令二次验证使用
	 */
	@Transient
	private String passwd2;

	/**
	 * @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE}) 
	 */
	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;

	// @Transient // form提交时，便于获取关联ID
	// private String address_id;

	@Lob
	@Column(name = "u_intro")
	private String intro;
	@Column(name = "u_salary", precision = 2)
	private Double salary;// 薪水 两位小数

	@Email
	@NotEmpty
	@Column(name = "email", length = 20)
	private String email;

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
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
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	// /**
	// * @return the address_id
	// */
	// public String getAddress_id() {
	// return address_id;
	// }
	//
	// /**
	// * @param address_id
	// * the address_id to set
	// */
	// public void setAddress_id(String address_id) {
	// this.address_id = address_id;
	// }

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
	 * @param passwd the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * @return the passwd2
	 */
	public String getPasswd2() {
		return passwd2;
	}

	/**
	 * @param passwd2 the passwd2 to set
	 */
	public void setPasswd2(String passwd2) {
		this.passwd2 = passwd2;
	}

	/**
	 * 口令验证
	 * @return
	 */
	public boolean passwordCheck() {
		return passwd.equals(passwd2);
	}

}
