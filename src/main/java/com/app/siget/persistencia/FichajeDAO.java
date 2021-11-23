package com.app.siget.persistencia;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import com.app.siget.dominio.Actividad;
import com.app.siget.dominio.DiaSemana;
import com.app.siget.dominio.Fichaje;
import com.app.siget.dominio.User;
import com.app.siget.excepciones.FranjaHorariaOcupadaException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

@Repository
public class FichajeDAO {
	public static final String FJS = "fichajes";
	public static final String USUARIO = "users";
	public static final String REUNION = "reunion";
	public static final String HORAI = "horaI";
	public static final String HORAF = "horaF";
	public static final String MINUTOSI = "minutosI";
	public static final String MINUTOSF = "minutosf";
	public static final String SEMANA = "semana";

	private FichajeDAO() {
		super();
	}

public static List<Fichaje> leerFichajes() {
		ArrayList<Fichaje> fichajes = new ArrayList<>();
		Document document;
		Fichaje fj;
		MongoCollection<Document> coleccion = AgenteDB.get().getBd(FJS);
		MongoCursor<Document> iter = coleccion.find().iterator();

		while ((iter.hasNext())) {
			document = iter.next();
			
					LocalTime horaI = LocalTime.of(document.getInteger(HORAI, 0), document.getInteger(MINUTOSI, 0));
					LocalTime horaF = LocalTime.of(document.getInteger(HORAF, 0), document.getInteger(MINUTOSF, 0));
					fj = new Fichaje(document.getInteger("id", -1), document.getString("name"),
							DiaSemana.valueOf(document.getString("dia")), horaI, document.getString(SEMANA));
					fj.setHoraF(horaF);
					fichajes.add(fj);
				
			
		}

		return fichajes;
	}

	public static Fichaje leerFichaje (int id) {

		for (Fichaje fj : FichajeDAO.leerFichajes()) {
			if (id == fj.getId()) {
				return fj;
			}
		}
		return null;

	}

	public static ArrayList<Fichaje> leerFichajesUsuario(String nombre) {
		User u = UserDAO.findUser(nombre);
		
		List <Fichaje> fichajes = FichajeDAO.leerFichajes() ;
		ArrayList <Fichaje> aux = new ArrayList<Fichaje>();
		for (Fichaje f : fichajes) {
 			if (f.getName().equals(u.getName())) {
				aux.add(f);
			}
		}
		return aux;
	}

	// Este metodo encuentra las actividades que estan en el horario del usuario
//	private static ArrayList<Actividad> buscarActividades(int[][] horario) {
//		Actividad a;
//		ArrayList<Actividad> actividades = new ArrayList<>();
//		for (int i = 0; i < horario.length; i++) {
//			for (int j = 0; j < horario[0].length; j++) {
//				if (horario[i][j] != 0) {
//					a = ActividadDAO.leerActividad(horario[i][j]);
//					if (a != null && !contiene(actividades, a)) {
//						actividades.add(a);
//					}
//				}
//			}
//		}
//		return actividades;
//	}

	

	public static void insertarFichaje(Fichaje fichaje) {

		MongoCollection<Document> coleccion = AgenteDB.get().getBd(FJS);
		Document document = new Document("name", fichaje.getName());
		document.append("id", fichaje.getId());
		document.append("dia", fichaje.getDia().toString());
		document.append(HORAI, fichaje.getHoraI().getHour());
		document.append(MINUTOSI, fichaje.getHoraI().getMinute());
		document.append(HORAF, 0);
		document.append(MINUTOSF, 0);
		document.append(SEMANA, fichaje.getSemana());
		coleccion.insertOne(document);

	}
	public static void modificarFichaje(Fichaje fichaje) {
		BSON b= new BSON();

		MongoCollection<Document> coleccion = AgenteDB.get().getBd(FJS);
		Document document = new Document("name", fichaje.getName());
		document.append("id", fichaje.getId());
		document.append("dia", fichaje.getDia().toString());
		document.append(HORAI, fichaje.getHoraI().getHour());
		document.append(MINUTOSI, fichaje.getHoraI().getMinute());
		document.append(HORAF, fichaje.getHoraF().getHour());
		document.append(MINUTOSF, fichaje.getHoraF().getMinute());
		document.append(SEMANA, fichaje.getSemana());
		
		coleccion.findOneAndReplace(Filters.eq("id", fichaje.getId()), document);

	}

	

	

	

	private static Bson eq(String string, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Document generarDocument(User user) {

		Document document = new Document("name", user.getName());
		document.append("email", user.getEmail());
		document.append("password", user.getPassword());
		
		return document;

	}

	

	
	

	public static void eliminar(Fichaje f) {
		Document document;
		MongoCollection<Document> coleccion;

		if (f != null) {
			coleccion = AgenteDB.get().getBd(FJS);
			document = new Document("name", f.getName());
			coleccion.findOneAndDelete(document);
		}

	}




}
