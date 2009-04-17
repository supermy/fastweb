package org.supermy.workflow.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.zip.ZipInputStream;

import org.hibernate.SessionFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.job.Job;
import org.jbpm.msg.db.DbMessageService;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.workflow.jbpm31.JbpmCallback;
import org.springmodules.workflow.jbpm31.JbpmTemplate;
import org.supermy.core.domain.User;
import org.supermy.core.service.BaseService;
import org.supermy.core.service.FastwebTemplate;
import org.supermy.workflow.domain.OrigApprove;
import org.supermy.workflow.domain.OrigItem;
import org.supermy.workflow.domain.OriginalCertificate;
import org.supermy.workflow.web.workflow.WorkflowAction;

/**
 * 审核报销流程的主要业务类
 * 
 * @author my
 * 
 */
@Transactional
@Service
public class ApproveService extends BaseService {

	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	private FastwebTemplate<User, Long> userUtil;
	private FastwebTemplate<OriginalCertificate, Long> origUtil;
	private FastwebTemplate<OrigItem, Long> itemUtil;
	private FastwebTemplate<OrigApprove, Long> approveUtil;

	@Autowired
	private JbpmTemplate jbpmUtil;
	@Autowired
	private JbpmConfiguration jbpmConfiguration;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		userUtil = new FastwebTemplate<User, Long>(sessionFactory, null,
				User.class);
		origUtil = new FastwebTemplate<OriginalCertificate, Long>(
				sessionFactory, null, OriginalCertificate.class);
		itemUtil = new FastwebTemplate<OrigItem, Long>(sessionFactory, null,
				OrigItem.class);
		approveUtil = new FastwebTemplate<OrigApprove, Long>(sessionFactory,
				null, OrigApprove.class);

	}

	public JbpmContext getJbpmContext() {
		JbpmContext jbpmContext = jbpmConfiguration.getCurrentJbpmContext();
		if (jbpmContext == null) {
			jbpmContext = jbpmConfiguration.createJbpmContext();
		}
		return jbpmContext;
	}

	// //业务逻辑start

	public void save(OriginalCertificate obj) {
		// 保存关系处理 TODO
		origUtil.save(obj);

		JbpmContext jbpmContext = jbpmUtil.getJbpmConfiguration()
				.getCurrentJbpmContext();
		if (jbpmContext == null) {
			jbpmContext = jbpmUtil.getJbpmConfiguration().createJbpmContext();
		}

		try {
			// 获取流程定义
			ProcessDefinition pd = jbpmContext.getGraphSession()
					.findLatestProcessDefinition("baoxiao");
			// 开始一个新的流程实例
			ProcessInstance pi = pd.createProcessInstance();
			// 设置流程实例变量
			pi.getContextInstance().setVariable(User.class.getName(),
					obj.getUser().getId());
			// 开始任务实例
			TaskInstance ti = pi.getTaskMgmtInstance()
					.createStartTaskInstance();

			// 角色设置
			ti.setActorId(obj.getUser().getId().toString());
			// 将单据于流程挂钩
			ti.setVariable(origUtil.getClass().getName(), obj.getId()
					.toString());
			ti.end();

		} finally {
			jbpmContext.close();
		}

	}

	/**
	 * 查找用户的任务列表
	 * 
	 * @param userid
	 * @return
	 */
	public List<Map<String, Object>> findTaskListByUserId(String userid) {

		JbpmContext jbpmContext = getJbpmContext();

		List<TaskInstance> personTasks = jbpmContext.getTaskMgmtSession()
				.findTaskInstances(userid);
		List<TaskInstance> groupTasks = jbpmContext.getTaskMgmtSession()
				.findPooledTaskInstances(userid);

		personTasks.addAll(groupTasks);
		List<Map<String, Object>> userlist = new ArrayList<Map<String, Object>>();
		for (TaskInstance el : groupTasks) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("ActorId", el.getActorId());
			m.put("Description", el.getDescription());
			m.put("Taskid", el.getId());
			m.put("Taskname", el.getName());
			m.put("Date", el.getCreate());
			m.put("Tokenid", el.getToken().getId());
			userlist.add(m);
		}

		jbpmContext.close();
		return userlist;
	}

	/**
	 * 查询用户自身任务列表
	 * 
	 * @param userid
	 * @return
	 */
	@Deprecated
	public List<Map<String, Object>> findUseTaskListByUserId(String userid) {
		JbpmContext jbpmContext = jbpmConfiguration.getCurrentJbpmContext();
		if (jbpmContext == null) {
			jbpmContext = jbpmConfiguration.createJbpmContext();
		}

		List<TaskInstance> temp = findAllTaskInstanceByActorId(userid);
		List<Map<String, Object>> userlist = new ArrayList<Map<String, Object>>();
		for (TaskInstance el : temp) {
			TaskInstance tt = jbpmContext.getTaskMgmtSession().getTaskInstance(
					el.getId());
			if (!tt.getToken().getProcessInstance().hasEnded()
					&& el.getName().equals("填写报销单")) {

				Map<String, Object> m = new HashMap<String, Object>();

				m.put("ActorId", el.getActorId());
				m.put("Description", el.getDescription());
				m.put("Taskid", el.getId());

				OriginalCertificate orig = origUtil.get(Long.parseLong(tt
						.getVariable("baoxiaoId").toString()));
				m.put("Taskname", orig.getTitle());
				m.put("Date", orig.getStart());
				m.put("Tokenid", tt.getToken().getId());
				userlist.add(m);
			}

		}
		jbpmContext.close();
		return userlist;
	}

	@Deprecated
	public Vector findUserMessageByUsername(String username) {
		DbMessageService msgService = new DbMessageService();
		Job msg = null;
		msgService.send(msg);
		msgService.close();
		JbpmContext c = jbpmConfiguration.getCurrentJbpmContext();
		msg.getJobExecutorName();
		// c.getJobSession().findJobsByToken()

		// Job j=new Job();

		/*
		 * JbpmContext jbpmContext = jbpmConfiguration.getCurrentJbpmContext();
		 * if (jbpmContext == null) { jbpmContext =
		 * jbpmConfiguration.createJbpmContext(); } Vector mess = new Vector();
		 * 
		 * List<TextMessage> list =
		 * jbpmContext.getMessagingSession().findMessages(username); for
		 * (TextMessage el : list) { TextMessage el = (TextMessage) iter.next();
		 * MessageForm form = new MessageForm();
		 * form.setId(String.valueOf(el.getId()));
		 * form.setContext(el.getText()); mess.add(form); } return mess;
		 */
		return null;
	}

	/**
	 * 将公用任务，转换为自己所有
	 * 
	 * @param taskid
	 * @param userName
	 * @return
	 */
	public boolean pullTask(Long taskid, String userName) {
		JbpmContext jbpmContext = jbpmConfiguration.getCurrentJbpmContext();
		if (jbpmContext == null) {
			jbpmContext = jbpmConfiguration.createJbpmContext();
		}
		try {
			TaskInstance ti = jbpmContext.getTaskInstance(taskid);
			ti.setActorId(userName);
			jbpmContext.close();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	public OriginalCertificate findOriginalCertificate(String tid) {
		JbpmContext jbpmContext = jbpmConfiguration.getCurrentJbpmContext();
		if (jbpmContext == null) {
			jbpmContext = jbpmConfiguration.createJbpmContext();
		}
		TaskInstance ti = jbpmContext.getTaskInstance(Long.parseLong(tid));

		String baoiaoid = (String) ti.getVariable("baoxiaoId");
		OriginalCertificate orig = origUtil.get(Long.parseLong(baoiaoid));
		return orig;
	}

	public boolean audit(OrigApprove obj, String tid) {

		// 级联数据处理，struts自动完成
		obj.getOrigCert();
		approveUtil.save(obj);

		JbpmContext jbpmContext = jbpmConfiguration.getCurrentJbpmContext();
		if (jbpmContext == null) {
			jbpmContext = jbpmConfiguration.createJbpmContext();
		}

		try {
			TaskInstance ti = jbpmContext.getTaskMgmtSession().getTaskInstance(
					Long.parseLong(tid));
			if (obj.getResult() == "主管同意" || obj.getResult() == "主管不同意") {
				ti.end("主管同意");
			} else {
				ti.end("要求修改");
			}

			return true;

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			jbpmContext.close();
		}
		return false;
	}

	/**
	 * @return the userUtil
	 */
	public FastwebTemplate<User, Long> getUserUtil() {
		return userUtil;
	}

	/**
	 * @return the origUtil
	 */
	public FastwebTemplate<OriginalCertificate, Long> getOrigUtil() {
		return origUtil;
	}

	/**
	 * @return the itemUtil
	 */
	public FastwebTemplate<OrigItem, Long> getItemUtil() {
		return itemUtil;
	}

	/**
	 * @return the approveUtil
	 */
	public FastwebTemplate<OrigApprove, Long> getApproveUtil() {
		return approveUtil;
	}

	/**
	 * @return the jbpmUtil
	 */
	public JbpmTemplate getJbpmUtil() {
		return jbpmUtil;
	}

	public ProcessInstance getUrl(TaskInstance ti) {
		Token token = ti.getToken();
		return token.getProcessInstance();
	}

	@Deprecated
	public List<TaskInstance> findAllTaskInstanceByActorId(String actorId) {
		TaskInstance ti = new TaskInstance();
		ti.getActorId();
		String hql = "from TaskInstance ti where ti.actorId='" + actorId + "'";
		List<TaskInstance> list = jbpmUtil.getHibernateTemplate().find(hql);
		return list;
	}

	/**
	 * 保存流程定义<br/>
	 * 
	 * @param xmlStr
	 *            流程定义的JPDL的XML字符串形式 　　
	 */
	@Deprecated
	public void saveWorkFlowDefinition(String xmlStr) {

		log.debug("saveWorkFlowDefinition start");

		final ProcessDefinition processDefinition = ProcessDefinition
				.parseXmlString(xmlStr);
		// call spring jbpm call back for save processdefinition
		jbpmUtil.execute(new JbpmCallback() {
			public Object doInJbpm(JbpmContext jbpmSession) {
				// jbpmSession.beginTransaction();
				GraphSession gSession = jbpmSession.getGraphSession();
				gSession.deployProcessDefinition(processDefinition);
				// jbpmSession.commitTransaction();//这里我们只需要提交事物即可!关闭SESSION的工作JBPMTEMPLATE回帮助我们完成!
				return processDefinition;
			}
		});
		log.debug("saveWorkFlowDefinition end");
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
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		try {
			ZipInputStream zin = new ZipInputStream(new FileInputStream(
					processZipFileName));
			ProcessDefinition processDefinition = ProcessDefinition
					.parseParZipInputStream(zin);
			jbpmContext.deployProcessDefinition(processDefinition);
			zin.close();

		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			jbpmContext.close();
		}
	}

	/**
	 * 启动流程并且开始任务
	 * 
	 * @param processDefinition
	 * @param variables
	 * @param businessKey
	 * @param userName
	 */
	public ProcessInstance startProcess(ProcessDefinition processDefinition,
			Map<String, Object> variables, String businessKey) {
		JbpmContext jbpmContext = getJbpmContext();
		ProcessInstance pi;
		try {
			 pi = processDefinition.createProcessInstance(
					variables, businessKey);
			// // 设定人物变量，可以在权限的时候自动获取
			// pi.getContextInstance().setVariable("userId", userName);
			jbpmContext.save(pi);

		} finally {
			jbpmContext.close();
		}
		
		return pi;
	}

	public void startTask(ProcessInstance pi, String userName) {
		JbpmContext jbpmContext = getJbpmContext();
		try {
			// 提交开始任务
			TaskInstance ti = pi.getTaskMgmtInstance()
					.createStartTaskInstance();
			// 任务退回的时候，直接退给本人
			ti.setActorId(userName);
			ti.end();

		} finally {
			jbpmContext.close();
		}
	}

}