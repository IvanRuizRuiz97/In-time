package com.app.siget.dominio;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import com.app.siget.excepciones.AccessNotGrantedException;
import com.app.siget.excepciones.CredencialesInvalidasException;
import com.app.siget.excepciones.FranjaHorariaOcupadaException;
import com.app.siget.persistencia.ActividadDAO;
import com.app.siget.persistencia.FichajeDAO;
import com.app.siget.persistencia.IncidenciaDao;
import com.app.siget.persistencia.TokenDAO;
import com.app.siget.persistencia.UserDAO;
import java.util.Objects;

public class Manager {

	private WebSocketSession session;
	public static final String USUARIOS = "usuarios";
	public static final String ASISTENTE = "ASISTENTE";

	public Manager() {
		// Metodo constructor vacio (no hay atributos)
	}

	private static class ManagerHolder {
		private static Manager singleton = new Manager();
	}

	public static Manager get() {
		return ManagerHolder.singleton;
	}

	public void login(String name, String password) throws CredencialesInvalidasException, IOException {
		boolean login = false;

		ArrayList<User> usuarios = (ArrayList<User>) UserDAO.leerUsers();
		for (User u : usuarios) {
			login = checkCredenciales(u, name, password);
			if (login) {

				Token t = new Token(name);
				TokenDAO.insert(t);

				JSONObject jso = new JSONObject();
				
				jso.put("token", t.getToken());
				jso.put("isAdmin",u.getIsAdmin());
				if (this.session != null) {
					this.session.sendMessage(new TextMessage(jso.toString()));
				}
				break;
			}
		}
		if (!login) {
			throw new CredencialesInvalidasException();
		}

	}
	public boolean RegistrarEntrada(String usuario) {
		ArrayList<Fichaje> fichajes = FichajeDAO.leerFichajesUsuario(usuario);
		for(Fichaje f: fichajes) {
			if(f.getHoraF()==null) {
				return false;
			}
			
		}
		Date now = new Date ();
		LocalTime horaIni = LocalTime.now();
		horaIni.getHour();
		horaIni.getMinute();
		
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
		
		String cadena = Normalizer.normalize(simpleDateformat.format(now).toString(), Normalizer.Form.NFD);
		String dia = cadena.replaceAll("[^\\p{ASCII}]", "");
		Calendar calendar = Calendar.getInstance();
		calendar.get(Calendar.WEEK_OF_YEAR);
		
	    DiaSemana diaSemana = DiaSemana.valueOf(dia.toUpperCase());
	    SimpleDateFormat simpleDateformatyear = new SimpleDateFormat("YYYY");
		String semana = simpleDateformatyear.format(now);
		semana += "-W";
		semana += String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
		
		Fichaje f= new Fichaje(usuario,diaSemana, horaIni,semana);
		FichajeDAO.insertarFichaje(f);
		

		return true;
		
		
		
	}

	public boolean RegistrarSalida(String usuario) {
		ArrayList<Fichaje> fichajes = FichajeDAO.leerFichajesUsuario(usuario);
		for(Fichaje f: fichajes) {
			if(f.getHoraF().toString().equals("00:00") ) {
				LocalTime horaF = LocalTime.now();
				f.setHoraF(horaF);
				FichajeDAO.modificarFichaje(f);
				return true;
			}
			
		}
		return false;
		
		
	}
	public boolean checkCredenciales(User u, String name, String password) throws CredencialesInvalidasException {
	
	if(u.getName().equals(name)&& u.getPassword().equals(encriptarMD5(password))) {
		return true;
	}
	return false;
	}

	public void register(String name, String email, String password) {
		User usuario = UserDAO.findUser(name);
		if(usuario==null) {
			
		
		UserDAO.insertar(new User(name, email, encriptarMD5(password),false));
		}

	}
	
	

	

	

	public JSONArray leerReuniones() {
		JSONArray jsa = new JSONArray();
		List<Actividad> actividades = ActividadDAO.leerActividades(true);
		if (!actividades.isEmpty()) {
			for (Actividad act : actividades) {
				jsa.put(act.toJSON());
			}
		}

		return jsa;

	}
	
	public JSONObject filtrarPorSemana(String semana) {
		JSONObject jso = new JSONObject();
		jso.put("type","buscarPorSemana");
		JSONArray jsa = new JSONArray();
		List<Actividad> actividades = ActividadDAO.leerActividades(true);
		if (!actividades.isEmpty()) {
			for (Actividad act : actividades) {
				if (Objects.nonNull(act.getSemana()) && act.getSemana().equals(semana)) {
					jsa.put(act.toJSON());
				}
			}
		}
		jso.put("actividades", jsa);
		return jso;
	}


	/*public void insertarActividad(String nombre, String dia, String horaI, String minutosI, String horaF,
			String minutosF, String usuario, String reunion, String semana) throws FranjaHorariaOcupadaException {

		List<User> users = UserDAO.leerUsers();

		LocalTime horaIni = LocalTime.of(Integer.parseInt(horaI), Integer.parseInt(minutosI));
		LocalTime horaFin = LocalTime.of(Integer.parseInt(horaF), Integer.parseInt(minutosF));
		boolean reunionB = Boolean.parseBoolean(reunion);

		for (User user : users) {
		
				ActividadDAO.insertarActividad((Asistente) user,
						new Actividad(nombre, DiaSemana.valueOf(dia), horaIni, horaFin, reunionB, semana));
			}
		}
	*/

	public void actualizar(String string, boolean boolean1) {
		// sustituir este metodo por su equivalente de los de arriba
	}

