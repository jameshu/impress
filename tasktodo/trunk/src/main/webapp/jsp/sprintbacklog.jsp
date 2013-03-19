<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="header.jsp" %>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="java.util.List" %>

<%
List<FeatureSprintObject> fsprintlist = (List<FeatureSprintObject>)request.getAttribute("fsprintlist");
int id = (Integer)request.getAttribute("backlog_id");
%>

<style type="text/css">
.treediv { 
	width:550px; 
	float:left; 
	margin:0; 
	border:1px solid gray; 
	background:white; 
	overflow:auto; }

.itemdiv { 
	width:500px; 
	float:left; 
	margin:0; 
	border:1px solid gray; 
	background:white; 
	overflow:auto; }
	
.sprintbacklogsdiv { 
	width:495px;
	height:300px; 
	float:left; 
	margin:0; 
	border:1px solid gray; 
	background:white; 
	overflow:auto; }	
	
</style>

<script language="javascript">

var selected_node;
var selected_backlog_id = -1;

$().ready(function(){
	$.ajaxSetup({cache:false});
	$("#demo1").jstree({
		"plugins" : [ "themes", "json_data","ui","crrm","contextmenu","dnd" ],
		"themes" : {  
            "theme" : "default", //apple,default,if in ie6 recommented you use classic  
            "dots" : true,  
            "icons" : true  
      	},
		"json_data" : {
			"ajax":{
				"url":"/backlog/treechildren",
				"data":function(n){
					//alert(n.attr("id"));
					var t = n.attr ? n.attr("id") : <%=id%> ;
					//alert(t);
					return { "id" : t };
				}
			}
			
		},
      	"core":{
      		"initially_open":["0"]
      	},
      	"dnd":{
      		"drop_target" : "#tabs",
      		"drag_target" : false,
      		"drop_finish" : function (data) {
      			//when this backlog has been drag into sprint div, binging it to this sprint.
      			//alert("test : "+data.o.attr("id"));
      			var selectedObj = $("#sprint_id_select option:selected");  
        		var selected = selectedObj.get(0).value; 
        		var url = "/backlog/"+data.o.attr("id")+"/bindSprint";
        		$.ajax({
        			type:"POST",
        			url:url,
        			data:{
        				"sprint_id":selected
        			},
        			success:function (r) {
        				var url = "/fsprint/"+selected+"/backloglist";
        	        	$("#sprintbacklogsdiv").load(url);
        			}
        		});
        		
                
            }
      	}
	}).bind("select_node.jstree", function (e, data) { 
		//select node action
		selected_backlog_id = data.rslt.obj.attr("id");
		//alert("select node :"+selected_backlog_id); 
		document.getElementById('backlog_id').value = selected_backlog_id;
		document.getElementById('backlog_name').value = data.rslt.obj.attr("name");
		document.getElementById('backlog_description').value = data.rslt.obj.attr("description");
		document.getElementById('backlog_link').value = data.rslt.obj.attr("link");
		
		$("#tabs").tabs( "option", "active", 0 );
		
	}).bind("create.jstree", function (e, data) {
		//alert("create node :"+data.rslt.name);
		$.ajax({
			type:"POST",
			url:"/backlog/save",
			data:{
				"name":data.rslt.name,
				"parent":data.rslt.parent.attr("id")
			},
			success:function (r) {
				var ro = eval('(' + r + ')');
				data.rslt.obj.attr("id", ""+ro.id);
				data.rslt.obj.attr("name", ro.name);
			}
		});
		
	}).bind("remove.jstree", function (e, data) {
		$.ajax({
			type:"POST",
			url:"/backlog/"+data.rslt.obj.attr("id")+"/delete",
			data:{},
			success:function (r) {
				//var ro = eval('(' + r + ')');
				//data.rslt.obj.attr("id", ""+ro.id);
				//data.rslt.obj.attr("name", data.rslt.new_name);
			}
		});
	}).bind("rename.jstree", function (e, data) {
		//alert(data.rslt.obj.attr("name"));
		$.ajax({
			type:"POST",
			url:"/backlog/"+data.rslt.obj.attr("id")+"/rename",
			data:{
				"rename":data.rslt.new_name
			},
			success:function (r) {
				//var ro = eval('(' + r + ')');
				//data.rslt.obj.attr("id", ""+ro.id);
				data.rslt.obj.attr("name", data.rslt.new_name);
			}
		});
	}).bind("move_node.jstree", function (e, data) {
	     data.rslt.o.each(function (i) {
	         $.ajax({
	             async : false,
	             type: 'POST',
	             url: "/backlog/"+$(this).attr("id")+"/move",
	             data : {
	                 "parent" : data.rslt.np.attr("id")
	             },
	             success : function (r) {
	             	//$(data.rslt.oc).attr("id", r.id);
	             }
	         });
	     });
	});

	
	var sprintSelect = $("#sprint_id_select");
    $.ajax({  
        type: "POST",  
        dataType: "json",  
        url: "/fsprint/listjson",  
        data: {},  
        success: function (data) {  
            sprintSelect.empty();
            $("<option value=''></option>").appendTo(sprintSelect);
            $.each(data, function (i, n) {  
                $("<option value=" + n.id + ">" + n.name + "</option>").appendTo(sprintSelect);  
            });  
        }  
    }); 
	
	$("#sprint_id_select").change( function(){
		var selectedObj = $("#sprint_id_select option:selected");  
		//alert(selectedObj.get(0).text);
        var selected = selectedObj.get(0).value; 
        if(selected){
        //ajax get backlog list for this sprint, and render the box.
        	var url = "/fsprint/"+selected+"/backloglist";
        	//alert(url);
        	$("#sprintbacklogsdiv").load(url);
		}
        
	});
	
});


