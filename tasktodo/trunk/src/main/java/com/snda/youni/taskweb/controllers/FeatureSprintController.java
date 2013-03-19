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
import com.snda.youni.taskweb.beans.BacklogObject;
import com.snda.youni.taskweb.beans.FeatureSprintObject;
import com.snda.youni.taskweb.beans.UserObject;
import com.snda.youni.taskweb.daos.BacklogDAO;
import com.snda.youni.taskweb.daos.FeatureSprintDAO;
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
	
	/**
	 * Use to show what adding backlogs to sprint page.
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/backlogbingding")
	public ModelAndView backlog(HttpServletRequest request,HttpServletResponse response){
		
		int backlog_id = RequestParameters.getInt(request, "backlog_id", -1);
		
		FeatureSprintDAO fsprintDAO = BeanLocator.getBean("fsprintDAO");
		List<FeatureSprintObject> fsprintlist = fsprintDAO.query();
		
		ModelAndView mav = new ModelAndView("sprintbacklog");
		mav.addObject("backlog_id", backlog_id);
		mav.addObject("fsprintlist",fsprintlist);
		return mav;
		
	}
	
	@RequestMapping(value="/{id}/backloglist")
	public ModelAndView backloglist(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
		
		int backlog_id = RequestParameters.getInt(request, "backlog_id", -1);
		
		BacklogDAO backlogDAO = BeanLocator.getBean("backlogDAO");
		List<BacklogObject> backlogs = backlogDAO.queryBySprint(id);
		
		FeatureSprintDAO fsprintDAO = BeanLocator.getBean("fsprintDAO");
		List<FeatureSprintObject> fsprintlist = fsprintDAO.query();
		
		ModelAndView mav = new ModelAndView("sprintbacklog_list");
		mav.addObject("backlog_id", backlog_id);
		mav.addObject("fsprintlist",fsprintlist);
		mav.addObject("backloglist",backlogs);
		return mav;
		
	}
	
//	@RequestMapping(value="/{id}/backlog/save")
//	public ModelAndView backlogsave(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
//		
//		int backlog_id = RequestParameters.getInt(request, "backlog_id", -1);
//		
//		BacklogDAO backlogDAO = BeanLocator.getBean("backlogDAO");
//		backlogDAO.setSprint(backlog_id, id);
//		
//		try {
//			response.setCharacterEncoding("utf-8");
//			response.getWriter().append("{\"status\":\"200\"}");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//		
//	}
	
	@RequestMapping(value="/listjson")
	public ModelAndView listjson(HttpServletRequest request,HttpServletResponse response){
		
		FeatureSprintDAO dao = BeanLocator.getBean("fsprintDAO");
		List<FeatureSprintObject> fsprintlist = dao.query();
		
		try {
			response.setCharacterEncoding("utf-8");
			OutputStream os = response.getOutputStream();
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(os, fsprintlist);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
		
}
