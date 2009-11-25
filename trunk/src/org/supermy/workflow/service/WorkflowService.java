package org.supermy.workflow.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.User;
import org.supermy.core.service.BaseService;
import org.supermy.core.service.FastwebTemplate;
import org.supermy.core.service.Page;
import org.supermy.workflow.domain.OrigApprove;
import org.supermy.workflow.domain.TaskItem;

/**
 * 审核报销流程的主要业务类
 * 
 * @author my
 * 
 */
@Transactional
@Service
public class WorkflowService {//extends BaseService {
//
//	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
//
//	private FastwebTemplate<User, Long> userUtil;
//	private FastwebTemplate<TaskItem, Long> taskItemUtil;
//	private FastwebTemplate<OrigApprove, Long> approveUtil;
//	private SessionFactory sessionFactory;
//	@Autowired
//	private JbpmConfiguration jbpmConfiguration;
//
//	@Autowired
//	public void setSessionFactory(SessionFactory sessionFactory) {
//		userUtil = new FastwebTemplate<User, Long>(sessionFactory, null,
//				User.class);
//		taskItemUtil = new FastwebTemplate<TaskItem, Long>(sessionFactory,
//				null, TaskItem.class);
//		approveUtil = new FastwebTemplate<OrigApprove, Long>(sessionFactory,
//				null, OrigApprove.class);
//		this.sessionFactory = sessionFactory;
//	}
//
//	public JbpmContext getJbpmContext() {
//		log.debug("get jbpm context:");
//		JbpmContext jbpmContext = jbpmConfiguration.getCurrentJbpmContext();
//		if (jbpmContext == null) {
//			log.debug("create jbpm context ... ... ");
//			jbpmContext = jbpmConfiguration.createJbpmContext();
//		}
//		// 保持session 一致
//		jbpmContext.setSessionFactory(sessionFactory);
//		jbpmContext.setSession(sessionFactory.getCurrentSession());
//		return jbpmContext;
//	}
//
//	/**
//	 * 测试的时候使用 filter自动关闭
//	 */
//	public void closeJbpmContext() {
//		log.debug("close context:");
//		jbpmConfiguration.getCurrentJbpmContext().close();
//	}
//
//	/**
//	 * @return the userUtil
//	 */
//	public FastwebTemplate<User, Long> getUserUtil() {
//		return userUtil;
//	}
//
//	/**
//	 * @return the approveUtil
//	 */
//	public FastwebTemplate<OrigApprove, Long> getApproveUtil() {
//		return approveUtil;
//	}
//
//	/**
//	 * @return the taskItemUtil
//	 */
//	public FastwebTemplate<TaskItem, Long> getTaskItemUtil() {
//		return taskItemUtil;
//	}
//
//	/**
//	 * 
//	 * 发布zip压缩的流程 <br />
//	 * 如下结构： <br/>
//	 * process1.zip<br/>
//	 * --processdefinition.xml<br/>
//	 * --classes<br/>
//	 * process2.zip<br/>
//	 * --processdefinition.xml<br/>
//	 * --classes <br/>
//	 * 流程档案部署后，就不再需要这个压缩包及其中的文件了，
//	 * 数据库中的jbpm_bytearray和jbpm_byteblock两张表存储了流程档案的文件内容 ，<br/>
//	 * 它们把流程档案中的文件拆开了存到了数据库。jbpm-bytearray存储了流程档案的目录，
//	 * jbpm_bytebolck则是将文件的二进制内容存了进去。对于流程档案中包含的流程定义中Action和Task等Java
//	 * ByteCode信息（.class文件），引擎会从数据库读出byte[]数组然后作为类加载
//	 * ，如果你的类存在于引擎可见的classpath，那么就会从那里加载
//	 * ，所以说流程所需的.class文件可以打包到流程档案，也可以不打包到流程档案，而是放在你的classpath目录下。
//	 * 
//	 * @param processZipFileName
//	 */
//	public void deployProcess(String processZipFileName) {
//		try {
//			ZipInputStream zin = new ZipInputStream(new FileInputStream(
//					processZipFileName));
//			ProcessDefinition processDefinition = ProcessDefinition
//					.parseParZipInputStream(zin);
//			getJbpmContext().deployProcessDefinition(processDefinition);
//			zin.close();
//
//		} catch (IOException e) {
//			throw new RuntimeException(e.getMessage());
//		}
//	}
//
//	/**
//	 * 取得所有的工作流定义<br>
//	 * 
//	 * @return 工作流定义列表 　　
//	 */
//	public List<ProcessDefinition> getAllWorkFlow() {
//		return getJbpmContext().getGraphSession()
//				.findLatestProcessDefinitions();
//	}
//
//	/**
//	 * 启动流程
//	 * 
//	 * @param businessKey
//	 * @param money
//	 *            TODO
//	 * @param processDefinitionName
//	 * @param userName
//	 * 
//	 * @return
//	 */
//	public ProcessInstance startProcess(Long id, String businessKey,
//			BigDecimal money) {
//
//		ProcessDefinition pd = getJbpmContext().getGraphSession()
//				.getProcessDefinition(id);
//
//		ProcessInstance pi = pd.createProcessInstance(new HashMap(),
//				businessKey);
//		// 子流程使用
//		pi.getContextInstance().createVariable("businessKey", businessKey);
//		pi.getContextInstance().createVariable("money", money.doubleValue());
//
//		pi.signal();
//
//		getJbpmContext().save(pi);
//
//		List<TaskInstance> tis = getJbpmContext().getTaskMgmtSession()
//				.findTaskInstancesByProcessInstance(pi);
//		for (TaskInstance ti : tis) {
//			ti.setActorId(getJbpmContext().getActorId());
//			getJbpmContext().save(ti);
//		}
//
//		return pi;
//	}
//
//	public void startTask(Double money) {
//		log.debug("=========== start task");
//		// 提交开始任务
//		List<TaskInstance> taskList = getJbpmContext().getTaskList();
//		log.debug("{} task:{}", getJbpmContext().getActorId(), taskList.size());
//		for (TaskInstance ti : taskList) {
//			ti.getContextInstance().createVariable("money", money);
//			log.debug("getActorId:{}",ti.getSwimlaneInstance().getActorId());
//			ti.getSwimlaneInstance().setActorId(getJbpmContext().getActorId());
//			ti.end();
//			getJbpmContext().save(ti);
//		}
//	}
//
//	public void checkTasks(ProcessInstance pi) {
//		log.debug("FirstFlowProcessTest.checkTasks()");
//		pi = getJbpmContext().getProcessInstance(pi.getId());
//		List<TaskInstance> coll = getJbpmContext().getTaskMgmtSession()
//				.findTaskInstancesByProcessInstance(pi);
//		log.debug("Process has task:");
//		for (TaskInstance ti : coll) {
//			log.debug(ti.getName());
//			log.debug(ti.getActorId());
//			log.debug(ti.getVariables().toString());
//		}
//		log.debug("end");
//		Assert.assertTrue(pi.hasEnded());
//
//		// jbpmContext.close();
//	}
//
//	public void approveByManager(String pass) {
//		approve(pass);
//	}
//
//	public void approveByBoss(String pass) {
//		approve(pass);
//	}
//
//	private void approve(String pass) {
//		JbpmContext jbpmContext = getJbpmContext();
//		log.debug("+++++++++++++++++++getActorId:{}",jbpmContext.getActorId());
//
//		// 登录用户的任务
//		List<TaskInstance> taskList = jbpmContext.getTaskList();
//		log.debug("self task:{}", taskList.size());
//
//		for (TaskInstance ti : taskList) {
//			ti.end(pass);
//		}
//
//		User user = getUserUtil().findUniqueByProperty("email",
//				jbpmContext.getActorId());
//		log.debug("user roles:{}",user.getRoles());
//		// 待处理的任务
//		List<String> roleNameList = user.getRoleNameList();
//		Assert.assertNotNull(roleNameList);
//		log.debug("{}",roleNameList);
//		
//		List<TaskInstance> findTaskInstances = jbpmContext.getTaskMgmtSession()
//				.findPooledTaskInstances(roleNameList);
//
//		log.debug("shared task:{}", findTaskInstances.size());
//
//		for (TaskInstance ti : findTaskInstances) {
//			log.debug("task getSwimlaneInstance:{}",ti.getSwimlaneInstance().getActorId());
//			
//			// 当前任务转移给审批人 只有处置人可以看到当前任务
//			ti.setActorId(jbpmContext.getActorId());
//			ti.getSwimlaneInstance().setActorId(ti.getActorId());
//			ti.end(pass);
//		}
//
//		// currentJbpmContext.close();
//	}
//
//	/**
//	 * 将公用任务，转换为自己所有
//	 * 
//	 * @param ti
//	 * @return
//	 */
//	public boolean pullTask(TaskInstance ti) {
//		ti.setActorId(getJbpmContext().getActorId());
//		getJbpmContext().save(ti);
//		return true;
//	}
//
//	/**
//	 * 让别人处理
//	 * 
//	 * @param ti
//	 * @return
//	 */
//	public boolean pushTask(TaskInstance ti) {
//		ti.setActorId(null);
//		getJbpmContext().save(ti);
//		return true;
//	}
//
//	public void TaskGo(TaskInstance task, String taskName) {
//		log.debug("task to {}",taskName);
//		task.end(taskName);
//		getJbpmContext().save(task);
//	}
//
//	public TaskInstance getTaskId(Long processInstanceId) {
//		ProcessInstance processInstance = getJbpmContext().getProcessInstance(
//				processInstanceId);
//
//		List<TaskInstance> tasks = getJbpmContext().getTaskMgmtSession()
//				.findTaskInstancesByProcessInstance(processInstance);
//		for (TaskInstance ti : tasks) {
//			return ti;
//		}
//		return null;
//
//		// TaskInstance next = processInstance.getTaskMgmtInstance()
//		// .getTaskInstances().iterator().next();
//		// return next.getToken().getId();
//	}
//
//	public Page<TaskItem> get(Page<TaskItem> page) {
//		page = getTaskItemUtil().get(page);
//		List<TaskItem> result = page.getResult();
//		for (TaskItem taskItem : result) {
//			taskItem.setTask(getTaskId(taskItem.getProcessInstanceId()));
//		}
//		return page;
//	}

}