<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<script type="text/javascript" src="<c:url value="/js/validator.jsp"/>"></script>
<!--script type="text/javascript" src="<c:url value="/js/commons-validator-1.3.1-compress.j"/>"></script-->

<v:javascript formName="user" staticJavascript="false" xhtml="true" cdata="false"/>

<h2><c:if test="${!user.old}">New </c:if>User:</h2>

<div id="right1">
    <b>奔流到海不复回</b>
</div>

<div id="left1">
    <p>黄河之水天上来</p>

</div>

<div id="center1">


<form:form modelAttribute="user" action="saveUser.do" onsubmit="return validateUser(this);">
  <table>
    <tr>
      <th>
        Name:<form:errors path="name" cssClass="errors"/>
        <br/>
        <form:input path="name" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        PassWord:
        <br/>
        <form:input path="passwd" size="30" maxlength="80"/>
      </th>
    </tr>
     <tr>
      <th>
        Email:
        <br/>
        <form:input path="email" size="30" maxlength="80"/>
      </th>
    </tr>
   <tr>
      <th>
        Address:
        <br/>
      </th>
    </tr>
    <tr>
      <td>
        <c:choose>
          <c:when test="${!user.old}">
            <p class="submit"><input type="submit" value="Add User"/></p>
          </c:when>
          <c:otherwise>
            <p class="submit"><input type="submit" value="Update User"/></p>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</form:form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
