package org.supermy.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.Length;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2008-7-30 下午04:30:38
 * 
 */
@Entity
@Table(name = "_address")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Address extends BaseDomain {

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "qq", length = 20)
	@Length(max = 20)
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

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