	public void eliminarUsuario(String usuario) {
		for (User u : UserDAO.leerUsers()) {
			
				UserDAO.eliminar(u, true);
			}
		}
	

	public void error() {
		// sustituir este metodo por su equivalente de los de arriba
	}

//	public JSONObject leer() {
//		JSONObject jso = new JSONObject();
//
//		jso.put("fichajes", Manager.get().leerAc());
//		jso.put("type", "leer");
//		return jso;
//	}
	
	public JSONObject leerFichajes(String nombre) {
		JSONObject jso = new JSONObject();
		JSONArray jsa = new JSONArray();

		for (Fichaje f : FichajeDAO.leerFichajesUsuario(nombre)) {
			jsa.put(f.toJSON());
		}
		jso.put("fichajes", jsa);
		return jso;
	}

	
	public void setSession(WebSocketSession session) {
		this.session = session;
	}

	

	/*public void convocarReunion(String nombre, String dia, String horaI, String minutosI, String horaF, String minutosF,
			String usuarios, String reunion, String semana) {

		JSONArray jsa = new JSONArray(usuarios);
		LocalTime horaIni = LocalTime.of(Integer.parseInt(horaI), Integer.parseInt(minutosI));
		LocalTime horaFin = LocalTime.of(Integer.parseInt(horaF), Integer.parseInt(minutosF));
		Actividad reunionPendiente = new Actividad(nombre, DiaSemana.valueOf(dia), horaIni, horaFin,
				Boolean.parseBoolean(reunion), semana);

		for (int i = 0; i < jsa.length(); i++) {
			for (User u : UserDAO.leerUsers()) {
				if (u.getName().equals(jsa.get(i))) {
					ActividadDAO.insertarReunionPend((Asistente) u, reunionPendiente);

				}
			}

		}

	}
*/
	// Este metodo comprueba si la reunion que se quiere convocar se solapa con
	// otras actividades de usuarios. Devuelve el listado de usuarios disponibles
	public JSONArray usuariosDisponibles(String nombre, String dia, String horaI, String minutosI, String horaF,
			String minutosF, String semana) {
		JSONArray jsa = new JSONArray();
		LocalTime horaIni = LocalTime.of(Integer.parseInt(horaI), Integer.parseInt(minutosI));
		LocalTime horaFin = LocalTime.of(Integer.parseInt(horaF), Integer.parseInt(minutosF));

		for (User u : UserDAO.leerUsers()) {
			
				
			}

		
		return jsa;
	}
	public JSONObject leerUsuarios() {
		JSONArray jsa = new JSONArray();
		JSONObject jso = new JSONObject();
		List<User> usuarios = UserDAO.leerUsers();

		for (User user : usuarios) {

			jsa.put(user.toJSON());
		}
		jso.put(USUARIOS, jsa);

		return jso;

	}

	public void modificarUsuario(String nombre, String emailNuevo, String passwordNueva) {
		// Mismo metodo para modificar usuario tanto para Asistente como para Admin

		for (User u : UserDAO.leerUsers()) {
			if (u.getName().equals(nombre)) {
				u.setEmail(emailNuevo);
				u.setPassword(encriptarMD5(passwordNueva));
				UserDAO.modificar(u);
			}
		}
	}


	public JSONArray leerInfoUsuario(String nombre) {
		JSONArray jsa = new JSONArray();
		JSONObject jso = new JSONObject();
		for (User u : UserDAO.leerUsers()) {
			if (u.getName().equals(nombre)) {
				
			}
		}
		jso.put(USUARIOS, jsa);

		return jsa;
	}


	
	

	public void cerrarSesion(String name) {
		TokenDAO.eliminar(new Token(name));
	}

	public void checkAccess(String name, String token, String page) throws AccessNotGrantedException {

		boolean adminPages = (page.contains("admin.html") || page.contains("gestion.html"));
		

		if (token.equals(TokenDAO.getToken(name).getToken())) {
			
				//cerrarSesion(name);
				//throw new AccessNotGrantedException();
			
		} else {
			cerrarSesion(name);
			throw new AccessNotGrantedException();
		}

	}

	public String encriptarMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			int diff = 32 - hashtext.length();
			StringBuilder bld = new StringBuilder();

			while (diff > 1) {
				bld.append("0");
				diff--;
			}

			return bld.toString() + hashtext;

		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}
	
	public Object filtrarPorSemanaUsuario(String semana, String usuario) {
		JSONObject jso = new JSONObject();
		JSONArray jsa = new JSONArray();
		
		for (Actividad a : ActividadDAO.leerActividades(usuario)) {
			if(a.getSemana().equals(semana)) {
			jsa.put(a.toJSON());
			}
		}

		jso.put("actividades", jsa);
		return jso;
	}
	public boolean crearIncidencia(Incidencia i) {
		//ArrayList<Incidencia> incidencias = IncidenciaDao.insertarIncidencia(i);
		IncidenciaDao.insertarIncidencia(i);
		

		return true;
		
		
		
	}
	public JSONObject leerIncidencia() {
		JSONObject jso = new JSONObject();
		JSONArray jsa = new JSONArray();
		
		ArrayList<Incidencia> inc = IncidenciaDao.leerIncidencia();

		for (int i = 0; i < inc.size(); i++) {
			Incidencia incidencia = inc.get(i);
			jsa.put(incidencia.toJSON());
			
		}
//			
		
		jso.put("incidencias", jsa);
		return jso;
		

		
		
		
		
	}

	


}
