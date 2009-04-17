package org.supermy.workflow.processdefinition.baoxiao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.db.TaskMgmtSession;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springmodules.workflow.jbpm31.JbpmTemplate;
import org.supermy.core.test.BaseServiceTest;
import org.supermy.workflow.service.ApproveService;

public class MyTest extends BaseServiceTest {

	@Autowired
	JbpmConfiguration jbpmConfig;
	@Autowired
	JbpmTemplate jbpmTemplate;
	@Autowired
	ApproveService util;

	Long id = new Date().getTime();// 单独方法测试，可能会出现问题。
	Long processInstanceId;
	Long userId = Long.parseLong("1");

	/**
	 * 发布流程定义
	 */
	@Test
	public void deployProcessDefinition() {

		JbpmContext currentJbpmContext = jbpmConfig.getCurrentJbpmContext();
		if (currentJbpmContext == null) {
			currentJbpmContext = jbpmConfig.createJbpmContext();
		}

		ProcessDefinition processDefinition = ProcessDefinition
				.parseXmlResource("org/supermy/workflow/test/baoxiao/processdefinition.xml");
		currentJbpmContext.deployProcessDefinition(processDefinition);
		ProcessDefinition findLatestProcessDefinition = currentJbpmContext
				.getGraphSession().findLatestProcessDefinition("baoxiao");
		Assert.assertNotNull(findLatestProcessDefinition);
		currentJbpmContext.close();
	}

	/**
	 * 现有流程列表
	 */
	@Test
	public void processDefinitionList() {

		JbpmContext currentJbpmContext = jbpmConfig.getCurrentJbpmContext();
		if (currentJbpmContext == null) {
			currentJbpmContext = jbpmConfig.createJbpmContext();
		}

		List<ProcessDefinition> findAllProcessDefinitions = currentJbpmContext
				.getGraphSession().findLatestProcessDefinitions();
		for (ProcessDefinition processDefinition : findAllProcessDefinitions) {
			log.debug("process definition:{}", processDefinition.getName());
		}
		List<ProcessDefinition> allWorkFlow = util.getAllWorkFlow();
		for (ProcessDefinition processDefinition : allWorkFlow) {
			log.debug("process definition:{}", processDefinition.getName());
		}

	}

	@Test
	public void processInstanceList() {

		JbpmContext currentJbpmContext = jbpmConfig.getCurrentJbpmContext();
		if (currentJbpmContext == null) {
			currentJbpmContext = jbpmConfig.createJbpmContext();
		}
		ProcessDefinition findLatestProcessDefinition = currentJbpmContext
				.getGraphSession().findLatestProcessDefinition("baoxiao");
		jbpmTemplate.setProcessDefinition(findLatestProcessDefinition);
		List<ProcessInstance> findProcessInstances = jbpmTemplate
				.findProcessInstances();
		log.debug("instance count:{}", findProcessInstances.size());
		for (ProcessInstance processInstance : findProcessInstances) {
			log.debug("process instance:,{}", processInstance.getKey());
			processInstance.end();
			// processInstance.signal();
		}

		// List<ProcessDefinition> findAllProcessDefinitions =
		// currentJbpmContext
		// .getGraphSession().findLatestProcessDefinitions();
		// for (ProcessDefinition processDefinition : findAllProcessDefinitions)
		// {
		// log.debug("process definition:{}", processDefinition.getName());
		// }
		// List<ProcessDefinition> allWorkFlow = util.getAllWorkFlow();
		// for (ProcessDefinition processDefinition : allWorkFlow) {
		// log.debug("process definition:{}", processDefinition.getName());
		// }

		currentJbpmContext.close();// 勿忘，否则不正确
	}

	private void start() {
		JbpmContext currentJbpmContext = jbpmConfig.getCurrentJbpmContext();
		if (currentJbpmContext == null) {
			currentJbpmContext = jbpmConfig.createJbpmContext();
		}
		GraphSession graphSession = currentJbpmContext.getGraphSession();

		// 加载公文
		Map<String, Object> doc = new HashMap<String, Object>();
		doc.put("id", id);
		doc.put("userId", userId);
		doc.put("title", "测试公文");
		doc.put("context", "关于JBPM流程测试的使用");
		// doc.put("ProcessDefinition", myPD.getName());

		// 启动一个新的流程
		// ProcessInstance processInstance =
		// util.startProcessInstance("baoxiao", id
		// .toString(), null);
		ProcessDefinition myPD = graphSession
				.findLatestProcessDefinition("baoxiao");
		Assert.assertNotNull(myPD);

		ProcessInstance processInstance = myPD.createProcessInstance(doc, id
				.toString());

		// processInstance.getContextInstance().setVariableLocally("userId",
		// userId);
		// 设定人物变量，可以在权限的时候自动获取
		processInstance.getContextInstance().setVariable("userId", userId);

		// 存储流程实例
		currentJbpmContext.save(processInstance);

		TaskInstance ti=processInstance.getTaskMgmtInstance().createStartTaskInstance();
		ti.setActorId(userId.toString());//任务退回的时候，直接退给本人
		ti.end();


		////以下是测试，或者测试程序使用
		doc.put("processInstance", processInstance.getId());
		processInstanceId = processInstance.getId();

		
		// 流程实例开始时间
		ProcessInstance processInstance2 = currentJbpmContext
				.getProcessInstance(processInstance.getId());
		Assert
				.assertEquals(processInstance2.getKey(), doc.get("id")
						.toString());

		// 当前节点
		log.debug("current node:{}", processInstance.getRootToken().getNode()
				.getName());

		// 启动流程
		//processInstance.signal();

		currentJbpmContext.close();

	}

