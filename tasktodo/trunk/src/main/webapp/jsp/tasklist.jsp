<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="header.jsp" %>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="com.snda.youni.taskweb.util.JsonUtil" %>
<%@page import="java.util.List" %>

<%

List<CategoryObject> cglist = (List<CategoryObject>)request.getAttribute("cglist");
List<UserObject> userlist = (List<UserObject>)request.getAttribute("userlist");

%>

<script type="text/javascript">

$().ready(function(){
	
	$('#start_time').timepicker();
	$('#due_time').timepicker();
	
	//var url = "/featureproject/ajaxsearch";
	//$("#q_featurename").autocomplete( 
	//		{ 
	//		source: url 
	//		} 
	//);
	
	var availableTags = [];
	var usersStr = '<%=JsonUtil.toJsonString(userlist)%>';
	//alert(usersStr);
	var userobjs = eval('('+usersStr+')');
	for(var i=0;i<userobjs.length;i++){
		availableTags.push( userobjs[i].login );
	}
	//alert(availableTags);
	
	function split( val ) {
	    return val.split( /,\s*/ );
	}
	function extractLast( term ) {
	    return split( term ).pop();
	}
	 
    $( "#q_assignee_login" )
      // don't navigate away from the field on tab when selecting an item
      .bind( "keydown", function( event ) {
        if ( event.keyCode === $.ui.keyCode.TAB &&
            $( this ).data( "autocomplete" ).menu.active ) {
          event.preventDefault();
        }
      })
      .autocomplete({
        minLength: 0,
        source: function( request, response ) {
          // delegate back to autocomplete, but extract the last term
          response( $.ui.autocomplete.filter(
            availableTags, extractLast( request.term ) ) );
        },
        focus: function() {
          // prevent value inserted on focus
          return false;
        },
        select: function( event, ui ) {
          var terms = split( this.value );
          // remove the current input
          terms.pop();
          // add the selected item
          terms.push( ui.item.value );
          // add placeholder to get the comma-and-space at the end
          terms.push( "" );
          this.value = terms.join( ", " );
          return false;
        }
      });
	
	
	//Loading the tasks what the login user doing.
	//var nowuser = <?php echo json_encode(Asf_Gdata::get("userobj"));?>
	//var nowuser_id = nowuser._id;
	$.ajax({  
        type: "POST",  
        dataType: "json",  
        url: "/task/me?rtype=json&q_status_state=1",  
        data: {},  
        success: function (data) {  
            //alert(data);  
            var table1 = $('#doingtable');
            //$("<tr><td>任务名称</td><td>状态</td><td>结束日期</td><tr>").appendTo(table1); 
            $.each(data, function (i, n) { 
            	var row = $("<tr></tr>");
                $("<td style='width:170px;word-wrap:break-word;word-break:break-all'><a href='/task/toedit?task_id="+n.id+"'>" + n.subject + "</a></td><td nowrap class='wordblue'>"+n.statusName+"</td><td nowrap>"+n.dueDateString+"</td>").appendTo(row); 
                table1.append(row);
            }); 
            
        }  
    });
	
	$("#tasklisttable").tablesorter( {sortList: [[0,0], [1,0]]} ); 
	
});

</script>

<div id="main" class="">

<div id="sidebar">
  <table class="cloth">
    <tr>
	    <th colspan="2">快速新建紧急任务【to自己】</th>
    </tr>
    <tr>
      <td>名称  </td>
      <td><input type="text" id="subject" name="subject" size="25" required /></td>
    </tr>
    <tr>
      <td>开始时间</td>
      <td><input name="start_time" id="start_time" size="6" required/>
                          结束时间
                          <input name="due_time" id="due_time" size="6" required/>
      </td>
    </tr>
  </table>
  
  <table id="doingtable" class="cloth">
    <tr>
	    <th>我在处理的任务（名称）</th>
	    <th>状态</th>
	    <th>结束日期</th>
    </tr>
  </table>
  <table class="cloth">
    <tr>
	    <th>自定义查询</th>
    </tr>
    <tr>
      <td>&nbsp;  </td>
    </tr>
  </table>
  
