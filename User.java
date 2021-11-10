# In-time
Este es el repositorio en el que se llevara a cabo una actividad escolar
[intime.zip](https://github.com/IvanRuizRuiz97/In-time/files/7514567/intime.zip)
package com.app.siget.dominio;

import java.util.Collection;

import org.json.JSONObject;

public  class User {
	protected String name;
	protected String email;
	protected String password;	
	

	public User(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
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

	public Collection<?> toJSON() {
		// TODO Auto-generated method stub
		return null;
	}
	
