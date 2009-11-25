package org.supermy.workflow.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import javax.sql.DataSource;

import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.ProcessInstanceQuery;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.history.HistoryActivityInstanceQuery;
import org.jbpm.api.identity.Group;
import org.jbpm.api.identity.User;
import org.jbpm.api.job.Job;
import org.jbpm.api.model.ActivityCoordinates;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.identity.impl.IdentitySessionImpl;
import org.jbpm.pvm.internal.identity.impl.UserImpl;
import org.jbpm.pvm.internal.identity.spi.IdentitySession;
import org.jbpm.pvm.internal.model.Activity;
import org.jbpm.pvm.internal.model.ActivityCoordinatesImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.Transition;
import org.jbpm.pvm.internal.task.OpenTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 莫勇
 * 
 */
@Transactional
@Service
public class JbpmTemplate {

	@Autowired
	private DataSource dataSource;
	@Autowired
	@Qualifier("processEngine")
	private ProcessEngine processEngine;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ExecutionService executionService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private ManagementService managementService;
	@Autowired
	private HistoryService historyService;

	/**
	 * 获取任务列表
	 * 
	 * @param username
	 * @return
	 */
	protected List<Task> getTask(String username) {
		List<Task> taskList = taskService.findPersonalTasks(username);
		return taskList;
	}

	/**
	 * 查看任务(单个任务)
	 * 
	 * @param taskId
	 * @return
	 */
	protected Map<String, Object> view(String taskId) {
		Set<String> set = taskService.getVariableNames(taskId);
		Map<String, Object> map = taskService.getVariables(taskId, set);
		return map;
	}

	/**
	 * 获取所有最新版本的流程定义，结果按名称排序；
	 * 
	 * @return
	 */
	public List<ProcessDefinition> getLatestProcessDefinitions() {
		List<ProcessDefinition> definitions = repositoryService
				.createProcessDefinitionQuery().orderAsc(
						ProcessDefinitionQuery.PROPERTY_NAME).list();

		// 过滤掉key重复流程
		Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
		for (ProcessDefinition pd : definitions) {
			String key = pd.getKey();
			ProcessDefinition processDefinition = map.get(key);

			if ((processDefinition == null)
					|| (processDefinition.getVersion() < pd.getVersion())) {
				map.put(key, pd);
			}
		}

		return new ArrayList<ProcessDefinition>(map.values());
	}

	/**
	 * 返回暂停的流程定义
	 * 
	 * @return
	 */
	public List<ProcessDefinition> getSuspendedProcessDefinitions() {
		return repositoryService.createProcessDefinitionQuery().suspended()
				.orderAsc(ProcessDefinitionQuery.PROPERTY_NAME).list();
	}

	/**
	 * 数据流方式发布流程
	 * 
	 * @param name
	 * @param inputStream
	 */
	public void deployXml(String name, InputStream inputStream) {
		repositoryService.createDeployment().addResourceFromInputStream(name,
				inputStream).deploy();
	}

	/**
	 * 发布zip包中的流程
	 * 
	 * @param inputStream
	 */
	public void deployZip(InputStream inputStream) {
		repositoryService.createDeployment().addResourcesFromZipInputStream(
				new ZipInputStream(inputStream)).deploy();
	}

	/**
	 * 字符串方式发布流程 TODO
	 * 
	 * @param xml
	 */
	public void deployXml(String xml) {
		repositoryService.createDeployment().addResourceFromString(
				"fastwebprocess.jpdl.xml", xml).deploy();
	}

