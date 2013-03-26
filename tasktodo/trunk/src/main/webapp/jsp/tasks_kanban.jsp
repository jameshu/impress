<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="header.jsp" %>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="com.snda.youni.taskweb.util.JsonUtil" %>
<%@page import="java.util.*" %>

<%

List<TaskObject> tasklist_todo= (List<TaskObject>)request.getAttribute("tasklist_todo");
List<TaskObject> tasklist_doing= (List<TaskObject>)request.getAttribute("tasklist_doing");
List<TaskObject> tasklist_done= (List<TaskObject>)request.getAttribute("tasklist_done");
List<TaskObject> tasklist_killed= (List<TaskObject>)request.getAttribute("tasklist_killed");

%>

<script type="text/javascript">

$().ready(function(){
	
	var currentURL = window.location.href;
	if(currentURL.indexOf("/task/me")>0){
		$("#subbar_myissues").show();
	}else{
		$("#subbar_myissues").hide();
	}
	
    // there's the panel_todo and the panel_doing
    var $panel_todo = $( "#panel_todo" ),
      $panel_doing = $( "#panel_doing" );
 
    // let the panel_todo items be draggable
    $( "div", $(".board_panel") ).draggable({
      cancel: "a.ui-icon", // clicking an icon won't initiate dragging
      revert: "invalid", // when not dropped, the item will revert back to its initial position
      containment: "document",
      helper: "clone",
      cursor: "move"
    });
 
    // let the panel_doing be droppable, accepting the panel_todo items
    $(".board_panel").droppable({
      accept: ".board_panel > div",
      activeClass: "ui-state-highlight",
      drop: function( event, ui ) {
    	  
    	  var current_issue_id = ui.draggable.attr("issue-id");
    	  var current_state_id = $(this).attr("status-state");
    	  
    	  //alert("www");
    	  
	  		$.ajax({
				type:"POST",
				url:"/task/"+current_issue_id+"/updatestatus",
				data:{
					"status_id":current_state_id
				},
				success:function (r) {
					
					//var ro = eval('(' + r + ')');
					//data.rslt.obj.attr("id", ""+ro.id);
					//data.rslt.obj.attr("name", data.rslt.new_name);
				}
			});
	  		ui.draggable.appendTo( this );
    	  //alert( $(this).attr("status-state") );
    	  
    	  //alert( ui.draggable.attr("issue-id") );
    	  
        //deleteImage( ui.draggable );
      }
    });



});

function onIssueEditDialog(id){
	//divIssueEditDialog.dialog("destroy");
	divIssueEditDialog.load("/task/"+id+"/edit");
	divIssueEditDialog.dialog("open");
}

