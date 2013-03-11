<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.snda.iyouni.icommon.context.AppSettings"%>
<%@ page import="com.snda.youni.taskweb.util.CurrentUserCookie"%>
<%@page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<%
int id = (Integer)request.getAttribute("id");
%>

<%
 int i = 0;
 i++;
 
 out.print( "ID:"+id +" : "+ (new Date()).getTime());

%>

<%@include file="footer.html" %>
