package org.supermy.workflow.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.NotEmpty;
import org.supermy.core.domain.BaseDomain;
import org.supermy.core.domain.User;

@Entity
@Table(name = "w_orig_appr")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@Proxy(lazy = false)
public class OrigApprove extends BaseDomain {

	/**
	 * 审核人
	 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "orig_id")
	private OriginalCertificate origCert;
	/**
	 * 审核结果
	 */
	@NotEmpty
	@Column(name = "result_", length = 180)
	private String result;
	/**
	 * 审核意见
	 */
	@Lob
	@Column(name = "remark_")
	private String remark;
	/**
	 * 审核日期
	 */
	@Column(name="start_")
	private Date start;

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

	/**
	 * @return the origCert
	 */
	public OriginalCertificate getOrigCert() {
		return origCert;
	}

	/**
	 * @param origCert
	 *            the origCert to set
	 */
	public void setOrigCert(OriginalCertificate origCert) {
		this.origCert = origCert;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

}