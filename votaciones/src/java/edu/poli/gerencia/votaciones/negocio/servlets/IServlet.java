package edu.poli.gerencia.votaciones.negocio.servlets;

import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.negocio.constantes.EAcciones;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */

public interface IServlet {
    public Respuesta procesar(EAcciones eAcciones, HttpServletRequest request, Connection cnn) throws VotacionesException;
}
