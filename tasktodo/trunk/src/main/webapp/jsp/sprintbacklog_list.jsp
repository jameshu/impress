<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="java.util.List" %>

<%
List<BacklogObject> backloglist = (List<BacklogObject>)request.getAttribute("backloglist");
%>

<table id="backlogtable" class="tablesorter">
 	<thead>
		<tr>
			<th>#</th>
			<th>Name</th>
			<th>Operation</th>
		</tr>
	</thead>
	<tbody>
	<%
	if(backloglist!=null){
		for(BacklogObject obj:backloglist){
			//out.println(": "+obj.getName()+"<br>");
		
	%>
		<tr>
			<td><%=obj.getId()%></td>
			<td><%=obj.getName()%></td>
			<td><a href='javascript:void(0)' onclick='onUnBind(<%=obj.getId()%>)'>移除</a></td>
		</tr>
	<%}}%>
	</tbody>
</table>

===================