package org.supermy.workflow.action;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.msg.db.DbMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.supermy.workflow.domain.OrigApprove;
import org.supermy.workflow.domain.OriginalCertificate;
import org.supermy.workflow.service.ApproveService;

public class ProcessResult implements ActionHandler {

	@Autowired
	private ApproveService daoService;

	public void execute(ExecutionContext arg0) throws Exception {
		String baoxiaoId = (String) arg0.getContextInstance().getVariable(
				"baoxiaoId");
		OriginalCertificate orig = daoService.getOrigUtil().get(
				Long.parseLong(baoxiaoId));

		String issueUser = orig.getUser().getName();

		OrigApprove list = daoService.getApproveUtil().get(
				Long.parseLong(baoxiaoId));
		String result = "不被批准";
		if (list != null) {
			result = list.getResult();
		}
		StringBuffer message = new StringBuffer();
		message.append(issueUser + ":您好！ ");
		message.append("您申请的" + orig.getTitle());
		message.append("已经被" + result);

		// Message msg=new TextMessage(message.toString());
		// msg.setDestination(issueUser);
		// msg.setToken(arg0.getProcessInstance().getRootToken());

		DbMessageService msgService = new DbMessageService();
		// msgService.send(msg);
		msgService.close();

	}

}
