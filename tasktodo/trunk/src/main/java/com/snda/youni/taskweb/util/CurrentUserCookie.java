package com.snda.youni.taskweb.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.snda.youni.taskweb.common.AppSettings;

public class CurrentUserCookie {

	public CurrentUserCookie(HttpServletRequest request) {
		String key = AppSettings.getInstance().getString("user.cookie.name", "");
		String key_sprint = AppSettings.getInstance().getString("user.cookie.sprintid", "");
		
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				
				//user cookie name
				if (cookie.getName().equals(key)) {
					String keyvalue = cookie.getValue();
					if(keyvalue!=null && keyvalue.length()>0){
						userId = Integer.parseInt( keyvalue.split(":")[0] );
						userLogin = keyvalue.split(":")[1];
					}
				}else if (cookie.getName().equals(key_sprint)) {
					String keyvalue = cookie.getValue();
					if(keyvalue!=null && keyvalue.length()>0){
						currentSprintId = keyvalue;
					}
				}
				
				//user cookie sprint
				
			}
			
		}
	}
	
	public int userId;
	public String userLogin;
	public String currentSprintId = "";

}
