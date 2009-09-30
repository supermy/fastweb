<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

			<!-- jsp:param 模拟对象传递 -->
			<%request.setAttribute("pagenav",request.getAttribute(request.getParameter("pagenav"))); %>
			<div>
				<s:text name="common.page.by"/>${pagenav.pageNo}<s:text name="common.page.page"/>, <s:text name="common.page.total"/>${pagenav.totalPages}<s:text name="common.page.page"/> 
				<s:if test="pagenav.hasPre">
					<a href="user.action?page.pageNo=${pagenav.prePage}&page.orderBy=${pagenav.orderBy}&page.order=${pagenav.order}"><s:text name="common.page.pre"/></a>
				</s:if>
				<s:if test="pagenav.hasNext">
					<a href="user.action?page.pageNo=${pagenav.nextPage}&page.orderBy=${pagenav.orderBy}&page.order=${pagenav.order}"><s:text name="common.page.next"/></a>
				</s:if>
				<br />
			</div>