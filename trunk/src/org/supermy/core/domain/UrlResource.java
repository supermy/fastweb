package org.supermy.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;
import org.hibernate.cfg.annotations.Comment;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.supermy.core.util.ListUtils;

/**
 * 受保护的资源.
 * 
 */
@Comment("资源")
@Entity
@Table(name = "c_url_resources")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UrlResource extends BaseDomain {
	

	// resourceType常量 //
	public static final String URL_TYPE = "url";
	public static final String MENU_TYPE = "menu";

	@Length(max=20)
	@NotEmpty
	@Comment("资源类型")
	@Index(name="i_res_type")
	@Column(name="resource_type",nullable = false,length=20)
	private String resourceType;

	@Length(max=250)
	@NotEmpty
	@Comment("资源描述")
	@Index(name="i_res_desc")
	@Column(name="desc_",length=250)
	private String desc;

	
	@Length(max=250)
	@NotEmpty
	@Comment("资源标识")
	@Index(name="i_res_value")
	@Column(name="value_",nullable = false, unique = true,length=250)
	private String value;

	@Comment(value="序号",desc="资源在 Spring Security 中的校验顺序字段.")
	@Column(name="position_")
	private double position=0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	private User manager;
	
	//("可访问该资源的授权集合.")
	@ManyToMany
	@JoinTable(name = "c_resource_authority", joinColumns = { @JoinColumn(name = "resource_id") }, inverseJoinColumns = { @JoinColumn(name = "authority_id") })
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("create")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<Authority> authorityList = new ArrayList<Authority>(0);

	
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	public List<Authority> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	/**
	 * 可访问该资源的授权名称字符串, 多个授权用','分隔.
	 */
	@Transient
	public String getAuthorityListName() {
		return ListUtils.propertyToString(new ArrayList<Object>(authorityList), "name", ",");
	}
	public List<Long> getAuthorityListId()  {
		return ListUtils.propertyToListLong(new ArrayList(authorityList), "id");
	}

}
