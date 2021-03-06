package com.app.siget.dominio;

import java.time.LocalTime;

import org.json.JSONObject;

public class  Fichaje implements Comparable<Fichaje>{
	private int id;
	private String name;
	private DiaSemana dia;
	private LocalTime horaI;
	private LocalTime horaF;
	private String semana;

	// a
	public Fichaje(String name, DiaSemana dia, LocalTime horaI, String semana) {
		this.id = Math.abs((int) System.currentTimeMillis());
		this.name = name;
		this.dia = dia;
		this.horaI = horaI;
		this.horaF = null;
		this.semana=semana;
	}

	public Fichaje(int id, String name, DiaSemana dia, LocalTime horaI,String semana) {
		this.id = id;
		this.name = name;
		this.dia = dia;
		this.horaI = horaI;
		//this.horaF = horaF;
		this.semana=semana;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DiaSemana getDia() {
		return this.dia;
	}

	public void setFecha(DiaSemana dia) {
		this.dia = dia;
	}

	public LocalTime getHoraI() {
		return this.horaI;
	}

	public void setHoraI(LocalTime horaI) {
		this.horaI = horaI;
	}

	public LocalTime getHoraF() {
		return this.horaF;
	}

	public void setHoraF(LocalTime horaF) {
		this.horaF = horaF;
	}

	public int getId() {
		return this.id;
	}
	
	public String getSemana() {
		return semana;
	}

	public void setSemana(String semana) {
		this.semana = semana;
	}

	public JSONObject toJSON() {
		JSONObject jso = new JSONObject();
		jso.put("id", this.getId());
		jso.put("name", this.getName());
		jso.put("dia", this.getDia());
		jso.put("HoraI", this.getHoraI());
		jso.put("HoraF", this.getHoraF());
		return jso;
	}

	@Override
	public int compareTo(Fichaje f) {
	if(id< f.getId())
	return -1;
	if(id > f.getId())
	return 1;
	return 0;
	}

}
