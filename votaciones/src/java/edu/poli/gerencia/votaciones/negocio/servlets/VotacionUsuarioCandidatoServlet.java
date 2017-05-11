package edu.poli.gerencia.votaciones.negocio.servlets;

import edu.poli.gerencia.votaciones.negocio.constantes.EAcciones;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.delegados.VotacionUsuarioCandidatoDelegado;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.negocio.utiles.GsonUtil;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.modelo.vo.VotacionUsuarioCandidato;
import edu.poli.gerencia.votaciones.negocio.utiles.SesionUtil;
import java.sql.Connection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
@WebServlet(name = "VotacionUsuarioCandidatoServlet", urlPatterns = {
    "/votacionusuariocandidato/insertar",
    "/votacionusuariocandidato/actualizar",
    "/votacionusuariocandidato/listartodos",
    "/votacionusuariocandidato/buscarporid",
    "/votacionusuariocandidato/eliminar"
})
public class VotacionUsuarioCandidatoServlet extends GenericoServlet implements IServlet {

    private VotacionUsuarioCandidatoDelegado votacionUsuarioCandidatoDelegado;

    @Override
    public Respuesta procesar(EAcciones eAcciones, HttpServletRequest request, Connection cnn) throws VotacionesException {
        Respuesta respuesta = null;
        votacionUsuarioCandidatoDelegado = new VotacionUsuarioCandidatoDelegado(cnn);
        respuesta = new Respuesta(EMensajes.RECURSO_NO_ENCONTRADO);
        switch (eAcciones) {
            case INSERTAR:
                respuesta = insertar(request);
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

    private Respuesta insertar(HttpServletRequest request) throws VotacionesException {
        if (!SesionUtil.logeado(request)) {
            throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
        }
        String json = request.getParameter("votacionUsuarioCandidato");
        VotacionUsuarioCandidato votacionUsuarioCandidato = GsonUtil.getInstanceShort().fromJson(json, VotacionUsuarioCandidato.class);
        Usuario usuario = SesionUtil.obtenerSesion(request);
        votacionUsuarioCandidato.setUsuarioConsUsuario(usuario);
        Integer idVotacion = Integer.parseInt(request.getParameter("idVotacion"));
        votacionUsuarioCandidatoDelegado.insertar(votacionUsuarioCandidato, idVotacion);
        Respuesta respuesta = new Respuesta(EMensajes.INSERTAR);
        return respuesta;
    }

    private Respuesta actualizar(HttpServletRequest request) throws VotacionesException {
        String json = request.getParameter("votacionUsuarioCandidato");
        VotacionUsuarioCandidato votacionUsuarioCandidato = GsonUtil.getInstanceShort().fromJson(json, VotacionUsuarioCandidato.class);
        votacionUsuarioCandidatoDelegado.actualizar(votacionUsuarioCandidato);
        Respuesta respuesta = new Respuesta(EMensajes.ACTUALIZAR);
        //response.setData(usuario.getConsUsuario());
        return respuesta;
    }

    private Respuesta listarTodos() throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        respuesta.setDatos(votacionUsuarioCandidatoDelegado.listarTodos());
        return respuesta;
    }

    private Respuesta consultar(HttpServletRequest request) {
        String query = request.getParameter("query");
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        //response.setDatos(votacionUsuarioCandidatoDelegado.query());
        return respuesta;
    }

    private Respuesta eliminar(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = null;
        String json = request.getParameter("votacionUsuarioCandidato");
        String id = request.getParameter("id");
        if (json != null) {
            VotacionUsuarioCandidato votacionUsuarioCandidato = GsonUtil.getInstanceShort().fromJson(json, VotacionUsuarioCandidato.class);
            votacionUsuarioCandidatoDelegado.eliminar(votacionUsuarioCandidato);
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        if (id != null) {
            votacionUsuarioCandidatoDelegado.eliminar(Integer.parseInt(id));
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        return respuesta;
    }

    private Respuesta buscarPorId(HttpServletRequest request) throws VotacionesException {
        String id = request.getParameter("id");
        Respuesta respuesta = new Respuesta(EMensajes.CONSULTAR);
        respuesta.setDatos(votacionUsuarioCandidatoDelegado.buscarPorId(Integer.parseInt(id)));
        return respuesta;
    }

}
