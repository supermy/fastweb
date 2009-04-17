package org.supermy.workflow.action;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.workflow.service.WorkflowService;

public class ProcessResult implements ActionHandler {
	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private WorkflowService daoService;

	public void execute(ExecutionContext executionContext) throws Exception {
		StringBuffer message = new StringBuffer();
		message.append(":您好！ ");
		message.append("您申请的");
		message.append("已经被");
		log.debug(message.toString());
		executionContext.leaveNode();
	}

}
