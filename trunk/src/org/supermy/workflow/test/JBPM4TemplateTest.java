package org.supermy.workflow.test;

import java.util.List;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
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
public class JBPM4TemplateTest extends TestBaseService {

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

	@Test
	public void allProcessDefinitions() {
		List<ProcessDefinition> latestProcessDefinitions = jt.getLatestProcessDefinitions();
		for (ProcessDefinition processDefinition : latestProcessDefinitions) {
			log.debug("{}",processDefinition.getKey());
			log.debug("{}",processDefinition.getName());
			log.debug("{}",processDefinition.getImageResourceName());
		}
	}
}
