/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.servlets;

import com.google.gson.Gson;
import edu.poli.gerencia.votacion.modelo.vo.Persona;
import edu.poli.gerencia.votacion.negocio.constantes.EDireccion;
import edu.poli.gerencia.votacion.negocio.constantes.EMensajes;
import edu.poli.gerencia.votacion.negocio.delegado.PersonaDelegado;
import edu.poli.gerencia.votacion.negocio.recursos.Respuesta;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jhon1
 */
@WebServlet(
        name = "PersonaServlet",
        urlPatterns = {
            "/registrarpersona",
            "/actualizarpersona",
            "/eliminarpersona"
        }
)
public class PersonaServlet extends GenericoServlet implements IServlet {

    private PersonaDelegado delegado;

    @Override
    public void init() throws ServletException {
        super.init();
        delegado = PersonaDelegado.getInstancia();
    }

    @Override
    public Respuesta procesar(EDireccion direccion, HttpServletRequest request) throws Exception {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_PUDO_PROCESAR);
        switch (direccion) {
            case REGISTRAR_PERSONA:
                respuesta = registrarPersona(request);
                break;
            case ACTUALIZAR_PERSONA:
                respuesta = actualizarPersona(request);
                break;
            case ELIMINAR_PERSONA:
                respuesta = eliminarPersona(request);
                break;
        }
        return respuesta;
    }

    private Respuesta registrarPersona(HttpServletRequest request) {
        Gson gson = new Gson();
        String json = request.getParameter("persona");
        Persona persona = gson.fromJson(json, Persona.class);
        return delegado.registrarPersona(persona);
    }

    private Respuesta actualizarPersona(HttpServletRequest request) {
        Gson gson = new Gson();
        String json = request.getParameter("persona");
        Persona persona = gson.fromJson(json, Persona.class);
        return delegado.actualizarPersona(persona);
    }

    private Respuesta eliminarPersona(HttpServletRequest request) {
        Gson gson = new Gson();
        String json = request.getParameter("persona");
        Persona persona = gson.fromJson(json, Persona.class);
        return delegado.eliminarPersona(persona);
    }

}
