<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<script type="text/javascript" src="<c:url value="/js/validator.jsp"/>"></script>
<v:javascript formName="login" staticJavascript="false" xhtml="true" cdata="false"/>

<h2>Login</h2>

<div id="right1">
</div>

<div id="left1">
</div>

<div id="center1">
	${errors}
<form:form modelAttribute="user" name="login" onsubmit="return validateLogin(this);">
  <table>
       <tr>
      <th>
        Email:
        <br/>
        <form:input path="email" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        PassWord:
        <br/>
        <form:password path="passwd" showPassword="true" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <td>
	      <p class="submit"><input type="submit" value="Login"/></p>
      </td>
    </tr>
  </table>
</form:form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
