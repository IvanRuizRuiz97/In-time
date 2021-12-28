package com.app.siget.excepciones;

/**
 * @author Equipo1
 *
 */
public class FranjaHorariaOcupadaException extends Exception {

	public FranjaHorariaOcupadaException() {
		// No se necesita hacer nada en el contructor
	}
	/**
	 * @return "Rol no permitido"
	 */
	@Override
	public String getMessage() {
		return "Franja horaria ocupada";
	}

}
