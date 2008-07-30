package org.supermy.core.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.Length;

@Entity
@Table(name = "c_address")
@org.hibernate.annotations.Entity(dynamicUpdate = true,dynamicInsert = true)
public class Address extends BaseDomain {

	@Column(name = "qq", length = 20)
	@Length(max = 20, message = "{QQ号的长度最大20个字符}")
	// 一般情况下使用默认提示。
	private String qq;
	@Column(name = "phone", length = 20)
	@Length(max = 20)
	private String phone;
	@Column(name = "msn", length = 20)
	@Length(max = 20)
	private String msn;

	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq
	 *            the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the msn
	 */
	public String getMsn() {
		return msn;
	}

	/**
	 * @param msn
	 *            the msn to set
	 */
	public void setMsn(String msn) {
		this.msn = msn;
	}

}
