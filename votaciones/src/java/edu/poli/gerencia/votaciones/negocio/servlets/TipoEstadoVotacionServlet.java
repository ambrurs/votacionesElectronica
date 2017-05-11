package edu.poli.gerencia.votaciones.negocio.servlets;

import edu.poli.gerencia.votaciones.negocio.constantes.EAcciones;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.delegados.TipoEstadoVotacionDelegado;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.negocio.utiles.GsonUtil;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.modelo.vo.TipoEstadoVotacion;
import java.sql.Connection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */

@WebServlet(name = "TipoEstadoVotacionServlet", urlPatterns = {
    "/tipoestadovotacion/insertar",
    "/tipoestadovotacion/actualizar",
    "/tipoestadovotacion/listartodos",
    "/tipoestadovotacion/buscarporid",    
    "/tipoestadovotacion/eliminar"
})
public class TipoEstadoVotacionServlet extends GenericoServlet implements IServlet {

    private TipoEstadoVotacionDelegado tipoEstadoVotacionDelegado;

    @Override
    public Respuesta procesar(EAcciones eAcciones, HttpServletRequest request, Connection cnn) throws VotacionesException {
        Respuesta respuesta = null;
        tipoEstadoVotacionDelegado = new TipoEstadoVotacionDelegado(cnn);
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
        String json = request.getParameter("tipoEstadoVotacion");
        TipoEstadoVotacion tipoEstadoVotacion = GsonUtil.getInstanceShort().fromJson(json, TipoEstadoVotacion.class);
        tipoEstadoVotacionDelegado.insertar(tipoEstadoVotacion);
        Respuesta respuesta = new Respuesta(EMensajes.INSERTAR);
        return respuesta;
    }

    private Respuesta actualizar(HttpServletRequest request) throws VotacionesException{
        String json = request.getParameter("tipoEstadoVotacion");
        TipoEstadoVotacion tipoEstadoVotacion = GsonUtil.getInstanceShort().fromJson(json, TipoEstadoVotacion.class);
        tipoEstadoVotacionDelegado.actualizar(tipoEstadoVotacion);
        Respuesta respuesta = new Respuesta(EMensajes.ACTUALIZAR);
        //response.setData(usuario.getConsUsuario());
        return respuesta;
    }

    private Respuesta listarTodos() throws VotacionesException{
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        respuesta.setDatos(tipoEstadoVotacionDelegado.listarTodos());
        return respuesta;
    }

    private Respuesta consultar(HttpServletRequest request) {
        String query = request.getParameter("query");
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        //response.setDatos(tipoEstadoVotacionDelegado.query());
        return respuesta;
    }

    private Respuesta eliminar(HttpServletRequest request) throws VotacionesException{
        Respuesta respuesta = null;
        String json = request.getParameter("tipoEstadoVotacion");
        String id = request.getParameter("id");
        if (json != null) {
            TipoEstadoVotacion tipoEstadoVotacion = GsonUtil.getInstanceShort().fromJson(json, TipoEstadoVotacion.class);
            tipoEstadoVotacionDelegado.eliminar(tipoEstadoVotacion);
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        if (id != null) {
            tipoEstadoVotacionDelegado.eliminar(Integer.parseInt(id));
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        return respuesta;
    }

    private Respuesta buscarPorId(HttpServletRequest request) throws VotacionesException{
        String id = request.getParameter("id");
        Respuesta respuesta = new Respuesta(EMensajes.CONSULTAR);
        respuesta.setDatos(tipoEstadoVotacionDelegado.buscarPorId(Integer.parseInt(id)));
        return respuesta;
    }

}
