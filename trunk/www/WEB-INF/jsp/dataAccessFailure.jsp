<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<div id="right1">
</div>

<div id="left1">
</div>

<div id="center1">
	<%
	Exception ex = (Exception) request.getAttribute("exception");
	%>
	<h2>Data access failure: <%= ex.getMessage() %></h2>
	<p/>
	<!--%
	ex.printStackTrace(new java.io.PrintWriter(out));
	%-->
	<p/>
	<input type="button" value="return" onclick="javascript:history.back(-1);" />
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
