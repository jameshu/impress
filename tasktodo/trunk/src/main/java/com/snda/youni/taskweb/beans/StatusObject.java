package com.snda.youni.taskweb.beans;

public class StatusObject extends BaseObject {

	private static final long serialVersionUID = 8865333147509934762L;

	private int state = 0;
	private int trackerId = 0;
	private String trackerName = "";
	
	public int getTrackerId() {
		return trackerId;
	}

	public void setTrackerId(int trackerId) {
		this.trackerId = trackerId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTrackerName() {
		return trackerName;
	}

	public void setTrackerName(String trackerName) {
		this.trackerName = trackerName;
	}
	
}
