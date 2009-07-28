package org.supermy.workflow.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.NotEmpty;
import org.supermy.core.domain.BaseDomain;

/**
 * @author my
 * 
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Table(name = "w_orig_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@Proxy(lazy = false)
public class OrigItem extends BaseDomain {

	// Fields
	@ManyToOne
	@JoinColumn(name = "orig_id")
	private OriginalCertificate origCert;
	@NotEmpty
	@Column(name = "name", length = 180)
	private String name;
	@Column
	private Double money = 0.00;

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
	 * @return the money
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(Double money) {
		this.money = money;
	}

	// Constructors

}