package edu.poli.gerencia.votaciones.negocio.servlets;

import edu.poli.gerencia.votaciones.negocio.constantes.EAcciones;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.delegados.VotacionDelegado;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.negocio.utiles.GsonUtil;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;
import edu.poli.gerencia.votaciones.negocio.utiles.SesionUtil;
import java.sql.Connection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
@WebServlet(name = "VotacionServlet", urlPatterns = {
    "/votacion/insertar",
    "/votacion/actualizar",
    "/votacion/listartodos",
    "/listarvotaciones",
    "/votacion/buscarporid",
    "/detallesvotacion",
    "/votacion/eliminar",
    "/lanzarjobvotaciones"
})
public class VotacionServlet extends GenericoServlet implements IServlet {

    private VotacionDelegado votacionDelegado;

    @Override
    public Respuesta procesar(EAcciones eAcciones, HttpServletRequest request, Connection cnn) throws VotacionesException {
        Respuesta respuesta = null;
        votacionDelegado = new VotacionDelegado(cnn);
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
            case LISTAR_VOTACIONES:
                respuesta = listarVotaciones(request);
                break;
            case BUSCAR_POR_ID:
                respuesta = buscarPorId(request);
                break;
            case ELIMINAR:
                respuesta = eliminar(request);
                break;
            case DETALLES_VOTACION:
                respuesta = consultarDetallesVotacion(request, cnn);
                break;
            case LANZAR_JOB_VOTACIONES:
                respuesta = lanzarJobVotaciones(request);
                break;
        }
        return respuesta;
    }

    private void comprobarPermisos(HttpServletRequest request) throws VotacionesException {
        if (!SesionUtil.esEmpresa(request) && !SesionUtil.esAdministrador(request)) {
            throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
        }
    }

    private Respuesta insertar(HttpServletRequest request) throws VotacionesException {
        comprobarPermisos(request);

        String json = request.getParameter("votacion");
        Votacion votacion = GsonUtil.getInstanceShort().fromJson(json, Votacion.class);
        Usuario usuario = SesionUtil.obtenerSesion(request);
        if (usuario == null) {
            throw new VotacionesException(EMensajes.SESION_EXPIRADA);
        }
        votacion.setConsUsuarioCreacion(usuario);
        System.out.println(votacion);
        votacionDelegado.insertar(votacion);
        Respuesta respuesta = new Respuesta(EMensajes.INSERTAR);
        return respuesta;
    }

    private Respuesta actualizar(HttpServletRequest request) throws VotacionesException {
        String json = request.getParameter("votacion");
        Votacion votacion = GsonUtil.getInstanceShort().fromJson(json, Votacion.class);
        votacionDelegado.actualizar(votacion);
        Respuesta respuesta = new Respuesta(EMensajes.ACTUALIZAR);
        return respuesta;
    }

    private Respuesta listarTodos(HttpServletRequest request) throws VotacionesException {
        if (!SesionUtil.esEmpresa(request)) {
            throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
        }
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        Usuario usuario = SesionUtil.obtenerSesion(request);
        respuesta.setDatos(votacionDelegado.listarVotacionesTabla(usuario));
        return respuesta;
    }

    private Respuesta eliminar(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = null;
        String json = request.getParameter("votacion");
        String id = request.getParameter("id");
        if (json != null) {
            Votacion votacion = GsonUtil.getInstanceShort().fromJson(json, Votacion.class);
            votacionDelegado.eliminar(votacion);
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        if (id != null) {
            votacionDelegado.eliminar(Integer.parseInt(id));
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        return respuesta;
    }

    private Respuesta buscarPorId(HttpServletRequest request) throws VotacionesException {
        String id = request.getParameter("id");
        Respuesta respuesta = new Respuesta(EMensajes.CONSULTAR);
        respuesta.setDatos(votacionDelegado.buscarPorId(Integer.parseInt(id)));
        return respuesta;
    }

    private Respuesta listarVotaciones(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        Usuario usuario = SesionUtil.obtenerSesion(request);
        respuesta.setDatos(votacionDelegado.listarVotaciones(usuario));
        return respuesta;
    }

    private Respuesta consultarDetallesVotacion(HttpServletRequest request, Connection cnn) throws VotacionesException {
        String id = request.getParameter("id");
        Respuesta respuesta = new Respuesta(EMensajes.CONSULTAR);
        Usuario usuario = SesionUtil.obtenerSesion(request);
        respuesta.setDatos(votacionDelegado.consultarDetallesVotacion(Integer.parseInt(id), usuario, cnn));
        return respuesta;
    }

    private Respuesta lanzarJobVotaciones(HttpServletRequest request) {
        Respuesta respuesta = new Respuesta(EMensajes.ACTUALIZAR);
        if (SesionUtil.esEmpresa(request)) {

        }
        return respuesta;
    }

}
