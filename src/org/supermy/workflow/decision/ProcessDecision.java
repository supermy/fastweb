package org.supermy.workflow.decision;

import java.util.Set;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.workflow.domain.OrigApprove;
import org.supermy.workflow.domain.OrigItem;
import org.supermy.workflow.domain.OriginalCertificate;
import org.supermy.workflow.service.ApproveService;

public class ProcessDecision implements DecisionHandler {

	@Autowired
	private ApproveService daoService;

	public String decide(ExecutionContext arg0) throws Exception {
		// 默认直接进入最后处理结果节点
		String go = "to result";

		String financeId = (String) arg0.getProcessInstance().getKey();

		OriginalCertificate orig = daoService.getOrigUtil().get(
				Long.parseLong(financeId));
		Set<OrigItem> bxItem = orig.getItems();
		double count = 0.00;
		for (OrigItem origItem : bxItem) {
			count = count + origItem.getMoney();
		}
		Set approve = orig.getApprovals();

		OrigApprove element = (OrigApprove) approve.iterator().next();
		String result = element.getResult();

		if (result.equals("管理者同意")) {
			if (count > 10000) {
				go = "to boss";
			} else
				go = "to caiwu";
		}
		return go;
	}

}
