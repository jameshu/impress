<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="header.jsp" %>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="java.util.List" %>

<script language="javascript">

$().ready(function(){
	$("#listtable").tablesorter( {sortList: [[0,0], [1,0]]} ); 
});


function editview(id,name,categorygroup_id){
	document.cform.category_id.value=id;
	document.cform.category_name.value=name;
	
	var objSelect = document.cform.categorygroup_select;
	for (var i = 0; i < objSelect.options.length; i++) {
		if (objSelect.options[i].value == categorygroup_id) {
			objSelect.options[i].selected = true;
			break;
		}
	}
}
</script>

<br>
<br>


<table>
<form id="cform" name="cform" method="post" action="/category/save">
  <tr>
  <td>Group Name<select id="categorygroup_select" name="categorygroup_id">
  
        <%
		   List<CategoryObject> cglist = (List<CategoryObject>)request.getAttribute("categorygrouplist");
		   if(cglist!=null){
			   for(CategoryObject obj : cglist){
		%>
        <option value="<%=obj.getId()%>"><%=obj.getName() %></option>
        <%} }%>
      </select>
  </td>
  </tr>
  <tr><td>
    Category Name : 
      <input name="category_name" size="50"/>  
      <input type="hidden" name="category_id"/>
          <input type="submit" name="submit" value="Save" />
      </td></tr>
</table>
<br>

<table id="listtable" class="tablesorter" width="60%">
 <thead>
		<tr>
			<th>id</th>
			<th>Name</th>
			<th>GroupName</th>
			<th>Operations</th>
		</tr>
</thead>
		<%
		   List<CategoryObject> list = (List<CategoryObject>)request.getAttribute("categorylist");
		   if(list!=null){
			   for(CategoryObject obj : list){
		%>
		
		<tr>
			<td ><%=obj.getId()%></td>
			<td style='width:100px;' nowrap><%=obj.getName()%></td>
			<td style='width:100px;' nowrap><%=obj.getGroupName()%></td>
			<td>
			<a href="/category/delete/<%=obj.getId()%>" style="margin-left:10px;">删除>>></a>
			<input type="button" name="edit" value="编辑" onclick="editview('<%=obj.getId()%>','<%=obj.getName()%>','<%=obj.getGroupId()%>')"/>
		  </td>
		</tr>
		<%} } %>
</table>  


<%@include file="footer.html" %>
