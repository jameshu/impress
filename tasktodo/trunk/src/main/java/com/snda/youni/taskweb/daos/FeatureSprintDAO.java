package com.snda.youni.taskweb.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.snda.youni.taskweb.beans.FeatureSprintObject;

public class FeatureSprintDAO extends AbstractDAOImpl {

	static{
		TABLENAME = "featureprojects";
	}
	public final static String SQL_INSERT = "insert into "+TABLENAME+" (name,description,pm,pd,createddate) value (?,?,?,?,?)";
	public final static String SQL_UPDATE = "update "+TABLENAME+" set name=?,description=?,pm=?,pd=? where _id=?";
	private final static String SQL_QUERY_ALL = "SELECT DISTINCT A._id,A.name, A.description,A.pm, B.name AS pm_name,A.pd,C.name AS pd_name,A.createddate " +
												"FROM featureprojects A , users B , users C " +
												"WHERE A.pm= B._id AND A.pd = C._id AND A.deleted<>1 AND A._id<>0 AND A.name IS NOT NULL AND A.name<>''";
	public final static String SQL_QUERY_BY_ID = "SELECT * FROM "+TABLENAME+" WHERE _id=?";
	public final static String SQL_DELETE = "update "+TABLENAME+" set deleted=1 where _id = ?";
	
	public boolean save(FeatureSprintObject obj){
		
		if( obj.getId()!=0 && findById( obj.getId())!=null ){
			getJdbcTemplate().update(SQL_UPDATE, obj.getName(),obj.getDescription(),obj.getPm(),obj.getPd(),obj.getId());
		}else{
			obj.setCreateddate( (new Date()).getTime() );
			getJdbcTemplate().update(SQL_INSERT, obj.getName(),obj.getDescription(),obj.getPm(),obj.getPd(),new Date() );
		}
		return true;
	}
	
	public FeatureSprintObject findById(int id){
		FeatureSprintObject obj = getJdbcTemplate().queryForObject(SQL_QUERY_BY_ID, new Object[]{id},new ObjectMapper());
		return obj;
	}
	
	public List<FeatureSprintObject> query(){
		return getJdbcTemplate().query(SQL_QUERY_ALL,new ObjectWithNameMapper());
	}
	
	public boolean delete(int id){
		getJdbcTemplate().update(SQL_DELETE, id);
		return true;
	}
	
	private static final class ObjectWithNameMapper implements RowMapper<FeatureSprintObject>{
		public FeatureSprintObject mapRow(ResultSet rs,int rownum) throws SQLException{
			FeatureSprintObject obj =new FeatureSprintObject();
			obj.setId( rs.getInt("_id") );
			obj.setName( rs.getString("name") );
			obj.setDescription( rs.getString( "description"));
			obj.setPd( rs.getInt("pd") );
			obj.setPdName( rs.getString( "pd_name"));
			obj.setPm( rs.getInt("pm") );
			obj.setPmName( rs.getString( "pm_name"));
			obj.setCreateddate( rs.getDate("createddate").getTime() );
			return obj;
		}
	}
	
	private static final class ObjectMapper implements RowMapper<FeatureSprintObject>{
		public FeatureSprintObject mapRow(ResultSet rs,int rownum) throws SQLException{
			FeatureSprintObject obj =new FeatureSprintObject();
			obj.setId( rs.getInt("_id") );
			obj.setName( rs.getString("name") );
			obj.setDescription( rs.getString( "description"));
			obj.setPd( rs.getInt("pd") );
			obj.setPm( rs.getInt("pm") );
			obj.setCreateddate( rs.getDate("createddate").getTime() );
			return obj;
		}
	}
	
}
