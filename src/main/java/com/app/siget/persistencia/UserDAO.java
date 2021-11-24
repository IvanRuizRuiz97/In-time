package com.app.siget.persistencia;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.app.siget.dominio.Actividad;

import com.app.siget.dominio.Horario;
import com.app.siget.dominio.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

@Repository
public final class UserDAO {
	public static final String USUARIO = "users";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String NAME = "name";
	public static final String ADMIN = "ADMIN";
	public static final String HORARIO = "horario";
	public static final String REUNIONESPENDIENTES = "reunionesPendientes";

	private UserDAO() {
		super();
	}

	public static User findUser(String name) {
		for (User u : UserDAO.leerUsers()) {
			if (name.equals(u.getName())) {
				return u;
			}
		}
		return null;
	}

	public static List<User> leerUsers() {
		ArrayList<User> usuarios = new ArrayList<>();
		Document document;
		User u = new User();
		MongoCollection<Document> coleccion = AgenteDB.get().getBd(USUARIO);
		MongoCursor<Document> iter = coleccion.find().iterator();

		while ((iter.hasNext())) {
			document = iter.next();
			
			u.setName(document.getString(NAME));
			u.setEmail (document.getString(EMAIL));
			u.setPassword(document.getString(PASSWORD));
			
			usuarios.add(u);
		
		}

		return usuarios;
	}

	public static List<User> leerUsers(String rol) {
		ArrayList<User> usuarios = new ArrayList<>();
		Document document;
		User u;
		MongoCollection<Document> coleccion = AgenteDB.get().getBd(USUARIO);
		MongoCursor<Document> iter = coleccion.find().iterator();

		while ((iter.hasNext())) {
			document = iter.next();
			
				
			
		}
		return usuarios;
	}

	public static void insertar(User user) {
		Document document;
		MongoCollection<Document> coleccion;
		if (user != null) {
			coleccion = AgenteDB.get().getBd(USUARIO);
			document = new Document(NAME, user.getName());
			document.append(EMAIL, user.getEmail());
			document.append(PASSWORD, user.getPassword());
			
			
			coleccion.insertOne(document);
		}
	}

	public static void eliminar(User user, boolean permanente) {

		Document document;
		MongoCollection<Document> coleccion;

		if (user != null) {
			coleccion = AgenteDB.get().getBd(USUARIO);
			document = new Document("name", user.getName());
			
			
			coleccion.findOneAndDelete(document);
		}
	}

	public static void modificar(User u) {
		// Mismo metodo para modificar usuario tanto para Asistente como para Admin
		UserDAO.eliminar(u, false);
		UserDAO.insertar(u);
	}

}
