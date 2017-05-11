package edu.poli.gerencia.votaciones.negocio.servlets;

import edu.poli.gerencia.votaciones.negocio.constantes.EAcciones;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.delegados.TipoUsuarioDelegado;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.negocio.utiles.GsonUtil;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.modelo.vo.TipoUsuario;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.negocio.utiles.SesionUtil;
import java.sql.Connection;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
@WebServlet(name = "TipoUsuarioServlet", urlPatterns = {
    "/tipousuario/insertar",
    "/tipousuario/actualizar",
    "/tipousuario/listartodos",
    "/listatipousuarios",
    //    "/tipousuario/buscarporid",
    "/tipousuario/eliminar"
})
public class TipoUsuarioServlet extends GenericoServlet implements IServlet {

    private TipoUsuarioDelegado tipoUsuarioDelegado;

    @Override
    public Respuesta procesar(EAcciones eAcciones, HttpServletRequest request, Connection cnn) throws VotacionesException {
        Respuesta respuesta = null;
        tipoUsuarioDelegado = new TipoUsuarioDelegado(cnn);
        respuesta = new Respuesta(EMensajes.RECURSO_NO_ENCONTRADO);
        switch (eAcciones) {
            case INSERTAR:
                respuesta = insertar(request);
                break;
            case ACTUALIZAR:
                respuesta = actualizar(request);
                break;
            case LISTAR_TIPOS_USUARIOS:
                respuesta = listarTodos(request);
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
        if (!SesionUtil.esAdministrador(request)) {
            throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
        }
        String json = request.getParameter("tipoUsuario");
        TipoUsuario tipoUsuario = GsonUtil.getInstanceShort().fromJson(json, TipoUsuario.class);
        tipoUsuarioDelegado.insertar(tipoUsuario);
        Respuesta respuesta = new Respuesta(EMensajes.INSERTAR);
        return respuesta;
    }

    private Respuesta actualizar(HttpServletRequest request) throws VotacionesException {
        if (!SesionUtil.esAdministrador(request)) {
            throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
        }
        String json = request.getParameter("tipoUsuario");
        System.out.println(json);
        Respuesta respuesta = new Respuesta(EMensajes.ACTUALIZAR);
        TipoUsuario tipoUsuario = GsonUtil.getInstanceShort().fromJson(json, TipoUsuario.class);
        tipoUsuarioDelegado.actualizar(tipoUsuario);
        //response.setData(usuario.getConsUsuario());
        return respuesta;
    }

    private Respuesta listarTodos(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        //Se comrpueba la sesión actual, para detectar si el usuario puede ver todos los tipos de usuario.        
        List<TipoUsuario> list = tipoUsuarioDelegado.listarTodos();
        System.out.println("PATH SERVLET: " + request.getServletPath());
        if (request.getServletPath().equals("/listatipousuarios")) {
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getPublico()) {
                    list.remove(i);
                }
            }
        }
        respuesta.setDatos(list);
        return respuesta;
    }

    private Respuesta consultar(HttpServletRequest request) {
        String query = request.getParameter("query");
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        //response.setDatos(tipoUsuarioDelegado.query());
        return respuesta;
    }

    private Respuesta eliminar(HttpServletRequest request) throws VotacionesException {
        if (!SesionUtil.esAdministrador(request)) {
            throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
        }
        Respuesta respuesta = null;
        String json = request.getParameter("tipoUsuario");
        String id = request.getParameter("id");
        if (json != null) {
            TipoUsuario tipoUsuario = GsonUtil.getInstanceShort().fromJson(json, TipoUsuario.class);
            tipoUsuarioDelegado.eliminar(tipoUsuario);
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        if (id != null) {
            tipoUsuarioDelegado.eliminar(Integer.parseInt(id));
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
        respuesta.setDatos(tipoUsuarioDelegado.buscarPorId(Integer.parseInt(id)));
        return respuesta;
    }

}
