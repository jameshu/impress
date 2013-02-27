package com.snda.youni.taskweb.managers;

import java.util.HashMap;
import java.util.Map;

import com.snda.youni.taskweb.beans.UserObject;
import com.snda.youni.taskweb.daos.UserDAO;

public class UserManager {

	private static Map cache = new HashMap();
	
	private UserDAO userDAO;

	public boolean saveUser(UserObject user){
		
		return userDAO.save(user);
	}
	
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	

}