	/**
	 * 暂停流程
	 * 
	 * @param processDefinitionId
	 */
	public void suspendProcessDefinitionById(String processDefinitionId) {
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).uniqueResult();
		repositoryService.suspendDeployment(pd.getDeploymentId());
	}

	/**
	 * 恢复流程
	 * 
	 * @param id
	 */
	public void resumeProcessDefinitionById(String id) {
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(id).uniqueResult();
		repositoryService.resumeDeployment(pd.getDeploymentId());
	}

	/**
	 * 删除流程
	 * 
	 * @param id
	 */
	public void removeProcessDefinitionById(String id) {
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(id).uniqueResult();
		repositoryService.deleteDeploymentCascade(pd.getDeploymentId());
	}

	/**
	 * TODO 指定任务的
	 * 
	 * @param taskId
	 * @return
	 */
	public Set<String> getTransitionsForTask(String taskId) {
		return taskService.getOutcomes(taskId);
	}

	/**
	 * TODO 指定流程实例的
	 * 
	 * @param processInstanceById
	 * @return
	 */
	public List<Transition> getTransitionsForSignalProcess(
			String processInstanceById) {
		ProcessInstance pi = executionService
				.findProcessInstanceById(processInstanceById);
		ExecutionImpl executionImpl = (ExecutionImpl) pi;
		Activity activity = executionImpl.getActivity();
		return activity.getOutgoingTransitions();
	}

	/**
	 * 开始一个流程实例
	 * 
	 * @param processDefinitionId
	 * @param variables
	 * @return
	 */
	public ProcessInstance startProcessId(String processDefinitionId,
			Map<String, Object> variables) {
		return executionService.startProcessInstanceById(processDefinitionId,
				variables);
	}

	/**
	 * 开始一个流程实例
	 * 
	 * @param processDefinitionId
	 * @param variables
	 * @return
	 */
	public ProcessInstance startProcessKey(String key,
			Map<String, Object> variables) {
		return executionService.startProcessInstanceByKey(key, variables);
	}

	/**
	 * 
	 * 发动进程
	 * 
	 * @param processDefinitionId
	 * @param transitionName
	 * @param variables
	 */
	public void signalProcess(String processInstanceId, String transitionName,
			Map<String, Object> variables) {
		executionService.setVariables(processInstanceId, variables);
		executionService.signalExecutionById(processInstanceId, transitionName);
	}

	/**
	 * 获取流程实例
	 * 
	 * @param pdId
	 * @return
	 */
	public List<ProcessInstance> getProcessInstances(String processDefinitionId) {
		return executionService.createProcessInstanceQuery()
				.processDefinitionId(processDefinitionId).list();
	}

	/**
	 * 暂停的流程实例列表
	 * 
	 * @return
	 */
	public List<ProcessInstance> getSuspendedProcessInstances() {
		return executionService.createProcessInstanceQuery().suspended().list();
	}

	/**
	 * TODO
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public ActivityCoordinates getActivityCoordinates(String processInstanceId) {
		ProcessInstance pi = executionService
				.findProcessInstanceById(processInstanceId);

		return repositoryService.getActivityCoordinates(pi
				.getProcessDefinitionId(), ((ActivityExecution) pi)
				.getActivityName());
	}

	/**
	 * 暂停流程实例
	 * 
	 * @param id
	 */
	public void suspendProcessInstance(String id) {
		ProcessInstance pi = executionService.findProcessInstanceById(id);
		((ExecutionImpl) pi).suspend();
	}

	/**
	 * 恢复流程实例
	 * 
	 * @param id
	 */
	public void resumeProcessInstance(String id) {
		ProcessInstance pi = executionService.createProcessInstanceQuery()
				.suspended().processInstanceId(id).uniqueResult();
		((ExecutionImpl) pi).resume();
	}

	/**
	 * 结束流程实例
	 * 
	 * @param id
	 */
	public void endProcessInstance(String id) {
		executionService.endProcessInstance(id, Execution.STATE_ENDED);
	}

	/**
	 * 删除流程实例
	 * 
	 * @param id
	 */
	public void removeProcessInstance(String id) {
		executionService.deleteProcessInstance(id);
	}

	/**
	 * 返回任务变量
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getVariablesForTask(String id) {
		Set<String> names = taskService.getVariableNames(id);
		return taskService.getVariables(id, names);
	}

	/**
	 * 返回流程实例变量
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getVariablesForProcess(String id) {
		Set<String> names = executionService.getVariableNames(id);
		return executionService.getVariables(id, names);
	}

	/**
	 * 返回所有的任务
	 * 
	 * @return
	 */
	public List<Task> getTasks() {
		return taskService.createTaskQuery().list();
	}

	/**
	 * 按人物名称返回任务列表
	 * 
	 * @param username
	 * @return
	 */
	public List<Task> getPersonalTasks(String username) {
		return taskService.findPersonalTasks(username);
	}

	/**
	 * 返回所属组的任务列表
	 * 
	 * @param username
	 * @return
	 */
	public List<Task> getGroupTasks(String username) {
		return taskService.findGroupTasks(username);
	}

	/**
	 * 接收任务
	 * 
	 * @param dbid
	 * @param username
	 */
	public void takeTask(String dbid, String username) {
		taskService.takeTask(dbid, username);
	}

	/**
	 * 取消任务
	 * 
	 * @param dbid
	 */
	public void cancelTask(String dbid) {
		Task task = taskService.getTask(dbid);
		OpenTask openTask = (OpenTask) task;
		openTask.delete("no reason");
	}

	/**
	 * 提交任务
	 * 
	 * @param dbid
	 */
	public void releaseTask(String dbid) {
		taskService.assignTask(dbid, null);
	}

	/**
	 * 返回流程定义
	 * 
	 * @param id
	 * @return
	 */
	public InputStream getResourceFromProcessDefinition(String id) {
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(id).uniqueResult();

		if (pd != null) {
			String processName = pd.getName() + ".jpdl.xml";

			System.out.println("processName0:  " + processName);

			return repositoryService.getResourceAsStream(pd.getDeploymentId(),
					processName);
		} else {
			return null;
		}
	}

	/**
	 * 返回流程定义
	 * 
	 * @param id
	 * @return
	 */
	public InputStream getResourceFromProcessInstance(String id) {
		ProcessInstanceQuery query = executionService
				.createProcessInstanceQuery();
		query.processInstanceId(id);

		Execution processInstance = (Execution) query.uniqueResult();
		ProcessDefinition pd = ((ExecutionImpl) processInstance)
				.getProcessDefinition();
		String processName = pd.getName() + ".jpdl.xml";
		System.out.println("processName:  " + processName);

		return repositoryService.getResourceAsStream(pd.getDeploymentId(),
				processName);
	}

	/**
	 * 返回所有用户
	 * 
	 * @return
	 */
	public List<User> getUsers() {
		return identityService.findUsers();
	}

	/**
	 * 返回用户所在的组
	 * 
	 * @param id
	 * @return
	 */
	public List<Group> getGroupsByUser(String id) {
		return identityService.findGroupsByUser(id);
	}

	/**
	 * 增加用户
	 * 
	 * @param id
	 * @param password
	 * @param givenName
	 * @param familyName
	 */
	public void addUser(String id, String password, String givenName,
			String familyName) {
		identityService.createUser(id, password, givenName, familyName);
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 */
	public void removeUser(String id) {
		identityService.deleteUser(id);
	}

	/**
	 * 所有的用户组
	 * 
	 * @return
	 */
	public List<Group> getGroups() {
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		Environment env = environmentFactory.openEnvironment();
		IdentitySession identitySession = env.get(IdentitySession.class);
		return ((IdentitySessionImpl) identitySession).findGroups();
	}

	/**
	 * 增加组
	 * 
	 * @param name
	 * @param type
	 * @param parentGroupId
	 */
	public void addGroup(String name, String type, String parentGroupId) {
		identityService.createGroup(name, type, parentGroupId);
	}

	/**
	 * 删除组
	 * 
	 * @param id
	 */
	public void removeGroup(String id) {
		identityService.deleteGroup(id);
	}

	/**
	 * 用户增加到组 按角色
	 * 
	 * @param userId
	 * @param groupId
	 * @param role
	 */
	public void addMember(String userId, String groupId, String role) {
		identityService.createMembership(userId, groupId, role);
	}

	/**
	 * 用户检测
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkLogin(String username, String password) {
		UserImpl user = (UserImpl) identityService.findUserById(username);
		return (user != null) && password.equals(user.getPassword());
	}

	/**
	 * 得到流程定义
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public ProcessDefinition getProcessDefinitionByProcessInstanceId(
			String processInstanceId) {
		ProcessInstance pi = executionService
				.findProcessInstanceById(processInstanceId);

		return ((ExecutionImpl) pi).getProcessDefinition();
	}

	public String reportOverallActivity() {
		StringBuffer buff = new StringBuffer(
				"<graph showNames='Overall Activity' decimalPrecision='2'>");
		Connection conn = null;

		try {
			conn = dataSource.getConnection();

			Statement state = conn.createStatement();
			ResultSet rs = state
					.executeQuery("SELECT d.DBID_ as dpl, p.STRINGVAL_ as processId"
							+ " FROM JBPM4_DEPLOYMENT d, JBPM4_DEPLOYPROP p"
							+ " WHERE p.KEY_='pdid'"
							+ " AND d.DBID_=p.DEPLOYMENT_"
							+ " GROUP BY dpl,processId");

			while (rs.next()) {
				buff.append("<set name='").append(rs.getString("dpl")).append(
						"' value='").append(rs.getString("processId")).append(
						"'/>");
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					System.out.println(ex);
				}
			}
		}

		buff.append("</graph>");

		return buff.toString();
	}

	public String reportMostActiveProcess() {
		StringBuffer buff = new StringBuffer(
				"<graph showNames='Most Active Process' decimalPrecision='2'>");
		Connection conn = null;

		try {
			conn = dataSource.getConnection();

			Statement state = conn.createStatement();

			String sql = "  select d.STRINGVAL_ as id, count(p.ID_) as num"
					+ "    from JBPM4_DEPLOYPROP d left join JBPM4_HIST_PROCINST p"
					+ "      on d.STRINGVAL_=p.PROCDEFID_"
					+ "   where d.KEY_='pdid' group by d.STRINGVAL_";
			ResultSet rs = state.executeQuery(sql);

			while (rs.next()) {
				buff.append("<set name='").append(rs.getString("id")).append(
						"' value='").append(rs.getString("num")).append("'/>");
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					System.out.println(ex);
				}
			}
		}

		buff.append("</graph>");

		return buff.toString();
	}

	public List<ActivityCoordinates> getHistoryActivities(
			String processInstanceId) {
		HistoryActivityInstanceQuery query = historyService
				.createHistoryActivityInstanceQuery();
		List<HistoryActivityInstance> activities = query.executionId(
				processInstanceId).list();

		ProcessDefinitionImpl processDefinition = (ProcessDefinitionImpl) this
				.getProcessDefinitionByProcessInstanceId(processInstanceId);

		List<ActivityCoordinates> list = new ArrayList<ActivityCoordinates>();
		ActivityImpl start = processDefinition.getInitial();
		ActivityCoordinates startAc = start.getCoordinates();
		startAc = new ActivityCoordinatesImpl(startAc.getX(), startAc.getY(),
				48, 48);
		list.add(startAc);

		for (HistoryActivityInstance activity : activities) {
			String activityName = activity.getActivityName();
			ActivityImpl activityImpl = processDefinition
					.findActivity(activityName);
			ActivityCoordinates ac = activityImpl.getCoordinates();

			if (activityImpl.getType().equals("decision")
					|| activityImpl.getType().equals("fork")
					|| activityImpl.getType().equals("join")
					|| activityImpl.getType().equals("end")
					|| activityImpl.getType().equals("end-cancel")
					|| activityImpl.getType().equals("end-error")) {
				ac = new ActivityCoordinatesImpl(ac.getX(), ac.getY(), 48, 48);
			}

			list.add(ac);
		}

		return list;
	}

	/**
	 * @param processInstanceId
	 * @return
	 */
	public List<HistoryActivityInstance> getHistoryActivitiesByProcessInstanceId(
			String processInstanceId) {
		HistoryActivityInstanceQuery query = historyService
				.createHistoryActivityInstanceQuery();
		return query.executionId(processInstanceId).list();
	}

	/**
	 * @return
	 */
	public List<Job> getJobs() {
		return managementService.createJobQuery().list();
	}

	public void deployProcess(String processZipFileName) {
		try {
			ZipInputStream zin = new ZipInputStream(new FileInputStream(
					processZipFileName));
			repositoryService.createDeployment()
					.addResourcesFromZipInputStream(zin).deploy();
			zin.close();

		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 查询制定的流程
	 * 
	 * @param id
	 * @return
	 */
	public ProcessDefinition getProcessDefinition(String id) {
		return repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(id).uniqueResult();
	}

	/**
	 * 查询所有的流程实例
	 * 
	 * @return
	 */
	public List<ProcessInstance> getAllProcessInstance() {
		return executionService.createProcessInstanceQuery().list();
	}
}
