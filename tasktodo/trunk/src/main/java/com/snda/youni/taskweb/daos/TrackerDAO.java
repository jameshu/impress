package com.snda.youni.taskweb.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.snda.youni.taskweb.beans.TrackerObject;

public class TrackerDAO extends AbstractDAOImpl {

	static{
		TABLENAME = "trackers";
	}
	public final static String SQL_INSERT = "insert into "+TABLENAME+" (name) value (?)";
	public final static String SQL_UPDATE = "update "+TABLENAME+" set name=? where _id=?";
	private final static String SQL_QUERY_ALL = "SELECT * FROM "+TABLENAME+" where deleted<>1 and _id<>0";
	public final static String SQL_QUERY_BY_ID = "SELECT * FROM "+TABLENAME+" WHERE _id=?";
	public final static String SQL_DELETE = "update "+TABLENAME+" set deleted=1 where _id = ?";
	
	public boolean save(TrackerObject obj){
		
		if(obj.getId()!=0 && findTrackerById( obj.getId())!=null ){
			getJdbcTemplate().update(SQL_UPDATE, obj.getName(),obj.getId());
		}else{
			//obj.setCreateddate( (new Date()).getTime() );
			getJdbcTemplate().update(SQL_INSERT, obj.getName());
		}
		return true;
	}
	
	public TrackerObject findTrackerById(int id){
		TrackerObject user = getJdbcTemplate().queryForObject(SQL_QUERY_BY_ID, new Object[]{id},new TrackerMapper());
		return user;
	}
	
	public List<TrackerObject> query(){
		return getJdbcTemplate().query(SQL_QUERY_ALL,new TrackerMapper());
	}
	
	public boolean delete(int id){
		getJdbcTemplate().update(SQL_DELETE, id);
		return true;
	}
	
	private static final class TrackerMapper implements RowMapper<TrackerObject>{
		public TrackerObject mapRow(ResultSet rs,int rownum) throws SQLException{
			TrackerObject tracker =new TrackerObject();
			tracker.setId( rs.getInt("_id") );
			tracker.setName( rs.getString("name") );
			return tracker;
		}
	}
	
}
