package com.snda.youni.taskweb.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.snda.iyouni.icommon.spring.BeanLocator;
import com.snda.youni.taskweb.beans.BacklogObject;
import com.snda.youni.taskweb.beans.BacklogTreeNode;
import com.snda.youni.taskweb.beans.FeatureSprintObject;
import com.snda.youni.taskweb.beans.PageResult;
import com.snda.youni.taskweb.beans.TaskObject;
import com.snda.youni.taskweb.beans.TreeNode;
import com.snda.youni.taskweb.daos.BacklogDAO;
import com.snda.youni.taskweb.daos.FeatureSprintDAO;
import com.snda.youni.taskweb.daos.TaskDAO;
import com.snda.youni.taskweb.daos.TaskQueryBuilder;
import com.snda.youni.taskweb.util.HtmlRegexpUtil;
import com.snda.youni.taskweb.util.RequestParameters;

@Controller
@RequestMapping("/backlog")
public class BacklogController {

	@RequestMapping
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return list(-1,request,response);
	}
	
	@RequestMapping(value="/{id}")
	public ModelAndView list(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
		
		FeatureSprintDAO fsprintDAO = BeanLocator.getBean("fsprintDAO");
		List<FeatureSprintObject> fsprintlist = fsprintDAO.query();
		
		BacklogDAO dao = BeanLocator.getBean("backlogDAO");
		List<TreeNode> list = new ArrayList<TreeNode>();
		if(id!=-1){
			
			//if(id==-1)id=
			BacklogObject obj = dao.findById(id);
			
			TreeNode node = new TreeNode();
			node.setData( obj.getName() );
			node.putAttr("id", ""+obj.getId());
			node.putAttr("name", obj.getName() );
			node.putAttr("description",HtmlRegexpUtil.replaceTag( NullStringConvert( obj.getDescription() )));
			list.add(node);
		}else{
			list=  dao.queryForTreeChildren(-1);
		}
		
		
		ModelAndView mav = new ModelAndView("backloglist");
		mav.addObject("id", id);
		mav.addObject("nodes",list);
		mav.addObject("fsprintlist",fsprintlist);
		return mav;
		
	}
	
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response){
		
		int id = RequestParameters.getInt(request, "id", -1);
		
		String name = request.getParameter("name");
		int parent = RequestParameters.getInt(request, "parent", 0);
		String description = RequestParameters.getString(request, "description", "");
		String link = RequestParameters.getString(request, "link", "");
		
		BacklogObject obj = new BacklogObject();
		if(id!=-1){obj.setId(id);}
		obj.setName(name);
		obj.setParent(parent);
		obj.setDescription(description);
		obj.setLink(link);
		
		BacklogDAO dao = BeanLocator.getBean("backlogDAO");
		int tmpid = dao.save(obj);
		obj.setId(tmpid);
		
		try {
			response.setCharacterEncoding("utf-8");
			OutputStream os = response.getOutputStream();
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(os, obj);
			os.flush();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/{id}/move")
	public ModelAndView move(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
		
		//int id = RequestParameters.getInt(request, "id", 0);
		int parent = RequestParameters.getInt(request, "parent", 0);
		
	
		BacklogDAO dao = BeanLocator.getBean("backlogDAO");
		dao.move(id,parent);
		
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().append("{\"status\":\"200\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}	

	@RequestMapping(value="/{id}/rename")
	public ModelAndView rename(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
		
		//int id = RequestParameters.getInt(request, "id", 0);
		String rename = RequestParameters.getString(request, "rename", "");

		if(rename.length()>0){
			BacklogDAO dao = BeanLocator.getBean("backlogDAO");
			dao.rename(id,rename);
		}
		
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().append("{\"status\":\"200\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}	

	@RequestMapping(value="/{id}/delete")
	public ModelAndView delete(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
		
		//int id = RequestParameters.getInt(request, "id", 0);
		if(id!=0){
			BacklogDAO dao = BeanLocator.getBean("backlogDAO");
			dao.delete(id);
		}
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().append("{\"status\":\"200\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/treechildren")
	public ModelAndView json(HttpServletRequest request,HttpServletResponse response){
		
		int parentId = RequestParameters.getInt(request, "id", -1);
		BacklogDAO dao = BeanLocator.getBean("backlogDAO");
		
		List<TreeNode> treelist = dao.queryForTreeChildren(parentId);
		//List<BacklogTreeNode> treelist = dao.queryByParentId(parentId);
		if(treelist==null) treelist = new ArrayList<TreeNode>();
		try {
			response.setCharacterEncoding("utf-8");
			OutputStream os = response.getOutputStream();
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(os, treelist);
			os.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
		
	@RequestMapping(value="/{id}/bindSprint")
	public ModelAndView bindSprint(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
		
		int sprint_id = RequestParameters.getInt(request, "sprint_id", -1);

		BacklogDAO backlogDAO = BeanLocator.getBean("backlogDAO");
		backlogDAO.setSprint(id, sprint_id);
		
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().append("{\"status\":\"200\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping(value="/{id}/unbindSprint")
	public ModelAndView unbindSprint(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
		
		int sprint_id = RequestParameters.getInt(request, "sprint_id", -1);
		if(sprint_id!=-1){
			BacklogDAO backlogDAO = BeanLocator.getBean("backlogDAO");
			backlogDAO.setSprint(id, 0);
		}
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().append("{\"status\":\"200\"}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	@RequestMapping(value="/{id}/tasks")
	public ModelAndView tasks(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
		
		Map<String,String> filters = new HashMap<String,String>();
		filters.put(TaskQueryBuilder.FILTERNAME_backlog_id, ""+id);
		TaskDAO taskDAO = BeanLocator.getBean("taskDAO");
		PageResult<TaskObject> pr = taskDAO.query(0, 50, filters);
		
		
		ModelAndView mav = new ModelAndView("backlogtasks");
		mav.addObject("id", id);
		mav.addObject("tasklist_pr",pr);
		return mav;
	}
	
	@RequestMapping(value="/sprintdiv")
	public ModelAndView sprintdiv(HttpServletRequest request,HttpServletResponse response){
		
			
		ModelAndView mav = new ModelAndView("backlogsprint_div");
		return mav;
	}
	
	private static String NullStringConvert(String input){
		return (input==null)?"":input;
	}
}
