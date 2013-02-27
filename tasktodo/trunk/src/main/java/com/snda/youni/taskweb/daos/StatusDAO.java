package com.snda.youni.taskweb.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.snda.youni.taskweb.beans.StatusObject;

public class StatusDAO extends AbstractDAOImpl {

	static{
		TABLENAME = "statuses";
	}
	public final static String SQL_INSERT = "insert into "+TABLENAME+" (name,state,tracker_id) value (?,?,?)";
	public final static String SQL_UPDATE = "update "+TABLENAME+" set name=?,state=?,tracker_id=? where _id=?";
	private final static String SQL_QUERY_ALL = "SELECT distinct A._id,A.name, A.state, A.tracker_id, B.name AS tracker_name " +
			"FROM statuses A , trackers B " +
			"WHERE A.tracker_id= B._id and A.deleted<>1 AND A._id<>0 AND A.name IS NOT NULL AND A.name<>''";
	public final static String SQL_QUERY_BY_ID = "SELECT * FROM "+TABLENAME+" WHERE _id=?";
	public final static String SQL_QUERY_BY_TRACKER_ID = "SELECT * FROM "+TABLENAME+" WHERE tracker_id=? and deleted<>1";
	public final static String SQL_DELETE = "update "+TABLENAME+" set deleted=1 where _id = ?";
	
	public boolean save(StatusObject obj){
		
		if( obj.getId()!=0 && findStatusById( obj.getId())!=null ){
			getJdbcTemplate().update(SQL_UPDATE, obj.getName(),obj.getState(),obj.getTrackerId(),obj.getId());
		}else{
			//obj.setCreateddate( (new Date()).getTime() );
			getJdbcTemplate().update(SQL_INSERT, obj.getName(),obj.getState(),obj.getTrackerId());
		}
		return true;
	}
	
	public StatusObject findStatusById(int id){
		StatusObject obj = getJdbcTemplate().queryForObject(SQL_QUERY_BY_ID, new Object[]{id},new StatusMapper());
		return obj;
	}
	
	public List<StatusObject> query(){
		return getJdbcTemplate().query(SQL_QUERY_ALL,new StatusWithTrackerNameMapper());
	}
	
	public List<StatusObject> queryByTrackerId(int tracker_id){
		return getJdbcTemplate().query(SQL_QUERY_BY_TRACKER_ID, new Object[]{tracker_id},new StatusMapper());
	}
	
	public boolean delete(int id){
		getJdbcTemplate().update(SQL_DELETE, id);
		return true;
	}
	
	private static final class StatusWithTrackerNameMapper implements RowMapper<StatusObject>{
		public StatusObject mapRow(ResultSet rs,int rownum) throws SQLException{
			StatusObject obj =new StatusObject();
			obj.setId( rs.getInt("_id") );
			obj.setName( rs.getString("name") );
			obj.setTrackerId( rs.getInt("tracker_id") );
			obj.setTrackerName(  rs.getString( "tracker_name"));
			obj.setState( rs.getInt( "state") );
			return obj;
		}
	}
	
	private static final class StatusMapper implements RowMapper<StatusObject>{
		public StatusObject mapRow(ResultSet rs,int rownum) throws SQLException{
			StatusObject obj =new StatusObject();
			obj.setId( rs.getInt("_id") );
			obj.setName( rs.getString("name") );
			obj.setTrackerId( rs.getInt("tracker_id") );
			obj.setState( rs.getInt( "state") );
			return obj;
		}
	}


}
