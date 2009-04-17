package org.supermy.workflow.processdefinition.expenditure;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
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

		JbpmContext currentJbpmContext = util.getJbpmContext();

		List<ProcessDefinition> findAllProcessDefinitions = currentJbpmContext
				.getGraphSession().findLatestProcessDefinitions();
		for (ProcessDefinition processDefinition : findAllProcessDefinitions) {
			log.debug("process definition:{}", processDefinition.getName());
		}
		List<ProcessDefinition> allWorkFlow = util.getAllWorkFlow();
		for (ProcessDefinition processDefinition : allWorkFlow) {
			log.debug("process definition:{}", processDefinition.getName());
		}

		currentJbpmContext.close();

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
		ProcessInstance pi = util.startProcess("expenditure", doc.get("id")
				.toString());
		util.startTask("test", 5000);
		util.getJbpmContext().setActorId("manager@super.com");
		util.approveByManager(true);
		util.checkTasks(pi);

		util.closeJbpmContext();
	}

	@Test
	public void approve15000() {
		// 测试专用 filter 会自动把登录用户设定
		util.getJbpmContext().setActorId("user@super.com");
		// 任务申请
		ProcessInstance pi = util.startProcess("expenditure", doc.get("id")
				.toString());
		util.startTask("test", 15000);
		util.getJbpmContext().setActorId("manager@super.com");
		util.approveByManager(true);
		util.getJbpmContext().setActorId("boss@super.com");
		util.approveByBoss(true);
		util.checkTasks(pi);

		util.closeJbpmContext();
	}

	@Test
	public void approve20000() {
		// 测试专用 filter 会自动把登录用户设定
		util.getJbpmContext().setActorId("user@super.com");
		// 任务申请
		ProcessInstance pi = util.startProcess("expenditure", doc.get("id")
				.toString());
		util.startTask("test", 20000);
		util.getJbpmContext().setActorId("manager@super.com");
		util.approveByManager(true);
		util.getJbpmContext().setActorId("boss@super.com");
		util.approveByBoss(false);
		util.checkTasks(pi);

		util.closeJbpmContext();
	}

}
