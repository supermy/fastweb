package org.supermy.workflow.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.hibernate.SessionFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.db.TaskMgmtSession;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.Assert;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supermy.core.domain.User;
import org.supermy.core.service.BaseService;
import org.supermy.core.service.FastwebTemplate;
import org.supermy.workflow.domain.OrigApprove;

import bsh.util.Util;

/**
 * 审核报销流程的主要业务类
 * 
 * @author my
 * 
 */
@Transactional
@Service
public class WorkflowService extends BaseService {

	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	private FastwebTemplate<User, Long> userUtil;
	private FastwebTemplate<OrigApprove, Long> approveUtil;

	@Autowired
	private JbpmConfiguration jbpmConfiguration;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		userUtil = new FastwebTemplate<User, Long>(sessionFactory, null,
				User.class);
		approveUtil = new FastwebTemplate<OrigApprove, Long>(sessionFactory,
				null, OrigApprove.class);
	}

	public JbpmContext getJbpmContext() {
		log.debug("get jbpm context:");
		JbpmContext jbpmContext = jbpmConfiguration.getCurrentJbpmContext();
		if (jbpmContext == null) {
			log.debug("create jbpm context ... ... ");
			jbpmContext = jbpmConfiguration.createJbpmContext();
		}
		return jbpmContext;
	}

	/**
	 * 测试的时候使用 filter自动关闭
	 */
	public void closeJbpmContext() {
		log.debug("close context:");
		jbpmConfiguration.getCurrentJbpmContext().close();
	}

	/**
	 * @return the userUtil
	 */
	public FastwebTemplate<User, Long> getUserUtil() {
		return userUtil;
	}

	/**
	 * @return the approveUtil
	 */
	public FastwebTemplate<OrigApprove, Long> getApproveUtil() {
		return approveUtil;
	}

	/**
	 * 
	 * 发布zip压缩的流程 <br />
	 * 如下结构： <br/>
	 * process1.zip<br/>
	 * --processdefinition.xml<br/>
	 * --classes<br/>
	 * process2.zip<br/>
	 * --processdefinition.xml<br/>
	 * --classes <br/>
	 * 流程档案部署后，就不再需要这个压缩包及其中的文件了，
	 * 数据库中的jbpm_bytearray和jbpm_byteblock两张表存储了流程档案的文件内容 ，<br/>
	 * 它们把流程档案中的文件拆开了存到了数据库。jbpm-bytearray存储了流程档案的目录，
	 * jbpm_bytebolck则是将文件的二进制内容存了进去。对于流程档案中包含的流程定义中Action和Task等Java
	 * ByteCode信息（.class文件），引擎会从数据库读出byte[]数组然后作为类加载
	 * ，如果你的类存在于引擎可见的classpath，那么就会从那里加载
	 * ，所以说流程所需的.class文件可以打包到流程档案，也可以不打包到流程档案，而是放在你的classpath目录下。
	 * 
	 * @param processZipFileName
	 */
	public void deployProcess(String processZipFileName) {
		// 获取上下文
		JbpmContext jbpmContext = getJbpmContext();
		try {
			ZipInputStream zin = new ZipInputStream(new FileInputStream(
					processZipFileName));
			ProcessDefinition processDefinition = ProcessDefinition
					.parseParZipInputStream(zin);
			jbpmContext.deployProcessDefinition(processDefinition);
			zin.close();

		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		// finally {
		// jbpmContext.close();
		// }
	}

	/**
	 * 取得所有的工作流定义<br>
	 * 
	 * @return 工作流定义列表 　　
	 */
	public List<ProcessDefinition> getAllWorkFlow() {
		JbpmContext jbpmContext = getJbpmContext();
		List<ProcessDefinition> results = jbpmContext.getGraphSession()
				.findLatestProcessDefinitions();
		// jbpmContext.close();
		return results;
	}

	/**
	 * 启动流程
	 * 
	 * @param processDefinitionName
	 * @param businessKey
	 * @param userName
	 * @return
	 */
	public ProcessInstance startProcess(String processDefinitionName,
			String businessKey) {
		JbpmContext jbpmContext = getJbpmContext();

		ProcessInstance pi;

		ProcessDefinition pd = jbpmContext.getGraphSession()
				.findLatestProcessDefinition(processDefinitionName);

		pi = pd.createProcessInstance(new HashMap(), businessKey);
		// 子流程使用
		pi.getContextInstance().createVariable("businessKey", businessKey);
		pi.signal();

		jbpmContext.save(pi);

		return pi;
	}

	public void startTask(String userName, int money) {
		JbpmContext jbpmContext = getJbpmContext();
		// 提交开始任务
		List<TaskInstance> taskList = jbpmContext.getTaskList();
		log.debug(userName + " task:{}", taskList);
		for (TaskInstance ti : taskList) {
			ti.getContextInstance().createVariable("money", money);
			ti.end();
			jbpmContext.save(ti);
		}
	}

	public void checkTasks(ProcessInstance pi) {
		log.debug("FirstFlowProcessTest.checkTasks()");
		JbpmContext jbpmContext = getJbpmContext();
		pi = jbpmContext.getProcessInstance(pi.getId());
		List<TaskInstance> coll = jbpmContext.getTaskMgmtSession()
				.findTaskInstancesByProcessInstance(pi);
		log.debug("Process has task:");
		for (TaskInstance ti : coll) {
			log.debug(ti.getName());
			log.debug(ti.getActorId());
			log.debug(ti.getVariables().toString());
		}
		log.debug("end");
		Assert.assertTrue(pi.hasEnded());

		// jbpmContext.close();
	}

	public void approveByManager(boolean pass) {
		JbpmContext jbpmContext = getJbpmContext();

		// 登录用户的任务
		List<TaskInstance> taskList = jbpmContext.getTaskList();
		log.debug("主管:{}", taskList.size());

		for (TaskInstance ti : taskList) {
			if (pass) {
				ti.end("主管审批通过");
			} else {
				ti.end("主管驳回");
			}
		}

		User user = getUserUtil().findUniqueByProperty("email",
				jbpmContext.getActorId());

		// 待处理的任务
		List<TaskInstance> findTaskInstances = jbpmContext.getTaskMgmtSession()
				.findPooledTaskInstances(user.getRoleNameList());

		log.debug("主管:{}", findTaskInstances.size());

		for (TaskInstance ti : findTaskInstances) {
			// 当前任务转移给审批人 只有处置人可以看到当前任务
			ti.setActorId(jbpmContext.getActorId());

			if (pass) {
				ti.end("主管审批通过");
			} else {
				ti.end("主管驳回");
			}
		}

		// currentJbpmContext.close();
	}

	public void approveByBoss(boolean pass) {
		JbpmContext jbpmContext = getJbpmContext();
		// 登录用户的任务
		List<TaskInstance> taskList = jbpmContext.getTaskList();
		log.debug("经理:{}", taskList.size());

		for (TaskInstance ti : taskList) {
			if (pass) {
				ti.end("经理同意");
			} else {
				ti.end("经理不同意");
			}
		}
		User user = getUserUtil().findUniqueByProperty("email",
				jbpmContext.getActorId());

		// 退回
		List<TaskInstance> findTaskInstances = jbpmContext.getTaskMgmtSession()
				.findPooledTaskInstances(user.getRoleNameList());

		log.debug("经理:{}", findTaskInstances.size());

		for (TaskInstance ti : findTaskInstances) {
			ti.setActorId(jbpmContext.getActorId());
			if (pass) {
				ti.end("经理同意");
			} else {
				ti.end("经理不同意");
			}
		}

		// currentJbpmContext.close();
	}

	/**
	 * 将公用任务，转换为自己所有
	 * 
	 * @param taskid
	 * @param userName
	 * @return
	 */
	public boolean pullTask(Long taskid, String userName) {
		JbpmContext jbpmContext = getJbpmContext();

		if (jbpmContext == null) {
			jbpmContext = jbpmConfiguration.createJbpmContext();
		}
		try {
			TaskInstance ti = jbpmContext.getTaskInstance(taskid);
			ti.setActorId(userName);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		// finally {
		// jbpmContext.close();
		// }

		return false;
	}

}