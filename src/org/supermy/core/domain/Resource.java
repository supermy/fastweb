package org.supermy.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Table(name = "c_files")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class Resource extends BaseDomain {

	@NotEmpty
	@Length(min = 2)
	@Column(name = "c_name", unique = true, length = 120)
	private String name;

	@NotEmpty
	@Length(min = 2)
	@Column(name = "c_path", length = 240)
	private String path;

	@NotEmpty
	@Length(min = 2)
	@Column(name = "c_filetype", length = 80)
	private String fileType;

	@NotEmpty
	@Length(min = 2)
	@Column(name = "c_type", length = 30)
	private String type;

	@Column(name = "c_done")
	private boolean done;

	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * @param done
	 *            the done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
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
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
