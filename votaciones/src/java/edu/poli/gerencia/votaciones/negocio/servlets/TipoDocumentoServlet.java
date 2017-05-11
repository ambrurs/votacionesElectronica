package edu.poli.gerencia.votaciones.negocio.servlets;

import edu.poli.gerencia.votaciones.negocio.constantes.EAcciones;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.delegados.TipoDocumentoDelegado;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.negocio.utiles.GsonUtil;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.modelo.vo.TipoDocumento;
import edu.poli.gerencia.votaciones.negocio.utiles.SesionUtil;
import java.sql.Connection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
@WebServlet(name = "TipoDocumentoServlet", urlPatterns = {
    "/tipodocumento/insertar",
    "/tipodocumento/actualizar",
    "/tipodocumento/listartodos",
//    "/tipodocumento/buscarporid",
    "/tipodocumento/eliminar"
})
public class TipoDocumentoServlet extends GenericoServlet implements IServlet {

    private TipoDocumentoDelegado tipoDocumentoDelegado;

    @Override
    public Respuesta procesar(EAcciones eAcciones, HttpServletRequest request, Connection cnn) throws VotacionesException {
        Respuesta respuesta = null;
        tipoDocumentoDelegado = new TipoDocumentoDelegado(cnn);
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
        if (!SesionUtil.esAdministrador(request)) {
            throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
        }
        String json = request.getParameter("tipoDocumento");
        TipoDocumento tipoDocumento = GsonUtil.getInstanceShort().fromJson(json, TipoDocumento.class);
        tipoDocumentoDelegado.insertar(tipoDocumento);
        Respuesta respuesta = new Respuesta(EMensajes.INSERTAR);
        return respuesta;
    }

    private Respuesta actualizar(HttpServletRequest request) throws VotacionesException {
        if (!SesionUtil.esAdministrador(request)) {
            throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
        }
        String json = request.getParameter("tipoDocumento");
        TipoDocumento tipoDocumento = GsonUtil.getInstanceShort().fromJson(json, TipoDocumento.class);
        tipoDocumentoDelegado.actualizar(tipoDocumento);
        Respuesta respuesta = new Respuesta(EMensajes.ACTUALIZAR);
        return respuesta;
    }

    private Respuesta listarTodos() throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        respuesta.setDatos(tipoDocumentoDelegado.listarTodos());
        return respuesta;
    }

    private Respuesta consultar(HttpServletRequest request) {
        String query = request.getParameter("query");
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        //response.setDatos(tipoDocumentoDelegado.query());
        return respuesta;
    }

    private Respuesta eliminar(HttpServletRequest request) throws VotacionesException {
        if (!SesionUtil.esAdministrador(request)) {
            throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
        }
        Respuesta respuesta = null;
        String json = request.getParameter("tipoDocumento");
        String id = request.getParameter("id");
        if (json != null) {
            TipoDocumento tipoDocumento = GsonUtil.getInstanceShort().fromJson(json, TipoDocumento.class);
            tipoDocumentoDelegado.eliminar(tipoDocumento);
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        if (id != null) {
            tipoDocumentoDelegado.eliminar(Integer.parseInt(id));
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        return respuesta;
    }

    private Respuesta buscarPorId(HttpServletRequest request) throws VotacionesException {
        if (!SesionUtil.esAdministrador(request)) {
            throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
        }
        String id = request.getParameter("id");
        Respuesta respuesta = new Respuesta(EMensajes.CONSULTAR);
        respuesta.setDatos(tipoDocumentoDelegado.buscarPorId(Integer.parseInt(id)));
        return respuesta;
    }

}