</script>

  <style>
  .board_panel { float: left; width: 95%; min-height: 500px; padding: 0.4em;}
  .board_panel.custom-state-active { background: #eee; }
  .board_panel h4 { line-height: 16px; margin: 0 0 0.4em;}
  .board_panel h4 .color_doing{ line-height: 16px; margin: 0 0 0.4em; color:green}
  .board_panel h4 .color_done{ line-height: 16px; margin: 0 0 0.4em; color:#3E5B76}
  .board_panel h4 .color_killed{ line-height: 16px; margin: 0 0 0.4em; color:#000000}
  .board_panel h4 .ui-icon { float: left; }
  .board_panel div { float: left; width: 200px; padding: 0.4em; margin: 0 0.4em 0.4em 0; text-align: left; cursor: move;}
  .board_panel div a { float: left; }
  
  </style>


<div id="subbar_myissues" stype="display:none;">
	<table cellspacing="0" cellpadding="0" width="100%" align="center"	border="0" class="st">
		<tr>
			<td class="subt">
				<div class="st1">
					<div class="isf">
						<span class="inst1"> <a href="/task/me?rview=kanban">白板</a></span> &nbsp; 
						<span class="inst1"> <a href="/task/me?rview=list">列表</a></span> &nbsp; 
					</div>
				</div>
	
			</td>
	
	
			<td align="right" valign="top" class="bevel-right"></td>
		</tr>
	</table>
</div>


<div id="main">
	<table width="100%">
		<tr>
			<td width="28%" style="vertical-align: top">
						<div id="panel_todo" class="board_panel ui-widget-content ui-state-default" status-state="0">
						  <h4 class="ui-widget-header"><span class="ui-icon ui-icon-panel_doing">Todo</span>Todo</h4>

						<%if(tasklist_todo!=null){
					    	 for(TaskObject obj : tasklist_todo){%>
							 <div class="ui-widget-content" issue-id="<%=obj.getId()%>">
						    	<a href="javascript:void(0)" onclick="onIssueEditDialog(<%=obj.getId()%>)">[<%=obj.getId()%>]:<%=obj.getSubject()%></a><br/>
						    	 
						    	<a href="javascript:void(0)" onclick="onIssueEditDialog(<%=obj.getId()%>)" title="View Issue Info" class="ui-icon ui-icon-zoomin">More</a> &nbsp;&nbsp; 
						    	<%if(!(obj.getFeatureName()==null || obj.getFeatureName().length()==0)){ %>
						    	(Sprint:<%=obj.getFeatureName()%>)
						    	<%} %>
						  	</div>
					     <%}}%>

						</div>
	
			</td>
			<td width="28%" style="vertical-align: top">
				<div id="panel_doing" class="board_panel ui-widget-content ui-state-default" status-state="1">
				  <h4 class="color_doing ui-widget-header"><span class="ui-icon ui-icon-panel_doing">Doing</span>Doing</h4>
				  	<%if(tasklist_doing!=null){
					    	 for(TaskObject obj : tasklist_doing){%>
							 <div class="ui-widget-content" issue-id="<%=obj.getId()%>">
						    	<a href="javascript:void(0)" onclick="onIssueEditDialog(<%=obj.getId()%>)">[<%=obj.getId()%>]:<%=obj.getSubject()%></a><br/>
						    	 
						    	<a href="javascript:void(0)" onclick="onIssueEditDialog(<%=obj.getId()%>)" title="View Issue Info" class="ui-icon ui-icon-zoomin">More</a> &nbsp;&nbsp; 
						    	<%if(!(obj.getFeatureName()==null || obj.getFeatureName().length()==0)){ %>
						    	(Sprint:<%=obj.getFeatureName()%>)
						    	<%} %>
						  	</div>
					     <%}}%>
				</div>
			</td>
			<td width="28%" style="vertical-align: top">
				<div id="panel_done" class="board_panel ui-widget-content ui-state-default" status-state="2">
				  <h4 class="color_done ui-widget-header"><span class="ui-icon ui-icon-panel_doing">Done</span>Done</h4>
				  <%if(tasklist_done!=null){
					    	 for(TaskObject obj : tasklist_done){%>
							 <div class="ui-widget-content" issue-id="<%=obj.getId()%>">
						    	<a href="javascript:void(0)" onclick="onIssueEditDialog(<%=obj.getId()%>)">[<%=obj.getId()%>]:<%=obj.getSubject()%></a><br/>
						    	 
						    	<a href="javascript:void(0)" onclick="onIssueEditDialog(<%=obj.getId()%>)" title="View Issue Info" class="ui-icon ui-icon-zoomin">More</a> &nbsp;&nbsp; 
						    	<%if(!(obj.getFeatureName()==null || obj.getFeatureName().length()==0)){ %>
						    	(Sprint:<%=obj.getFeatureName()%>)
						    	<%} %>
						  	</div>
					     <%}}%>
				</div>
			</td>
			<td width="16%" style="vertical-align: top">
				<div id="panel_killed" class="board_panel ui-widget-content ui-state-default" status-state="3">
				  <h4 class="color_killed ui-widget-header"><span class="ui-icon ui-icon-panel_doing">Killed</span>Killed</h4>
				  <%if(tasklist_killed!=null){
					    	 for(TaskObject obj : tasklist_killed){%>
							 <div class="ui-widget-content" issue-id="<%=obj.getId()%>">
						    	<a href="javascript:void(0)" onclick="onIssueEditDialog(<%=obj.getId()%>)">[<%=obj.getId()%>]:<%=obj.getSubject()%></a><br/>
						    	 
						    	<a href="javascript:void(0)" onclick="onIssueEditDialog(<%=obj.getId()%>)" title="View Issue Info" class="ui-icon ui-icon-zoomin">More</a> &nbsp;&nbsp; 
						    	<%if(!(obj.getFeatureName()==null || obj.getFeatureName().length()==0)){ %>
						    	(Sprint:<%=obj.getFeatureName()%>)
						    	<%} %>
						  	</div>
					     <%}}%>
				</div>
			</td>
		</tr>
	</table>





</div>

<%@include file="footer.html" %>
