<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="header.jsp" %>
<%@include file="subbar_admin.jsp" %>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="java.util.List" %>

<script language="javascript">

$().ready(function(){
	$("#userlisttable").tablesorter( {sortList: [[0,0], [1,0]]} ); 
});


function editview(id,login,name,email){
	document.userform.user_id.value=id;
	document.userform.user_login.value=login;
	document.userform.user_name.value=name;
	document.userform.user_email.value=email;
}
</script>

<br>
<br>


<table>
<form id="userform" name="userform" method="post" action="/user/save">
  <tr><td>
  Login Name : 
  <input type="hidden" name="user_id"/>
  <input name="user_login" size="50"/> 
  
  </td></tr>
  <tr>
  	<td>
  		Name : <input name="user_name" size="50"/> 
  	</td>
  </tr>
  <tr>
  	<td>
  		Email : <input name="user_email" size="50"/> 
  		<input type="submit" value="Save" />
  	</td>
  </tr>
</form>
</table>

<br>



<table id="userlisttable" class="tablesorter" width="60%">
 <thead>
		<tr>
			<th>id</th>
			<th>Login Name</th>
			<th>Name</th>
			<th>Email</th>
			<th>Operations</th>
		</tr>
</thead>
		<%
		   List<UserObject> userlist = (List<UserObject>)request.getAttribute("userlist");
		   if(userlist!=null){
			   for(UserObject obj : userlist){
		%>
		
		<tr>
			<td ><%=obj.getId()%></td>
			<td style='width:100px;' nowrap><%=obj.getLogin() %></td>
			<td style='width:100px;' nowrap><%=obj.getName()%></td>
			<td style='width:100px;' nowrap><%=obj.getEmail()%></td>
			<td>
			<a href="/user/delete/<%=obj.getId()%>" style="margin-left:10px;">删除>>></a>
			<input type="button" name="edit" value="编辑" onclick="editview('<%=obj.getId()%>','<%=obj.getLogin()%>','<%=obj.getName()%>','<%=obj.getEmail()%>')"/>
		  </td>
		</tr>
		<%} } %>
</table>  


<%@include file="footer.html" %>
