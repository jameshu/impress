<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="header.jsp" %>
<%@include file="subbar_admin.jsp" %>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="java.util.List" %>

<script language="javascript">

$().ready(function(){
	$("#listtable").tablesorter( {sortList: [[0,0], [1,0]]} ); 
});


function editview(id,name){
	document.cgform.cg_id.value=id;
	document.cgform.cg_name.value=name;
}
</script>

<br>
<br>


<table>
<form id="cgform" name="cgform" method="post" action="/category/groupsave">
  <tr><td>
  Group Name : <input name="cg_name" size="50"/> 
  <input type="hidden" name="cg_id"/> 
  <input type="submit" name="Add Group" value="Submit" />
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
		   List<CategoryObject> list = (List<CategoryObject>)request.getAttribute("categorygrouplist");
		   if(list!=null){
			   for(CategoryObject obj : list){
		%>
		
		<tr>
			<td ><%=obj.getId()%></td>
			<td style='width:100px;' nowrap><%=obj.getName()%></td>
			<td>
			<a href="/category/groupdelete/<%=obj.getId()%>" style="margin-left:10px;">删除>>></a>
			<input type="button" name="edit" value="编辑" onclick="editview('<%=obj.getId()%>','<%=obj.getName()%>')"/>
		  </td>
		</tr>
		<%} } %>
</table>  


<%@include file="footer.html" %>
