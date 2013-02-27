package com.snda.youni.taskweb.beans;

import java.util.List;

public class PageResult<T> implements java.io.Serializable {

	private static final long serialVersionUID = 3003651629555090178L;
	
	public int count;
	public int page_count;
	public int page_rows = 50;
	public int page = 1;
	
	public List<T> data;
	
	public void build(){
		if(count%page_rows==0){
			page_count = count/page_rows;
		}else{
			page_count = count/page_rows+1;
		}
	}
	
}
