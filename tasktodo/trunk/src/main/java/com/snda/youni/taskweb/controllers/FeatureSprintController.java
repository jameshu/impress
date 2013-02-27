package com.snda.youni.taskweb.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.snda.iyouni.icommon.spring.BeanLocator;
import com.snda.youni.taskweb.beans.FeatureSprintObject;
import com.snda.youni.taskweb.beans.StatusObject;
import com.snda.youni.taskweb.beans.UserObject;
import com.snda.youni.taskweb.daos.FeatureSprintDAO;
import com.snda.youni.taskweb.daos.StatusDAO;
import com.snda.youni.taskweb.daos.UserDAO;
import com.snda.youni.taskweb.util.RequestParameters;

@Controller
@RequestMapping("/fsprint")
public class FeatureSprintController {

	@RequestMapping(value="/list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		
		FeatureSprintDAO dao = BeanLocator.getBean("fsprintDAO");
		List<FeatureSprintObject> fsprintlist = dao.query();
		
		UserDAO userDAO = BeanLocator.getBean("userDAO");
		List<UserObject> userlist = userDAO.query();
		
		ModelAndView mav =  new ModelAndView("featuresprintlist");
		mav.addObject("userlist", userlist);
		mav.addObject("fsprintlist",fsprintlist);
		return mav;
	}
	
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response){
		
		int id = RequestParameters.getInt(request, "id", 0);
		String name = request.getParameter("name");
		String desc = RequestParameters.getString(request, "description", "");
		int pd_id = RequestParameters.getInt(request, "pd", 0);
		int pm_id = RequestParameters.getInt(request, "pm", 0);
		
		FeatureSprintObject obj = new FeatureSprintObject();
		obj.setId(id);
		obj.setName(name);
		obj.setDescription(desc);
		obj.setPd(pd_id);
		obj.setPm(pm_id);
		
		FeatureSprintDAO dao = BeanLocator.getBean("fsprintDAO");
		dao.save(obj);
		
		try {
			response.sendRedirect("/fsprint/list");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/delete/{id}")
	public ModelAndView groupdelete(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
		
		FeatureSprintDAO dao = BeanLocator.getBean("fsprintDAO");
		dao.delete(id);
		
		try {
			response.sendRedirect("/fsprint/list");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		
}
