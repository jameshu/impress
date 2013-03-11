<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="header.jsp" %>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="java.util.List" %>

<%
int id = (Integer)request.getAttribute("id");
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
</style>

<script language="javascript">

var selected_backlog_id = -1;

$().ready(function(){
	$.ajaxSetup({cache:false});
	$("#demo1").jstree({ 
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
		"plugins" : [ "themes", "json_data","ui","crrm","contextmenu","dnd" ],
		"themes" : {  
            "theme" : "classic", //apple,default,if in ie6 recommented you use classic  
            "dots" : true,  
            "icons" : true  
      	},
      	"core":{
      		"initially_open":["0"]
      	}
	}).bind("select_node.jstree", function (e, data) { 
		//select node action
		selected_backlog_id = data.rslt.obj.attr("id");
		//alert("select node :"+selected_backlog_id); 
		
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

	
	$( "#tabs" ).tabs({
	      beforeLoad: function( event, ui ) {
	    	//set backlog tasks
	    	$("#a_tasks").attr('href',"/backlog/"+selected_backlog_id+"/tasks");
	
	        ui.jqXHR.error(function() {
	          ui.panel.html(
	            "Couldn't load this tab. We'll try to fix this as soon as possible. " +
	            "If this wouldn't be a demo." );
	        });
	     },
	     beforeActivate: function( event, ui ) {
	    	 $("#a_tasks").attr('href',"/backlog/"+selected_backlog_id+"/tasks");
	     }
	});
	
});


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
  		<ul>
    		<li><a href="#tabs-backlog">Backlog Show</a></li>
    		<li><a id="a_tasks" href="/backlog/0/tasks">Tasks Show</a></li>
		</ul>
		<div id="tabs-backlog" >
			<table>
	       		<tr>
	       			<td>名称</td>
	       			<td><input type="text" id="backlog_name" name="name" size="50"/></td>
	       		</tr>
	       		<tr>
	       			<td>描述</td>
	       			<td><textarea id="backlog_description" name="description" cols="50" rows="5"></textarea></td>
	       		</tr>
	       		<tr>
	       			<td>Link</td>
	       			<td><textarea id="backlog_link" name="link" cols="50" rows="3"></textarea></td>
	       		</tr>
	       </table>
		</div>
	</div>

	
	</td>
</tr>


</table>





</div>



<%@include file="footer.html" %>
