package edu.poli.gerencia.votaciones.negocio.servlets;

import edu.poli.gerencia.votaciones.modelo.dao.PersonaDAO;
import edu.poli.gerencia.votaciones.modelo.vo.Persona;
import edu.poli.gerencia.votaciones.negocio.constantes.EAcciones;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.delegados.TipoVotacionDelegado;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.negocio.utiles.GsonUtil;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.modelo.vo.TipoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.negocio.utiles.SesionUtil;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
@WebServlet(name = "TipoVotacionServlet", urlPatterns = {
    "/tipovotacion/insertar",
    "/tipovotacion/actualizar",
    "/tipovotacion/listartodos",
    "/tipovotacion/buscarporid",
    "/tipovotacion/eliminar"
})
public class TipoVotacionServlet extends GenericoServlet implements IServlet {

    private TipoVotacionDelegado tipoVotacionDelegado;

    @Override
    public Respuesta procesar(EAcciones eAcciones, HttpServletRequest request, Connection cnn) throws VotacionesException {
        Respuesta respuesta = null;
        if (!SesionUtil.esEmpresa(request) && !SesionUtil.esAdministrador(request)) {
            throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
        }
        tipoVotacionDelegado = new TipoVotacionDelegado(cnn);
        respuesta = new Respuesta(EMensajes.RECURSO_NO_ENCONTRADO);
        switch (eAcciones) {
            case INSERTAR:
                respuesta = insertar(request, cnn);
                break;
            case ACTUALIZAR:
                respuesta = actualizar(request);
                break;
            case LISTAR_TODOS:
                respuesta = listarTodos();
                break;
            case BUSCAR_POR_ID:
                respuesta = buscarPorId(request);
                break;
            case ELIMINAR:
                respuesta = eliminar(request);
                break;
        }
        return respuesta;
    }

    private Respuesta insertar(HttpServletRequest request, Connection cnn) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.ERROR_INSERTAR);
        String json = request.getParameter("tipoVotacion");
        TipoVotacion tipoVotacion = GsonUtil.getInstanceShort().fromJson(json, TipoVotacion.class);
        Usuario usuario = SesionUtil.obtenerSesion(request);
        PersonaDAO personaDAO = new PersonaDAO(cnn);
        try {
            Persona persona = personaDAO.consultar("SELECT p.* FROM persona p INNER JOIN usuario u ON p.CONS_USUARIO = u.CONS_USUARIO WHERE u.CONS_USUARIO = " + usuario.getConsUsuario());
            tipoVotacion.setIdPersona(persona.getConsPersona());
            tipoVotacionDelegado.insertar(tipoVotacion);
            respuesta = new Respuesta(EMensajes.INSERTAR);
        } catch (SQLException e) {
            throw new VotacionesException(EMensajes.ERROR_INSERTAR);
        }
        return respuesta;
    }

    private Respuesta actualizar(HttpServletRequest request) throws VotacionesException {
        String json = request.getParameter("tipoVotacion");
        TipoVotacion tipoVotacion = GsonUtil.getInstanceShort().fromJson(json, TipoVotacion.class);
        tipoVotacionDelegado.actualizar(tipoVotacion);
        Respuesta respuesta = new Respuesta(EMensajes.ACTUALIZAR);
        //response.setData(usuario.getConsUsuario());
        return respuesta;
    }

    private Respuesta listarTodos() throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        respuesta.setDatos(tipoVotacionDelegado.listarTodos());
        return respuesta;
    }

    private Respuesta consultar(HttpServletRequest request) {
        String query = request.getParameter("query");
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        //response.setDatos(tipoVotacionDelegado.query());
        return respuesta;
    }

    private Respuesta eliminar(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = null;
        String json = request.getParameter("tipoVotacion");
        String id = request.getParameter("id");
        if (json != null) {
            TipoVotacion tipoVotacion = GsonUtil.getInstanceShort().fromJson(json, TipoVotacion.class);
            tipoVotacionDelegado.eliminar(tipoVotacion);
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        if (id != null) {
            tipoVotacionDelegado.eliminar(Integer.parseInt(id));
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        return respuesta;
    }

    private Respuesta buscarPorId(HttpServletRequest request) throws VotacionesException {
        String id = request.getParameter("id");
        Respuesta respuesta = new Respuesta(EMensajes.CONSULTAR);
        respuesta.setDatos(tipoVotacionDelegado.buscarPorId(Integer.parseInt(id)));
        return respuesta;
    }

}
