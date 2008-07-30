<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>



<div id="right1">
    <b>奔流到海不复回</b>
</div>

<div id="left1">
    <p>黄河之水天上来</p>

</div>

<div id="center1">

    <h2>Users:</h2>

		<table>
		  <tr>
		  <thead>
		    <th>Name</th>
		    <th>Address</th>
		  </thead>
		  </tr>
		  <br/>
		  
		  <c:forEach var="user" items="${users}">
		    <tr>
		      <td>
		          <a href="user.do?userId=${user.id}">${user.name}</a>
		      </td>
		      <td> QQ:${user.address.qq}  MSN:${user.address.msn}  EMail:${user.email} </td>
		    </tr>
		  </c:forEach>
		</table>

</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
