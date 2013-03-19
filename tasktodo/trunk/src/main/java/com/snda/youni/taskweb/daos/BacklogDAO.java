package com.snda.youni.taskweb.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.snda.youni.taskweb.beans.BacklogObject;
import com.snda.youni.taskweb.beans.BacklogTreeNode;
import com.snda.youni.taskweb.beans.IssueType;
import com.snda.youni.taskweb.beans.TreeNode;
import com.snda.youni.taskweb.util.HtmlRegexpUtil;

public class BacklogDAO extends AbstractDAOImpl {
	static{
		TABLENAME = "tasks"; // issuetype=IssueType.BACKLOG
	}
	public final static String SQL_INSERT = "insert into "+TABLENAME+" (issuetype,subject,description,parent,createddate) value ("+IssueType.BACKLOG.ordinal()+",?,?,?,?)";
	public final static String SQL_UPDATE = "update "+TABLENAME+" set subject=?,description=? where _id=?";
	private final static String SQL_QUERY_ALL = "SELECT _id,subject,description,parent FROM "+TABLENAME+" where issuetype="+IssueType.BACKLOG.ordinal()+" and deleted<>1";
	public final static String SQL_QUERY_BY_ID = "SELECT _id,subject,description,parent FROM "+TABLENAME+" WHERE issuetype="+IssueType.BACKLOG.ordinal()+" and _id=?";
	public final static String SQL_DELETE = "update "+TABLENAME+" set deleted=1 where _id = ?";
	
	public final static String SQL_QUERY_BY_PARENTID = "SELECT _id,subject,description,parent FROM "+TABLENAME+" WHERE issuetype="+IssueType.BACKLOG.ordinal()+" and parent=? and deleted<>1";
	public final static String SQL_UPDATE_MOVE = "update "+TABLENAME+" set parent=? where _id=?";
	public final static String SQL_UPDATE_RENAME = "update "+TABLENAME+" set subject=? where _id=?";
	public final static String SQL_UPDATE_SPRINT = "update "+TABLENAME+" set feature_id=? where _id=?";
	public final static String SQL_QUERY_BY_SPRINT = "SELECT _id,subject,description,parent FROM "+TABLENAME+" WHERE issuetype="+IssueType.BACKLOG.ordinal()+" and feature_id=? and deleted<>1";
	
	public int save(final BacklogObject obj){
		if( obj.getId()!=0 && findById( obj.getId())!=null ){
			getJdbcTemplate().update(SQL_UPDATE, obj.getName(),obj.getDescription(),obj.getId());
		}else{
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			getJdbcTemplate().update(new PreparedStatementCreator() {  
			    @Override  
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {  
			        PreparedStatement ps = (PreparedStatement) connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			        ps.setString(1, obj.getName());
			        ps.setString(2, obj.getDescription());
			        //ps.setString(3, obj.getLink());
			        ps.setInt(3, obj.getParent());
			        ps.setDate(4, new java.sql.Date((new Date()).getTime()));
			        return ps;  
			    }  
			}, keyHolder);
			
			//obj.setCreateddate( (new Date()).getTime() );
			//getJdbcTemplate().update(SQL_INSERT, obj.getName(),obj.getDescription(),obj.getLink(),obj.getParent(),new Date() );
			int id = keyHolder.getKey().intValue() ;
			return id;
		}
		return obj.getId();
	}
	
	public boolean move(int id,int moveToParent){
		getJdbcTemplate().update(SQL_UPDATE_MOVE,moveToParent,id);
		return true;
	}
	
	public boolean rename(int id, String newname){
		getJdbcTemplate().update(SQL_UPDATE_RENAME,newname,id);
		return true;
	}
	
	public BacklogObject findById(int id){
		BacklogObject obj = getJdbcTemplate().queryForObject(SQL_QUERY_BY_ID, new Object[]{id},new BacklogMapper());
		return obj;
	}
	
	public List<BacklogObject> query(){
		return getJdbcTemplate().query(SQL_QUERY_ALL,new BacklogMapper());
	}
	
	public List<BacklogTreeNode> queryByParentId(int parent){
		return getJdbcTemplate().query(SQL_QUERY_BY_PARENTID,new Object[]{parent},new BacklogTreeNodeMapper());
	}
	
	public List<TreeNode> queryForTreeChildren(int parent){
		return getJdbcTemplate().query(SQL_QUERY_BY_PARENTID,new Object[]{parent},new TreeNodeMapper());
	}
	
	public boolean delete(int id){
		getJdbcTemplate().update(SQL_DELETE, id);
		return true;
	}
	
	public List<BacklogObject> queryBySprint(int sprint_id){
		return getJdbcTemplate().query(SQL_QUERY_BY_SPRINT,new Object[]{sprint_id},new BacklogMapper());
	}
	
	public boolean setSprint(int id,int sprint_id){
		getJdbcTemplate().update(SQL_UPDATE_SPRINT,sprint_id,id);
		return true;
	}
	
	private static final class BacklogMapper implements RowMapper<BacklogObject>{
		public BacklogObject mapRow(ResultSet rs,int rownum) throws SQLException{
			BacklogObject obj =new BacklogObject();
			obj.setId( rs.getInt("_id") );
			obj.setName( rs.getString("subject") );
			obj.setParent( rs.getInt("parent"));
			//obj.setLink( rs.getString("link"));
			//obj.setCreateddate( rs.getDate("createddate").getTime() );
			return obj;
		}
	}
	
	private static final class BacklogTreeNodeMapper implements RowMapper<BacklogTreeNode>{
		public BacklogTreeNode mapRow(ResultSet rs,int rownum) throws SQLException{
			BacklogTreeNode obj =new BacklogTreeNode();
			obj.setId( rs.getInt("_id") );
			obj.setName( rs.getString("subject") );
			//obj.setParent( rs.getInt("parent"));
			if(obj.getId()!=203 && obj.getId()!=205 && obj.getId()!=204){
				obj.setState("open");
			}
			//obj.setLink( rs.getString("link"));
			//obj.setCreateddate( rs.getDate("createddate").getTime() );
			return obj;
		}
	}
	
	private static final class TreeNodeMapper implements RowMapper<TreeNode>{
		public TreeNode mapRow(ResultSet rs,int rownum) throws SQLException{
			TreeNode obj =new TreeNode();
			obj.setData(rs.getString("subject"));
			
			obj.putAttr("id", ""+rs.getInt("_id"));
			obj.putAttr("name",NullStringConvert(rs.getString("subject")));
			obj.putAttr("description",HtmlRegexpUtil.replaceTag( NullStringConvert(rs.getString("description"))));
			//obj.putAttr("link", NullStringConvert(rs.getString("link")));
			//int folder = rs.getInt("folder");
			//if(folder==1) obj.setIcon("folder");
			
			//TreeNode node_b_1 = new TreeNode();
			//node_b_1.setData("B");
			//node_b_1.putMetadata("id", "002");			
			//obj.getChildren().add(node_b_1);
			
			return obj;
		}
	}
	
	private static String NullStringConvert(String input){
		return (input==null)?"":input;
	}
	
	
	
}
