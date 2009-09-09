
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title><s:text name="myTigerList.title"/></title>
    <meta name="heading" content="<s:text name='myTigerList.heading'/>"/>

	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>

</head>

<body>
<div class="container">

	<%@ include file="/common/tools.jsp"%>
	
	<%@ include file="/common/nav.jsp"%>
	
	<div class="span-17 prepend-1 colborder">

		<c:set var="buttons">
			<div>
				第${pagemytiger.pageNo}页, 共${pagemytiger.totalPages}页 
				<s:if test="pagemytiger.hasPre">
					<a href="user.action?pagemytiger.pageNo=${pagemytiger.prePage}&pagemytiger.orderBy=${pagemytiger.orderBy}&pagemytiger.order=${pagemytiger.order}">上一页</a>
				</s:if>
				<s:if test="pagemytiger.hasNext">
					<a href="user.action?pagemytiger.pageNo=${pagemytiger.nextPage}&pagemytiger.orderBy=${pagemytiger.orderBy}&pagemytiger.order=${pagemytiger.order}">下一页</a>
				</s:if>
				<br />
			</div>
		</c:set>
				
		<c:out value="${buttons}" escapeXml="false" />
		<display:table 
			name="pagemytiger.result" 
			sort="external"
			class="table" 
			requestURI="" 
			id="myTigerList" 
			export="false">
			
			    <display:column property="id" 
			    	sortable="false"
			    	sortName="id" 
			    	href="my-tiger!input.action?pagemytiger.pageRequest=${pagemytiger.pageRequest}" media="html"
			        paramId="id" 
			        paramProperty="id" 
			        titleKey="myTiger.id"/>
			    <display:column 
			    	property="id" 
			    	media="csv excel xml pdf"
			    	titleKey="myTiger.id"/>
			    	
			
			
		            <display:column property="create"  format="{0,date,yyyy-MM-dd}" sortProperty="create" sortable="false" sortName="create" titleKey="myTiger.create"/>
			
			
		             <display:column sortProperty="enabled" sortable="false" sortName="enabled" titleKey="myTiger.enabled">
		                 <input type="checkbox" disabled="disabled" <c:if test="${myTigerList.enabled}">checked="checked"</c:if>/>
		             </display:column>
			
			
	        	    <display:column property="myEmail" sortable="false" sortName="myEmail" titleKey="myTiger.myEmail"/>
			    
			
			
	        	    <display:column property="myTiger" sortable="false" sortName="myTiger" titleKey="myTiger.myTiger"/>
			    
			
			
		    <display:setProperty name="paging.banner.item_name"><s:text name="myTigerList.myTiger"/></display:setProperty>
		    <display:setProperty name="paging.banner.items_name"><s:text name="myTigerList.myTigers"/></display:setProperty>
		
		    <display:setProperty name="export.excel.filename"><s:text name="myTigerList.title"/>.xls</display:setProperty>
		    <display:setProperty name="export.csv.filename"><s:text name="myTigerList.title"/>.csv</display:setProperty>
		    <display:setProperty name="export.pdf.filename"><s:text name="myTigerList.title"/>.pdf</display:setProperty>
		</display:table>
		<c:out value="${buttons}" escapeXml="false" />		
		
		<script type="text/javascript">
			function highlightTableRows(tableId) {
			    var previousClass = null;
			    var table = document.getElementById(tableId);
			    var tbody = table.getElementsByTagName("tbody")[0];
			    var rows = tbody.getElementsByTagName("tr");
			    // add event handlers so rows light up and are clickable
			    for (i=0; i < rows.length; i++) {
			        rows[i].onmouseover = function() { previousClass=this.className;this.className+=' over' };
			        rows[i].onmouseout = function() { this.className=previousClass };
			        rows[i].onclick = function() {
			            var cell = this.getElementsByTagName("td")[0];
			            if (cell.getElementsByTagName("a").length > 0) {
			                var link = cell.getElementsByTagName("a")[0];
			                if (link.onclick) {
			                    call = link.getAttributeValue("onclick");
			                    // this will not work for links with onclick handlers that return false
			                    eval(call);
			                } else {
			                  location.href = link.getAttribute("href");
			                }
			                this.style.cursor="wait";
			            }
			        }
			    }
			} 	
			function addFocusHandlers(elements) {
			    for (i=0; i < elements.length; i++) {
			        if (elements[i].type != "button" && elements[i].type != "submit" &&
			            elements[i].type != "reset" && elements[i].type != "checkbox" && elements[i].type != "radio") {
			            if (!elements[i].getAttribute('readonly') && !elements[i].getAttribute('disabled')) {
			                elements[i].onfocus=function() {this.style.backgroundColor='#ffd';this.select()};
			                elements[i].onmouseover=function() {this.style.backgroundColor='#ffd'};
			                // elements[i].onblur=function() {this.style.backgroundColor='';} 就是这一句话使jsp(html)中的onblur事件不能触发
			                elements[i].onmouseout=function() {this.style.backgroundColor='';}
			            }
			        }
			    }
			}
		    highlightTableRows("myTigerList");
		</script>

		
	</div>
	
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
		<security:authorize ifAnyGranted="AUTH_MODIFY_USER">
			<a href="my-tiger!input.action?pagemytiger.pageRequest=${pagemytiger.pageRequest}">新建</a>
		</security:authorize>
		<hr class="space"/>
		<div id="filter">
			<form action="MyTiger!search.action" method="post">
				<input type="text" 
						name="filter_LIKE_name|email" 
						value="${param['filter_LIKE_name|email']}" 
						size="10"/>
				<input type="submit" value="搜索" />
			</form>
		</div> 
	</div>
		

	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>
