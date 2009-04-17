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
	public void execute(ExecutionContext arg0) throws Exception {
		Object financeId = arg0.getContextInstance().getVariable("financeId");
		log.info("财务处理子流程中报销ID:{}", financeId);
	}

}
