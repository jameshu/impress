<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="header.jsp" %>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="com.snda.youni.taskweb.util.*" %>
<%@page import="java.util.List" %>

<%
List<FeatureSprintObject> fsprintlist = (List<FeatureSprintObject>)request.getAttribute("fsprintlist");
List<TrackerObject> trackerlist = (List<TrackerObject>)request.getAttribute("trackerlist");
List<UserObject> userlist = (List<UserObject>)request.getAttribute("userlist");
List<CategoryObject> cglist = (List<CategoryObject>)request.getAttribute("cglist");

TaskObject taskObject = (TaskObject)request.getAttribute("task");

%>

<script type="text/javascript">

$().ready(function(){
	
	
	
	$('#commentForm').validate();
	
	

	$("#tracker_id_select").CascadingSelect($("#status_id_select"),
			"/status/json", {
				datatype : "json",
				textfield : "name",
				valuefiled : "id",
				parameter : "tracker_id"
			});

	$("#categorygroup_id_select").CascadingSelect(
			$("#category_id_select"), "/category/json", {
				datatype : "json",
				textfield : "name",
				valuefiled : "id",
				parameter : "categorygroup_id"
			});

	$("#start_datetime").datetimepicker({
		dateFormat : "yy-mm-dd",
		//timeOnly : true,
		timeFormat: "HH:mm",
		minuteGrid: 15
	});
	$("#due_datetime").datetimepicker({
		dateFormat : "yy-mm-dd",
		//timeOnly : true,
		timeFormat: "HH:mm",
		minuteGrid: 15
	});
	
	//$("#start_date").datepicker({dateFormat : "yy-mm-dd"});
	//$("#due_date").datepicker({	dateFormat : "yy-mm-dd"	});
	//$('#start_time').timepicker();
	//$('#due_time').timepicker();
    
	
	
	var nowuser_id = <%=currentuser_id%>;
	if(nowuser_id!=0){
		var objSelect = document.getElementById('assignee_id_select');
		for (var i = 0; i < objSelect.options.length; i++) {
			if (objSelect.options[i].value == nowuser_id) {
				objSelect.options[i].selected = true;
				break;
			}
		}
	}	
	
	
	
	<%if(taskObject!=null){%>
	
	//var obj = taskObj.parseJSON()
	//alert(taskObj.description);
	var taskObj_feature_id = <%=taskObject.getFeatureId()%>;
	
	
	if(taskObj_feature_id!=0){
	
		var selectElement = document.getElementById('feature_id_select');
		for(var i=0;i<selectElement.options.length;i++){
			if( selectElement.options[i].value==taskObj_feature_id ){
				selectElement.options[i].selected = true;
			}
		}
	}
	
	var taskObj_tracker_id = <%=taskObject.getTrackerId()%>;
	if(taskObj_tracker_id!=0){
		var selectElement = document.getElementById('tracker_id_select');
		for(var i=0;i<selectElement.options.length;i++){
			if( selectElement.options[i].value==taskObj_tracker_id ){
				selectElement.options[i].selected = true;
			}
		}
	}
	
	var selectElement_status = document.getElementById('status_id_select');
	$.ajax({  
        type: "POST",  
        dataType: "json",  
        url: "/status/json",  
        data: { tracker_id: taskObj_tracker_id },  
        success: function (data) {  
            //alert(data);  
            $.each(data, function (i, n) {  
                $("<option value=" + n.id + ">" + n.name + "</option>").appendTo(selectElement_status);  
            });
            
            
            
        }  
    });
	
	
	var taskObj_status_id = <%=taskObject.getStatusId()%>;
	if(taskObj_status_id!=0){
		for(var i=0;i<selectElement_status.options.length;i++){
			//alert(i + ' '+selectElement_status.options[i].value + '  ' + taskObj_status_id );
			if( selectElement_status.options[i].value==taskObj_status_id ){
				selectElement_status.options[i].selected = true;
			}
		}
	}
	
	
	
	document.getElementById('subject').value = '<%=taskObject.getSubject()%>';
	
	var taskObj_assignee_id = <%=taskObject.getAssignee()%>;
	if(taskObj_assignee_id!=0){
		var objSelect = document.getElementById('assignee_id_select');
		for (var i = 0; i < objSelect.options.length; i++) {
			if (objSelect.options[i].value == taskObj_assignee_id) {
				objSelect.options[i].selected = true;
				break;
			}
		}
	}
	
	var taskObj_cg_id = <%=taskObject.getCategorygroup()%>;
	if(taskObj_cg_id!=0){
		var selectElement = document.getElementById('categorygroup_id_select');
		for(var i=0;i<selectElement.options.length;i++){
			if( selectElement.options[i].value==taskObj_cg_id ){
				selectElement.options[i].selected = true;
			}
		}
	}
	
	var selectElement_category = document.getElementById('category_id_select');
	$.ajax({  
        type: "POST",  
        dataType: "json",  
        url: "/category/json",  
        data: { categorygroup_id: taskObj_cg_id },  
        success: function (data) {  
            //alert(data);  
            $.each(data, function (i, n) {  
                $("<option value=" + n.id + ">" + n.name + "</option>").appendTo(selectElement_category);  
            });  
        }  
    });
	
	var taskObj_c_id = <%=taskObject.getCategory()%>;
	if(taskObj_c_id!=0){
		for(var i=0;i<selectElement_category.options.length;i++){
			//alert(i + ' '+selectElement_category.options[i].value + '  ' + taskObj_c_id );
			if( selectElement_category.options[i].value==taskObj_c_id ){
				selectElement_category.options[i].selected = true;
			}
		}
	}

	document.getElementById('start_datetime').value = '<%=TaskObject.getDatetimeString(taskObject.getStartDate())%>';
	document.getElementById('due_datetime').value = '<%=TaskObject.getDatetimeString(taskObject.getDueDate())%>';
	
	//document.getElementById("submit").disabled=true;
	//document.getElementById("deletebutton").disabled=true;

	<%}%>	
	
});
</script>

