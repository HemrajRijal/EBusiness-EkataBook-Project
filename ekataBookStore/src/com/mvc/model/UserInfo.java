package com.mvc.model;

public class UserInfo {
 int userid;
 String username;
 String userpwd;
 
 public UserInfo()
 {
	 
 }

public int getUserid() {
	return userid;
}

public void setUserid(int userid) {
	this.userid = userid;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getUserpwd() {
	return userpwd;
}

public void setUserpwd(String userpwd) {
	this.userpwd = userpwd;
}

public UserInfo(int userid, String username, String userpwd) {
	super();
	this.userid = userid;
	this.username = username;
	this.userpwd = userpwd;
}
}
