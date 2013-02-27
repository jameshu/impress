package com.snda.youni.taskweb.daos;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractDAOImpl {

	public static String TABLENAME = "";
	
	//public final static String SQL_DELETE = "update "+TABLENAME+" set deleted=1 where _id = ?";
	public final static String SQL_QUERY_BY_ID = "SELECT * FROM "+TABLENAME+" WHERE _id=?";
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//public boolean delete(String id){
	//	this.jdbcTemplate.update(SQL_DELETE, id);
	//	return true;
	//}
	
	
}
