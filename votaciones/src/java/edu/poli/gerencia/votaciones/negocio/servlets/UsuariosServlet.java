package edu.poli.gerencia.votaciones.negocio.servlets;

import edu.poli.gerencia.votacion.negocio.recursos.Email;
import edu.poli.gerencia.votaciones.modelo.vo.Persona;
import edu.poli.gerencia.votaciones.negocio.constantes.EAcciones;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.delegados.UsuarioDelegado;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.negocio.utiles.GsonUtil;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.negocio.utiles.SesionUtil;
import java.sql.Connection;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
@WebServlet(name = "UsuariosServlet", urlPatterns = {
    "/usuario/insertar",
    "/usuario/actualizar",
    "/usuario/listartodos",
    "/usuario/buscarporid",
    "/usuario/eliminar",
    "/recuperarcuenta",
    "/comprobartoken",
    "/iniciarsesion",
    "/comprobarsesion",
    "/cerrarsesion",
    "/actualizarclave",
    "/consultarnotificaciones",
    "/usuariosregistrados",
    "/cambiarestadousuario",
    "/consultarinformacioncuenta",})
public class UsuariosServlet extends GenericoServlet implements IServlet {

    private UsuarioDelegado usuarioDelegado;

    @Override
    public Respuesta procesar(EAcciones eAcciones, HttpServletRequest request, Connection cnn) throws VotacionesException {
        Respuesta respuesta = null;
        usuarioDelegado = new UsuarioDelegado(cnn);
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
            case RECUPERAR_CUENTA:
                respuesta = recuperarCuenta(request);
                break;
            case COMPROBAR_TOKEN:
                respuesta = comprobarToken(request);
                break;
            case INICIAR_SESION:
                respuesta = iniciarSesion(request);
                break;
            case COMPROBAR_SESION:
                respuesta = comprobarSesion(request);
                break;
            case ACTUALIZAR_CLAVE:
                respuesta = actualizarClave(request);
                break;
            case CONSULTAR_NOTIFICACIONES:
                respuesta = consultarNotificaciones(request);
                break;
            case CERRAR_SESION:
                respuesta = cerrarSesion(request);
                break;
            case USUARIOS_REGISTRADOS:
                respuesta = consultarUsuariosRegistrados(request);
                break;
            case CAMBIAR_ESTADO_USUARIO:
                respuesta = cambiarEstadoUsuario(request);
                break;
            case CONSULTAR_INFORMACION_CUENTA:
                respuesta = consultarInformacionCuenta(request);
                break;
        }
        return respuesta;
    }

    private Respuesta insertar(HttpServletRequest request) throws VotacionesException {
        String json = request.getParameter("persona");
        Persona persona = GsonUtil.getInstanceShort().fromJson(json, Persona.class);
        Persona personaInsertada = usuarioDelegado.insertar(persona);
        Respuesta respuesta = new Respuesta(EMensajes.INSERTAR);
        respuesta.setDatos(personaInsertada);
        return respuesta;
    }

    private Respuesta actualizar(HttpServletRequest request) throws VotacionesException {
        String json = request.getParameter("persona");
        Persona persona = GsonUtil.getInstanceShort().fromJson(json, Persona.class);
        usuarioDelegado.actualizar(persona);
        Respuesta respuesta = new Respuesta(EMensajes.ACTUALIZAR);
        return respuesta;
    }

    private Respuesta listarTodos() throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        respuesta.setDatos(usuarioDelegado.listarTodos());
        return respuesta;
    }

    private Respuesta consultar(HttpServletRequest request) {
        String query = request.getParameter("query");
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        //response.setDatos(usuarioDelegado.query());
        return respuesta;
    }

    private Respuesta eliminar(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = null;
        String json = request.getParameter("usuario");
        String id = request.getParameter("id");
        if (json != null) {
            Usuario usuario = GsonUtil.getInstanceShort().fromJson(json, Usuario.class);
            usuarioDelegado.eliminar(usuario);
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        if (id != null) {
            usuarioDelegado.eliminar(Integer.parseInt(id));
            respuesta = new Respuesta(EMensajes.ELIMINAR);
        }
        return respuesta;
    }

    private Respuesta buscarPorId(HttpServletRequest request) throws VotacionesException {
        String id = request.getParameter("id");
        Respuesta respuesta = new Respuesta(EMensajes.CONSULTAR);
        respuesta.setDatos(usuarioDelegado.buscarPorId(Integer.parseInt(id)));
        return respuesta;
    }

    private String getConext() {
        ServletContext context = getServletContext();
        String pathProject = context.getRealPath("/");
        return pathProject;
    }

    private Respuesta recuperarCuenta(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        String credencial = request.getParameter("credencial");
        Persona persona = usuarioDelegado.existe(credencial);
        if (persona.getConsUsuario().getActivo().equals("S")) {
            Email email = new Email();
            email.setPathProject(getConext());
            if (email.recuperarCuenta(persona)) {
                respuesta = new Respuesta(EMensajes.EXITO);
            } else {
                respuesta = new Respuesta(EMensajes.ERROR);
            }
        } else {
            respuesta.setMensaje("Lo sentimos, esta cuenta no esta activa, por lo tanto no podrá recuperarla.");
        }
        return respuesta;
    }

    private Respuesta comprobarToken(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.ERROR_TOKEN_EMAIL);
        String token = request.getParameter("token");
        if (usuarioDelegado.comprobarToken(token)) {
            respuesta = new Respuesta(EMensajes.CONSULTAR);
        }
        return respuesta;
    }

    private Respuesta iniciarSesion(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.ERROR_AUTENTICACION);
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        Usuario u = usuarioDelegado.iniciarSesion(usuario, clave);
        switch (u.getActivo()) {
            case "N":
                throw new VotacionesException(EMensajes.CUENTA_PENDIENTE_POR_ACTIVACION);
            case "I":
                throw new VotacionesException(EMensajes.CUENTA_INACTIVA);
            case "B":
                throw new VotacionesException(EMensajes.CUENTA_BLOQUEADA);
        }
        u.setContrasena(null);
        if (u != null) {
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", u);
            respuesta = new Respuesta(EMensajes.AUTENTICADO);
        }
        return respuesta;
    }

    private Respuesta comprobarSesion(HttpServletRequest request) {
        Respuesta respuesta = new Respuesta(EMensajes.SESION_EXPIRADA);
        if (SesionUtil.logeado(request)) {
            respuesta = new Respuesta(EMensajes.SESION_ACTIVA);
            respuesta.setDatos(SesionUtil.obtenerSesion(request));
        }
        return respuesta;
    }

    private Respuesta actualizarClave(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.ERROR_ACTUALIZAR);
        Usuario usuario = SesionUtil.obtenerSesion(request);
        String token = request.getParameter("token");
        String claveNueva = request.getParameter("clave");
        if (usuario == null && token != null) {
            usuario = usuarioDelegado.consultarPorTokenAccion(token);
        }
        if (usuario != null) {
            usuarioDelegado.actualizarClave(usuario.getConsUsuario(), claveNueva, token);
            respuesta = new Respuesta(EMensajes.ACTUALIZAR);
        } else {
            respuesta = new Respuesta(EMensajes.SESION_EXPIRADA);
        }
        return respuesta;
    }

    private Respuesta consultarNotificaciones(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        if (SesionUtil.esAdministrador(request)) {
            respuesta.setDatos(usuarioDelegado.consultarNotificaciones());
        }
        return respuesta;
    }

    private Respuesta cerrarSesion(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.CERRAR_SESION);
        SesionUtil.cerrarSesion(request);
        return respuesta;
    }

    private Respuesta consultarUsuariosRegistrados(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        if (SesionUtil.esAdministrador(request)) {
            respuesta.setDatos(usuarioDelegado.consultarUsuariosRegistrados());
        }
        return respuesta;
    }

    private Respuesta cambiarEstadoUsuario(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.ACTUALIZAR);
        if (SesionUtil.esAdministrador(request)) {
            Integer idUsuario = Integer.parseInt(request.getParameter("usuario"));
            String estado = request.getParameter("estado");
            respuesta = usuarioDelegado.cambiarEstadoUsuario(idUsuario, estado, getConext());
        } else {
            respuesta = new Respuesta(EMensajes.ACCESO_DENEGADO);
        }
        return respuesta;
    }

    private Respuesta consultarInformacionCuenta(HttpServletRequest request) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.ACCESO_DENEGADO);
        if (SesionUtil.logeado(request)) {
            Usuario usuario = SesionUtil.obtenerSesion(request);
            Persona persona = usuarioDelegado.consultarInformacionCuenta(usuario);
            respuesta = new Respuesta(EMensajes.CONSULTAR);
            respuesta.setDatos(persona);
        }
        return respuesta;
    }

}
