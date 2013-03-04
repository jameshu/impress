<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="header.jsp" %>
<%@page import="com.snda.youni.taskweb.beans.*" %>
<%@page import="com.snda.youni.taskweb.calendar.*" %>
<%@page import="com.snda.youni.taskweb.util.JsonUtil" %>
<%@page import="java.util.*" %>
<%@page import="java.text.SimpleDateFormat" %>

<%

Integer pagenum = (Integer)request.getAttribute("page");
if(pagenum==null){
	pagenum = 1;
}
List<CategoryObject> cglist = (List<CategoryObject>)request.getAttribute("cglist");
List<UserObject> userlist = (List<UserObject>)request.getAttribute("userlist");
List<TrackerObject> trackerlist = (List<TrackerObject>)request.getAttribute("trackerlist");
PageResult<TaskObject> pr = (PageResult<TaskObject>)request.getAttribute("tasklist_pr");
List<TaskObject> tasklist= pr.data; 

String nowdatestr = TaskObject.getDateString(  (new Date()).getTime());

String q_cgid="";
if(request.getAttribute("q_cgid")!=null){
	q_cgid = (String)request.getAttribute("q_cgid");
}

String q_fs_id="";
if(request.getAttribute("q_fs_id")!=null){
	q_fs_id = (String)request.getAttribute("q_fs_id");
}

%>

<script type="text/javascript">

$().ready(function(){
	
	$("#tracker_id_select").CascadingSelect($("#status_id_select"),
			"/status/json", {
				datatype : "json",
				textfield : "name",
				valuefiled : "id",
				parameter : "tracker_id"
	});
	
	
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
                $("<td style='width:170px;word-wrap:break-word;word-break:break-all'><a href='/task/"+n.id+"/edit'>" + n.subject + "</a></td><td nowrap class='wordblue'>"+n.statusName+"</td><td nowrap>"+n.dueDateString+"</td>").appendTo(row); 
                table1.append(row);
            }); 
            
        }  
    });
	
	$("#tasklisttable").tablesorter( {sortList: [[0,0], [1,0]]} ); 
	
});

function onQuerySubmit(pagenum){
	document.getElementById('q_page').value = pagenum;
	$("#taskqueryform").submit();
}

</script>

<div id="main" class="">

<div id="sidebar">

  <%if(q_fs_id!=null && q_fs_id.length()>0){%>
  <a href="/task/list?q_fs_id=<%=q_fs_id%>">列表视图</a>
  <%} %>

  <table class="cloth">
    <form id="newtaskform" method="post" action="/task/save">
    <tr>
	    <th colspan="2">快速新建研发任务【to自己】
	    <input id="submit" class="submit" type="submit" name="submit" value="保存" />
	    </th>
    </tr>
    <tr>
      <td>名称  </td>
      <td>
      <input type="hidden" name="assignee_id" value="<%=currentuser_id%>"/>
      <input type="text" id="subject" name="subject" size="25" required />
      <input type="hidden" name="start_date" value="<%=nowdatestr%>"/>
      <input type="hidden" name="due_date" value="<%=nowdatestr%>"/>
      </td>
    </tr>
    <tr>
      <td> Tracker
      </td>
      <td>
           <select id="tracker_id_select" name="tracker_id" required>
								<option value="" selected="true">请选择</option> 
								<%if(trackerlist!=null){
								  for(TrackerObject obj:trackerlist){
								%>
								<option value="<%=obj.getId()%>"><%=obj.getName()%></option> 
								<%}} %>
		   </select>
		   Status:
		   <select id="status_id_select" name="status_id" required>
		   </select>
      </td>
    </tr>
    <tr>
      <td>开始时间</td>
      <td><input name="start_time" id="start_time" size="6" required/>
                          结束时间
          <input name="due_time" id="due_time" size="6" required/>
      </td>
    </tr>
    </form>
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
  <input type="hidden" id="q_page" name="q_page" value=""/>
  <tr>
    <td>
                     任务名称:<input type="text" name="q_subject" size="15" value="<%=(request.getAttribute("q_subject")==null)?"":request.getAttribute("q_subject")%>"/> &nbsp;&nbsp;
                    特性冲刺：<input type="text" id="q_fs_name" name="q_fs_name" size="15" value="<%=(request.getAttribute("q_fs_name")==null)?"":request.getAttribute("q_fs_name")%>" />
        CategoryGroup：
        <select id="categorygroup_select" name="q_cgid">
            <option value="" selected="true">请选择</option>
	        <%if(cglist!=null){
				for(CategoryObject obj:cglist){
			%>
			<option value="<%=obj.getId()%>" <%if((""+obj.getId()).equals(q_cgid)){out.print("selected");} %>> <%=obj.getName()%> </option> <%}} %>
        </select> &nbsp;&nbsp;
        Assigee: <input type="text" id="q_assignee_login" name="q_assignee_login" size="25" value="<%=(request.getAttribute("q_assignee_login")==null)?"":request.getAttribute("q_assignee_login")%>" />
        <input type="button" name="btnsubmit" value="搜索" onclick="javascript:onQuerySubmit(1)"/>     
    </td>
  </tr>
  </form>
</table>

<!-- ##################### Used to show calendar view ############### -->
<table class="cal">
<thead>
<tr>
<th scope="col">一</th>
<th scope="col">二</th>
<th scope="col">三</th>
<th scope="col">四</th>
<th scope="col">五</th>
<th scope="col">六</th>
<th scope="col">日</th>
</tr>
</thead>
<tbody>
<tr>
<%
Date nowDate = new Date();
NowCalendar nc = new NowCalendar();
nc.init();

SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
String newNowDateStr = sdf.format(nowDate);
nowDate = sdf.parse(newNowDateStr);

int interval = Integer.parseInt(""+(nc.endDate.getTime() - nc.startDate.getTime())/(3600*1000*24));
Calendar c = Calendar.getInstance();
c.setTime(nc.startDate);
for (int i = 1; i <= interval+1; i++) {
	Date tmpDate = c.getTime();
	String tmpDateStr = sdf.format(tmpDate);
	tmpDate = sdf.parse(tmpDateStr);
%>
	<td class='<%=(tmpDate.compareTo(nc.currentMonth_firstDate)<0)?"odd":"even"%> <%if(tmpDate.compareTo(nowDate)==0){out.print("today");}%>'><p class="day-num"><%=c.get(Calendar.DAY_OF_MONTH)%></p>
	     <%if(tasklist!=null){
	    	 for(TaskObject obj : tasklist){
	    		 Date tmpStartDate = sdf.parse( TaskObject.getDateString(obj.getStartDate()) );
	    		 Date tmpDueDate = sdf.parse( TaskObject.getDateString(obj.getDueDate()) );
	         if( tmpDate.getTime() >=tmpStartDate.getTime()
	        		 && tmpDate.getTime()<=tmpDueDate.getTime()){%>
			 <div class="issue<%=obj.getStatusState()%> <%if(tmpDate.getTime()<tmpDueDate.getTime()){out.print("starting");}else{out.print("ending");}%>">
			   <a href="/task/<%=obj.getId()%>/edit"><%=obj.getId()+":"+obj.getSubject()%></a><br>
			   Assigee:<%=obj.getAssigeeName()%> <br>
			   Status:<%=obj.getStatusName() %>
			 </div>	
	     <%}}} %>
	  
	</td>
	<%if(i%7==0){ %></tr><tr> <%} %>
<%
c.add(Calendar.DATE, 1);
} 

%>
</tr>
</tbody>
</table>


</div>

</div>


<%@include file="footer.html" %>
