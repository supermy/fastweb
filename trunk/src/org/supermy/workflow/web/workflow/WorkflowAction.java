package org.supermy.workflow.web.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.security.SecurityUtils;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.core.web.Struts2Utils;
import org.supermy.workflow.service.ApproveService;

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
@Results( { @Result(name = BaseActionSupport.RELOAD, location = "workflow.action?page.pageRequest=${page.pageRequest}", type = "redirect") })
public class WorkflowAction extends BaseActionSupport<ProcessDefinition> {

	@Autowired
	public ApproveService approveService;

	// 基本属性
	public ProcessDefinition processDefinition;
	private Long id;

	private List<ProcessDefinition> results = new ArrayList<ProcessDefinition>(
			5);

	// 基本属性访问函数 //
	public ProcessDefinition getModel() {
		return processDefinition;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			processDefinition = approveService.getJbpmContext()
					.getGraphSession().getProcessDefinition(id);
		} else {
			processDefinition = new ProcessDefinition();
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the processDefinitions
	 */
	public List<ProcessDefinition> getResults() {
		return results;
	}

	// CRUD Action 函数 //
	@Override
	public String list() throws Exception {
		results = approveService.getAllWorkFlow();
		log.debug("find :{}", results.size());
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		addActionMessage("保存资源成功");
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
	 * 显示流程图
	 * 
	 * @throws Exception
	 */
	public void workFlowImage() throws Exception {
		prepareModel();

		JbpmContext jbpmContext = approveService.getJbpmContext();
		try {
			Struts2Utils.renderImage(processDefinition.getFileDefinition()
					.getBytes("processimage.jpg"));
			// fileDefinition.getBytes("gpd.xml");
		} finally {
			jbpmContext.close();
		}
	}

	public String startProcessInstance() throws Exception {
		prepareModel();

		// 创建和提交，可以将任务分为两步
		// TODO　改为从表单获取数据
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("title", "关于报销的申请");
		variables.put("context", "XXX 报销差旅费 nnn 元。");
		String businessKey = "1";

		ProcessInstance startProcess = approveService.startProcess(
				processDefinition, variables, businessKey);
		approveService.startTask(startProcess, SecurityUtils
				.getCurrentUserName());

		return SUCCESS;
	}
}
