package com.snda.youni.taskweb.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
import com.snda.youni.taskweb.beans.TreeNode;
import com.snda.youni.taskweb.daos.BacklogDAO;
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
		
		ModelAndView mav = new ModelAndView("backloglist");
		mav.addObject("id", id);
		return mav;
		
	}
	
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response){
		
		String name = request.getParameter("name");
		int parent = RequestParameters.getInt(request, "parent", 0);
		
		BacklogObject obj = new BacklogObject();
		obj.setName(name);
		obj.setParent(parent);
		
		BacklogDAO dao = BeanLocator.getBean("backlogDAO");
		int id = dao.save(obj);
		obj.setId(id);
		
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
		
		int parentId = RequestParameters.getInt(request, "id", 0);
		BacklogDAO dao = BeanLocator.getBean("backlogDAO");
		
		List<TreeNode> treelist = dao.queryForTreeChildren(parentId);
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
	
	@RequestMapping(value="/{id}/tasks")
	public ModelAndView tasks(@PathVariable int id,HttpServletRequest request,HttpServletResponse response){
		
		ModelAndView mav = new ModelAndView("backlogtasks");
		mav.addObject("id", id);
		return mav;
	}	
}
