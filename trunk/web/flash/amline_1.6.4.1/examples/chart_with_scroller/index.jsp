<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- amline script-->
  <script type="text/javascript" src="../../amline/swfobject.js"></script>
	<div id="flashcontent">
		<strong>You need to upgrade your Flash Player</strong>
	</div>

	<script type="text/javascript">
		// <![CDATA[		
		var so = new SWFObject("../../amline/amline.swf", "amline", "800", "600", "8", "#000000");
		so.addVariable("path", "../../amline/");
		so.addVariable("settings_file", encodeURIComponent("amline_settings.xml"));
		so.addVariable("data_file", encodeURIComponent("/fastweb/ssq/double-color-ball!showtrendlinexml.action?location=${param.location}&limit=${param.limit}"));
		so.write("flashcontent");
		// ]]>
	</script>
<!-- end of amline script -->