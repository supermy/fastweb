package org.supermy.workflow.processdefinition.expenditure;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.core.test.BaseServiceTest;
import org.supermy.core.util.CommandUtil;
import org.supermy.workflow.service.WorkflowService;

public class ExpenditureTest extends BaseServiceTest {

	@Autowired
	WorkflowService util;

	Map<String, Object> doc;

	long processDefinitionId;

	@Before
	public void before() {

		CommandUtil.loadFastweb();

		deployProcessDefinition();

		// 加载公文
		Long id = new Date().getTime();// 单独方法测试，可能会出现问题。
		doc = new HashMap<String, Object>();
		doc.put("id", id);
		doc.put("userId", "test");
		doc.put("title", "测试公文");
		doc.put("money", 5000);
		doc.put("context", "关于JBPM流程测试的使用");

		ProcessDefinition findLatestProcessDefinition = util.getJbpmContext()
				.getGraphSession().findLatestProcessDefinition("expenditure");

		processDefinitionId = findLatestProcessDefinition.getId();
	}

	/**
	 * 发布流程定义
	 */
	private void deployProcessDefinition() {

		JbpmContext currentJbpmContext = util.getJbpmContext();

		// 要先部署子流程，然后再部署主流程，否则，主流成执行的时候会报找不到子流程的异常
		ProcessDefinition finance = ProcessDefinition
				.parseXmlResource("org/supermy/workflow/processdefinition/finance/processdefinition.xml");
		currentJbpmContext.deployProcessDefinition(finance);

		ProcessDefinition expenditure = ProcessDefinition
				.parseXmlResource("org/supermy/workflow/processdefinition/expenditure/processdefinition.xml");
		currentJbpmContext.deployProcessDefinition(expenditure);

		ProcessDefinition findLatestProcessDefinition1 = currentJbpmContext
				.getGraphSession().findLatestProcessDefinition("expenditure");
		Assert.assertNotNull(findLatestProcessDefinition1);

		// Assert.assertNotNull(findLatestProcessDefinition1.getFileDefinition().getBytes("processimage.jpg"));
		// Assert.assertNotNull(findLatestProcessDefinition1.getFileDefinition().getBytes("gpd.xml"));

		ProcessDefinition findLatestProcessDefinition2 = currentJbpmContext
				.getGraphSession().findLatestProcessDefinition("finance");
		Assert.assertNotNull(findLatestProcessDefinition2);

		currentJbpmContext.close();
	}

	/**
	 * 现有流程列表
	 */
	@Test
	public void processDefinitionList() {

		JbpmContext jbpmContext = util.getJbpmContext();

		List<ProcessDefinition> findAllProcessDefinitions = jbpmContext
				.getGraphSession().findLatestProcessDefinitions();
		for (ProcessDefinition processDefinition : findAllProcessDefinitions) {
			log.debug("process definition:{}", processDefinition.getName());
		}
		List<ProcessDefinition> allWorkFlow = util.getAllWorkFlow();
		for (ProcessDefinition processDefinition : allWorkFlow) {
			log.debug("process definition:{}", processDefinition.getName());
		}

		jbpmContext.close();

	}

	/**
	 * 某流程的所有实例列表
	 */
	@Test
	public void processInstanceList() {

		JbpmContext jbpmContext = util.getJbpmContext();

		List<ProcessDefinition> allWorkFlow = util.getAllWorkFlow();
		for (ProcessDefinition processDefinition : allWorkFlow) {
			log.debug("process definition:{}", processDefinition.getName());
			ProcessInstance processInstance = jbpmContext.getProcessInstance(
					processDefinition, "1");
			if (processInstance != null) {
				log.debug(processInstance.getRootToken().getName());
				List<TaskInstance> findTaskInstancesByProcessInstance = jbpmContext
						.getTaskMgmtSession()
						.findTaskInstancesByProcessInstance(processInstance);
				for (TaskInstance taskInstance : findTaskInstancesByProcessInstance) {
					log.debug("taskInstance:{}", taskInstance.getName());
				}
			}
		}

		jbpmContext.close();

	}

	/**
	 * 创建流程实例：将业务于流程绑定
	 */
	@Test
	public void approve5000() {
		// 测试专用 web server 的时候 filter 会自动把登录用户设定
		// 设置登录的用户
		util.getJbpmContext().setActorId("user@super.com");
		// 任务
		ProcessInstance pi = util.startProcess(processDefinitionId, doc.get(
				"id").toString(), new BigDecimal(5000.00));
		util.startTask(5000.00);

		util.getJbpmContext().setActorId("manager@super.com");
		util.approveByManager("同意");
		util.checkTasks(pi);

		util.closeJbpmContext();
	}

	@Test
	public void approve5000sendback() {
		// 测试专用 web server 的时候 filter 会自动把登录用户设定
		// 设置登录的用户
		util.getJbpmContext().setActorId("user@super.com");
		// 任务
		ProcessInstance pi = util.startProcess(processDefinitionId, doc.get(
				"id").toString(), new BigDecimal(5000.00));
		util.startTask(5000.00);
		util.getJbpmContext().setActorId("manager@super.com");
		util.approveByManager("退回");
		util.getJbpmContext().setActorId("user@super.com");
		util.startTask(4000.00);
		util.getJbpmContext().setActorId("manager@super.com");
		util.approveByManager("同意");
		util.checkTasks(pi);

		util.closeJbpmContext();
	}

	@Test
	public void approve0aggin() {
		// 测试专用 web server 的时候 filter 会自动把登录用户设定
		// 设置登录的用户
		util.getJbpmContext().setActorId("user@super.com");
		// 任务
		ProcessInstance pi = util.startProcess(processDefinitionId, doc.get(
				"id").toString(), new BigDecimal(5000.00));
		util.startTask(5000.00);
		util.getJbpmContext().setActorId("manager@super.com");
		util.approveByManager("退回");
		util.getJbpmContext().setActorId("user@super.com");
		util.startTask(0.00);
		util.getJbpmContext().setActorId("manager@super.com");
		util.approveByManager("不同意");
		util.checkTasks(pi);

		util.closeJbpmContext();
	}

	@Test
	public void approve15000() {
		// 测试专用 filter 会自动把登录用户设定
		util.getJbpmContext().setActorId("user@super.com");
		// 任务申请
		ProcessInstance pi = util.startProcess(processDefinitionId, doc.get(
				"id").toString(), new BigDecimal(5000.00));
		util.startTask(15000.00);
		util.getJbpmContext().setActorId("manager@super.com");
		util.approveByManager("同意");
		util.getJbpmContext().setActorId("boss@super.com");
		util.approveByBoss("同意");
		util.checkTasks(pi);

		util.closeJbpmContext();
	}

	@Test
	public void approve20000() {
		// 测试专用 filter 会自动把登录用户设定
		util.getJbpmContext().setActorId("user@super.com");
		// 任务申请
		ProcessInstance pi = util.startProcess(processDefinitionId, doc.get(
				"id").toString(), new BigDecimal(5000.00));
		util.startTask(20000.00);
		util.getJbpmContext().setActorId("manager@super.com");
		util.approveByManager("同意");
		util.getJbpmContext().setActorId("boss@super.com");
		util.approveByBoss("不同意");
		util.checkTasks(pi);

		util.closeJbpmContext();
	}

}