<div id="content">

<table width="700">
	<tr>
		<td>

			<table class="cloth">
				<form class="taskform" id="taskform" method="post"	action="/task/save">
				    <%if(taskObject!=null){ %>
				    <input type="hidden" name="task_id" value="<%=taskObject.getId()%>"/>
				    <%} %>
					<tr>
						<th>Task创建:</th>
						<td align="right">
						  <input type="button" name="编辑" value="编辑" onclick="edit()" />
						</td>
					</tr>
					<tr>
					<tr>
						<td>Feature :</td>
						<td>
						<select id="feature_id_select" name="feature_id">
								<option value=""></option>
								<%if(fsprintlist!=null){
								  for(FeatureSprintObject obj:fsprintlist){
								%>
								<option value="<%=obj.getId()%>"><%=obj.getName()%></option>
								<%}} %>
						</select></td>
					</tr>
					<tr>
						<td>tracker【*】 :</td>
						<td><select id="tracker_id_select" name="tracker_id" required>
								<option value="" selected="true">请选择</option> 
								<%if(trackerlist!=null){
								  for(TrackerObject obj:trackerlist){
								%>
								<option value="<%=obj.getId()%>"><%=obj.getName()%></option> 
								<%}} %>
						</select></td>
					</tr>

					<tr>
						<td>status 【*】:</td>
						<td><select id="status_id_select" name="status_id" required>
						</select></td>
					</tr>

					<tr>
						<td>subject 【*】:</td>
						<td><input type="text" id="subject" name="subject" size="70"
							required /></td>
					</tr>
					<tr>
						<td>Description :</td>
						<td>
						<%if(taskObject!=null){%>
						<textarea id="description" name="description" cols="50" rows="5"><%=HtmlRegexpUtil.replaceTag(taskObject.getDescription())%></textarea>
						<%}else{%>
						<textarea id="description" name="description" cols="50" rows="5"></textarea>
						<%} %>
						</td>
					</tr>

					<tr>
						<td>Assignee 【*】:</td>
						<td><select id="assignee_id_select" name="assignee_id" required>
								<option value="" selected="true">请选择</option> 
								<%if(userlist!=null){
								  for(UserObject obj:userlist){
								%>
								<option value="<%=obj.getId()%>"><%=obj.getName()%></option> <%}} %>
						</select></td>
					</tr>
					<tr>
						<td>CategoryGroup :</td>
						<td><select id="categorygroup_id_select"
							name="categorygroup_id">
								<option value="" selected="true">请选择</option> 
								<%if(cglist!=null){
								  for(CategoryObject obj:cglist){
								%>
								<option value="<%=obj.getId()%>"><%=obj.getName()%></option> <%}} %>
						</select></td>
					</tr>
					<tr>
						<td>Cagegory :</td>
						<td><select id="category_id_select" name="category_id">
						</select></td>
					</tr>
					<tr>
						<td>Start Date 【*】:</td>
						<td><input name="start_datetime" id="start_datetime" size="20"
							required />
						</td>
					</tr>
					<tr>
						<td>Due Date 【*】:</td>
						<td><input name="due_datetime" id="due_datetime" size="20"
							required />
						</td>
					</tr>
				<tr>
					<td>
						<input id="submit" class="submit" type="submit" name="submit" value="保存" />
						<input id="deletebutton" type="button" name="deletebutton" value="删除" />
					</td>
				</tr>
				</form>
			</table>
		<td>
	</tr>
</table>

</div>

<%@include file="footer.html" %>
