package com.snda.youni.taskweb.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.snda.youni.taskweb.beans.CategoryObject;

public class CategorygroupDAO extends AbstractDAOImpl {

	static{
		TABLENAME = "categorygroups";
	}
	public final static String SQL_INSERT = "insert into "+TABLENAME+" (name) value (?)";
	public final static String SQL_UPDATE = "update "+TABLENAME+" set name=? where _id=?";
	private final static String SQL_QUERY_ALL = "SELECT * FROM "+TABLENAME+" where deleted<>1 and _id<>0";
	public final static String SQL_QUERY_BY_ID = "SELECT * FROM "+TABLENAME+" WHERE _id=?";
	public final static String SQL_DELETE = "update "+TABLENAME+" set deleted=1 where _id = ?";
	
	public boolean save(CategoryObject obj){
		
		if( obj.getId()!=0 && findCategorygroupById( obj.getId())!=null ){
			getJdbcTemplate().update(SQL_UPDATE, obj.getName(),obj.getId());
		}else{
			//obj.setCreateddate( (new Date()).getTime() );
			getJdbcTemplate().update(SQL_INSERT, obj.getName() );
		}
		return true;
	}
	
	public CategoryObject findCategorygroupById(int id){
		CategoryObject obj = getJdbcTemplate().queryForObject(SQL_QUERY_BY_ID, new Object[]{id},new CategorygourpMapper());
		if( obj!=null ) obj.isGroup = true;
		return obj;
	}
	
	public List<CategoryObject> query(){
		return getJdbcTemplate().query(SQL_QUERY_ALL,new CategorygourpMapper());
	}
	
	public boolean delete(int id){
		getJdbcTemplate().update(SQL_DELETE, id);
		return true;
	}
	
	private static final class CategorygourpMapper implements RowMapper<CategoryObject>{
		public CategoryObject mapRow(ResultSet rs,int rownum) throws SQLException{
			CategoryObject obj =new CategoryObject();
			obj.isGroup = true;
			obj.setId( rs.getInt("_id") );
			obj.setName( rs.getString("name") );
			return obj;
		}
	}
}
