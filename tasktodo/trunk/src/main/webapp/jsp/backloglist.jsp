<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="header.jsp" %>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="com.snda.youni.taskweb.util.*" %>
<%@page import="java.util.List" %>

<%
int id = (Integer)request.getAttribute("id");
List<TreeNode> nodes = (List<TreeNode>)request.getAttribute("nodes");
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

var selected_node;
var selected_backlog_id = -1;
var treeinst;

$().ready(function(){
	$.ajaxSetup({cache:false});
	$("#demo1").jstree({
		"plugins" : [ "themes", "json_data","ui","crrm","contextmenu","dnd","search" ],
		"themes" : {  
            "theme" : "default", //apple,default,if in ie6 recommented you use classic  
            "dots" : true,  
            "icons" : true  
      	},
		"json_data" : {
			"data":<%=(nodes!=null)?JsonUtil.toJsonString(nodes):""%>,
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
		"ui" : {
			"initially_select" : [ "204" ]

		},
      	"core":{
      		"initially_load":["204"]
      		
      	},
      	"dnd":{
      		"drop_target" : "#tabs",
      		"drag_target" : false,
      		"drop_finish" : function (data) {
                alert("test : "+data.o.attr("id"));
            }
      	}
	}).bind("loaded.jstree", function (e, data) {
		//data.inst.open_all(-1);
		treeinst = data.inst;
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

function onExpendNodeAll(){
	//$("#demo1").jstree().open_all(-1);
	treeinst.open_all(-1) ;
}

function onCloseNodeAll(){
	treeinst.close_all(-1) ;
}

function onToRootNode(){
	location.href ="/backlog";
}

function onToParentNode(){
	
}

function onToSelectedNode(){
	location.href ="/backlog/"+selected_backlog_id;
}

function onNodeSearch(){
	var s = document.getElementById('search_name').value;
	$("#demo1").jstree("search",s);
}

</script>

<br>
<br>
<div id="content">

<table>
<tr>
	<td>
		<input type="button" name="savebutton" value="展开" onclick="onExpendNodeAll()"/>
		<input type="button" name="savebutton" value="关闭" onclick="onCloseNodeAll()"/>
		<a href='javascript:void(0)' onclick='onToRootNode()'>返回顶节点</a>  --> 
		<a href='javascript:void(0)' onclick='onToSelectedNode()'>当前选择节点</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="text" id="search_name" name="search_name" size="20"/>
		<input type="button" name="savebutton" value="search" onclick="onNodeSearch()"/>
	</td>
	<td></td>
</tr>
<tr>
	<td width="550px" style="vertical-align: top;">
		<div id="demo1" class="treediv"></div>
	</td>
	<td style="vertical-align: top;">

			<table>
			    <form id="backlogform" name="backlogform" method="post" action="/backlog/save">
			    <tr>
	       			<td>ID</td>
	       			<td><input type="text" id="backlog_id" name="id" size="50" readonly/></td>
	       		</tr>
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
	       			<td><textarea id="backlog_link" name="link" cols="50" rows="3"></textarea>
	       			    <input type="button" name="savebutton" value="Save" onclick="onBtnClick_BacklogForm()"/>
	       			</td>
	       		</tr>
	       		</form>
	       </table>
	
	</td>
</tr>


</table>





</div>



<%@include file="footer.html" %>
