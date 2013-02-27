package com.snda.youni.taskweb.util;

import javax.servlet.http.HttpServletRequest;

public class RequestParameters {

	public static String getString(HttpServletRequest request,String param,String defaultvalue){
		String value = request.getParameter(param);
		if(value==null || value.equals("")) value = defaultvalue;
		return value;
	}
	
	public static int getInt(HttpServletRequest request,String param,int defaultvalue){
		String value = request.getParameter(param);
		if(value==null || value.equals("")) value = ""+defaultvalue;
		return Integer.parseInt(value);
	}
	
}
