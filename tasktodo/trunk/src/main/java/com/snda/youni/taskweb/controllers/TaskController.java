package com.snda.youni.taskweb.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.snda.youni.taskweb.beans.CategoryObject;
import com.snda.youni.taskweb.beans.FeatureSprintObject;
import com.snda.youni.taskweb.beans.PageResult;
import com.snda.youni.taskweb.beans.TaskObject;
import com.snda.youni.taskweb.beans.TrackerObject;
import com.snda.youni.taskweb.beans.UserObject;
import com.snda.youni.taskweb.common.BeanLocator;
import com.snda.youni.taskweb.daos.CategorygroupDAO;
import com.snda.youni.taskweb.daos.FeatureSprintDAO;
import com.snda.youni.taskweb.daos.TaskDAO;
import com.snda.youni.taskweb.daos.TaskQueryBuilder;
import com.snda.youni.taskweb.daos.TrackerDAO;
import com.snda.youni.taskweb.daos.UserDAO;
import com.snda.youni.taskweb.util.CurrentUserCookie;
import com.snda.youni.taskweb.util.RequestParameters;


/*
 * There are three return tasks views: json, list , calendar , kanban . 
 * parameter use "rview" .
 */
@Controller
@RequestMapping("/task")
public class TaskController {

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return list(request,response);
	}
	
	@RequestMapping(value="/new")
	public ModelAndView _new(HttpServletRequest request,HttpServletResponse response){

		FeatureSprintDAO fsprintDAO = BeanLocator.getBean("fsprintDAO");
		List<FeatureSprintObject> fsprintlist = fsprintDAO.query();
		
		TrackerDAO trackerDAO = BeanLocator.getBean("trackerDAO");
		List<TrackerObject> trackerlist = trackerDAO.query();
		
		UserDAO userDAO = BeanLocator.getBean("userDAO");
		List<UserObject> userlist = userDAO.query();
		
		CategorygroupDAO cgDAO = BeanLocator.getBean("categorygroupDAO");
		List<CategoryObject> cglist = cgDAO.query();
		
		ModelAndView mav =  new ModelAndView("taskedit");
		mav.addObject("fsprintlist",fsprintlist);
		mav.addObject("trackerlist", trackerlist);
		mav.addObject("userlist", userlist);
		mav.addObject("cglist", cglist);
		return mav;
	}
	
	@RequestMapping(value="/{taskid}/edit")
	public ModelAndView edit(@PathVariable int taskid,HttpServletRequest request,HttpServletResponse response){
		TaskObject toEditTask = null;
		
		TaskDAO taskDAO = BeanLocator.getBean("taskDAO");
		if(taskid!=0){
			toEditTask = taskDAO.findById(taskid);
		}
		
		//featuresprintlist
		FeatureSprintDAO fsprintDAO = BeanLocator.getBean("fsprintDAO");
		List<FeatureSprintObject> fsprintlist = fsprintDAO.query();
		
		TrackerDAO trackerDAO = BeanLocator.getBean("trackerDAO");
		List<TrackerObject> trackerlist = trackerDAO.query();
		
		UserDAO userDAO = BeanLocator.getBean("userDAO");
		List<UserObject> userlist = userDAO.query();
		
		CategorygroupDAO cgDAO = BeanLocator.getBean("categorygroupDAO");
		List<CategoryObject> cglist = cgDAO.query();
		
		ModelAndView mav =  new ModelAndView("taskedit");
		mav.addObject("fsprintlist",fsprintlist);
		mav.addObject("trackerlist", trackerlist);
		mav.addObject("userlist", userlist);
		mav.addObject("cglist", cglist);
		mav.addObject("task",toEditTask);
		return mav;
	}
	
	@RequestMapping(value="/{taskid}/updatestatus")
	public ModelAndView updatestatus(@PathVariable int taskid,HttpServletRequest request,HttpServletResponse response){
		int status_id = RequestParameters.getInt(request, "status_id", 0);
		
		TaskDAO taskDAO = BeanLocator.getBean("taskDAO");
		taskDAO.updateStatus(taskid, status_id);
		
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().append("{\"status\":\"200\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response){
		
		int task_id = RequestParameters.getInt(request, "task_id", 0);
		int feature_id = RequestParameters.getInt(request, "feature_id", 0);
		int tracker_id = RequestParameters.getInt(request, "tracker_id", 0);
		int status_id = RequestParameters.getInt(request, "status_id", 0);
		String subject = RequestParameters.getString(request, "subject", "");
		String description = RequestParameters.getString(request, "description", "");
		int assignee_id = RequestParameters.getInt(request, "assignee_id", 0);
		int categorygroup_id = RequestParameters.getInt(request, "categorygroup_id", 0);
		int category_id = RequestParameters.getInt(request, "category_id", 0);
		String start_date = request.getParameter("start_datetime");
		String due_date = request.getParameter("due_datetime");
		
		//String start_time = RequestParameters.getString(request, "start_time", "");
		//String due_time = RequestParameters.getString(request, "due_time", "");
		
		//start_time = (start_time.length()>0)?start_time + ":00":"00:00:01";
		//due_time = (due_time.length()>0)?due_time + ":00":"00:00:01";
		
		long startDate = 0;
		long dueDate = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			startDate = sdf.parse(start_date + ":00").getTime();
			dueDate = sdf.parse(due_date + ":00").getTime();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		TaskObject obj = new TaskObject();
		obj.setId( task_id);
		obj.setFeatureId(feature_id);
		obj.setTrackerId(tracker_id);
		obj.setStatusId(status_id);
		obj.setSubject(subject);
		obj.setDescription(description);
		obj.setAssignee(assignee_id);
		obj.setCategorygroup(categorygroup_id);
		obj.setCategory(category_id);
		obj.setStartDate(startDate);
		obj.setDueDate(dueDate);
		
		TaskDAO taskDAO = BeanLocator.getBean("taskDAO");
		taskDAO.save(obj);
		
		
		try {
			//response.sendRedirect("/task/list");
			response.setCharacterEncoding("utf-8");
			response.getWriter().append("{\"status\":\"200\"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		
		int page = RequestParameters.getInt(request, "q_page", 1);
		int startnum = (page-1)*50;
		
		//list or calendar view
		String view = RequestParameters.getString(request, "view","list" );
		
		Map<String,String> map = new HashMap<String,String>();
		
		String q_subject = RequestParameters.getString(request, "q_subject", "");
		if(q_subject.length()>0){
			map.put(TaskQueryBuilder.FILTERNAME_subject, ""+q_subject);
		}
		int q_fs_id = RequestParameters.getInt(request, "q_fs_id" ,0);
		if(q_fs_id!=0){
			map.put(TaskQueryBuilder.FILTERNAME_feature_id, ""+q_fs_id);
		}
		String q_fs_name = RequestParameters.getString(request, "q_fs_name", "");
		if(q_fs_name.length()>0){
			map.put(TaskQueryBuilder.FILTERNAME_feature_name, ""+q_fs_name);
		}
		int q_cgid = RequestParameters.getInt(request, "q_cgid" ,0);
		if(q_cgid!=0){
			map.put(TaskQueryBuilder.FILTERNAME_categorygroup, ""+q_cgid);
		}
		String q_assigneelogins = RequestParameters.getString(request, "q_assignee_login", "");
		if(q_assigneelogins.length()>0){
			map.put(TaskQueryBuilder.FILTERNAME_assignee_login, ""+q_assigneelogins);
		}

		//output result:
		ModelAndView mav =  new ModelAndView("task"+view);
		
		TaskDAO taskDAO = BeanLocator.getBean("taskDAO");
		PageResult<TaskObject> pr = taskDAO.query(startnum, 50, map);
		pr.page = page;
		
		CategorygroupDAO cgDAO = BeanLocator.getBean("categorygroupDAO");
		List<CategoryObject> cglist = cgDAO.query();
		
		UserDAO userDAO = BeanLocator.getBean("userDAO");
		List<UserObject> userlist = userDAO.query();
		
		TrackerDAO trackerDAO = BeanLocator.getBean("trackerDAO");
		List<TrackerObject> trackerlist = trackerDAO.query();
		
		mav.addObject("tasklist_pr",pr);
		mav.addObject("cglist",cglist);
		mav.addObject("userlist", userlist);
		mav.addObject("trackerlist", trackerlist);
		
		for (Iterator iterator = request.getParameterMap().keySet().iterator(); iterator.hasNext();) {
			String kname = (String) iterator.next();
			if(kname.startsWith("q_")){
				mav.addObject(kname,request.getParameter(kname));
			}
		}

		return mav;
	}
	
	@RequestMapping(value="/me")
	public ModelAndView me(HttpServletRequest request,HttpServletResponse response){
		
		String rview = RequestParameters.getString(request, "rview", "kanban");
		CurrentUserCookie cuc = new CurrentUserCookie(request);
		
		String q_status_state = RequestParameters.getString(request, "q_status_state", "");
		
		int page = RequestParameters.getInt(request, "page", 1);
		int startnum = (page-1)*50;	

		Map<String,String> map = new HashMap<String,String>();
		map.put(TaskQueryBuilder.FILTERNAME_assignee, ""+cuc.userId);
		
		
		if(q_status_state.length()>0){
			map.put(TaskQueryBuilder.FILTERNAME_status_state, ""+q_status_state);
		}

		TaskDAO taskDAO = BeanLocator.getBean("taskDAO");
		PageResult<TaskObject> pr = taskDAO.query(startnum, 50, map);
		pr.page = page;
		
		if(rview.equals("list")){
			ModelAndView mav =  new ModelAndView("tasklist");
			mav.addObject("tasklist_pr",pr);
			return mav;
		}else if(rview.equals("json")){
			try {
				ObjectMapper mapper = new ObjectMapper();
				response.setCharacterEncoding("utf-8");
				//String jsonstr = mapper.writeValueAsString(pr.data);
				OutputStream os = response.getOutputStream();
				mapper.writeValue(os, pr.data);
				os.flush();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}else if(rview.equals("kanban")){
			map.clear();
			map.put(TaskQueryBuilder.FILTERNAME_assignee, ""+cuc.userId);
			map.put(TaskQueryBuilder.FILTERNAME_status_state, "0");

			List<TaskObject> list_todo = taskDAO.queryForList(0, 15, map);
			
			map.put(TaskQueryBuilder.FILTERNAME_status_state, "1");
			List<TaskObject> list_doing = taskDAO.queryForList(0, 15, map);

			map.put(TaskQueryBuilder.FILTERNAME_status_state, "2");
			List<TaskObject> list_done = taskDAO.queryForList(0, 15, map);
			
			map.put(TaskQueryBuilder.FILTERNAME_status_state, "3");
			List<TaskObject> list_killed = taskDAO.queryForList(0, 15, map);
			
			ModelAndView mav =  new ModelAndView("tasks_kanban");
			mav.addObject("tasklist_todo",list_todo);
			mav.addObject("tasklist_doing",list_doing);
			mav.addObject("tasklist_done",list_done);
			mav.addObject("tasklist_killed",list_killed);
			return mav;
		}
		return null;
	}
	
}
