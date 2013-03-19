<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="header.jsp" %>
<%@include file="subbar_admin.jsp" %>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="java.util.List" %>

<script language="javascript">

$().ready(function(){
	$("#listtable").tablesorter( {sortList: [[0,0], [1,0]]} ); 
});


function editview(id,name,state,tracker_id){
	document.statusform.status_id.value=id;
	document.statusform.status_name.value=name;
	
	var objSelect = document.statusform.tracker_select;
	for (var i = 0; i < objSelect.options.length; i++) {
		if (objSelect.options[i].value == tracker_id) {
			objSelect.options[i].selected = true;
			break;
		}
	}
	
	var objSelect2 = document.statusform.state_select;
	for (var i = 0; i < objSelect2.options.length; i++) {
		if (objSelect2.options[i].value == state) {
			objSelect2.options[i].selected = true;
			break;
		}
	}
}
</script>

<br>
<br>


<table>
<form id="statusform" name="statusform" method="post" action="/status/save">
  <tr>
  <td>Group Name<select id="tracker_select" name="tracker_id">
  
        <%
		   List<TrackerObject> cglist = (List<TrackerObject>)request.getAttribute("trackerlist");
		   if(cglist!=null){
			   for(TrackerObject obj : cglist){
		%>
        <option value="<%=obj.getId()%>"><%=obj.getName() %></option>
        <%} }%>
      </select>
  </td>
  </tr>
  <tr><td>
  		<select id="state_select" name="state">
  			<option value="1" selected="selected">open</option>
  			<option value="0" >closed</option>
  		</select>
  </td></tr>
  <tr><td>
    Status Name : 
      <input name="status_name" size="50"/>  
      <input type="hidden" name="status_id"/>
          <input type="submit" name="submit" value="Save" />
      </td></tr>
</form>
</table>
<br>

<table id="listtable" class="tablesorter" width="60%">
 <thead>
		<tr>
			<th>id</th>
			<th>Name</th>
			<th>Tracker Name</th>
			<th>Operations</th>
		</tr>
</thead>
		<%
		   List<StatusObject> list = (List<StatusObject>)request.getAttribute("statuslist");
		   if(list!=null){
			   for(StatusObject obj : list){
		%>
		
		<tr>
			<td ><%=obj.getId()%></td>
			<td style='width:100px;' nowrap><%=obj.getName()%></td>
			<td style='width:100px;' nowrap><%=obj.getTrackerName()%></td>
			<td>
			<a href="/status/delete/<%=obj.getId()%>" style="margin-left:10px;">删除>>></a>
			<input type="button" name="edit" value="编辑" onclick="editview('<%=obj.getId()%>','<%=obj.getName()%>','<%=obj.getState()%>','<%=obj.getTrackerId()%>')"/>
		  </td>
		</tr>
		<%} } %>
</table>  


<%@include file="footer.html" %>
