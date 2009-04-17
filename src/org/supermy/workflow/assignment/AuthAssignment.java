package org.supermy.workflow.assignment;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.workflow.service.ApproveService;

/**
 * 授权直接与权限绑定,可以直接用于泳道
 * 
 * @author my
 * 
 */
public class AuthAssignment implements AssignmentHandler {
	@Autowired
	private ApproveService daoService;

	public void assign(Assignable arg0, ExecutionContext arg1) throws Exception {
		// arg0.setPooledActors(boss);
		// arg0.setActorId(arg0);
	}

}
