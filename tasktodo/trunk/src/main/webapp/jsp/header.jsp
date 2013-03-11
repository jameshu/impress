<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.snda.iyouni.icommon.context.AppSettings"%>
<%@ page import="com.snda.youni.taskweb.util.CurrentUserCookie"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>TaskWeb-</title>
<link rel="stylesheet" href="/css/mainLayer.css" />
<!--[if IE]>
<style type="text/css"> 
.twoColLiqLtHdr #sidebar1 { padding-top: 30px; }
.twoColLiqLtHdr #mainContent { zoom: 1; padding-top: 15px; }
</style>
<![endif]-->
<!-- 
<script src="http://dl.y.sdo.com/ystatic/common/jquery/1.7.1.min.js"></script>
<script src="http://dl.y.sdo.com/ystatic/common/jquery/jquery-ui-1.8.18.min.js"></script>
<script src="http://dl.y.sdo.com/ystatic/common/jquery/jquery.upload-1.0.2.min.js"></script>
<script src="http://dl.y.sdo.com/ystatic/common/jquery/jquery-ui-timepicker-addon.js"></script>
<link rel="stylesheet" href="http://dl.y.sdo.com/ystatic/common/jquery/plugin/datepicker-themes/base/jquery.ui.all.css">
<link rel="stylesheet" href="/css/demos.css">
 -->


 
<link rel="stylesheet"	href="/js/jquery/ui/1.9.2/themes/base/jquery-ui.css" />
<link rel="stylesheet"	href="/css/application.css" />	

<script src="/js/jquery/jquery-1.9.1.min.js"></script>   
<script src="/js/jquery/ui/1.9.2/jquery-ui.js"></script>


<script type="text/javascript" src="/js/jquery.validate/1.11.0/jquery.validate.js"></script>

<!-- JQuery TimePicker Addon plugin,  need to include jQuery and jQuery UI with datepicker and slider wigits  -->
<script type="text/javascript" src="/js/timepicker/jquery-ui-timepicker-addon.js"></script>
<link rel="stylesheet" href="/js/timepicker/jquery-ui-timepicker-addon.css" />

<link rel="stylesheet" href="/js/tablecloth/tablecloth.css" />
<script type="text/javascript" src="/js/tablecloth/tablecloth.js"></script>

<link rel="stylesheet" href="/js/tablesorter/style.css" />
<script type="text/javascript" src="/js/tablesorter/jquery.tablesorter.js"></script>

<script type="text/javascript" src="/js/jQuery.FillOptions.js"></script>
<script type="text/javascript" src="/js/jQuery.CascadingSelect.js"></script>

<!-- 
<script type="text/javascript" src="/js/jstree/jquery.js"></script>
 -->

<script type="text/javascript" src="/js/jstree/jquery.jstree.js"></script>
<script type="text/javascript" src="/js/jstree/jquery.cookie.js"></script>
<script type="text/javascript" src="/js/jstree/jquery.hotkeys.js"></script>
<link rel="stylesheet"	href="/js/jstree/themes/classic/style.css" />	

<link type="text/css" rel="stylesheet" href="/js/jstree/_docs/syntax/!style.css"/>
<script type="text/javascript" src="/js/jstree/_docs/syntax/!script.js"></script>
<link rel="stylesheet" type="text/css" href="/js/jstree/_docs/!style.css" />



</head>

<%
  
  CurrentUserCookie cuc = new CurrentUserCookie(request);
  String currentuser_login = cuc.userLogin;
  int currentuser_id = cuc.userId;

  
%>

<body>

<div id="top-menu">
   <a href="/">taskTodo System</a>
   <div id="loggedas">Logged in as <a href="#"><%=currentuser_login%></a></div>
</div>

<div id="header">
	<a href="/task/new" ><b>创建任务</b> </a> &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
	<a href="/task/list" >任务列表</a> &nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp; 
    <a href="/fsprint/list" >目标冲刺</a> &nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp; 
    <a href="/task/me">MyTasks</a>
    </td>
    <td style="margin-right: 12px;">    
    <a href="/tracker/list" style="margin-left: 12px;">Trackers</a> , 
    <a href="/status/list" style="margin-left: 12px;">Status</a> , 
    <a href="/category/grouplist">CategoryGroups</a> , 
    <a href="/category/list" style="margin-left: 12px;">Categorys</a> , 
    <a href="/user/list" style="margin-left: 12px;">Users</a>


</div>