	private void groupStart() {
		JbpmContext currentJbpmContext = jbpmConfig.createJbpmContext();

		// 某个用户是否有任务
		TaskMgmtSession taskMgmtSession = currentJbpmContext
				.getTaskMgmtSession();

		List<TaskInstance> tasks1 = taskMgmtSession
				.findPooledTaskInstances("组长");
		List<TaskInstance> tasks10 = taskMgmtSession.findTaskInstances("组长");
		List<TaskInstance> tasks101 = currentJbpmContext.getTaskList("组长");
//		通过改变人物来完成
//		currentJbpmContext.setActorId("组长");
		List<TaskInstance> tasks102 = currentJbpmContext.getTaskList();

		List<TaskInstance> tasks11 = taskMgmtSession
				.findPooledTaskInstances("副组长");
		List<TaskInstance> tasks2 = taskMgmtSession
				.findPooledTaskInstances("科长");
		List<TaskInstance> tasks3 = taskMgmtSession
				.findPooledTaskInstances("处长");
		log.debug("组长,{}", tasks1.size());
		log.debug("组长,{}", tasks10.size());
		log.debug("组长,{}", tasks101.size());
		log.debug("组长,{}", tasks102.size());

		log.debug("副组长,{}", tasks11.size());
		log.debug("科长,{}", tasks2.size());
		log.debug("处长,{}", tasks3.size());

		List<TaskInstance> findTaskInstances = taskMgmtSession
				.findPooledTaskInstances("组长");
		log.debug("组长:{}", findTaskInstances.size());

		for (TaskInstance taskInstance : findTaskInstances) {
			// 角色审批完成
			// log.debug("taskInstance:{}",taskInstance.getPooledActors());

			// 记住设置泳道的actorId，否则被退回的时候，所有的人都能得到任务了。
			// 当使用setPooledActors的时候，需要用findPooledTaskInstances.
			// 当退回以后，就得使用findTaskInstances来得到任务了。
			// taskInstance.setActorId("组长");
//			taskInstance.setActorId("组长");
			currentJbpmContext.save(taskInstance);

			taskInstance.end();
			String key = taskInstance.getProcessInstance().getKey();

			log.debug("组长{}审批完成", key);
		}
		currentJbpmContext.close();

	}

	private void chiefReturn() {
		JbpmContext currentJbpmContext = jbpmConfig.createJbpmContext();
		TaskMgmtSession taskMgmtSession = currentJbpmContext
				.getTaskMgmtSession();

		// 科长第一次，退回
		List<TaskInstance> findTaskInstances = taskMgmtSession
				.findPooledTaskInstances("科长");
		log.debug("科长:{}", findTaskInstances.size());

		for (TaskInstance taskInstance : findTaskInstances) {
			Node node = taskInstance.getTask().getTaskNode();// 取任务b所在节点
			if (node == null) {
				// node = taskInstance.getTask().getStartState();//有可能是要退到头的
				node = taskInstance.getToken().getParent().getNode();
			}
			// taskInstance.addComment("退回");
			// taskInstance.getToken().setNode(node);
			// 直接调用TaskInstance.getPreviousActId()方法，获得上一个任务的执行者，任务回退后，后在任务设定这个setPooledActors

			String previousActorId = taskInstance.getPreviousActorId();
			// previousActorId = "组长";
			log.debug("previousActorId,{}", previousActorId);
			taskInstance.end("修改");

			// / taskInstance.getToken().signal("修改");// 退回
			// List<TaskInstance> findTaskInstancesByProcessInstance =
			// taskMgmtSession
			// .findTaskInstancesByProcessInstance(processInstance);
			// for (TaskInstance taskInstance3 :
			// findTaskInstancesByProcessInstance) {
			// TaskInstance taskInstance2 = currentJbpmContext
			// .getTaskInstanceForUpdate(taskInstance3.getId());
			// log.debug("{}", taskInstance2.getName());
			// Set<PooledActor> pooledActors = taskInstance2.getPooledActors();
			// log.debug("{}", pooledActors);
			// taskInstance2.setPooledActors(previousActorId); //
			// taskInstance2.setSwimlaneInstance(null);
			//
			// currentJbpmContext.save(taskInstance2);
			// }
			// SwimlaneInstance swimlaneInstance = new SwimlaneInstance();
			// swimlaneInstance.setPooledActors(previousActorId);
			// taskInstance.getToken().resume();
			// taskInstance.cancel();
		}

		// 看看回退之后的节点
		ProcessInstance arg0 = currentJbpmContext
				.getProcessInstance(processInstanceId);
		List<TaskInstance> findTaskInstancesByProcessInstance = taskMgmtSession
				.findTaskInstancesByProcessInstance(arg0);
		for (TaskInstance taskInstance : findTaskInstancesByProcessInstance) {
			log.debug("{}", taskInstance.getName());
			log.debug("{}", taskInstance.getActorId());
			log.debug("{}", taskInstance.getSwimlaneInstance());
			log.debug("{}", taskInstance.getPooledActors());
		}
		List<TaskInstance> taskList = currentJbpmContext.getTaskList("组长");
		log.debug("taskList:{}", taskList.size());
		List<TaskInstance> findPooledTaskInstances = taskMgmtSession
				.findPooledTaskInstances("组长");
		log.debug("findPooledTaskInstances:{}", findPooledTaskInstances.size());

		currentJbpmContext.close();
	}

