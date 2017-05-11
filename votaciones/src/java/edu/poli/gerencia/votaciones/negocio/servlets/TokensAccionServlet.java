package edu.poli.gerencia.votaciones.negocio.servlets;

import edu.poli.gerencia.votaciones.negocio.constantes.EAcciones;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.delegados.TokensAccionDelegado;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.negocio.utiles.GsonUtil;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.modelo.vo.TokensAccion;
import edu.poli.gerencia.votaciones.negocio.utiles.SesionUtil;
import java.sql.Connection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
@WebServlet(name = "TokensAccionServlet", urlPatterns = {
    "/tokensaccion/insertar",
//    "/tokensaccion/actualizar",
//    "/tokensaccion/listartodos",
//    "/tokensaccion/buscarporid",
//    "/tokensaccion/eliminar"
})
public class TokensAccionServlet extends GenericoServlet implements IServlet {
    
    private TokensAccionDelegado tokensAccionDelegado;
    
    @Override
    public Respuesta procesar(EAcciones eAcciones, HttpServletRequest request, Connection cnn) throws VotacionesException {
        comprobarPermisos(request);
        Respuesta respuesta = null;
        tokensAccionDelegado = new TokensAccionDelegado(cnn);
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
    
    private void comprobarPermisos(HttpServletRequest request) throws VotacionesException {
        if (!SesionUtil.esAdministrador(request)) {
            throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
        }
    }
    
    private Respuesta insertar(HttpServletRequest request) throws VotacionesException {
        String json = request.getParameter("tokensAccion");
        TokensAccion tokensAccion = GsonUtil.getInstanceShort().fromJson(json, TokensAccion.class);
        tokensAccionDelegado.insertar(tokensAccion);
        Respuesta respuesta = new Respuesta(EMensajes.INSERTAR);
        return respuesta;
    }
    
    private Respuesta actualizar(HttpServletRequest request) throws VotacionesException {
        String json = request.getParameter("tokensAccion");
        TokensAccion tokensAccion = GsonUtil.getInstanceShort().fromJson(json, TokensAccion.class);
        tokensAccionDelegado.actualizar(tokensAccion);
        Respuesta respuesta = new Respuesta(EMensajes.ACTUALIZAR);
        //response.setData(usuario.getConsUsuario());
        return respuesta;
    }
    
    private Respuesta listarTodos() throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        respuesta.setDatos(tokensAccionDelegado.listarTodos());
        return respuesta;
    }   
    
    private Respuesta eliminar(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = null;
        String json = request.getParameter("tokensAccion");
        String id = request.getParameter("id");
        if (json != null) {
            TokensAccion tokensAccion = GsonUtil.getInstanceShort().fromJson(json, TokensAccion.class);
            tokensAccionDelegado.eliminar(tokensAccion);
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        if (id != null) {
            tokensAccionDelegado.eliminar(Integer.parseInt(id));
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        return respuesta;
    }
    
    private Respuesta buscarPorId(HttpServletRequest request) throws VotacionesException {
        String id = request.getParameter("id");
        Respuesta respuesta = new Respuesta(EMensajes.CONSULTAR);
        respuesta.setDatos(tokensAccionDelegado.buscarPorId(Integer.parseInt(id)));
        return respuesta;
    }
    
}
