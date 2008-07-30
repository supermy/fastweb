<%@ include file="/WEB-INF/jsp/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<h2>Find Users:</h2>
<form:form modelAttribute="user">
	<table>
		<tr>
			<th>Name: <br />
			<form:input path="name" size="30" maxlength="80" /></th>
		</tr>
		<tr>
			<td>
			<p class="submit"><input type="submit" value="Find Users" /></p>
			</td>
		</tr>
	</table>
</form:form>
<br />
<a href="<c:url value="/addUser.do"/>">Add User</a>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
