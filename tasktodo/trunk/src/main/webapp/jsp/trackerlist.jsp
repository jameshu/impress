<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="header.jsp" %>
<%@include file="subbar_admin.jsp" %>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="java.util.List" %>

<script language="javascript">

$().ready(function(){
	
	$('#trackerform').validate({
		
		rules:{
			tracker_name:{required:true	}
		},
		messages:{
			tracker_name:{required :"这个字段你必须填~~！"}
		} 
		
	});
	
	$("#listtable").tablesorter( {sortList: [[0,0], [1,0]]} ); 
});


function editview(id,name){
	document.trackerform.tracker_id.value=id;
	document.trackerform.tracker_name.value=name;
}
</script>

<br>
<br>


<table>
<form id="trackerform" name="trackerform" method="post" action="/tracker/save">
  <tr><td>
  Tracker Name : 
  <input name="tracker_name" size="50"/>  
  <input type="hidden" name="tracker_id"/>
  <input type="submit" name="Add Group" value="Save" />
  </td></tr>
</table>
<br>

<table id="listtable" class="tablesorter" width="60%">
 <thead>
		<tr>
			<th>id</th>
			<th>Name</th>
			<th>Operations</th>
		</tr>
</thead>
		<%
		   List<TrackerObject> list = (List<TrackerObject>)request.getAttribute("trackerlist");
		   if(list!=null){
			   for(TrackerObject obj : list){
		%>
		
		<tr>
			<td ><%=obj.getId()%></td>
			<td style='width:100px;' nowrap><%=obj.getName()%></td>
			<td>
			<a href="/tracker/delete/<%=obj.getId()%>" style="margin-left:10px;">删除>>></a>
			<input type="button" name="edit" value="编辑" onclick="editview('<%=obj.getId()%>','<%=obj.getName()%>')"/>
		  </td>
		</tr>
		<%} } %>
</table>  


<%@include file="footer.html" %>
