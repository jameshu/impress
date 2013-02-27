package com.snda.youni.taskweb.beans;

public class FeatureSprintObject extends BaseObject {

	private static final long serialVersionUID = 6644506047139341082L;

	private int pm = 0;
	private String pmName;
	private int pd = 0;
	private String pdName;
	public int getPm() {
		return pm;
	}
	public void setPm(int pm) {
		this.pm = pm;
	}
	public String getPmName() {
		return pmName;
	}
	public void setPmName(String pmName) {
		this.pmName = pmName;
	}
	public int getPd() {
		return pd;
	}
	public void setPd(int pd) {
		this.pd = pd;
	}
	public String getPdName() {
		return pdName;
	}
	public void setPdName(String pdName) {
		this.pdName = pdName;
	}
	

	
}
