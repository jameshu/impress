<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.snda.iyouni.icommon.context.AppSettings"%>
<%@ page import="com.snda.youni.taskweb.util.CurrentUserCookie"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>impressTodo-</title>
<link rel="stylesheet"	href="/css/application.css" />

<%
String js = (String)request.getParameter("js");
if(js==null || js.length()==0){
	js = "jqueryui";
}
%>
<%if("jqueryui".equals(js)){%>	
<%@include file="js_jqueryui.html" %>
<% }else{%>
<%@include file="js_easyui.html" %>
<%} %>

<script type="text/javascript">

var divIssueEditDialog;

$().ready(function(){
	var currentURL = window.location.href;
	if(currentURL.indexOf("/task")>0){
		if(currentURL.indexOf("/task/new")>0){
			$("#a_gtb_newissue").attr("class","tab active");
		}else if(currentURL.indexOf("/task/me")>0){
			$("#a_gtb_myissues").attr("class","tab active");
		}else if(currentURL.indexOf("/task/list")>0){
			$("#a_gtb_issues").attr("class","tab active");
		}else{
			$("#a_gtb_issues").attr("class","tab active");
		}
	}else if(currentURL.indexOf("/fsprint/list")>0){
		$("#a_gtb_sprints").attr("class","tab active");
	}else{
		$("#a_gtb_admin").attr("class","tab active");
	}
	
	//I have to use gloabl var to close dialog.
	divIssueEditDialog = $( "#issueDialog" );
	divIssueEditDialog.dialog({
	      autoOpen: false,
	      height: 530,
	      width: 650,
	      modal: true,
	      buttons: {
	    	  "关闭":function(){
	    		  divIssueEditDialog.dialog('close')
	    	  }
	      }
	});
	
	
});

function onIssueAddDialog(){
	divIssueEditDialog.load("/task/new");
	divIssueEditDialog.dialog("open");
	//
	
}

</script>
 
<style type="text/css">
.gtb{background:#ebeff9;border-bottom:1px solid #6b90da;padding:5px 10px 0 5px;white-space:nowrap}
.gtb .tab{color:#00c;cursor:pointer;float:left;margin:5px 15px 6px 10px}
.gtb .active{background:#fff;border:1px solid #6b90da;border-bottom:0;color:#000;cursor:default;font-weight:bold;margin:0 5px -1px 0;padding:5px 9px 6px;text-decoration:none}
.gtb .gtbc{clear:left}

.subt{background:#fff;filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff',endColorstr='#f1f1f1');background:-webkit-gradient(linear,left top,left bottom,from(#fff),to(#f1f1f1));background:-moz-linear-gradient(top,#fff,#f1f1f1);border-bottom:1px solid #ccc;padding:0 0 0 14px;height:33px}
.st1 .inst1 a,.st2 .inst2 a,.st3 .inst3 a,.st4 .inst4 a,.st5 .inst5 a,.st6 .inst6 a,.st7 .inst7 a,.st8 .inst8 a,.st9 .inst9 a{color:#000;font-weight:bold;text-decoration:none;height:20px}
</style>
 
</head>

<%
  String requestURL = request.getRequestURL().toString();

  CurrentUserCookie cuc = new CurrentUserCookie(request);
  String currentuser_login = cuc.userLogin;
  int currentuser_id = cuc.userId;

  
%>

<body>

<div id="top-menu">
   <a href="/">impressTodo</a>
   <div id="loggedas">
   
   
   Logged in as <a href="#"><%=currentuser_login%></a>
   </div>
</div>

<!--
<div id="header">
	<a href="/task/new" ><b>创建任务</b> </a> &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
	<a href="/task/me">MyTasks</a> &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
	
	<a href="/backlog" >功能需求</a> &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
	
	<a href="/fsprint/list" >冲刺阶段</a> &nbsp;&nbsp;&nbsp; 
	<a href="/fsprint/backlogbingding" >冲刺需求</a> &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
	
	<a href="/fsprint/tasks" >冲刺任务</a> &nbsp;&nbsp;&nbsp;
	<a href="/task/list" >任务列表</a> &nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp; 
</div>
  -->
<div id="mt" class="gtb"> 
 <a id="a_gtb_newissue" href="javascript:void(0)" class="tab " onclick="onIssueAddDialog()">New Issue</a>
 <a id="a_gtb_myissues" href="/task/me" class="tab ">My Issues</a>
 <a id="a_gtb_backlogs" href="#" class="tab ">Backlogs</a>
 <a id="a_gtb_sprints" href="/fsprint/list" class="tab ">Sprints</a>
 <a id="a_gtb_issues" href="/task/list" class="tab ">Issues[All]</a>
 <a id="a_gtb_admin" href="/tracker/list" class="tab ">Admin</a>
 <div class=gtbc></div>
</div>
<!--
<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0" class="st">
 <tr>
 <td class="subt">
 <div class="st1">
 <div class="isf">
 <span class="inst1">
 <a href="/p/jquery-column-navigator/">Summary</a>
 </span>
 
 
 
 &nbsp;
 <span class="inst3">
 <a href="/p/jquery-column-navigator/people/list">People</a>
 </span>
 
 
 </div>
</div>

 </td>
 
 
 <td align="right" valign="top" class="bevel-right"></td>
 </tr>
</table>
  -->

<div id="issueDialog" title="Edit Issus">
</div>  
  