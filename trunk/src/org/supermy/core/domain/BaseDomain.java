package org.supermy.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.annotations.Index;
import org.hibernate.cfg.annotations.Comment;


/**
 * 减少属性的使用，提高效率。
 * 
 * @author my
 *
 */
@MappedSuperclass
public class BaseDomain {

	public String toString() {
		 //开发模式
//		 return ToStringBuilder.reflectionToString(this);
		// 生产模式
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				this.id).toString();
	}

	public boolean equals(BaseDomain o) {
		if (this == o) {
			return true;
		}
		// if (o.getClass().getName().equals(getClass().getName())) {// FIXME
		// return false;
		// }
		return o.getId().equals(id);
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// /**
	// * @return the isnew
	// */
	// @Transient
	// private boolean old;

	// /**
	// * @return the old
	// */
	// public boolean isOld() {
	// return null != id;
	// }
	//oracle
	//@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="fastweb_seq")    
	//@SequenceGenerator(name="fastweb_seq",allocationSize=50, sequenceName="seq_fastweb")  	
	//mysql user  @GeneratedValue  is ok
	
	@Field
	@Id
	@GeneratedValue
	@Column(name = "id_")
	@Comment("物理主键")
	private Long id;// =new Long(0);

	//FIXME 通过类型区分不同的domain;重复的ID索引会被覆盖，对于数字型ID不是通用的解决办法；
	@Transient
	@Field("indextype_t")
	private String indexType;
	
	
	public String getIndexType() {
		this.indexType =getClass().getSimpleName();
		return indexType;
	}

	@Field("indextype_t")
	public void setIndexType(String indexType) {
		this.indexType =getClass().getSimpleName();
	}

	/**
	 * @return
	 * 
	 * 新的对象
	 */
	public boolean isnew(){
		return id==null;
	}

	// @Version
	// @Column(name = "OPTLOCK")
	// private Integer version;

	@Comment(value="创建时间",desc="数据的保存时间")
	@Index(name="i_create")
	@Column(name = "create_",updatable = false)//本属性只在save时有效,update时无效
	private Date create = new Date();

	@Comment("更新时间")
	@Index(name="i_update")
	@Column(name = "update_",insertable = false)//本属性只在update时有效,save时无效
	private Date update = new Date();

	@Comment("创建人")
	@Index(name="i_create_user")
	@Column(name = "create_user",length=80,updatable = false)//本属性只在save时有效,update时无效
	private String createBy;

	@Comment("最后修改人")
	@Index(name="i_update_user")
	@Column(name = "update_user",length=80,insertable = false)//本属性只在update时有效,save时无效
	private String updateBy;
	
	
	@Comment("有效")
	@Index(name="i_enabled")
	@Column(name = "enabled_")
	private boolean enabled = true;

	// /**
	// * @return the version
	// */
	// public Integer getVersion() {
	// return version;
	// }
	//
	// /**
	// * @param version
	// * the version to set
	// */
	// public void setVersion(Integer version) {
	// this.version = version;
	// }

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

//	/**
//	 * @return the upate
//	 */
//	public Date getUpate() {
//		return upate;
//	}
//
//	/**
//	 * @param upate
//	 *            the upate to set
//	 */
//	public void setUpate(Date upate) {
//		this.upate = upate;
//	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}



	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}


	
}
