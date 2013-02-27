package com.snda.youni.taskweb.beans;

public class CategoryObject extends BaseObject {

	private static final long serialVersionUID = -906214504522955311L;
	
	public boolean isGroup = false;
	
	private int groupId = 0;
	private String groupName;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
