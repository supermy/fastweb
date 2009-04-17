package org.supermy.workflow.assignment;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.workflow.service.WorkflowService;

/**
 * 授权直接与权限绑定,可以直接用于泳道
 * 
 * @author my
 * 
 */
public class UserAssignment implements AssignmentHandler {
	private org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private WorkflowService daoService;

	public void assign(Assignable assignable, ExecutionContext executionContext)
			throws Exception {
		log.debug("daoService:{}", daoService);
		// arg0.setPooledActors(boss);
		// arg0.setActorId(arg0);
		assignable.setActorId(executionContext.getJbpmContext().getActorId());
	}

}
