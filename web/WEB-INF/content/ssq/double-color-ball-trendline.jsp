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
	<div class="span-22 prepend-1 colborder">

		<table>
			<thead>
			<tr>
					<th>01</th>
					<th>02</th>
					<th>03</th>
					<th>04</th>
					<th>05</th>
					<th>06</th>
					<th>07</th>
					<th>08</th>
					<th>09</th>
					<th>10</th>
					<th>11</th>
					<th>12</th>
					<th>13</th>
					<th>14</th>
					<th>15</th>
					<th>16</th>
					<th>17</th>
					<th>18</th>
					<th>19</th>
					<th>20</th>
					<th>21</th>
					<th>22</th>
					<th>23</th>
					<th>24</th>
					<th>25</th>
					<th>26</th>
					<th>27</th>
					<th>28</th>
					<th>29</th>
					<th>30</th>
					<th>31</th>
					<th>32</th>
					<th>33</th>
			</tr>
			</thead>


			<s:iterator value="trendline" status='st'>
			<tbody>		
			    <s:if test="#st.Odd">
				<tr  class="odd">
			    </s:if>

			    <s:if test="#st.Even">
				<tr  class="even">
			    </s:if>

					<td>${ball01}</td>
					<td>${ball02}</td>
					<td>${ball03}</td>
					<td>${ball04}</td>
					<td>${ball05}</td>
					<td>${ball06}</td>
					<td>${ball07}</td>
					<td>${ball08}</td>
					<td>${ball09}</td>
					<td>${ball10}</td>
					<td>${ball11}</td>
					<td>${ball12}</td>
					<td>${ball13}</td>
					<td>${ball14}</td>
					<td>${ball15}</td>
					<td>${ball16}</td>
					<td>${ball17}</td>
					<td>${ball18}</td>
					<td>${ball19}</td>
					<td>${ball20}</td>
					<td>${ball21}</td>
					<td>${ball22}</td>
					<td>${ball23}</td>
					<td>${ball24}</td>
					<td>${ball25}</td>
					<td>${ball26}</td>
					<td>${ball27}</td>
					<td>${ball28}</td>
					<td>${ball29}</td>
					<td>${ball30}</td>
					<td>${ball31}</td>
					<td>${ball32}</td>
					<td>${ball33}</td>
				</tr>
			</tbody>		
			</s:iterator>
		</table>
		
	</div>
	
		
	<!-- 布局  底 -->
	<%@ include file="/common/footer.jsp"%>
</div>
</body>

</html>
