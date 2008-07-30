<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>


<div id="right1">
    <b>奔流到海不复回</b>
</div>

<div id="left1">
    <p>黄河之水天上来</p>

</div>

<div id="center1">
	
	<h2>User Info</h2>
	  <table>
	    <tr>
	      <th>Name</th>
	      <td><b>${user.name}</b></td>
	    </tr>
	    <tr>
	      <th>Address</th>
	      <td>QQ:${user.address.qq}  MSN:${user.address.msn}  EMail:${user.email}  </td>
	    </tr>
	  </table>
	  <table class="table-buttons">
	    <tr>
	      <td colspan="2" align="center">
	        <form method="GET" action="<c:url value="/editUser.do"/>">
	          <input type="hidden" name="userId" value="${user.id}"/>
	          <p class="submit"><input type="submit" value="Edit User"/></p>
	        </form>
	      </td>
	      <td>
	        <form method="GET" action="<c:url value="/addUser.do"/>" name="formAddUser">
	          <p class="submit"><input type="submit" value="Add New User"/></p>
	        </form>
	      </td>
	    </tr>
	  </table>

</div>
  
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
