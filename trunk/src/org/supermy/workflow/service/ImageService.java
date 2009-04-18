package org.supermy.workflow.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.xpath.DefaultXPath;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ImageService {
	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private JbpmConfiguration jbpmConfiguration;

	private ProcessDefinition processDefinition;

	private static long taskInstanceId;

	public JbpmContext getJbpmContext() {

		log.debug("get jbpm context:");
		JbpmContext jbpmContext = jbpmConfiguration.getCurrentJbpmContext();
		if (jbpmContext == null) {
			log.debug("create jbpm context ... ... ");
			jbpmContext = jbpmConfiguration.createJbpmContext();
		}

		return jbpmContext;

	}

	public Token getCurrentToken(long taskInstanceId) {

		JbpmContext jbpmContext = this.getJbpmContext();

		TaskInstance taskInstance = jbpmContext.getTaskMgmtSession()
				.loadTaskInstance(taskInstanceId);

		return taskInstance.getToken();

	}

	public void setJbpmConfiguration(JbpmConfiguration jbpmConfiguration) {

		this.jbpmConfiguration = jbpmConfiguration;

	}

	private ProcessDefinition getProcessDefinition(long taskInstanceId) {

		return this.getCurrentToken(taskInstanceId).getProcessInstance()
				.getProcessDefinition();

	}

	public long getProcessDefinitionID(long taskInstanceId) {

		return this.getProcessDefinition(taskInstanceId).getId();

	}

	public byte[] getGpdBytes(long taskInstanceId) {

		return this.getProcessDefinition(taskInstanceId).getFileDefinition()
				.getBytes("gpd.xml");

	}

	public byte[] getImageBytes(long taskInstanceId) {

		return this.getProcessDefinition(taskInstanceId).getFileDefinition()
				.getBytes("processimage.jpg");

	}

	public List walkTokens(List allTokens, long taskInstanceId)

	{

		Map children = this.getCurrentToken(taskInstanceId).getChildren();

		if (children != null && children.size() > 0)

		{

			Collection childTokens = children.values();

			for (Iterator iterator = childTokens.iterator(); iterator.hasNext();)

			{

				Token child = (Token) iterator.next();

				walkTokens(allTokens, taskInstanceId);

			}

		}

		allTokens.add(this.getCurrentToken(taskInstanceId));

		return allTokens;

	}

	public Token getParentToken(Token token) {

		return token.getParent();

	}

	public int[] extractBoxConstraint(Element root, Token token) {

		int[] result = new int[4];

		String nodeName = token.getNode().getName();

		XPath xPath = new DefaultXPath("//node[@name='" + nodeName + "']");

		Element node = (Element) xPath.selectSingleNode(root);

		result[0] = Integer.valueOf(node.attribute("x").getValue()).intValue();

		result[1] = Integer.valueOf(node.attribute("y").getValue()).intValue();

		result[2] = Integer.valueOf(node.attribute("width").getValue())
				.intValue();

		result[3] = Integer.valueOf(node.attribute("height").getValue())
				.intValue();

		return result;

	}

	public int[] extractBoxConstraint(Element root, long taskInstanceId) {

		int[] result = new int[4];

		String nodeName = this.getCurrentToken(taskInstanceId).getNode()
				.getName();

		XPath xPath = new DefaultXPath("//node[@name='" + nodeName + "']");

		Element node = (Element) xPath.selectSingleNode(root);

		result[0] = Integer.valueOf(node.attribute("x").getValue()).intValue();

		result[1] = Integer.valueOf(node.attribute("y").getValue()).intValue();

		result[2] = Integer.valueOf(node.attribute("width").getValue())
				.intValue();

		result[3] = Integer.valueOf(node.attribute("height").getValue())
				.intValue();

		return result;

	}

}