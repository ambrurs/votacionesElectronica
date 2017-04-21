/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.servlets;

import com.google.gson.Gson;
import edu.poli.gerencia.votacion.negocio.constantes.EDireccion;
import edu.poli.gerencia.votacion.negocio.constantes.EMensajes;
import edu.poli.gerencia.votacion.negocio.recursos.Respuesta;
import edu.poli.gerencia.votacion.negocio.util.Log;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jhon1
 */
public abstract class GenericoServlet extends HttpServlet implements IServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
//        Log.info("inicio petición " + request.getRemoteAddr() + " " + request.getRemoteAddr() + " " + request.getRequestURI().toString());
        PrintWriter out = response.getWriter();
        Respuesta respuesta = new Respuesta(EMensajes.RECURSO_NO_ENCONTRADO);
        try {
            String url = request.getServletPath();
            EDireccion direccion = EDireccion.getDireccion(url);
            respuesta = procesar(direccion, request);
        } catch (Throwable e) {
            Logger.getLogger(GenericoServlet.class.getName()).log(Level.SEVERE, null, e);
            System.err.println(e.getMessage());
        } finally {
            String json = new Gson().toJson(respuesta);
            out.print(json);
            out.close();
        };
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

}
