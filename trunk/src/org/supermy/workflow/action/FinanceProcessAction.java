package org.supermy.workflow.action;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.slf4j.LoggerFactory;

public class FinanceProcessAction implements ActionHandler {

	private org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	/*
	 * 财务子流程没有任务节点，只有一个自动节点，在这个自动节点里只是添加了一个Action用来输出从主流程中得到的流程变量。
	 * 
	 * @see
	 * org.jbpm.graph.def.ActionHandler#execute(org.jbpm.graph.exe.ExecutionContext
	 * )
	 */
	public void execute(ExecutionContext executionContext) throws Exception {
		log.debug("财务处理...");

		String actorId = executionContext.getJbpmContext().getActorId();
		log.debug("actorId:{}", actorId);

		int count = (Integer) executionContext.getProcessInstance()
				.getContextInstance().getVariable("money");
		String mainID = (String) executionContext.getProcessInstance()
				.getContextInstance().getVariable("businessKey");

		log.debug("businessKey:{} ,money:{}", mainID, count);

		// Object financeId = executionContext.getContextInstance().getVariable(
		// "financeId");
		// log.info("财务处理子流程中报销ID:{}", financeId);
		executionContext.leaveNode();
	}

}
