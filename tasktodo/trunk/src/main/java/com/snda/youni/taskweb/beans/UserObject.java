package com.snda.youni.taskweb.beans;


public class UserObject extends BaseObject {

	private static final long serialVersionUID = -6191974308049827147L;

	private String login;
	private String password;
	//private String name;
	private String email;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
