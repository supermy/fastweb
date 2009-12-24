package com.gogo.comix.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.cfg.annotations.Comment;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.supermy.core.domain.BaseDomain;


@Entity
@Table(name = "my_tiger")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// @Proxy(lazy=false)
@Comment(value = "老虎", desc = "测试使用")
public class MyTiger extends BaseDomain {

	@Comment("审核日期")
	@Index(name = "i_audit")
	@Column(name = "audit")
	private Date audit;

	@Comment("我的老虎")
	@Length(max = 200)
	@Index(name = "i_my_tiger")
	@Column(name = "my_tiger", length = 100)
	private String myTiger;

	@Comment("我的信箱")
	@Length(max = 100)
	// @Email
	@NotEmpty
	@Index(name = "i_my_email")
	@Column(name = "my_email", nullable = false)
	private String myEmail;

	public String getMyTiger() {
		return this.myTiger;
	}

	public void setMyTiger(String myTiger) {
		this.myTiger = myTiger;
	}

	/**
	 * @return the myEmail
	 */
	public String getMyEmail() {
		return myEmail;
	}

	/**
	 * @param myEmail
	 *            the myEmail to set
	 */
	public void setMyEmail(String myEmail) {
		this.myEmail = myEmail;
	}

	public Date getAudit() {
		return audit;
	}

	public void setAudit(Date audit) {
		this.audit = audit;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("id").append("='").append(getId()).append("', ");
		sb.append("create").append("='").append(getCreate()).append("', ");
		sb.append("myTiger").append("='").append(getMyTiger()).append("'");
		sb.append("myEmail").append("='").append(getMyEmail()).append("'");
		sb.append("]");

		return sb.toString();
	}

}
