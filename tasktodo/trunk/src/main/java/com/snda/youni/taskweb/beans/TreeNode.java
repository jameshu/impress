package com.snda.youni.taskweb.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snda.youni.taskweb.util.JsonUtil;

public class TreeNode {

	private String data;
	private Map<String,String> attr = new HashMap<String,String>();
	//private List<TreeNode> children = new ArrayList<TreeNode>();
	
	private String state = "closed"; //open
	private String icon = "/";  //folder

	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public Map<String, String> getAttr() {
		return attr;
	}


	public void setAttr(Map<String, String> metadata) {
		this.attr = metadata;
	}
	
	public void putAttr(String key,String value){
		this.attr.put(key, value);
	}


//	public List<TreeNode> getChildren() {
//		return children;
//	}
//
//
//	public void setChildren(List<TreeNode> children) {
//		this.children = children;
//	}
	
	public static void main(String[] args){
		TreeNode node = new TreeNode();
		node.data = "A";
		node.attr.put("id", "001");
		node.attr.put("name", "n001");
		
		TreeNode node_b_1 = new TreeNode();
		node_b_1.data = "B";
		node_b_1.attr.put("id", "002");
		node_b_1.attr.put("name", "n002");
		
		//node.children.add(node_b_1);
		
		List<TreeNode> root = new ArrayList<TreeNode>();
		root.add(node);
		
		System.out.println( JsonUtil.toJsonString( root ));
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}

	
}
