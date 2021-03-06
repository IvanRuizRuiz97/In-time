package com.app.siget.ws;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.app.siget.dominio.Manager;

@Component
public class SpringWebSocket extends TextWebSocketHandler {

	private static final String NOMBRE = "nombre";
	private static final String TYPE = "type";
	private static final String VISTA = "vista";
	private static final String HF = "horaFinal";
	private static final String DIA = "dia";
	private static final String HI = "horaInicio";
	private static final String MF = "minutoFinal";
	private static final String MI = "minutoInicio";
	public static final String SEMANA = "semana";

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Manager.get().setSession(session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		JSONObject jso = new JSONObject(message.getPayload().toString());
		switch (jso.getString(TYPE)) {

		case "check":
			session.sendMessage(
					new TextMessage(Manager.get().usuariosDisponibles(jso.getString(NOMBRE), jso.getString(DIA),
							jso.getString(HI), jso.getString(MI), jso.getString(HF), jso.getString(MF), jso.getString("semana")).toString()));
			break;
		case "leer":
				session.sendMessage(new TextMessage(Manager.get().leerFichajes().toString()));
			
			break;
		case "leerFichajeUsuario":
			session.sendMessage(new TextMessage(Manager.get().leerFichajesUsuario(jso.getString("nombre")).toString()));
		
		break;
		case "infoUsuarios":
			session.sendMessage(new TextMessage(Manager.get().leerUsuarios().toString()));
			break;
		case "buscarPorSemana":
			System.out.println("Buscar por semana");

//			
			
			break;
		case "leerIncidencias":
			session.sendMessage(new TextMessage(Manager.get().leerIncidencia().toString()));
			break;
			
//		case "entrada":
//			Manager.get().entradaFichaje((String) jso.get(NOMBRE), jso.getString(DIA), jso.getString(HI), jso.getString(HF), "false", jso.getString("semana"));
//			break;
	
		case "register":
			Manager.get().register((String) jso.get(NOMBRE), jso.getString("email"), jso.getString("pwd1"));
			break;
		
		case "modificar":
			// Misma condicion para modificar usuario tanto para Asistente como para Admin
			Manager.get().modificarUsuario(jso.getString(NOMBRE), jso.getString("email"), jso.getString("pwd"));
			break;
		
		
		
		
		default:
			break;
		}
	}
}
