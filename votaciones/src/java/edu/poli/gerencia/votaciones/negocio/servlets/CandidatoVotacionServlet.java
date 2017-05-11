package edu.poli.gerencia.votaciones.negocio.servlets;

import edu.poli.gerencia.votaciones.negocio.constantes.EAcciones;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.delegados.CandidatoVotacionDelegado;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.negocio.utiles.GsonUtil;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.modelo.vo.CandidatoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.negocio.utiles.SesionUtil;
import java.sql.Connection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
@WebServlet(name = "CandidatoVotacionServlet", urlPatterns = {
    "/candidatovotacion/insertar",
    "/candidatovotacion/actualizar",
    "/candidatovotacion/listartodos",
    "/candidatovotacion/buscarporid",
    "/candidatovotacion/eliminar"
})
public class CandidatoVotacionServlet extends GenericoServlet implements IServlet {
    
    private CandidatoVotacionDelegado candidatoVotacionDelegado;
    
    @Override
    public Respuesta procesar(EAcciones eAcciones, HttpServletRequest request, Connection cnn) throws VotacionesException {
        Respuesta respuesta = null;
        candidatoVotacionDelegado = new CandidatoVotacionDelegado(cnn);
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
        }
        return respuesta;
    }
    
    private Respuesta insertar(HttpServletRequest request) throws VotacionesException {
        String json = request.getParameter("candidatoVotacion");
        Usuario usuario = SesionUtil.obtenerSesion(request);        
        CandidatoVotacion candidatoVotacion = GsonUtil.getInstanceShort().fromJson(json, CandidatoVotacion.class);
        candidatoVotacion.setConsUsuarioVotacion(usuario);
        candidatoVotacion.setCantidadVotos(0);
        candidatoVotacionDelegado.insertar(candidatoVotacion);
        Respuesta respuesta = new Respuesta(EMensajes.INSERTAR);
        return respuesta;
    }
    
    private Respuesta actualizar(HttpServletRequest request) throws VotacionesException {
        String json = request.getParameter("candidatoVotacion");
        CandidatoVotacion candidatoVotacion = GsonUtil.getInstanceShort().fromJson(json, CandidatoVotacion.class);
        candidatoVotacionDelegado.actualizar(candidatoVotacion);
        Respuesta respuesta = new Respuesta(EMensajes.ACTUALIZAR);
        return respuesta;
    }
    
    private Respuesta listarTodos(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        String idString = request.getParameter("idVotacion");
        Integer id = Integer.parseInt(idString);
        respuesta.setDatos(candidatoVotacionDelegado.listarTodos(id));
        return respuesta;
    }
    
    private Respuesta consultar(HttpServletRequest request) {
        String query = request.getParameter("query");
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        //response.setDatos(candidatoVotacionDelegado.query());
        return respuesta;
    }
    
    private Respuesta eliminar(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.ERROR_ELIMINAR);
        String id = request.getParameter("idVotacion");
        if (id != null) {
            Usuario usuario = SesionUtil.obtenerSesion(request);
            candidatoVotacionDelegado.eliminar(Integer.parseInt(id), usuario);
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        return respuesta;
    }
    
    private Respuesta buscarPorId(HttpServletRequest request) throws VotacionesException {
        String id = request.getParameter("id");
        Respuesta respuesta = new Respuesta(EMensajes.CONSULTAR);
        respuesta.setDatos(candidatoVotacionDelegado.buscarPorId(Integer.parseInt(id)));
        return respuesta;
    }
    
}
