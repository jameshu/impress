package com.snda.youni.taskweb.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskObject extends BaseObject {

	private static final long serialVersionUID = 8814853055078849473L;

	private int featureId;
	private int trackerId;
	private int statusId;
	private String subject;
	private int assignee;
	private long startDate;
	private long dueDate;
	private int categorygroup;
	private int category;
	
	private String featureName;
	private String trackerName;
	private int statusState;
	private String statusName;
	private String assigeeName;
	private String assigeeLogin;
	private String categorygroupName;
	private String categoryName;
	
	public int getFeatureId() {
		return featureId;
	}
	public void setFeatureId(int featureId) {
		this.featureId = featureId;
	}
	public int getTrackerId() {
		return trackerId;
	}
	public void setTrackerId(int trackerId) {
		this.trackerId = trackerId;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getAssignee() {
		return assignee;
	}
	public void setAssignee(int assignee) {
		this.assignee = assignee;
	}
	public long getStartDate() {
		return startDate;
	}	
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	public long getDueDate() {
		return dueDate;
	}
	public String getDueDateString(){
		return getDateString(getDueDate());
	}
	public void setDueDate(long dueDate) {
		this.dueDate = dueDate;
	}
	public int getCategorygroup() {
		return categorygroup;
	}
	public void setCategorygroup(int categorygroup) {
		this.categorygroup = categorygroup;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	public String getTrackerName() {
		return trackerName;
	}
	public void setTrackerName(String trackerName) {
		this.trackerName = trackerName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getAssigeeName() {
		return assigeeName;
	}
	public void setAssigeeName(String assigeeName) {
		this.assigeeName = assigeeName;
	}
	public String getAssigeeLogin() {
		return assigeeLogin;
	}
	public void setAssigeeLogin(String assigeeLogin) {
		this.assigeeLogin = assigeeLogin;
	}
	public String getCategorygroupName() {
		return categorygroupName;
	}
	public void setCategorygroupName(String categorygroupName) {
		this.categorygroupName = categorygroupName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	//formmat date time
	public static String getDateString(long time){
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		d.setTime(time);
		return sdf.format( d );
	} 
	
	public static String getDatetimeString(long time){
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d = new Date();
		d.setTime(time);
		return sdf.format( d );
	}
	
	public static String getTimeString(long time){
		SimpleDateFormat sdf =  new SimpleDateFormat("HH:mm");
		Date d = new Date();
		d.setTime(time);
		return sdf.format( d );
	}
	public int getStatusState() {
		return statusState;
	}
	public void setStatusState(int statusState) {
		this.statusState = statusState;
	} 
	

	
}
