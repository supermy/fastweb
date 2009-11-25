package org.supermy.workflow.processdefinition;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

public class LeaveDays implements DecisionHandler {

	private static final long serialVersionUID = 1L;

	public String decide(OpenExecution execution) {
		Integer days = (Integer) execution.getVariable("请假天数");
		if (days > 3) {
			return "请假天数>3";
		} else
			return "to 结束";
	}
}