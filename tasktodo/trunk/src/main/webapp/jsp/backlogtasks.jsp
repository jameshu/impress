<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.snda.iyouni.icommon.context.AppSettings"%>
<%@ page import="com.snda.youni.taskweb.util.CurrentUserCookie"%>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="com.snda.youni.taskweb.util.JsonUtil" %>
<%@page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet"	href="/css/application.css" />	
<style type="text/css">
div.issue {background:#ffffdd; padding:1px; margin-bottom:1px;border: 1px solid #d7d7d7;margin: 0px 1 0px 1;}
</style>

</head>
<%
//int id = (Integer)request.getAttribute("id");
%>

<%

 out.print( "ID : "+ (new Date()).getTime());

%>

<body>

<%
PageResult<TaskObject> pr = (PageResult<TaskObject>)request.getAttribute("tasklist_pr");
List<TaskObject> tasklist= pr.data; 
if(tasklist!=null){
	for(TaskObject obj : tasklist){
%>

<div class="issue">
			   <a href="/task/<%=obj.getId()%>/edit"><%=obj.getId()+":"+obj.getSubject()%></a><br>
			   Assigee:<%=obj.getAssigeeName()%> <br>
			   Status:<%=obj.getStatusName() %>
</div>
<%}} %>

</body>