	private void group2Start() {
		JbpmContext currentJbpmContext = jbpmConfig.createJbpmContext();
		TaskMgmtSession taskMgmtSession = currentJbpmContext
				.getTaskMgmtSession();
		List<TaskInstance> tasks11 = taskMgmtSession.findTaskInstances("组长");
		log.debug("===组长,{}", tasks11.size());
		for (TaskInstance taskInstance : tasks11) {
			if (!taskInstance.hasEnded()) {
				log.debug("===组长,{}", taskInstance.getName());
				taskInstance.end();

			}
		}

		// Assert.assertTrue(false);

		// /////////////退回begin 只有组长能够有权限
		try {
			tasks11 = taskMgmtSession.findPooledTaskInstances("副组长");
			log.debug("副组长,{}", tasks11.size());

		} catch (Exception e) {
			log.error("副组长,{}", e.getMessage());
		}

		List<TaskInstance> findTaskInstances = taskMgmtSession
				.findPooledTaskInstances("组长");
		log.debug("组长:{}", findTaskInstances.size());

		for (TaskInstance taskInstance : findTaskInstances) {
			// 角色审批完成
			taskInstance.end();
			String key = taskInstance.getProcessInstance().getKey();
			log.debug("组长{}审批完成", key);
		}

		// 科长第一次，退回
		findTaskInstances = taskMgmtSession.findPooledTaskInstances("科长");
		log.debug("科长:{}", findTaskInstances.size());

		for (TaskInstance taskInstance : findTaskInstances) {
			// 角色审批完成
//			taskInstance.setActorId("科长");
			taskInstance.end();
			String key = taskInstance.getProcessInstance().getKey();
			log.debug("科长{}审批完成", key);
		}
		currentJbpmContext.close();
		// /////////////退回end

	}

	private void director() {
		JbpmContext currentJbpmContext = jbpmConfig.createJbpmContext();
		TaskMgmtSession taskMgmtSession = currentJbpmContext
				.getTaskMgmtSession();

		List<TaskInstance> findTaskInstances = taskMgmtSession
				.findPooledTaskInstances("处长");
		log.debug("处长:{}", findTaskInstances.size());

		for (TaskInstance taskInstance : findTaskInstances) {
			// 角色审批完成
			taskInstance.end();
			String key = taskInstance.getProcessInstance().getKey();
			log.debug("处长{}审批完成", key);

		}

		// 审批、流转
		ProcessInstance findProcessInstance = currentJbpmContext
				.getProcessInstance(processInstanceId);
		// findProcessInstance.
		/*
		 * // 触发流程实例走向下一步 findProcessInstance.getRootToken().signal(); //
		 * findProcessInstance.signal();区别？
		 */
		// 是否结束
		log
				.debug("process instance is end:,{}", findProcessInstance
						.hasEnded());
		findProcessInstance.end();
		Assert.assertTrue(findProcessInstance.hasEnded());
		// 查看流程图

		currentJbpmContext.close();// 勿忘，否则不正确

	}

	/*
	 * <任务列表有我来处理，随即通过下面的方法，将任务转为自己所有>
	 * 对于JBPM来说，我们可以把任务分给一个用户或者一个用户组（多个用户），分给一个用户就是我们这里把<br>
	 * 它叫他“个人任务”，分给一组用户的话因为每个用户登录后都可以看到该任务
	 * ，都可以处理该任务，所以我们把它叫“共享任务”。在这里我们规定，对于个人任务当前用户可以直接去处理，
	 * 对于共享任务当前用户如果要处理该任务要首先把该任务拉到自己的任务列表里，然后才可以对其进行处理。
	 */
	private void changeTask(Long taskId, String userId) throws Exception {
		JbpmContext jbpmContext = jbpmConfig.createJbpmContext();
		TaskInstance ti = jbpmContext.getTaskInstance(taskId);
		ti.setActorId(userId);
		jbpmContext.save(ti);
		jbpmContext.close();
	}

	/**
	 * 创建流程实例：将业务于流程绑定
	 */
	@Test
	public void startProcessInstance() {//
		// 任务申请
		start();
		//任务列表
//		changeTask(processInstanceId, userId);
		groupStart();
		chiefReturn();
		group2Start();
		director();
	}

}
