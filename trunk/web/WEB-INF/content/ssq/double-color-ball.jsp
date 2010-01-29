<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.supermy.core.security.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name="doubleColorBallList.title"/></title>
    <meta name="heading" content="<s:text name='doubleColorBallList.heading'/>"/>
	<%@ include file="/common/meta.jsp"%>
	<%@ include file="/common/css.jsp"%>

	<link rel="stylesheet" href="${css}/css/table/iqcontent.css" type="text/css" media="screen, projection"/>

</head>
<body>
<!-- 布局 容器  -->
<div class="container">
	<!-- 布局  顶部工具条-->
	<%@ include file="/common/tools.jsp"%>
	<!-- 布局  顶部导航栏目-->
	<%@ include file="/common/nav.jsp"%>
	<!-- 布局  左边列-->
	<div class="span-17 prepend-1 colborder">

		<c:set var="buttons">
			<div>
				<s:text name="common.page.by"/>${pagedoublecolorball.pageNo}<s:text name="common.page.page"/>, <s:text name="common.page.total"/>${pagedoublecolorball.totalPages}<s:text name="common.page.page"/> 
				<!--
				<s:if test="pagedoublecolorball.hasPre">
					<a href="double-color-ball.action?pagedoublecolorball.pageNo=${pagedoublecolorball.prePage}&pagedoublecolorball.orderBy=${pagedoublecolorball.orderBy}&pagedoublecolorball.order=${pagedoublecolorball.order}"><s:text name="common.page.pre"/></a>
				</s:if>
				<s:if test="pagedoublecolorball.hasNext">
					<a href="double-color-ball.action?pagedoublecolorball.pageNo=${pagedoublecolorball.nextPage}&pagedoublecolorball.orderBy=${pagedoublecolorball.orderBy}&pagedoublecolorball.order=${pagedoublecolorball.order}"><s:text name="common.page.next"/></a>
				</s:if>
				-->
				
				<s:property value="pagedoublecolorball.genNav('double-color-ball.action?','pagedoublecolorball',4)"  escape="false"/>
				<br />
			</div>
		</c:set>
		
		
		<c:out value="${buttons}" escapeXml="false" />
		<table>
			<thead>
			<tr>
				<th rowspan="2" >
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=id&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='id'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.id"/></a>
				</th>
				<th rowspan="2" >
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=num&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='num'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.num"/></a>
				</th>
				<th rowspan="2" >
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=lotteryDates&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='lotteryDates'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.lotteryDates"/></a>
				</th>
				<th rowspan="2" >
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=redBlueBall&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='redBlueBall'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.redBlueBall"/></a>
				</th>
	        	<th colspan="6" >
	        		出奖次序
				</th>
	        	<th colspan="6" >
	        		大小排序
				</th>
	        	<th  rowspan="2" >
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=blueBall&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='blueBall'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.blueBall"/></a>

							<br/>
							table
							<br/>
							<a href="double-color-ball!showtrendline.action?location=7&limit=10"  target="_blank">10</a>         
							<br/>
							<a href="double-color-ball!showtrendline.action?location=7&limit=30"  target="_blank">30</a>         
							<br/>
							<a href="double-color-ball!showtrendline.action?location=7&limit=50"  target="_blank">50</a>
							<br>

							map
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=7&limit=10"  target="_blank">10</a>
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=7&limit=30"  target="_blank">30</a>
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=7&limit=50"  target="_blank">50</a>
							<br>
							         
							         
							
				</th>
				<th rowspan="2" >
					manager
				</th>
			</tr>
			<tr>
	        	<th>
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=sequence1&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='sequence1'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.sequence1"/></a>
							
							
							<br/>
							<a href="double-color-ball!showtrendline.action?location=1&limit=10"  target="_blank">10</a>         
							<br/>
							<a href="double-color-ball!showtrendline.action?location=1&limit=30"  target="_blank">30</a>         
							<br/>
							<a href="double-color-ball!showtrendline.action?location=1&limit=50"  target="_blank">50</a>         
							
							map
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=1&limit=10"  target="_blank">10</a>
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=1&limit=30"  target="_blank">30</a>
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=1&limit=50"  target="_blank">50</a>
							<br>
				</th>
	        	<th>
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=sequence2&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='sequence2'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.sequence2"/></a>
							
							
							<br/>
							<a href="double-color-ball!showtrendline.action?location=2&limit=10"  target="_blank">10</a>         
							<br/>
							<a href="double-color-ball!showtrendline.action?location=2&limit=30"  target="_blank">30</a>         
							<br/>
							<a href="double-color-ball!showtrendline.action?location=2&limit=50"  target="_blank">50</a>         
							
							map
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=2&limit=10"  target="_blank">10</a>
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=2&limit=30"  target="_blank">30</a>
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=2&limit=50"  target="_blank">50</a>
							<br>
				</th>
	        	<th>
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=sequence3&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='sequence3'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.sequence3"/></a>

							
							<br/>
							<a href="double-color-ball!showtrendline.action?location=3&limit=10"  target="_blank">10</a>         
							<br/>
							<a href="double-color-ball!showtrendline.action?location=3&limit=30"  target="_blank">30</a>         
							<br/>
							<a href="double-color-ball!showtrendline.action?location=3&limit=50"  target="_blank">50</a>         
							
							map
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=3&limit=10"  target="_blank">10</a>
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=3&limit=30"  target="_blank">30</a>
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=3&limit=50"  target="_blank">50</a>
							<br>
				</th>
	        	<th>
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=sequence4&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='sequence4'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.sequence4"/></a>

							
							<br/>
							<a href="double-color-ball!showtrendline.action?location=4&limit=10"  target="_blank">10</a>         
							<br/>
							<a href="double-color-ball!showtrendline.action?location=4&limit=30"  target="_blank">30</a>         
							<br/>
							<a href="double-color-ball!showtrendline.action?location=4&limit=50"  target="_blank">50</a>         
							
							map
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=4&limit=10"  target="_blank">10</a>
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=4&limit=30"  target="_blank">30</a>
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=4&limit=50"  target="_blank">50</a>
							<br>
				</th>
	        	<th>
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=sequence5&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='sequence5'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.sequence5"/></a>

							
							<br/>
							<a href="double-color-ball!showtrendline.action?location=5&limit=10"  target="_blank">10</a>         
							<br/>
							<a href="double-color-ball!showtrendline.action?location=5&limit=30"  target="_blank">30</a>         
							<br/>
							<a href="double-color-ball!showtrendline.action?location=5&limit=50"  target="_blank">50</a>         
							
							map
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=5&limit=10"  target="_blank">10</a>
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=5&limit=30"  target="_blank">30</a>
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=5&limit=50"  target="_blank">50</a>
							<br>
				</th>
	        	<th>
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=sequence6&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='sequence6'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.sequence6"/></a>

							
							<br/>
							<a href="double-color-ball!showtrendline.action?location=6&limit=10"  target="_blank">10</a>         
							<br/>
							<a href="double-color-ball!showtrendline.action?location=6&limit=30"  target="_blank">30</a>         
							<br/>
							<a href="double-color-ball!showtrendline.action?location=6&limit=50"  target="_blank">50</a>         
							
							map
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=6&limit=10"  target="_blank">10</a>
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=6&limit=30"  target="_blank">30</a>
							<br>
							<a href="${ctx}/flash/amline_1.6.4.1/examples/chart_with_scroller/index.jsp?location=6&limit=50"  target="_blank">50</a>
							<br>
				</th>
	        	<th>
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=size1&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='size1'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.size1"/></a>
				</th>
	        	<th>
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=size2&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='size2'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.size2"/></a>
				</th>
	        	<th>
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=size3&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='size3'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.size3"/></a>
				</th>
	        	<th>
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=size4&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='size4'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.size4"/></a>
				</th>
	        	<th>
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=size5&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='size5'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.size5"/></a>
				</th>
	        	<th>
							<a href="double-color-ball.action?pagedoublecolorball.orderBy=size6&pagedoublecolorball.order=
							<s:if test="pagedoublecolorball.orderBy=='size6'">${pagedoublecolorball.inverseOrder}</s:if>
							<s:else>desc</s:else>">
							<s:text name="doubleColorBall.size6"/></a>
				</th>
			</tr>
			</thead>


			<s:iterator value="pagedoublecolorball.result" status='st'>
			<tbody>		
			    <s:if test="#st.Odd">
				<tr  class="odd">
			    </s:if>

			    <s:if test="#st.Even">
				<tr  class="even">
			    </s:if>
				
						<td>
								<a href="double-color-ball!input.action?id=${id}&pagedoublecolorball.pageRequest=${pagedoublecolorball.pageRequest}">
									${id}
								</a>
					</td>
						<td>
									${num}
					</td>
						<td>
									<s:date name="lotteryDates" format="yyyy-MM-dd"/>
					</td>
						<td>
									${redBlueBall}
					</td>
						<td>
									${sequence1}
					</td>
						<td>
									${sequence2}
					</td>
						<td>
									${sequence3}
					</td>
						<td>
									${sequence4}
					</td>
						<td>
									${sequence5}
					</td>
						<td>
									${sequence6}
					</td>
						<td>
									${size1}
					</td>
						<td>
									${size2}
					</td>
						<td>
									${size3}
					</td>
						<td>
									${size4}
					</td>
						<td>
									${size5}
					</td>
						<td>
									${size6}
					</td>
						<td>
									${blueBall}
					</td>
					<security:authorize ifAnyGranted="AUTH_EDIT_DOUBLECOLORBALL">
						<td>
							<a href="double-color-ball!input.action?id=${id}&pagedoublecolorball.pageRequest=${pagedoublecolorball.pageRequest}">
								manager
							</a>
						</td>
					</security:authorize>
					
				</tr>
			</tbody>		
			</s:iterator>
		</table>			<!--#include "list-view-displaytag.ftl"/-->
		<c:out value="${buttons}" escapeXml="false" />		
		
	</div>
	
	<!-- 布局  右边列-->
	<div class="column span-5 last">
		<s:actionmessage theme="mytheme"/>
		<hr class="space"/>
		<security:authorize 
			ifAnyGranted="AUTH_EDIT_DOUBLECOLORBALL">
			<a  class="button" 
				href="double-color-ball!input.action?pagedoublecolorball.pageRequest=${pagedoublecolorball.pageRequest}">
				<s:text name="common.domain.create"/>
			</a>
		</security:authorize>
		<hr class="space"/>
		<div id="filter">
			<form 
				action="double-color-ball.action" 
				method="post">
				<input type="text" 
						name="filter_LIKE_num|sequence1|sequence2|sequence3|sequence4|sequence5|sequence6|size1|size2|size3|size4|size5|size6|blueBall|redBlueBall" 
						value="${param['filter_LIKE_num|sequence1|sequence2|sequence3|sequence4|sequence5|sequence6|size1|size2|size3|size4|size5|size6|blueBall|redBlueBall']}" 
						size="10"/>
				<s:submit 
					cssClass="button" 
					method="search"  
					key="common.domain.search" 
					/>
			</form>
		</div> 

		<div id="fulltext">
			<form 
				action="double-color-ball.action" 
				method="post">
				<input type="text" 
						name="q" 
						value="${param['q']}" 
						size="10"/>
				<s:submit 
					cssClass="button" 
					method="fulltext"  
					key="common.domain.fulltext" 
					/>
			</form>
		</div> 

	</div>
		
	<!-- 布局  底 -->
	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>
