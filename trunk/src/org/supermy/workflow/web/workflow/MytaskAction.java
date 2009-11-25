package org.supermy.workflow.web.workflow;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
//import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.domain.User;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.workflow.domain.TaskItem;
import org.supermy.workflow.service.WorkflowService;

/**
 * 工作流方面的查询，分页的话需要重写查询语句.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 
 */
/**
 * @author my
 * 
 */
@Results( { @Result(name = BaseActionSupport.RELOAD, location = "mytask.action", type = "redirect") })
public class MytaskAction{// extends BaseActionSupport<TaskInstance> {
//
//	
//	@Autowired
//	private WorkflowService approveService;
//
//	// 基本属性
//	private TaskInstance task;
//	private TaskItem taskItem;
//	private Long id;
//	private String pass;
//	private String remark;
//	
//	private List<TaskInstance> mytasks = new ArrayList<TaskInstance>(5);
//	private List<TaskInstance> todotasks = new ArrayList<TaskInstance>(5);
//
//	// 基本属性访问函数 //
//	public TaskInstance getModel() {
//		return task;
//	}
//
//	@Override
//	protected void prepareModel() throws Exception {
//		if (id != null) {
//			task = approveService.getJbpmContext().getTaskInstance(id);
//			log.debug("task name:{}", task.getName());
//		} else {
//			task = new TaskInstance();
//		}
//	}
//	@Override
//	protected void prepareModelSave() throws Exception {
//		prepareModel();
//		
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	// CRUD Action 函数 //
//
//	/**
//	 * @return the pass
//	 */
//	public String getPass() {
//		return pass;
//	}
//
//	/**
//	 * @return the remark
//	 */
//	public String getRemark() {
//		return remark;
//	}
//
//	/**
//	 * @return the taskItem
//	 */
//	public TaskItem getTaskItem() {
//		return taskItem;
//	}
//
//	/**
//	 * @param pass
//	 *            the pass to set
//	 */
//	public void setPass(String pass) {
//		this.pass = pass;
//	}
//
//	/**
//	 * @return the mytasks
//	 */
//	public List<TaskInstance> getMytasks() {
//		return mytasks;
//	}
//
//	/**
//	 * @return the todotasks
//	 */
//	public List<TaskInstance> getTodotasks() {
//		return todotasks;
//	}
//
//	@Override
//	public String list() throws Exception {
//		mytasks = approveService.getJbpmContext().getTaskList();
//		User user = approveService.getUserUtil().findUniqueByProperty("email",
//				approveService.getJbpmContext().getActorId());
//		todotasks = approveService.getJbpmContext().getTaskMgmtSession()
//				.findPooledTaskInstances(user.getRoleNameList());
//
//		log.debug("mytasks size:{}", mytasks.size());
//		log.debug("todotasks size:{}", todotasks.size());
//
//		return SUCCESS;
//
//	}
//
//	@Override
//	public String input() throws Exception {
//		taskItem = approveService.getTaskItemUtil().get(
//				Long.parseLong(task.getProcessInstance().getKey()));
//		return INPUT;
//	}
//
//	@Override
//	public String save() throws Exception {
//
//		task.addComment(remark);
//		
//		approveService.TaskGo(task, pass);
//
//		addActionMessage(pass);
//		addActionMessage("审批完成");
//		return RELOAD;
//	}
//
//	@Override
//	public String delete() throws Exception {
//		try {
//			addActionMessage("删除资源成功");
//		} catch (RuntimeException e) {
//			log.error(e.getMessage(), e);
//			addActionMessage(e.getMessage());
//		}
//		return RELOAD;
//	}
//
//	/**
//	 * 我来处理
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public String pullTask() throws Exception {
//		prepareModel();
//
//		approveService.pullTask(task);
//		return RELOAD;
//	}
//
//	/**
//	 * 让别人处理
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public String pushTask() throws Exception {
//		prepareModel();
//
//		approveService.pushTask(task);
//		return RELOAD;
//	}

}
