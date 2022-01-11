package com.app.siget.dominio;

import java.time.LocalTime;

import org.json.JSONObject;

public class Incidencia {
	private int id;
	private String tipo;
	private long fecha;
	private String name;
	private String body;

	
	public Incidencia( String tipo, String name, String body) {
		super();
		this.id = Math.abs((int) System.currentTimeMillis());
		this.tipo = tipo;
		this.fecha = System.currentTimeMillis();
		this.name = name;
		this.body = body;
	}
	public Incidencia() {
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public long getFecha() {
		return fecha;
	}


	public void setFecha(long fecha) {
		this.fecha = fecha;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}
	
	public JSONObject toJSON() {
		JSONObject jso = new JSONObject();
		jso.put("id", this.getId());
		jso.put("name", this.getName());
		jso.put("fecha", this.getFecha());
		jso.put("tipo", this.getTipo());
		jso.put("body", this.getBody());
		return jso;
	}
	public String toString() {
		return this.toJSON().toString();
	}


	
	
	

}
