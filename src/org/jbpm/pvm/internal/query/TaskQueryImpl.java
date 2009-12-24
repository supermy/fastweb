/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jbpm.pvm.internal.query;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.jbpm.api.JbpmException;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.identity.Group;
import org.jbpm.api.task.Task;
import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.identity.spi.IdentitySession;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.ParticipationImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

/**
 * @author Tom Baeyens
 * @author Heiko Braun <heiko.braun@jboss.com>
 */
public class TaskQueryImpl extends AbstractQuery implements TaskQuery {

  private static final long serialVersionUID = 1L;
  
  private static Log log = Log.getLog(TaskQueryImpl.class.getName());

  protected boolean unassigned = false;
  protected String assignee = null;
  protected String candidate = null;
  protected Boolean suspended = null;
  protected String processInstanceId = null;
  protected String processDefinitionId = null;
  protected String activityName = null;

  /* groupIds transports the groupIds from the hql to the applyParameters */
  protected List<String> groupIds; 

  public TaskQuery assignee(String assignee) {
    if (candidate!=null) {
      throw new JbpmException("assignee(...) cannot be combined with candidate(...) in one query");
    }
    this.assignee = assignee;
    return this;
  }

  public TaskQuery candidate(String userId) {
    this.candidate = userId;
    unassigned();
    return this;
  }

  public TaskQuery processInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
    return this;
  }

  public TaskQuery processDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
    return this;
  }

  public TaskQuery activityName(String activityName) {
    this.activityName = activityName;
    return this;
  }

  public TaskQuery unassigned() {
    this.assignee = null;
    this.unassigned = true;
    return this;
  }

  public TaskQuery suspended() {
    this.suspended = true;
    return this;
  }

  public TaskQuery notSuspended() {
    this.suspended = false;
    return this;
  }

  public TaskQuery orderAsc(String property) {
    orderByClause = "task." + property + " asc ";
    return this;
  }

  public TaskQuery orderDesc(String property) {
    orderByClause = "task." + property + " desc ";
    return this;
  }

  public TaskQuery page(int firstResult, int maxResults) {
    page = new Page(firstResult, maxResults);
    return this;
  }

  protected void applyParameters(Query query) {
    if (assignee!=null) {
      log.debug("setting parameter assignee: "+assignee);
      query.setString("assignee", assignee);
    }
    
    if (candidate!=null) {
      log.debug("setting parameter candidateUserId: "+candidate);
      query.setString("candidateUserId", candidate);
      
      if (groupIds!=null) {
        log.debug("setting parameter candidateGroupIds: "+groupIds);
        query.setParameterList("candidateGroupIds", groupIds);
      }
    }
  }

  public String hql() {
  	StringBuilder hql = new StringBuilder();
    hql.append("select task ");
    hql.append("from ");
    hql.append(TaskImpl.class.getName());
    hql.append(" as task ");

    // participations
    if (candidate!=null) {
      hql.append(", ");
      hql.append(ParticipationImpl.class.getName());
      hql.append(" as participant ");

      appendWhereClause("participant.task = task ", hql);
      appendWhereClause("participant.type = 'candidate' ", hql);

      IdentitySession identitySession = EnvironmentImpl.getFromCurrent(IdentitySession.class);
      List<Group> groups = identitySession.findGroupsByUser(candidate);
      if (groups.isEmpty()) {
        groupIds = null;
        appendWhereClause("participant.userId = :candidateUserId ", hql);
        
      } else {
        groupIds = new ArrayList<String>();
        for (Group group: groups) {
          groupIds.add(group.getSId().toString());
        }
        appendWhereClause("((participant.userId = :candidateUserId) or (participant.groupId in (:candidateGroupIds)))", hql);
      }
    }
    
    if (suspended!=null) {
      if (suspended) {
        appendWhereClause("task.state = '"+ExecutionImpl.STATE_SUSPENDED+"' ", hql);
      } else {
        appendWhereClause("task.state != '"+ExecutionImpl.STATE_SUSPENDED+"' ", hql);
      }
    }
    
    if (processInstanceId!=null) {
      appendWhereClause("task.processInstance.id = '"+processInstanceId+"' ", hql);
    }

    if (activityName!=null) {
      appendWhereClause("task.execution.activityName = '"+activityName+"' ", hql);
    }

    if (processDefinitionId!=null) {
      appendWhereClause("task.processInstance.processDefinitionId = '"+processDefinitionId+"' ", hql);
    }

    if (assignee!=null) {
      appendWhereClause("task.assignee = :assignee ", hql);
    } else if (unassigned) {
      appendWhereClause("task.assignee is null ", hql);
    }

    appendOrderByClause(hql);

    String hqlQuery = hql.toString();
    
    log.debug(hqlQuery);
    
    return hqlQuery;
  }
  
  public List<Task> list() {
    return (List<Task>) commandService.execute(this);
  }
  
  public Task uniqueResult() {
    return (Task)untypedUniqueResult();
  }
}
