package com.snda.youni.taskweb.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.snda.iyouni.icommon.spring.BeanLocator;
import com.snda.youni.taskweb.beans.StatusObject;
import com.snda.youni.taskweb.beans.TrackerObject;
import com.snda.youni.taskweb.daos.StatusDAO;
import com.snda.youni.taskweb.daos.TrackerDAO;
import com.snda.youni.taskweb.util.RequestParameters;

@Controller
@RequestMapping("/status")
public class StatusController {

	@RequestMapping(value="/list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		
		TrackerDAO trackerDAO = BeanLocator.getBean("trackerDAO");
		List<TrackerObject> trackerlist = trackerDAO.query();
		
		StatusDAO dao = BeanLocator.getBean("statusDAO");
		List<StatusObject> list = dao.query();
		
		ModelAndView mav =  new ModelAndView("statuslist");
		mav.addObject("statuslist", list);
		mav.addObject("trackerlist",trackerlist);
		return mav;
	}
	
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response){
		
		int id = RequestParameters.getInt(request, "status_id", 0);
		String name = request.getParameter("status_name");
		int tracker_id = RequestParameters.getInt(request, "tracker_id", 0);
		int state = RequestParameters.getInt(request, "state", 0);
		
		StatusObject obj = new StatusObject();
		obj.setId(id);
		obj.setName(name);
		obj.setTrackerId(tracker_id);
		obj.setState(state);
		
		StatusDAO dao = BeanLocator.getBean("statusDAO");
		dao.save(obj);
		
		try {
			response.sendRedirect("/status/list");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/delete/{id}")
	public ModelAndView groupdelete(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
		
		StatusDAO dao = BeanLocator.getBean("statusDAO");
		dao.delete(id);
		
		try {
			response.sendRedirect("/status/list");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/json")
	public ModelAndView json(HttpServletRequest request,HttpServletResponse response){
		
		int tracker_id = RequestParameters.getInt(request, "tracker_id", 0);
		
		StatusDAO dao = BeanLocator.getBean("statusDAO");
		List<StatusObject> list = dao.queryByTrackerId(tracker_id);
		
		ObjectMapper mapper = new ObjectMapper();
		//mapper.write
		
		
		
		try {
			response.setCharacterEncoding("utf-8");
			//String jsonstr = mapper.writeValueAsString(list);
			OutputStream os = response.getOutputStream();
			mapper.writeValue(os, list);
			os.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
		//ModelAndView mav =  new ModelAndView("statuslist");
		//mav.addObject("statuslist", list);
		//return mav;
	}
		
}
