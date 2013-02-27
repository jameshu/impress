package com.snda.youni.taskweb.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.snda.iyouni.icommon.context.AppSettings;

public class CurrentUserCookie {

	public CurrentUserCookie(HttpServletRequest request) {
		String key = AppSettings.getInstance().getString("user.cookie.name", "");
		String keyvalue = "";
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(key)) {
					keyvalue = cookie.getValue();
				}
			}
			if(keyvalue!=null && keyvalue.length()>0){
				userId = Integer.parseInt( keyvalue.split(":")[0] );
				userLogin = keyvalue.split(":")[1];
			}
		}
	}
	
	public int userId;
	public String userLogin;

}
