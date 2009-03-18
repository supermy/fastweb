package org.supermy.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;


@MappedSuperclass
public class BaseDomain {

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
//		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
//				this.id).toString();
	}

	public boolean equals(BaseDomain o) {
		if (this == o) {
			return true;
		}
		if (o.getClass().getName().equals(getClass().getName())) {// FIXME
			return false;
		}
		return o.getId().equals(id);
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return the isnew
	 */
	@Transient
	private boolean old;

	/**
	 * @return the old
	 */
	public boolean isOld() {
		return null != id;
	}

	@Id
	@GeneratedValue
	@Column(name = "c_id")
	private Long id;//=new Long(0);

	@Version
	@Column(name = "OPTLOCK")
	private Integer version;
	@Column(name = "c_create")
	private Date create=new Date();
	@Column(name = "c_update")
	private Date upate=new Date();

	@Column(name = "c_enabled")
	private boolean enabled;
	
	
	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long long1) {
		id = long1;
	}

	/**
	 * @return the create
	 */
	public Date getCreate() {
		return create;
	}

	/**
	 * @param create
	 *            the create to set
	 */
	public void setCreate(Date create) {
		this.create = create;
	}

	/**
	 * @return the upate
	 */
	public Date getUpate() {
		return upate;
	}

	/**
	 * @param upate
	 *            the upate to set
	 */
	public void setUpate(Date upate) {
		this.upate = upate;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
}
