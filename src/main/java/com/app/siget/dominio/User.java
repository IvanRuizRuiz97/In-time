package com.app.siget.dominio;

import java.util.Collection;

import org.json.JSONObject;

public  class User {
	protected String name;
	protected String email;
	protected String password;
	protected Boolean isAdmin;
	

	public User(String name, String email, String password,boolean isAdmin) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.isAdmin = isAdmin;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}
	
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public Collection<?> toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
	
	
	
	

}
