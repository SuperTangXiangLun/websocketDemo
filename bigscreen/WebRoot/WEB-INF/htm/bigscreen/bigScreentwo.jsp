<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String webProject = request.getServerName() + ":"
			+ request.getServerPort() + path ;
%>
<script type="text/javascript">
var callcenter = callcenter || {};
	callcenter.basePath = "<%=basePath%>";
	callcenter.webProject =  "<%=webProject%>";
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>test2</title>
<link href="<%=path%>/plugIn/css/UtryJsLib.all.css" type="text/css"
	rel="stylesheet">
<link href="<%=path%>/plugIn/jquery-ui/jquery-ui.css" type="text/css"
	rel="stylesheet">
<link href="<%=path%>/css/UtryUI.css" type="text/css" rel="stylesheet">
<link href="<%=path%>/css/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=path%>/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/plugIn/jquery-ui/jquery-ui.all.js"></script>
<script type="text/javascript"
	src="<%=path%>/plugIn/js/UtryJsLib.all.js"></script>
<script type="text/javascript"
	src="<%=path%>/plugIn/js/UtryJsLib.all.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugIn/charts/esl.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/bigscreen/tubiao.js"></script>
<script type="text/javascript" src="<%=path%>/js/bigscreen/bigscreentwo.js"></script>
<script type="text/javascript"
	src="<%=path%>/js/monitor/clientDataAction.js"></script>
 <style>
		*{font-size:20px;}
		.layout-top ,.layout-center,.layout-bottom{background:none;}
		html{overflow:auto;}
		body{padding:0px; font-size:18px; color:#fff; font-weight:500;}
    </style>
</head>
 
</html>
