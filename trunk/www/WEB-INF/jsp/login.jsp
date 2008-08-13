<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<%@ page import="org.springframework.security.ui.AbstractProcessingFilter"%>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page import="org.springframework.security.AuthenticationException"%>

<!--
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
-->


<h2>Login</h2>

<div id="right1">
</div>

<div id="left1">
</div>

<div id="center1">

			<%
				if (session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY) != null) {
			%>
			<font color="red"> 登录失败，请重试.<BR> <BR> 原因: <%=((AuthenticationException) session
								.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY))
								.getMessage()%> </font>
			<%
				}
			%>
			<form action="<c:url value='/j_security_check'/>" method="POST" >
				<table>
					<tr>
						<td>
							用户名:
						</td>
						<td>
							<input type='text' name='j_username'
								<c:if test="not empty param.login_error"> value='<%=session.getAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY)%>'</c:if>>
						</td>
					</tr>
					<tr>
						<td>
							密码:
						</td>
						<td>
							<input type='password' name='j_password'>
						</td>
					</tr>
					<tr>
						<td>
							<input type="checkbox" name="_spring_security_remember_me">
						</td>
						<td>
							两周内记住我
						</td>
					</tr>
					<tr>
						<td colspan='2'>
							<input value="登录" type="submit">
						</td>
					</tr>
				</table>
			</form>
			<P>（管理员用户名<b>admin</b>, 密码<b>admin</b>）</P>
			<!--

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
			-->
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
