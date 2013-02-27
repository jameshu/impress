package com.snda.youni.taskweb.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.snda.youni.taskweb.beans.PageResult;
import com.snda.youni.taskweb.beans.TaskObject;

public class TaskDAO extends AbstractDAOImpl {

	static {
		TABLENAME = "tasks";
	};
	private final static String SQL_INSERT = "insert into "+TABLENAME+" (" +
			"feature_id,tracker_id,status_id,subject,description,assignee,start_date,due_date,categorygroup,category,createddate) " +
			"value (?,?,?,?,?,?,?,?,?,?,? )";
	private final static String SQL_UPDATE = "update "+TABLENAME+" set feature_id=?,"+
					"tracker_id=?,status_id=?, subject=?, description=?,"+
					"assignee=?,start_date=?,due_date=?,"+
					"categorygroup=?,category=? where _id=?";

	/*
	private final static String SQL_QUERY_BY_ID = "select _id,feature_id,tracker_id,subject,description,assignee,status_id,"+
			"DATE_FORMAT(start_date,'%Y-%m-%d') as start_date,"+
			"DATE_FORMAT(start_date,'%k:%i') as start_time,"+
			"DATE_FORMAT(due_date,'%Y-%m-%d') as due_date,"+
			"DATE_FORMAT(due_date,'%k:%i') as due_time,"+
			"categorygroup,category,project_id from "+TABLENAME+" where _id = ?";
	
	private final static String SQL_QUERY_COMPLEX_From = "DISTINCT T._id,T.feature_id,T.tracker_id,T.subject,T.assignee,T.status_id,T.categorygroup,T.category,"+
			"T.start_date,"+
			"T.due_date,"+
			"F.name as feature_name,"+
			"TR.name as tracker_name,"+
			"S.name as status_name,"+
			"U.name as assignee_name, U.login as assignee_login,"+
			"CG.name as categorygroup_name,"+
			"C.name as category_name "+
			"from tasks T, featureprojects F, trackers TR, statuses S , users U, categorygroups CG ,categorys C";
	*/
	public boolean save(TaskObject obj){
		Date startDate = new Date();
		startDate.setTime(obj.getStartDate());
		Date dueDate = new Date();
		dueDate.setTime( obj.getDueDate());
		if( obj.getId()>0 ){
			//update user;
			getJdbcTemplate().update(SQL_UPDATE, obj.getFeatureId(),obj.getTrackerId(),obj.getStatusId(),
					obj.getSubject(),obj.getDescription(),obj.getAssignee(),startDate,dueDate,
					obj.getCategorygroup(),obj.getCategory(),obj.getId());
		}else{
			//obj.setCreateddate( (new Date()).getTime() );
			getJdbcTemplate().update(SQL_INSERT, obj.getFeatureId(),obj.getTrackerId(),obj.getStatusId(),
					obj.getSubject(),obj.getDescription(),obj.getAssignee(),startDate,dueDate,
					obj.getCategorygroup(),obj.getCategory(),new Date());
			//insert user;
		}
		return true;
	}
	
	public TaskObject findById(int id){
		
		TaskQueryBuilder qb = new TaskQueryBuilder();
		qb.addQuery(TaskQueryBuilder.FILTERNAME_id, ""+id);
		TaskObject user = getJdbcTemplate().queryForObject(qb.buildQuerySQL(), new ObjectMapper());
		return user;
	}
	
	public PageResult<TaskObject> query(int startnum,int num,Map<String,String> filters){
		PageResult<TaskObject> pr = new PageResult<TaskObject>();
		TaskQueryBuilder qb = new TaskQueryBuilder();
		for(String key:filters.keySet()){
			qb.addQuery(key, filters.get(key));
		}
		String buildsql = qb.buildQuerySQL();
		String sql_count = "select count(*) from ("+buildsql +") as tempt";
		String sql = "select * from ("+buildsql +") as tempt limit ?,?";
		pr.count = getJdbcTemplate().queryForInt(sql_count);
		pr.data = getJdbcTemplate().query(sql, new Object[]{startnum,num},new ObjectMapper());
		pr.build();
		return pr;
	}
	
	private static final class ObjectMapper implements RowMapper<TaskObject>{
		public TaskObject mapRow(ResultSet rs,int rownum) throws SQLException{
			TaskObject obj =new TaskObject();
			obj.setId( rs.getInt("_id") );
			obj.setFeatureId(rs.getInt("feature_id"));
			obj.setTrackerId(rs.getInt("tracker_id"));
			obj.setSubject( rs.getString("subject") );
			obj.setDescription(rs.getString("description"));
			obj.setAssignee( rs.getInt("assignee"));
			obj.setStatusId( rs.getInt("status_id"));
			obj.setCategorygroup( rs.getInt( "categorygroup"));
			obj.setCategory( rs.getInt("category"));
			
			obj.setStartDate( rs.getTimestamp("start_date").getTime() );
			obj.setDueDate( rs.getTimestamp("due_date").getTime());
			obj.setFeatureName( rs.getString("feature_name"));
			obj.setTrackerName( rs.getString( "tracker_name"));
			obj.setStatusState( rs.getInt( "status_state"));
			obj.setStatusName( rs.getString("status_name"));
			obj.setAssigeeName( rs.getString( "assignee_name"));
			obj.setAssigeeLogin( rs.getString( "assignee_login"));
			obj.setCategorygroupName( rs.getString( "categorygroup_name") );
			obj.setCategoryName( rs.getString( "category_name"));
			//obj.setCreateddate( rs.getDate("createddate").getTime() );
			return obj;
		}
	}
	
}
