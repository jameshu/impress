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
import com.snda.youni.taskweb.beans.CategoryObject;
import com.snda.youni.taskweb.beans.TrackerObject;
import com.snda.youni.taskweb.daos.CategorygroupDAO;
import com.snda.youni.taskweb.daos.TrackerDAO;
import com.snda.youni.taskweb.util.RequestParameters;

@Controller
@RequestMapping("/tracker")
public class TrackerController {

	@RequestMapping(value="/list")
	public ModelAndView grouplist(HttpServletRequest request,HttpServletResponse response){
		
		TrackerDAO dao = BeanLocator.getBean("trackerDAO");
		List<TrackerObject> list = dao.query();
		
		ModelAndView mav =  new ModelAndView("trackerlist");
		mav.addObject("trackerlist", list);
		return mav;
	}
	
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response){
		
		int id = RequestParameters.getInt(request, "tracker_id", 0);
		String name = request.getParameter("tracker_name");
		
		TrackerObject obj = new TrackerObject();
		obj.setId(id);
		obj.setName(name);
		
		TrackerDAO dao = BeanLocator.getBean("trackerDAO");
		dao.save(obj);
		
		try {
			response.sendRedirect("/tracker/list");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/delete/{id}")
	public ModelAndView groupdelete(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
		
		TrackerDAO dao = BeanLocator.getBean("trackerDAO");
		dao.delete(id);
		
		try {
			response.sendRedirect("/tracker/list");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		
}
