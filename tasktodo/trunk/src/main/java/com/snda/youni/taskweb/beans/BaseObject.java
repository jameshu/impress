package com.snda.youni.taskweb.beans;

import java.io.Serializable;

public class BaseObject implements Serializable {

	private static final long serialVersionUID = -6570434494494099095L;
	
	private int id;
	private int delete;
	private long createddate;
	private long updateddate;
	
	private String name;
	private String description;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDelete() {
		return delete;
	}
	public void setDelete(int delete) {
		this.delete = delete;
	}
	public long getCreateddate() {
		return createddate;
	}
	public void setCreateddate(long createddate) {
		this.createddate = createddate;
	}
	public long getUpdateddate() {
		return updateddate;
	}
	public void setUpdateddate(long updateddate) {
		this.updateddate = updateddate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
