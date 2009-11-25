package org.supermy.workflow.assignment;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.jbpm.pvm.internal.env.ExecutionContext;
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
	@Override
	public void assign(Assignable arg0, OpenExecution arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void assign(Assignable assignable, ExecutionContext executionContext)
			throws Exception {
		log.debug("daoService:{}", daoService);
		String assignee = null;
		// arg0.setPooledActors(boss);
		// arg0.setActorId(arg0);
		//TODO　String assignee=executionContext.getExecution().getActorId();
		assignable.setAssignee(assignee);
	}

}
