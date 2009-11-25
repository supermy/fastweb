package org.supermy.workflow.web.workflow;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.web.BaseActionSupport;
import org.supermy.core.web.Struts2Utils;
import org.supermy.workflow.service.JbpmTemplate;

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
	public JbpmTemplate approveService;
	@Autowired
	public RepositoryService repositoryService;

	// 基本属性
	public ProcessDefinition processDefinition;
	private String id;

	private List<ProcessDefinition> pdresults = new ArrayList<ProcessDefinition>(
			5);
	private List<ProcessInstance> piresults = new ArrayList<ProcessInstance>(5);

	// 基本属性访问函数 //
	public ProcessDefinition getModel() {
		return processDefinition;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			processDefinition = approveService.getProcessDefinition(id);
		} else {
			processDefinition = new ProcessDefinitionImpl();
		}
	}

	@Override
	protected void prepareModelSave() throws Exception {
		prepareModel();

	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the processDefinitions
	 */
	public List<ProcessDefinition> getPdresults() {
		return pdresults;
	}

	public List<ProcessInstance> getPiresults() {
		return piresults;
	}

	// CRUD Action 函数 //
	@Override
	public String list() throws Exception {
		pdresults = approveService.getLatestProcessDefinitions();
		piresults = approveService.getAllProcessInstance();
		log.debug("getLatestProcessDefinitions:{}", pdresults.size());
		log.debug("getAllProcessInstance:{}", piresults.size());

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
			repositoryService.deleteDeploymentCascade(processDefinition
					.getDeploymentId());
			addActionMessage("删除流程成功");
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
	public void workflowImage() throws Exception {
		prepareModel();
		// 获取流程图
		InputStream io = repositoryService.getResourceAsStream(
				processDefinition.getDeploymentId(), processDefinition
						.getImageResourceName());
		if (io != null) {
			byte[] bytes = new byte[1024 * 4];
			int size = 0;
			// 输出流程图
			while ((size = io.read(bytes, 0, 1024 * 4)) != -1) {
				Struts2Utils.getResponse().getOutputStream().write(bytes, 0,
						size);
			}
		}
	}

}
