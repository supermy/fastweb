package org.supermy.workflow.web.workflow;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.xpath.DefaultXPath;
import org.jbpm.JbpmContext;
import org.jbpm.db.TaskMgmtSession;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.domain.User;
import org.supermy.core.security.SecurityUtils;
import org.supermy.core.web.BaseActionSupport;
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
@Results( { @Result(name = BaseActionSupport.RELOAD, location = "mytask.action?page.pageRequest=${page.pageRequest}", type = "redirect") })
public class MytaskAction extends BaseActionSupport<TaskInstance> {

	@Autowired
	private WorkflowService approveService;

	// 基本属性
	private TaskInstance task;
	private Long id;

	private List<TaskInstance> mytasks = new ArrayList<TaskInstance>(5);
	private List<TaskInstance> todotasks = new ArrayList<TaskInstance>(5);

	// 基本属性访问函数 //
	public TaskInstance getModel() {
		return task;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			task = approveService.getJbpmContext().getTaskInstance(id);
		} else {
			task = new TaskInstance();
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	// CRUD Action 函数 //

	/**
	 * @return the mytasks
	 */
	public List<TaskInstance> getMytasks() {
		return mytasks;
	}

	/**
	 * @return the todotasks
	 */
	public List<TaskInstance> getTodotasks() {
		return todotasks;
	}

	@Override
	public String list() throws Exception {
		JbpmContext jbpmContext = approveService.getJbpmContext();
		try {
			String userName = SecurityUtils.getCurrentUserName();
			// mytasks=jbpmContext.getTaskList();
			// todotasks=jbpmContext.getGroupTaskList(SecurityUtils.getCurrentUserName());
			TaskMgmtSession taskMgmtSession = jbpmContext.getTaskMgmtSession();
			mytasks = taskMgmtSession.findTaskInstances(userName);
			// TODO 可以设置为角色或者权限
			User user = approveService.getUserUtil().findUniqueByProperty(
					"email", userName);
			todotasks = taskMgmtSession.findPooledTaskInstances(user
					.getRoleNameList());

		} finally {
			jbpmContext.close();
		}

		return SUCCESS;

	}

	/**
	 * 我处理
	 * 
	 * @return
	 * @throws Exception
	 */
	public String pullTask() throws Exception {
		prepareModel();

		approveService.pullTask(id, SecurityUtils.getCurrentUserName());

		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		addActionMessage("审批完成");
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			addActionMessage("删除资源成功");
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
			addActionMessage(e.getMessage());
		}
		return RELOAD;
	}

	// 其他属性访问函数与Action函数 //
	/**
	 * 显示带节点的流程图
	 * 
	 * @throws Exception
	 */
	public void workFlowImage() throws Exception {
		prepareModel();

		JbpmContext jbpmContext = approveService.getJbpmContext();
		try {
			// Struts2Utils.renderImage(task.getFileDefinition().getBytes(
			// "processimage.jpg"));
			// fileDefinition.getBytes("gpd.xml");
		} finally {
			jbpmContext.close();
		}
	}

	// 从currentToken中可以得到当前节点在显示的时候的长度、宽度、横纵坐标等值。得到的方式如下：
	private int[] extractBoxConstraint(Token currentToken, Element root) {
		int[] result = new int[4];
		String nodeName = currentToken.getNode().getName();
		XPath xPath = new DefaultXPath("//node[@name='" + nodeName + "']");
		Element node = (Element) xPath.selectSingleNode(root);
		result[0] = Integer.valueOf(node.attribute("x").getValue()).intValue();
		result[1] = Integer.valueOf(node.attribute("y").getValue()).intValue();
		result[2] = Integer.valueOf(node.attribute("width").getValue())
				.intValue();
		result[3] = Integer.valueOf(node.attribute("height").getValue())
				.intValue();
		return result;
	}

}
