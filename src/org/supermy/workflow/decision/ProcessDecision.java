package org.supermy.workflow.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.workflow.service.WorkflowService;

public class ProcessDecision implements DecisionHandler {
	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

//	@Autowired
//	private ApproveService daoService;

	public String decide(ExecutionContext ec) throws Exception {
		log.debug("decide");
		// 默认直接进入最后处理结果节点
		String go = "";

		String financeId = (String) ec.getProcessInstance().getKey();
		int count = (Integer) ec.getProcessInstance().getContextInstance()
				.getVariable("money");
		
		log.debug("id:{} ,money:{}",financeId, count);
		if (count > 10000) {
			go = "大于10000";
		} else
			go = "小于10000";
		return go;
	}

}
