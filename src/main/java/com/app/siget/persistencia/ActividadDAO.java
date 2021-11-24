package com.app.siget.persistencia;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import org.springframework.stereotype.Repository;

import com.app.siget.dominio.Actividad;

import com.app.siget.dominio.DiaSemana;
import com.app.siget.dominio.User;
import com.app.siget.excepciones.FranjaHorariaOcupadaException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

@Repository
public final class ActividadDAO {
	public static final String ACTS = "actividades";
	public static final String USUARIO = "users";
	public static final String REUNION = "reunion";
	public static final String HORAI = "horaI";
	public static final String HORAF = "horaF";
	public static final String MINUTOSI = "minutosI";
	public static final String MINUTOSF = "minutosf";
	public static final String SEMANA = "semana";

	private ActividadDAO() {
		super();
	}

	public static List<Actividad> leerActividades(boolean onlyReuniones) {
		ArrayList<Actividad> actividades = new ArrayList<>();
		Document document;
		Actividad act;
		MongoCollection<Document> coleccion = AgenteDB.get().getBd(ACTS);
		MongoCursor<Document> iter = coleccion.find().iterator();

		while ((iter.hasNext())) {
			document = iter.next();
			if (Boolean.TRUE.equals(document.getBoolean(REUNION))) {
				LocalTime horaI = LocalTime.of(document.getInteger(HORAI, 0), document.getInteger(MINUTOSI, 0));
				LocalTime horaF = LocalTime.of(document.getInteger(HORAF, 0), document.getInteger(MINUTOSF, 0));
				act = new Actividad(document.getInteger("id", -1), document.getString("name"),
						DiaSemana.valueOf(document.getString("dia")), horaI, horaF, true, document.getString(SEMANA));
				actividades.add(act);
			} else {
				if (!onlyReuniones) {
					LocalTime horaI = LocalTime.of(document.getInteger(HORAI, 0), document.getInteger(MINUTOSI, 0));
					LocalTime horaF = LocalTime.of(document.getInteger(HORAF, 0), document.getInteger(MINUTOSF, 0));
					act = new Actividad(document.getInteger("id", -1), document.getString("name"),
							DiaSemana.valueOf(document.getString("dia")), horaI, horaF, false, document.getString(SEMANA));
					actividades.add(act);
				}
			}
		}

		return actividades;
	}

	public static Actividad leerActividad(int id) {

		for (Actividad act : ActividadDAO.leerActividades(false)) {
			if (id == act.getId()) {
				return act;
			}
		}
		return null;

	}

	public static ArrayList<Actividad> leerActividades(String nombre) {

		for (User u : UserDAO.leerUsers()) {
			if (u.getName().equals(nombre)) {
				//int[][] horario = ((User) u).getHorario().getMatrizHorario();
				//return buscarActividades(horario);
			}
		}
		return new ArrayList<>();
	}

	// Este metodo encuentra las actividades que estan en el horario del usuario
	private static ArrayList<Actividad> buscarActividades(int[][] horario) {
		Actividad a;
		ArrayList<Actividad> actividades = new ArrayList<>();
		for (int i = 0; i < horario.length; i++) {
			for (int j = 0; j < horario[0].length; j++) {
				if (horario[i][j] != 0) {
					a = ActividadDAO.leerActividad(horario[i][j]);
					if (a != null && !contiene(actividades, a)) {
						actividades.add(a);
					}
				}
			}
		}
		return actividades;
	}

	private static boolean contiene(List<Actividad> actividades, Actividad a) {
		for (Actividad b : actividades) {
			if (b.getId() == a.getId()) {
				return true;
			}
		}

		return false;
	}

	public static void insertarActividad(Actividad actividad) {

		MongoCollection<Document> coleccion = AgenteDB.get().getBd(ACTS);
		Document document = new Document("name", actividad.getName());
		document.append("id", actividad.getId());
		document.append("dia", actividad.getDia().toString());
		document.append(HORAI, actividad.getHoraI().getHour());
		document.append(MINUTOSI, actividad.getHoraI().getMinute());
		document.append(HORAF, actividad.getHoraF().getHour());
		document.append(MINUTOSF, actividad.getHoraF().getMinute());
		document.append(REUNION, actividad.isReunion());
		document.append(SEMANA, actividad.getSemana());
		coleccion.insertOne(document);

	}

	public static Document generarDocument(User user) {

		Document document = new Document("name", user.getName());
		document.append("email", user.getEmail());
		document.append("password", user.getPassword());
		
		return document;

	}

	public static void insertarActividad(User user, Actividad actividad) throws FranjaHorariaOcupadaException {
		if (user != null) {
			//user.insertarActividad(actividad);
			insertarActividad(actividad);
			Document document = generarDocument(user);
			MongoCollection<Document> coleccion = AgenteDB.get().getBd(USUARIO);
			//document.append("horario", user.getHorario().toString());
			
			UserDAO.eliminar(user, false);
//			coleccion.insertOne(document);
		}

	}

	public static void insertarReunionPend(User user, Actividad actividad) {

		if (user != null) {
			// SI LA REUNION YA EXISTE NO SE METE
			if (leerActividad(actividad.getId()) == null) {
				insertarActividad(actividad);
			}
			MongoCollection<Document> coleccion = AgenteDB.get().getBd(USUARIO);
			Document document = generarDocument(user);
			
			//document.append("horario", user.getHorario().toString());
			
			UserDAO.eliminar(user, false);
			coleccion.insertOne(document);
		}

	}

	public static void eliminar(Actividad a) {
		Document document;
		MongoCollection<Document> coleccion;

		if (a != null) {
			coleccion = AgenteDB.get().getBd(ACTS);
			document = new Document("name", a.getName());
			coleccion.findOneAndDelete(document);
		}

	}

}
