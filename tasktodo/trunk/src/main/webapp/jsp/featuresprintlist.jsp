<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="header.jsp" %>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="java.util.List" %>

<script language="javascript">

$().ready(function(){
	$("#listtable").tablesorter( {sortList: [[0,0], [1,0]]} ); 
	
	$('#fpform').validate({
		
		rules:{
			name:{required:true	},
			pm:{required:true	}
		},
		messages:{
			name:{required :"FeatureName必须填~~！"},
			pm:{required :"项目负责人必须填~~！"}
		} 
		
	});
	
});


function editview(id,name,description,pm,pd){
	document.fpform.id.value=id;
	document.fpform.name.value=name;
	document.fpform.description.value=description;
	
	var objSelect = document.fpform.pm_select;
	for (var i = 0; i < objSelect.options.length; i++) {
		if (objSelect.options[i].value == pm) {
			objSelect.options[i].selected = true;
			break;
		}
	}
	
	var objSelect = document.fpform.pd_select;
	for (var i = 0; i < objSelect.options.length; i++) {
		if (objSelect.options[i].value == pd) {
			objSelect.options[i].selected = true;
			break;
		}
	}
	
}
</script>

<br>
<br>

<%
   List<UserObject> users = (List<UserObject>)request.getAttribute("userlist");
%>

<table>
<form id="fpform" name="fpform" method="post" action="/fsprint/save">
  <tr>
  <th>
     Feature创建:
  </th>
  </tr>
  <tr>
  <td>
  Feature Name【*】 : 
  </td>
  <td>
  <input name="name" size="50" type="text"/>  
  <input type="hidden" name="id"/>
  
  </td>
  <tr>
  	<td>
  	Feature Description
  	</td>
  	<td>
  	<textarea name="description" cols="50" rows="5"></textarea>
  	
  	</td>
  </tr>
  <tr>
	<td>项目负责人 【*】 :</td>
	<td><select id="pm_select" name="pm" >
			<option value="" selected="true">请选择</option>
			<%if(users!=null){ 
		      	for(UserObject user : users){
			%>
			<option value="<%=user.getId()%>"><%=user.getName() %></option>
			<%} }%>
		</select>
	</td>
  </tr>
  <tr>
	<td>特性负责人 :</td>
	<td><select id="pd_select" name="pd" >
			<option value="" selected="true">请选择</option>
			<%if(users!=null){ 
		      	for(UserObject user : users){
			%>
			<option value="<%=user.getId()%>"><%=user.getName() %></option>
			<%} }%>
		</select>
		
	</td>
  </tr>
  <tr>
    <td>&nbsp;</td><td><input type="submit" name="Save" value="Save" /></td>
  </tr>
</form>
</table>
<br>

<table id="listtable" class="tablesorter" width="60%">
 <thead>
		<tr>
			<th>id</th>
			<th>Name</th>
			<th>Description</th>
			<th>Sprint Owner【项目】</th>
			<th>Feature Owner【特性】</th>
			<th>Operations</th>
		</tr>
</thead>
		<%
		   List<FeatureSprintObject> list = (List<FeatureSprintObject>)request.getAttribute("fsprintlist");
		   if(list!=null){
			   for(FeatureSprintObject obj : list){
		%>
		
		<tr>
			<td style='width:40px;' nowrap><%=obj.getId()%></td>
			<td style='width:150px;' nowrap><%=obj.getName()%></td>
			<td style='width:450px;word-wrap:break-word;word-break:break-all'><%=obj.getDescription()%></td>
			<td style='width:100px;' nowrap><%=obj.getPmName()%></td>
			<td style='width:100px;' nowrap><%=obj.getPdName()%></td>
			<td>
			<a href="/fsprint/delete/<%=obj.getId()%>" style="margin-left:10px;">删除>>></a>
			<input type="button" name="edit" value="编辑" onclick="editview('<%=obj.getId()%>','<%=obj.getName()%>','<%=obj.getDescription()%>','<%=obj.getPm()%>','<%=obj.getPd()%>')"/>
		  </td>
		</tr>
		<%} } %>
</table>  


<%@include file="footer.html" %>