</div>

<div id="content">

<table >
  <form id="taskqueryform" method="post" action="/task/list">
  <tr>
    <td>
                     任务名称:<input type="text" name="q_subject" size="15" value=""/> &nbsp;&nbsp;
                    特性冲刺：<input type="text" id="q_fs_name" name="q_fs_name" size="15" value="" />
        CategoryGroup：
        <select id="categorygroup_select" name="q_cgid">
            <option value="" selected="true">请选择</option>
	        <%if(cglist!=null){
				for(CategoryObject obj:cglist){
			%>
			<option value="<%=obj.getId()%>"><%=obj.getName()%></option> <%}} %>
        </select> &nbsp;&nbsp;
        Assigee: <input type="text" id="q_assignee_login" name="q_assignee_login" size="25" value="" />
        <input id="submit" type="submit" name="submit" value="搜索" />       
    </td>
  </tr>
  </form>
</table>

<table id="tasklisttable" class="tablesorter">
 <thead>
		<tr>
			<th>#</th>
			<th>Tracker</th>
			<th>Feature</th>
			<th>Subject</th>
			<th>Assignee</th>
			<th>Status</th>
			<th>CategoryGr</th>
			<th>Category</th>
			<th>StartDate</th>
			<th>DuteDate</th>
		</tr>
	</thead>
	<tbody>
	    <% 
	       PageResult<TaskObject> pr = (PageResult<TaskObject>)request.getAttribute("tasklist_pr");
	       List<TaskObject> tasklist= pr.data; 
	       if(tasklist!=null){
	       int i= 0;
	       
	       for(TaskObject obj : tasklist){
	       i++;
	    %>
		<tr>
			<td><%=i%></td>
			<td nowrap style='width:20px;'><%=obj.getTrackerName()%></td>
			<td style='width:100px;word-wrap:break-word;word-break:break-all'>
			<a href="/task/list?fs_id=<%=obj.getFeatureId()%>&view=list"><%=obj.getFeatureName()%></a>
			</td>
			<td style='width:300px;word-wrap:break-word;word-break:break-all'>
			<a href="/task/<%=obj.getId()%>/edit" ><%=obj.getSubject()%></a>
			</td>
			<td nowrap><%=obj.getAssigeeName()%></td>
			<td  nowrap class="<%out.print( (obj.getStatusState()==1)?"wordblue":"" );%>"><%=obj.getStatusName() %></td>
			<td nowrap><%=obj.getCategorygroupName() %></td>
			<td nowrap><%=obj.getCategoryName() %></td>
			<td nowrap><%=TaskObject.getDateString( obj.getStartDate() )%></td>
			<td nowrap><%=TaskObject.getDateString( obj.getDueDate() )%></td>
		</tr>
		<%} } %>
 
	</tbody>
	<tfoot>
		<tr>
			<td colspan="4"> <% out.print( "<a href='/task/list?page="+(pr.page-1)+"'>Previous</a> &nbsp;&nbsp;" ); 
               for(int i=1;i<pr.page_count;i++){
            	   if(i!=pr.page) out.print("<a href='/task/list?page="+i+"'>");
            	   out.print(i);
            	   if(i!=pr.page) out.print("</a>");
            	   out.print(" ,");
               }
               if(pr.page!=pr.page_count) out.print("<a href='/task/list?page="+pr.page_count+"'>");
              	out.print( pr.page_count);
              	if(pr.page!=pr.page_count) out.print("</a>");
              	out.print( "&nbsp;&nbsp; <a href='/task/list?page="+(pr.page+1)+"'>Next</a> &nbsp;&nbsp;" );
              	out.print( pr.count);
			%>
			</td>
		</tr>
	</tfoot>
</table>  

</div>

</div>


<%@include file="footer.html" %>
