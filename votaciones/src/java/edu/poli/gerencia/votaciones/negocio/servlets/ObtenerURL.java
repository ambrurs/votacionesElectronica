/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votaciones.negocio.servlets;

import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.negocio.constantes.EAcciones;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.constantes.EmailTemplates;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import java.sql.Connection;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jhon1
 */
@WebServlet(name = "ObtenerURL", urlPatterns = {
    "/obtenerurl"
})

public class ObtenerURL extends GenericoServlet implements IServlet {

    @Override
    public Respuesta procesar(EAcciones eAcciones, HttpServletRequest request, Connection cnn) throws VotacionesException {
        Respuesta respuesta = null;
        respuesta = consultarPath(request);
        return respuesta;
    }

    private Respuesta consultarPath(HttpServletRequest request) {
        Respuesta respuesta = new Respuesta(EMensajes.CONSULTAR);
        ServletContext context = getServletContext();
        String path = context.getRealPath("/");
        path = path + EmailTemplates.RECUPERAR_CUENTA.getPath();
        System.out.println("PATH: " + path);
        respuesta.setDatos(path);
        return respuesta;
    }

}
