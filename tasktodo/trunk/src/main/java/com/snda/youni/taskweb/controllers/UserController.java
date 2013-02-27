package com.snda.youni.taskweb.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.snda.iyouni.icommon.spring.BeanLocator;
import com.snda.youni.taskweb.beans.UserObject;
import com.snda.youni.taskweb.daos.UserDAO;
import com.snda.youni.taskweb.util.RequestParameters;

@Controller
@RequestMapping("/user")
public class UserController {

	@RequestMapping(value="/list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		
		UserDAO userDAO = BeanLocator.getBean("userDAO");
		List<UserObject> userlist = userDAO.query();
		
		ModelAndView mav =  new ModelAndView("userlist");
		mav.addObject("userlist", userlist);
		return mav;
	}
	
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response){
		
		int user_id = RequestParameters.getInt(request, "user_id", 0);
		String user_login = request.getParameter("user_login");
		String user_name = request.getParameter("user_name");
		String user_email = request.getParameter("user_email");
		
		UserObject obj = new UserObject();
		obj.setId(user_id);
		obj.setLogin(user_login);
		obj.setName(user_name);
		obj.setEmail(user_email);
		
		UserDAO userDAO = BeanLocator.getBean("userDAO");
		userDAO.save(obj);
		
		List<UserObject> userlist = userDAO.query();
		
		ModelAndView mav =  new ModelAndView("userlist");
		mav.addObject("userlist", userlist);
		return mav;
	}
	
}
