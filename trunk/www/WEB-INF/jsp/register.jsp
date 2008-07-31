<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<script type="text/javascript" src="<c:url value="/js/validator.jsp"/>"></script>
<v:javascript formName="user" staticJavascript="false" xhtml="true" cdata="false"/>

<h2>注册:</h2>

<div id="right1">
</div>

<div id="left1">
</div>

<div id="center1">
	${errors}
<form:form modelAttribute="user" onsubmit="return validateUser(this);">
  <table>
    <tr>
      <th>
        昵称:
        <br/>
        <form:input path="name" size="16" maxlength="20"/>
      </th>
    </tr>
    <tr>
      <th>
        EMail:登陆凭证，要求有效EMail<br/>
        <form:input path="email" size="16" maxlength="20"/>
      </th>
    </tr>
    <tr>
      <th>
        口令:
        <br/>
        <form:password path="passwd" size="10" maxlength="10"/>
      </th>
    </tr>
    <tr>
      <th>
        口令验证:
        <br/>
        <form:password path="passwd2" size="10" maxlength="10"/>
      </th>
    </tr>
    <tr>
      <td>
            <p class="submit"><input type="submit" value="注册"/></p>
      </td>
    </tr>
  </table>
</form:form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
