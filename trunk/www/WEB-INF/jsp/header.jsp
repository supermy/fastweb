<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

		<title>Framework demo</title>	
		<style type="text/css">
			/* 按钮使用 */
			body {
			margin:0;
			padding:0;
			}
			#toggle {
			text-align: center;
			padding: 0em;
			}
			#toggle a {
			padding: 0 5px;
			border-left: 1px solid black;
			}
			#tRight {
			border-left: none !important;
			}
		</style>

		<link rel="stylesheet" type="text/css" href="yui/reset-fonts-grids/reset-fonts-grids.css" />
		<link rel="stylesheet" type="text/css" href="yui/resize/assets/skins/sam/resize.css" />
		<link rel="stylesheet" type="text/css" href="yui/layout/assets/skins/sam/layout.css" />
		<link rel="stylesheet" type="text/css" href="yui/button/assets/skins/sam/button.css" />
		<script type="text/javascript" src="yui/yahoo/yahoo-min.js"></script>
		<script type="text/javascript" src="yui/event/event-min.js"></script>
		<script type="text/javascript" src="yui/dom/dom-min.js"></script>

		<script type="text/javascript" src="yui/element/element-beta-min.js"></script>
		<script type="text/javascript" src="yui/dragdrop/dragdrop-min.js"></script>
		<script type="text/javascript" src="yui/resize/resize-beta-min.js"></script>
		<script type="text/javascript" src="yui/animation/animation-min.js"></script>
		<script type="text/javascript" src="yui/layout/layout-beta-min.js"></script>

	</head>

	<body class=" yui-skin-sam">

		<div id="top1">
			<a href="welcome.do" >welcome</a>  <a href="users.do" >用户</a>  <a href="register.do" >注册</a> <a href="login.do" >登陆</a> <a href="userManager.do" >管理</a>
			${loginMsg}    
			<p id="toggle"><a href="#" id="tRight">右</a><a href="#" id="tLeft">左</a><a href="#" id="closeLeft">左隐</a><a href="#" id="padRight">宽线</a></p>
		</div>

