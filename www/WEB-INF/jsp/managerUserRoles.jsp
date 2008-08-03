<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<h2>Manager User Roles</h2>

<div id="right1">
</div>

<div id="left1">
</div>

<div id="center1">
	${errors}
<form:form  modelAttribute="role" >
  <table>
       <tr>
      <th>
        Roles:
        <br/>
	<form:checkboxes path="roles" items="${allRoles}"/>
      </th>
    </tr>
    <tr>
      <td>
	 <p class="submit"><input type="submit" value="Save"/></p>
      </td>
    </tr>
  </table>
</form:form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
