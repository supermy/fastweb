package org.supermy.workflow.assignment;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.workflow.service.WorkflowService;

/**
 * 授权直接与权限绑定,可以直接用于泳道
 * 
 * @author my
 * 
 */
public class AuthAssignment implements AssignmentHandler {
	@Autowired
	private WorkflowService daoService;

	@Override
	public void assign(Assignable arg0, OpenExecution arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}


}
