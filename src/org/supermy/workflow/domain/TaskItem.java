package org.supermy.workflow.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.NotEmpty;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.supermy.core.domain.BaseDomain;

/**
 * @author my
 * 
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Table(name = "w_task_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class TaskItem extends BaseDomain {

	@NotEmpty
	@Column(name = "user_name", length = 246)
	private String userName;

	@NotEmpty
	@Column(name = "title_", length = 246)
	private String title;

	@NotEmpty
	@Column(name = "url_", length = 300)
	private String url;

	@Column(name = "remark_", length = 1000)
	private String remark;

	@Column(name = "money_")
	private BigDecimal money = new BigDecimal(0.00);

	@Column(name = "instance_id")
	private Long processInstanceId;

	@Column(name = "defintion_id")
	private Long processDefinitionId;

	@Transient
	private TaskInstance task;

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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the money
	 */
	public BigDecimal getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	/**
	 * @return the processInstanceId
	 */
	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	/**
	 * @param processInstanceId
	 *            the processInstanceId to set
	 */
	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	/**
	 * @return the processDefinitionId
	 */
	public Long getProcessDefinitionId() {
		return processDefinitionId;
	}

	/**
	 * @param processDefinitionId
	 *            the processDefinitionId to set
	 */
	public void setProcessDefinitionId(Long processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the task
	 */
	public TaskInstance getTask() {
		return task;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public void setTask(TaskInstance task) {
		this.task = task;
	}

	
}
