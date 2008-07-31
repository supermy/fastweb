<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<script type="text/javascript" src="<c:url value="/js/validator.jsp"/>"></script>
<v:javascript formName="user" staticJavascript="false" xhtml="true" cdata="false"/>

<h2><c:if test="${!user.old}">New </c:if>User:</h2>

<div id="right1">
</div>

<div id="left1">
</div>

<div id="center1">
	${errors}
<form:form modelAttribute="user" action="saveUser.do" onsubmit="return validateUser(this);">
	<form:hidden path="id" />
	<form:hidden path="version" />
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

        <form:password path="passwd" showPassword="true" size="30" maxlength="80"/>
        <form:password path="passwd2" showPassword="true" size="30" maxlength="80"/>
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
        Intro:
        <br/>
        <form:textarea path="intro" rows="6" cols="60"/>
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
