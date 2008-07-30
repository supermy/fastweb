<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<h2>注册:</h2>

<div id="right1">
    <b>奔流到海不复回</b>
</div>

<div id="left1">
    <p>黄河之水天上来</p>

</div>

<div id="center1">

<form:form modelAttribute="user" >
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
        EMail:登陆凭证，要求有效EMail
        <br/>
        <form:input path="email" size="16" maxlength="30"/>
      </th>
    </tr>
    <tr>
      <th>
        口令:<form:errors path="*" cssClass="errors"/>
        <br/>
        <form:input path="passwd" size="10" maxlength="10"/>
      </th>
    </tr>
    <tr>
      <th>
        口令验证:
        <br/>
        <form:input path="passwd2" size="10" maxlength="10"/>
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
