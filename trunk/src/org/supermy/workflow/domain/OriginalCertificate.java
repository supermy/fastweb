package org.supermy.workflow.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.NotEmpty;
import org.supermy.core.domain.BaseDomain;
import org.supermy.core.domain.User;

/**
 * 报销审批单
 * 
 * @author my
 * 
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Table(name = "w_orig_cert")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class OriginalCertificate extends BaseDomain {

	// Fields
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@NotEmpty
	@Column(name = "title", length = 180)
	private String title;
	@Lob
	@Column(name = "remark")
	private String remark;
	@Column
	private Date start;
	@Column
	private Boolean done=false;

	/**
	 * 原始凭证明细表,数据量小
	 */
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "orig_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<OrigItem> items = new HashSet<OrigItem>(0);

	/**
	 * 审批意见表，数据量小
	 * 
	 */
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "orig_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<OrigItem> approvals = new HashSet<OrigItem>(0);

	// Constructors
	/** default constructor */
	public OriginalCertificate() {
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

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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

	/**
	 * @return the done
	 */
	public Boolean getDone() {
		return done;
	}

	/**
	 * @param done
	 *            the done to set
	 */
	public void setDone(Boolean done) {
		this.done = done;
	}

	/**
	 * @return the items
	 */
	public Set<OrigItem> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(Set<OrigItem> items) {
		this.items = items;
	}

	/**
	 * @return the approvals
	 */
	public Set<OrigItem> getApprovals() {
		return approvals;
	}

	/**
	 * @param approvals the approvals to set
	 */
	public void setApprovals(Set<OrigItem> approvals) {
		this.approvals = approvals;
	}


}