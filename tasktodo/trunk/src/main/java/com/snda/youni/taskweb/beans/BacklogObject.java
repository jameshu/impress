package com.snda.youni.taskweb.beans;

public class BacklogObject extends BaseObject {

	private static final long serialVersionUID = -4812762920790580243L;

	private String link;
	private int parent;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	
}
