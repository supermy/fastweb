package org.supermy.core.domain;

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
import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

/**
 * @author supermy E-mail:springclick@gmail.com
 * @version create time：2009-7-28 上午11:27:58
 * 
 * 父子结构递归循环；
 * 编码对象->树结构（Struts2 tree标签，Flex 组织机构图）
 * 
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Table(name = "_departments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@Proxy(lazy = false)
public class Department extends BaseDomain {

	@NotEmpty
	@Length(min = 2)
	@Column(name = "_name", unique = true, length = 220)
	private String name;
	
	/**
	 * 01010101  (高层 部门 科室 岗位)
	 */
	@NotEmpty
	@Length(min = 2)
	@Column(name = "_code", unique = true, length = 20)
	private String code;

	@Email
	@NotEmpty
	@Column(name = "email", unique = true, length = 80)
	private String email;

	@Lob
	@Column(name = "_intro")
	private String intro;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private DepartmentType type;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Department parent;
	
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "parent_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Department> children;
	
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the intro
	 */
	public String getIntro() {
		return intro;
	}

	/**
	 * @param intro
	 *            the intro to set
	 */
	public void setIntro(String intro) {
		this.intro = intro;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return the parent
	 */
	public Department getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Department parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public Set<Department> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Set<Department> children) {
		this.children = children;
	}

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the type
	 */
	public DepartmentType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(DepartmentType type) {
		this.type = type;
	}


}
