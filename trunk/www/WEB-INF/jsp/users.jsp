<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<div id="right1">
</div>

<div id="left1">
</div>

<div id="center1">

	<h2>Users:</h2>

	<table>
		<tr>
			<thead>
				<th>Id</th>
				<th>Name</th>
				<th>Email</th>
			</thead>
		</tr>
		<br/>

		<c:forEach var="user" items="${users}">
		<tr>
			<td>
				${user.id}
			</td>
			<td>
				<a href="user.do?userId=${user.id}">${user.name}</a>
			</td>
			<td>EMail:${user.email} </td>
		</tr>
		</c:forEach>
	</table>

</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
