


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
	import com.app.siget.dominio.Incidencia;
	import com.app.siget.dominio.User;
	import com.app.siget.excepciones.FranjaHorariaOcupadaException;
	import com.mongodb.client.MongoCollection;
	import com.mongodb.client.MongoCursor;
	import com.mongodb.client.model.Filters;
	import com.mongodb.client.model.Updates;

	@Repository
	public class IncidenciaDao {
		
		public static final String USUARIO = "users";
		public static final String INC= "incidencia";
		public static final String SEMANA = "semana";

		private IncidenciaDao() {
			super();
		}

	public static ArrayList<Incidencia> leerIncidencia() {
			ArrayList<Incidencia> incidencias = new ArrayList<Incidencia>();
			Document document;
			Incidencia ic;
			MongoCollection<Document> coleccion = AgenteDB.get().getBd(INC);
			MongoCursor<Document> iter = coleccion.find().iterator();

			while ((iter.hasNext())) {
				document = iter.next();
				ic = new Incidencia();
				ic.setId(Integer.parseInt(document.getString("id")));
				ic.setFecha(Long.parseLong(document.getString("fecha")));
				ic.setBody((document.getString("body")));
				ic.setTipo((document.getString("tipo")));
				ic.setName((document.getString("name")));
				
				incidencias.add(ic);
					
				
			}

			return incidencias;
		}

		public static Incidencia leerIncidencia (int id) {

			for (Incidencia ic : IncidenciaDao.leerIncidencia()) {
				if (id == ic.getId()) {
					return ic;
				}
			}
			return null;

		}

//		public static ArrayList<Fichaje> leerFichajesUsuario(String nombre) {
//			User u = UserDAO.findUser(nombre);
//			
//			List <Fichaje> fichajes = FichajeDAO.leerFichajes() ;
//			ArrayList <Fichaje> aux = new ArrayList<Fichaje>();
//			for (Fichaje f : fichajes) {
//	 			if (f.getName().equals(u.getName())) {
//					aux.add(f);
//				}
//			}
//			return aux;
//		}

		// Este metodo encuentra las actividades que estan en el horario del usuario
//		private static ArrayList<Actividad> buscarActividades(int[][] horario) {
//			Actividad a;
//			ArrayList<Actividad> actividades = new ArrayList<>();
//			for (int i = 0; i < horario.length; i++) {
//				for (int j = 0; j < horario[0].length; j++) {
//					if (horario[i][j] != 0) {
//						a = ActividadDAO.leerActividad(horario[i][j]);
//						if (a != null && !contiene(actividades, a)) {
//							actividades.add(a);
//						}
//					}
//				}
//			}
//			return actividades;
//		}

		

		public static void insertarIncidencia(Incidencia incidencia) {

			MongoCollection<Document> coleccion = AgenteDB.get().getBd(INC);
			Document document = new Document("name", incidencia.getName());
			document.append("tipo",incidencia.getTipo());
			document.append("id",String.valueOf(incidencia.getId()));
			document.append("body", incidencia.getBody());
			document.append("fecha", String.valueOf(incidencia.getFecha()));
			coleccion.insertOne(document);
			

		}
		public static void modificarIncidencia(Incidencia incidencia) {
			BSON b= new BSON();

			MongoCollection<Document> coleccion = AgenteDB.get().getBd(INC);
			Document document = new Document();
			
			
			
			coleccion.findOneAndReplace(Filters.eq("id", incidencia.getId()), document);

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
				coleccion = AgenteDB.get().getBd(INC);
				document = new Document("name", f.getName());
				coleccion.findOneAndDelete(document);
			}

		}




	}