function onBtnClick_BacklogForm(){

	var id = document.getElementById('backlog_id').value;
	var name = document.getElementById('backlog_name').value;
	var description = document.getElementById('backlog_description').value;
	var link = document.getElementById('backlog_link').value;
	
	$.ajax({
		type:"POST",
		url:"/backlog/save",
		data:{
			"id":id,
			"name":name,
			"description":description,
			"link":link,
		},
		success:function (r) {
			//var ro = eval('(' + r + ')');
			//data.rslt.obj.attr("id", ""+ro.id);
			//data.rslt.obj.attr("name", data.rslt.new_name);
			
			//$("#demo1").refresh(selected_backlog_id);
			var tree = jQuery.jstree._reference("#demo1"); 
			//var currentNode = tree._get_node(null);
			tree.refresh();
		}
	});
	
	
	
}

function onUnBind(backlog_id){
	//alert("remove backlog id : "+backlog_id);
	
	var urlstr = "/backlog/"+backlog_id+"/unbindSprint";
	$.ajax({
		type:"POST",
		url:urlstr,
		data:{
			"sprint_id":"1"
		},
		success:function (r) {
			var selectedObj = $("#sprint_id_select option:selected");  
			var selected = selectedObj.get(0).value; 
			var url = "/fsprint/"+selected+"/backloglist";
        	$("#sprintbacklogsdiv").load(url);
		}
	});
}


</script>

<br>
<br>
<div id="content">

<table>

<tr>
	<td width="550px" style="vertical-align: top;">
		<div id="demo1" class="treediv"></div>
	</td>
	<td style="vertical-align: top;">
	
	
	<div id="tabs" class="itemdiv">
	
		选择冲刺：
   <select id="sprint_id_select" name="feature_id">
		<option value=""></option>
   </select>	

		<div id="sprintbacklogsdiv" class="sprintbacklogsdiv">
		</div>

	</div>

	</td>
</tr>


</table>





</div>



<%@include file="footer.html" %>
