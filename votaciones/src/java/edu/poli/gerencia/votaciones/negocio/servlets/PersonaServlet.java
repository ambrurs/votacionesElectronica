package edu.poli.gerencia.votaciones.negocio.servlets;

import edu.poli.gerencia.votaciones.negocio.constantes.EAcciones;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.delegados.PersonaDelegado;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.negocio.utiles.GsonUtil;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.modelo.vo.Persona;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.negocio.utiles.SesionUtil;
import java.sql.Connection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
@WebServlet(name = "PersonaServlet", urlPatterns = {
//    "/persona/insertar",
//    "/persona/actualizar",
//    "/persona/listartodos",
    "/persona/buscarporid",
    //    "/persona/eliminar", --> Para desactivar el uso de los métodos, solo basta con comentar o eliminar la url del servlet.
    "/persona/filtrar"
})
public class PersonaServlet extends GenericoServlet implements IServlet {

    private PersonaDelegado personaDelegado;

    @Override
    public Respuesta procesar(EAcciones eAcciones, HttpServletRequest request, Connection cnn) throws VotacionesException {
        Respuesta respuesta = null;
        personaDelegado = new PersonaDelegado(cnn);
        respuesta = new Respuesta(EMensajes.RECURSO_NO_ENCONTRADO);
        switch (eAcciones) {
            case INSERTAR:
                respuesta = insertar(request);
                break;
            case ACTUALIZAR:
                respuesta = actualizar(request);
                break;
            case LISTAR_TODOS:
                respuesta = listarTodos(request);
                break;
            case BUSCAR_POR_ID:
                respuesta = buscarPorId(request);
                break;
            case ELIMINAR:
                respuesta = eliminar(request);
                break;
            case FILTRAR:
                respuesta = filtrar(request);
                break;
        }
        return respuesta;
    }

    private Respuesta insertar(HttpServletRequest request) throws VotacionesException {
        String json = request.getParameter("persona");
        Persona persona = GsonUtil.getInstanceShort().fromJson(json, Persona.class);
        personaDelegado.insertar(persona);
        Respuesta respuesta = new Respuesta(EMensajes.INSERTAR);
        return respuesta;
    }

    private Respuesta actualizar(HttpServletRequest request) throws VotacionesException {
        String json = request.getParameter("persona");
        Persona persona = GsonUtil.getInstanceShort().fromJson(json, Persona.class);
        Usuario usuario = SesionUtil.obtenerSesion(request);
        if ((usuario.getConsUsuario() != persona.getConsUsuario().getConsUsuario()) && !SesionUtil.esAdministrador(request)) {
            throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
        }
        personaDelegado.actualizar(persona);
        Respuesta respuesta = new Respuesta(EMensajes.ACTUALIZAR);
        return respuesta;
    }

    private Respuesta listarTodos(HttpServletRequest request) throws VotacionesException {
        if (!SesionUtil.esAdministrador(request)) {
            throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
        }
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        respuesta.setDatos(personaDelegado.listarTodos());
        return respuesta;
    }

    private Respuesta eliminar(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = null;
        String json = request.getParameter("persona");
        String id = request.getParameter("id");
        if (json != null) {
            Persona persona = GsonUtil.getInstanceShort().fromJson(json, Persona.class);
            personaDelegado.eliminar(persona);
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        if (id != null) {
            personaDelegado.eliminar(Integer.parseInt(id));
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        return respuesta;
    }

    private Respuesta buscarPorId(HttpServletRequest request) throws VotacionesException {
        if (!SesionUtil.logeado(request)) {
            throw new VotacionesException(EMensajes.SESION_EXPIRADA);
        }
        String id = request.getParameter("id");
        Respuesta respuesta = new Respuesta(EMensajes.CONSULTAR);
        respuesta.setDatos(personaDelegado.buscarPorId(Integer.parseInt(id)));
        return respuesta;
    }

    private Respuesta filtrar(HttpServletRequest request) throws VotacionesException {        
        String filtro = request.getParameter("q");
        System.out.println(filtro);
        Respuesta respuesta = new Respuesta(EMensajes.CONSULTAR);
        respuesta.setDatos(personaDelegado.filtrar(filtro));
        return respuesta;
    }

}
