package org.supermy.workflow.web.workflow;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.security.SecurityUtils;
import org.supermy.core.service.Page;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.workflow.domain.TaskItem;
import org.supermy.workflow.service.WorkflowService;

/**
 * 实例列表，整体监督流程的进度
 * 
 * @author my
 * 
 */
@Results( { @Result(name = BaseActionSupport.RELOAD, location = "taskitem.action?page.pageRequest=${page.pageRequest}", type = "redirect") })
public class TaskitemAction extends BaseActionSupport<TaskItem> {

	@Autowired
	private WorkflowService approveService;

	// 基本属性
	private TaskItem taskItem;
	private Long id;
	private TaskInstance task;

	private Page<TaskItem> page = new Page<TaskItem>(5);

	// 基本属性访问函数 //
	public TaskItem getModel() {
		return taskItem;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			taskItem = approveService.getTaskItemUtil().get(id);
		} else {
			taskItem = new TaskItem();
		}
	}
	@Override
	protected void prepareModelSave() throws Exception {
		prepareModel();
		
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the taskTokenId
	 */
	public TaskInstance getTask() {
		return task;
	}

	/**
	 * @return the page
	 */
	public Page<TaskItem> getPage() {
		return page;
	}

	// CRUD Action 函数 //
	@Override
	public String list() throws Exception {
		page = approveService.getTaskItemUtil().get(page);
		List<TaskItem> result = page.getResult();
		for (TaskItem taskItem : result) {
			taskItem.setTask(approveService.getTaskId(taskItem
					.getProcessInstanceId()));
		}
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {

		if (taskItem.getProcessInstanceId() != null) {
			task = approveService.getTaskId(taskItem.getProcessInstanceId());
		}
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		if (taskItem.getUserName() == null) {
			taskItem.setUserName(SecurityUtils.getCurrentUserName());
		}
		// save
		approveService.getTaskItemUtil().save(taskItem);
		addActionMessage("保存任务完成");

		// 启动流程
		if (taskItem.getProcessInstanceId() == null) {
			ProcessInstance startProcess = approveService.startProcess(taskItem
					.getProcessDefinitionId(), taskItem.getId().toString(),
					taskItem.getMoney());

			taskItem.setProcessInstanceId(startProcess.getId());
			approveService.getTaskItemUtil().save(taskItem);

			addActionMessage("启动流程完成");
		}

		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			addActionMessage("删除成功");
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
			addActionMessage(e.getMessage());
		}
		return RELOAD;
	}

}
