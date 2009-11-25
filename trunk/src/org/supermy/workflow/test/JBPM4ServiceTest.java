package org.supermy.workflow.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.supermy.core.test.TestBaseService;
import org.supermy.workflow.service.JbpmTemplate;

/**
 * 使用AbstractTransactionalJbpmTestCase 来对流程进行隔离测试（比如，不影响数据库）。 这个类继承了
 * AbstractTransactionalDataSourceSpringContextTests类， 这意味着，测试一个流程和测试一个DAO
 * 的方式是完全相同的。
 * 
 * @author 莫勇
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/app-springs.xml" })
public class JBPM4ServiceTest extends TestBaseService {

	@Autowired
	@Qualifier("processEngine")
	private ProcessEngine processEngine;

	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ExecutionService executionService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ManagementService managementService;

	@Autowired
	private JbpmTemplate jt;

	@Test
	public void allService() {
		log.debug("{}", processEngine);
		log.debug("{}", repositoryService);
		log.debug("{}", executionService);
		log.debug("{}", taskService);
		log.debug("{}", historyService);
		log.debug("{}", managementService);
	}

	// @Autowired
	// WorkflowService util;
	HashMap<String, Object> doc;
	String deploymentId;
	String pdId;

	@Before
	public void before() {
		// 删除所有流程
		// List<ProcessDefinition> definitions = repositoryService
		// .createProcessDefinitionQuery().orderAsc(
		// ProcessDefinitionQuery.PROPERTY_NAME).list();
		// for (ProcessDefinition processDefinition : definitions) {
		// repositoryService.deleteDeploymentCascade(processDefinition.getDeploymentId());
		// }

		// 发布流程
		deploymentId = repositoryService
				.createDeployment()
				.addResourceFromClasspath(
						"org/supermy/workflow/processdefinition/leave.jpdl.xml")
				.deploy();

		pdId = repositoryService.createProcessDefinitionQuery()
				.processDefinitionName("leave").uniqueResult().getId();

		// 公文信息初始化
		Long id = new Date().getTime();// 单独方法测试，可能会出现问题。
		doc = new HashMap<String, Object>();
		doc.put("id", id);
		doc.put("userId", "test");
		doc.put("title", "测试公文");
		doc.put("money", 5000);
		doc.put("context", "关于JBPM流程测试的使用");
	}

	@After
	public void after() {
		// 删除流程定义
		repositoryService.deleteDeploymentCascade(deploymentId);
	}

	/**
	 * 单个流程查询
	 */
	@Test
	public void get() {
		ProcessDefinition leave1 = repositoryService
				.createProcessDefinitionQuery().processDefinitionName("leave")
				.uniqueResult();
		Assert.assertNotNull(leave1);
		// Assert.assertEquals(deploymentId, leave1.getId());
		log.debug("{}", leave1.getId());
		log.debug("{}", leave1.getKey());
		log.debug("{}", leave1.getName());
		log.debug("{}", leave1.getDescription());

		ProcessDefinition leave2 = repositoryService
				.createProcessDefinitionQuery().processDefinitionId(
						leave1.getId()).uniqueResult();
		Assert.assertNotNull(leave2);
		log.debug("{}", leave2.getKey());
	}

	/**
	 * 现有流程列表
	 */
	@Test
	public void processDefinitionList() {
		List<ProcessDefinition> latestProcessDefinitions = jt
				.getLatestProcessDefinitions();
		Assert.assertTrue(latestProcessDefinitions.size() > 0);
	}

	/**
	 * 某流程的所有实例列表
	 */
	@Test
	public void processInstanceList() {
		List<ProcessInstance> processInstances = jt.getProcessInstances(pdId);
		Assert.assertTrue(processInstances.size() == 0);
	}

	/**
	 * 启动实例列表
	 */
	@Test
	public void startProcess() {

		ProcessInstance startProcessInstanceById = executionService
				.startProcessInstanceById(pdId);
		Assert.assertNotNull(startProcessInstanceById);

		ProcessInstance startProcessInstanceById2 = executionService
				.startProcessInstanceByKey("leave");
		Assert.assertNotNull(startProcessInstanceById2);

		List<ProcessInstance> processInstances = jt.getProcessInstances(pdId);
		Assert.assertTrue(processInstances.size() == 2);

	}

	/**
	 * 创建流程实例：将业务于流程绑定
	 */
	/**
	 * 非经理级别员工，请假2天，批准
	 */
	@Test
	public void approve_2_nomanager_agree() {
		// 测试专用 web server 的时候 filter 会自动把登录用户设定
		// 设置登录的用户

		// 记住发起人，因为要退回到起始节点
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("actorId", "user@super.com");
		variables.put("请假天数", 2);
		variables.put("manager", "否");

		ProcessInstance pi = jt.startProcessKey("leave", variables);
		Assert.assertTrue(pi.isActive("填写请假单"));

		List<Task> taskList = taskService.findPersonalTasks("employee1");
		Assert.assertEquals(1, taskList.size());
		Task task = taskList.get(0);
		Assert.assertEquals("填写请假单", task.getName());

		taskService.completeTask(task.getId(), variables);

		// 经理审批
		List<Task> taskList1 = taskService.findPersonalTasks("manager1");
		Assert.assertEquals(1, taskList1.size());
		Task task1 = taskList1.get(0);
		Assert.assertEquals("经理审核", task1.getName());

		// 表单构造与流程结合， 表单变量传入；
		String agree = "批准";
		taskService.completeTask(task1.getId(), agree);

		pi = executionService.findProcessInstanceById(pi.getId());
		Assert.assertNull(pi);
	}

	/**
	 * 非经理级别员工，请假2天，批准
	 */
	@Test
	public void approve_2_nomanager_noagree() {
		// 测试专用 web server 的时候 filter 会自动把登录用户设定
		// 设置登录的用户

		// 记住发起人，因为要退回到起始节点
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("actorId", "user@super.com");
		variables.put("请假天数", 2);
		variables.put("manager", "否");

		ProcessInstance pi = jt.startProcessKey("leave", variables);
		Assert.assertTrue(pi.isActive("填写请假单"));

		List<Task> taskList = taskService.findPersonalTasks("employee1");
		Assert.assertEquals(1, taskList.size());
		Task task = taskList.get(0);
		Assert.assertEquals("填写请假单", task.getName());

		taskService.completeTask(task.getId(), variables);

		// 经理审批
		List<Task> taskList1 = taskService.findPersonalTasks("manager1");
		Assert.assertEquals(1, taskList1.size());
		Task task1 = taskList1.get(0);
		Assert.assertEquals("经理审核", task1.getName());

		// 表单构造与流程结合， 表单变量传入；
		String agree = "批准";
		String noagree = "不批准";
		taskService.completeTask(task1.getId(), noagree);

		pi = executionService.findProcessInstanceById(pi.getId());
		Assert.assertNull(pi);
	}

	/**
	 * 非经理级别员工，请假2天，驳回
	 */
	@Test
	public void approve_2_nomanager_back() {
		// 测试专用 web server 的时候 filter 会自动把登录用户设定
		// 设置登录的用户

		// 记住发起人，因为要退回到起始节点
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("actorId", "user@super.com");
		variables.put("请假天数", 2);
		variables.put("manager", "否");

		ProcessInstance pi = jt.startProcessKey("leave", variables);
		Assert.assertTrue(pi.isActive("填写请假单"));

		List<Task> taskList = taskService.findPersonalTasks("employee1");
		Assert.assertEquals(1, taskList.size());
		Task task = taskList.get(0);
		Assert.assertEquals("填写请假单", task.getName());

		taskService.completeTask(task.getId(), variables);

		// 经理审批
		List<Task> taskList1 = taskService.findPersonalTasks("manager1");
		Assert.assertEquals(1, taskList1.size());
		Task task1 = taskList1.get(0);
		Assert.assertEquals("经理审核", task1.getName());

		// 表单构造与流程结合， 表单变量传入；
		String agree = "批准";
		String noagree = "不批准";
		String back = "驳回";

		taskService.completeTask(task1.getId(), back);

		List<Task> taskList2 = taskService.findPersonalTasks("employee1");
		Assert.assertEquals(1, taskList2.size());
		Task task2 = taskList2.get(0);
		Assert.assertEquals("填写请假单", task2.getName());
	}

	/**
	 * 经理级别员工，请假
	 */
	@Test
	public void approve_2_manager() {
		// 测试专用 web server 的时候 filter 会自动把登录用户设定
		// 设置登录的用户

		// 记住发起人，因为要退回到起始节点
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("actorId", "user@super.com");
		variables.put("请假天数", 2);
		variables.put("manager", "是");

		ProcessInstance pi = jt.startProcessKey("leave", variables);
		Assert.assertTrue(pi.isActive("填写请假单"));

		List<Task> taskList = taskService.findPersonalTasks("employee1");
		Assert.assertEquals(1, taskList.size());
		Task task = taskList.get(0);
		Assert.assertEquals("填写请假单", task.getName());

		taskService.completeTask(task.getId(), variables);

		// 经理审批
		List<Task> taskList1 = taskService.findPersonalTasks("manager1");
		Assert.assertEquals(0, taskList1.size());

		// 老板审批
		List<Task> taskList2 = taskService.findPersonalTasks("boss1");
		Assert.assertEquals(1, taskList2.size());
		Task task2 = taskList2.get(0);
		Assert.assertEquals("老板审批", task2.getName());

	}

	/**
	 * 经理级别员工，请假,驳回
	 */
	@Test
	public void approve_2_manager_Bback() {
		// 测试专用 web server 的时候 filter 会自动把登录用户设定
		// 设置登录的用户

		// 记住发起人，因为要退回到起始节点
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("actorId", "user@super.com");
		variables.put("请假天数", 2);
		variables.put("manager", "是");

		ProcessInstance pi = jt.startProcessKey("leave", variables);
		Assert.assertTrue(pi.isActive("填写请假单"));

		List<Task> taskList = taskService.findPersonalTasks("employee1");
		Assert.assertEquals(1, taskList.size());
		Task task = taskList.get(0);
		Assert.assertEquals("填写请假单", task.getName());

		taskService.completeTask(task.getId(), variables);

		// 经理审批
		List<Task> taskList1 = taskService.findPersonalTasks("manager1");
		Assert.assertEquals(0, taskList1.size());

		// 老板审批
		List<Task> taskList2 = taskService.findPersonalTasks("boss1");
		Assert.assertEquals(1, taskList2.size());
		Task task2 = taskList2.get(0);
		Assert.assertEquals("老板审批", task2.getName());

		// 表单构造与流程结合， 表单变量传入；
		String back = "驳回";
		taskService.completeTask(task2.getId(), back);

		List<Task> taskList3 = taskService.findPersonalTasks("employee1");
		Assert.assertEquals(1, taskList3.size());
		Task task3 = taskList3.get(0);
		Assert.assertEquals("填写请假单", task3.getName());
	}

	/**
	 * 非经理级别员工，请假4天，经理批准，老板批准
	 */
	@Test
	public void approve_4_nomanager_Magree_Bagree() {
		// 测试专用 web server 的时候 filter 会自动把登录用户设定
		// 设置登录的用户

		// 记住发起人，因为要退回到起始节点
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("actorId", "user@super.com");
		variables.put("请假天数", 4);
		variables.put("manager", "否");

		ProcessInstance pi = jt.startProcessKey("leave", variables);
		Assert.assertTrue(pi.isActive("填写请假单"));

		List<Task> taskList = taskService.findPersonalTasks("employee1");
		Assert.assertEquals(1, taskList.size());
		Task task = taskList.get(0);
		Assert.assertEquals("填写请假单", task.getName());

		taskService.completeTask(task.getId(), variables);

		// 经理审批
		List<Task> taskList1 = taskService.findPersonalTasks("manager1");
		Assert.assertEquals(1, taskList1.size());
		Task task1 = taskList1.get(0);
		Assert.assertEquals("经理审核", task1.getName());

		// 表单构造与流程结合， 表单变量传入；
		String agree = "批准";
		taskService.completeTask(task1.getId(), agree);

		// 老板审批
		List<Task> taskList2 = taskService.findPersonalTasks("boss1");
		Assert.assertEquals(1, taskList2.size());
		Task task2 = taskList2.get(0);
		Assert.assertEquals("老板审批", task2.getName());

		// 表单构造与流程结合， 表单变量传入；
		taskService.completeTask(task2.getId(), agree);

		pi = executionService.findProcessInstanceById(pi.getId());
		Assert.assertNull(pi);
	}

	/**
	 * 非经理级别员工，请假4天，经理批准，老板不批准
	 */
	@Test
	public void approve_4_nomanager_Magree_BNoagree() {
		// 测试专用 web server 的时候 filter 会自动把登录用户设定
		// 设置登录的用户

		// 记住发起人，因为要退回到起始节点
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("actorId", "user@super.com");
		variables.put("请假天数", 4);
		variables.put("manager", "否");

		ProcessInstance pi = jt.startProcessKey("leave", variables);
		Assert.assertTrue(pi.isActive("填写请假单"));

		List<Task> taskList = taskService.findPersonalTasks("employee1");
		Assert.assertEquals(1, taskList.size());
		Task task = taskList.get(0);
		Assert.assertEquals("填写请假单", task.getName());

		taskService.completeTask(task.getId(), variables);

		// 经理审批
		List<Task> taskList1 = taskService.findPersonalTasks("manager1");
		Assert.assertEquals(1, taskList1.size());
		Task task1 = taskList1.get(0);
		Assert.assertEquals("经理审核", task1.getName());

		// 表单构造与流程结合， 表单变量传入；
		String agree = "批准";
		String noagree = "不批准";
		taskService.completeTask(task1.getId(), agree);

		// 老板审批
		List<Task> taskList2 = taskService.findPersonalTasks("boss1");
		Assert.assertEquals(1, taskList2.size());
		Task task2 = taskList2.get(0);
		Assert.assertEquals("老板审批", task2.getName());

		// 表单构造与流程结合， 表单变量传入；
		taskService.completeTask(task2.getId(), noagree);

		pi = executionService.findProcessInstanceById(pi.getId());
		Assert.assertNull(pi);
	}
}
