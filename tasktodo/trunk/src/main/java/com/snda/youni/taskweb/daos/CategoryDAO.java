package com.snda.youni.taskweb.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.snda.youni.taskweb.beans.CategoryObject;

public class CategoryDAO extends AbstractDAOImpl {

	static{
		TABLENAME = "categorys";
	}
	public final static String SQL_INSERT = "insert into "+TABLENAME+" (name,categorygroup_id) value (?,?)";
	public final static String SQL_UPDATE = "update "+TABLENAME+" set name=?,categorygroup_id=? where _id=?";
	private final static String SQL_QUERY_ALL = "SELECT distinct A._id,A.name, A.categorygroup_id, B.name AS categorygroup_name " +
			"FROM categorys A , categorygroups B " +
			"WHERE A.categorygroup_id= B._id and A.deleted<>1 AND A._id<>0 AND A.name IS NOT NULL AND A.name<>''";
	public final static String SQL_QUERY_BY_ID = "SELECT * FROM "+TABLENAME+" WHERE _id=?";
	public final static String SQL_QUERY_BY_CATEGORYGROUP_ID = "SELECT * FROM "+TABLENAME+" WHERE categorygroup_id=? and deleted<>1";
	public final static String SQL_DELETE = "update "+TABLENAME+" set deleted=1 where _id = ?";
	
	public boolean save(CategoryObject obj){
		
		if( obj.getId()!=0 && findCategoryById( obj.getId())!=null ){
			getJdbcTemplate().update(SQL_UPDATE, obj.getName(),obj.getGroupId(),obj.getId());
		}else{
			//obj.setCreateddate( (new Date()).getTime() );
			getJdbcTemplate().update(SQL_INSERT, obj.getName(),obj.getGroupId() );
		}
		return true;
	}
	
	public CategoryObject findCategoryById(int id){
		CategoryObject obj = getJdbcTemplate().queryForObject(SQL_QUERY_BY_ID, new Object[]{id},new CategoryMapper());
		return obj;
	}
	
	public List<CategoryObject> query(){
		return getJdbcTemplate().query(SQL_QUERY_ALL,new CategoryWithGroupNameMapper());
	}
	
	public List<CategoryObject> queryByCategorygroupId(int id){
		return getJdbcTemplate().query(SQL_QUERY_BY_CATEGORYGROUP_ID,new Object[]{id},new CategoryMapper());
	}
	
	public boolean delete(int id){
		getJdbcTemplate().update(SQL_DELETE, id);
		return true;
	}
	
	private static final class CategoryWithGroupNameMapper implements RowMapper<CategoryObject>{
		public CategoryObject mapRow(ResultSet rs,int rownum) throws SQLException{
			CategoryObject obj =new CategoryObject();
			obj.isGroup = false;
			obj.setId( rs.getInt("_id") );
			obj.setName( rs.getString("name") );
			obj.setGroupId( rs.getInt("categorygroup_id") );
			obj.setGroupName( rs.getString( "categorygroup_name"));
			return obj;
		}
	}
	
	private static final class CategoryMapper implements RowMapper<CategoryObject>{
		public CategoryObject mapRow(ResultSet rs,int rownum) throws SQLException{
			CategoryObject obj =new CategoryObject();
			obj.isGroup = false;
			obj.setId( rs.getInt("_id") );
			obj.setName( rs.getString("name") );
			obj.setGroupId( rs.getInt("categorygroup_id") );
			return obj;
		}
	}
}
