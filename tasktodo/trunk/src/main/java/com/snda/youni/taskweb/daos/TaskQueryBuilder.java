package com.snda.youni.taskweb.daos;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snda.youni.taskweb.beans.IssueType;

public class TaskQueryBuilder {

	private static final Logger logger = LoggerFactory.getLogger(TaskQueryBuilder.class);
	
	public final static String FILTERNAME_id = "id";
	public final static String FILTERNAME_subject = "subject";
	public final static String FILTERNAME_feature_id = "feature_id";
	public final static String FILTERNAME_feature_name = "feature_name";
	public final static String FILTERNAME_assignee = "assignee";
	public final static String FILTERNAME_status_state = "status_state";
	public final static String FILTERNAME_assignee_login = "assignee_login";
	public final static String FILTERNAME_start_date = "start_date";
	public final static String FILTERNAME_categorygroup = "categorygroup";
	public final static String FILTERNAME_category = "category";
	
	public final static String FILTERNAME_backlog_id = "backlog_id";
	
	public final static String SQL_QUERY_SELECT = 
			"SELECT * FROM ("+
				"SELECT DISTINCT T._id,T.feature_id,T.tracker_id,T.backlog_id,T.subject,T.description,T.assignee,T.status_id,T.categorygroup,T.category,"+
				"T.start_date,"+
				"T.due_date,"+
				"F.name as feature_name,"+
				"TR.name as tracker_name,"+
				"S.state as status_state,"+
				"S.name as status_name,"+
				"U.name as assignee_name, U.login as assignee_login,"+
				"CG.name as categorygroup_name,"+
				"C.name as category_name "+
				"FROM tasks T, featureprojects F, trackers TR, statuses S , users U, categorygroups CG ,categorys C "+
				"WHERE T.feature_id = F._id"+
				"  AND T.tracker_id = TR._id"+
				"  AND T.status_id = S._id"+
				"  AND T.assignee = U._id"+
				"  AND T.categorygroup = CG._id"+
				"  AND T.category = C._id "+
				"  AND T.issuetype <> "+ IssueType.BACKLOG.ordinal()+
			") AS temptasks ";
	
	//private String sql = "";
	//private String sql_s = "";
	
	private Map<String,String> filters = new HashMap<String,String>();
	
	public void addQuery(String filter,String value){
		filters.put(filter, value);
	}
	
	public String buildQuerySQL(){
		String sql = "";
		String sql_filter = "";
		String sql_w = "";
		for(String filter:filters.keySet()){
			if(FILTERNAME_id.equals(filter)){
				sql_filter = sql_filter +" and "+ "_id="+filters.get(filter);
				continue;
			}
			if(FILTERNAME_feature_id.equals(filter)){
				sql_filter = sql_filter +" and "+ "feature_id="+filters.get(filter);
				continue;
			}
			if(FILTERNAME_subject.equals(filter)){
				sql_filter = sql_filter +" and " + "subject like '%"+filters.get(filter)+"%'";
				continue;
			}
			if(FILTERNAME_feature_name.equals(filter)){
				sql_filter = sql_filter +" and "+ "feature_name like '%"+filters.get(filter)+"%'";
				continue;
			}
			if(FILTERNAME_assignee.equals(filter)){
				sql_filter = sql_filter +" and "+ "assignee="+filters.get(filter);
				continue;
			}
			if(FILTERNAME_status_state.equals(filter)){
				sql_filter = sql_filter +" and "+ "status_state in ("+filters.get(filter)+")";
				continue;
			}
			if(FILTERNAME_assignee_login.equals(filter)){
				String value = filters.get(filter);
				value = value.replace(" ","");
				value = value.replace(",","','");
				//String instr = "";
				//for (String t: value.split(",")){
				//	instr = instr + ",'"+t+"'";
				//}
				//instr = instr.substring(1);
				sql_filter = sql_filter +" and "+ "assignee_login in ('"+value+"')";
				continue;
			}
			if(FILTERNAME_categorygroup.equals(filter)){
				sql_filter = sql_filter +" and "+ "categorygroup="+filters.get(filter);
				continue;
			}
			
			if(FILTERNAME_backlog_id.equals(filter)){
				sql_filter = sql_filter +" and "+ "backlog_id="+filters.get(filter);
				continue;
			}
			
		}
		if(sql_filter.length()>0){
			sql_filter = sql_filter.substring(5);
			sql_w = " where "+sql_filter;
		}
		String sql_o = " order by start_date desc";
		sql = SQL_QUERY_SELECT + sql_w + sql_o;
		
		logger.debug(" task query sql = "+sql);
		
		return sql;
	}
}
