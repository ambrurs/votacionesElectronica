/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.servlets;

import com.google.gson.Gson;
import edu.poli.gerencia.votacion.modelo.dto.UsuarioDto;
import edu.poli.gerencia.votacion.modelo.vo.Persona;
import edu.poli.gerencia.votacion.modelo.vo.Usuario;
import edu.poli.gerencia.votacion.negocio.constantes.EDireccion;
import edu.poli.gerencia.votacion.negocio.constantes.EMensajes;
import edu.poli.gerencia.votacion.negocio.delegado.UsuariosDelegado;
import edu.poli.gerencia.votacion.negocio.recursos.Respuesta;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jhon1
 */
@WebServlet(
        name = "UsuariosServlet",
        urlPatterns = {
            "/iniciarsesion",
            "/cerrarsesion",
            "/registrarusuario",
            "/recuperarcuenta",
            "/comprobartoken",
            "/actualizarcuenta",
            "/comprobarsesion"
        }
)
public class UsuariosServlet extends GenericoServlet implements IServlet {

    private UsuariosDelegado delegado;

    @Override
    public void init() throws ServletException {
        super.init();
        delegado = UsuariosDelegado.getInstancia();
    }

    @Override
    public Respuesta procesar(EDireccion direccion, HttpServletRequest request) throws Exception {
        Respuesta respuesta = new Respuesta(EMensajes.RECURSO_NO_ENCONTRADO);
        switch (direccion) {
            case INICIAR_SESION:
                respuesta = iniciarSesion(request);
                break;
            case CERRAR_SESION:
                respuesta = cerrarSesion(request);
                break;
            case REGISTRAR_USUARIO:
                respuesta = registrarUsuario(request);
                break;
            case RECUPERAR_CUENTA:
                respuesta = recuperarCuenta(request);
                break;
            case COMPROBAR_TOKEN:
                respuesta = comprobarToken(request);
                break;
            case ACTUALIZAR_CUENTA:
                respuesta = actualizarCuenta(request);
                break;
            case COMPROBAR_SESION:
                respuesta = comprobarSesion(request);
                break;
        }
        return respuesta;
    }

    private Respuesta iniciarSesion(HttpServletRequest request) {
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        Respuesta respuesta = delegado.ingresar(usuario, clave);
        if (respuesta.getDatos() != null) {
            UsuarioDto usuarioVO = (UsuarioDto) respuesta.getDatos();
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", usuarioVO);
            System.out.println(sesion.getAttribute("usuario"));
        }
        return respuesta;
    }

    private Respuesta registrarUsuario(HttpServletRequest request) {
        Gson gson = new Gson();
        String json = request.getParameter("usuario");
        Usuario usuario = gson.fromJson(json, Usuario.class);
        json = request.getParameter("persona");
        Persona persona = gson.fromJson(json, Persona.class);
        return delegado.registrarUsuario(usuario, persona);
    }

    private Respuesta cerrarSesion(HttpServletRequest request) {
        return delegado.cerrarSesion(request);
    }

    private Respuesta recuperarCuenta(HttpServletRequest request) {
        String credencial = request.getParameter("credencial");
        System.out.println(credencial);
        return delegado.recuperarCuenta(credencial);
    }

    private Respuesta comprobarToken(HttpServletRequest request) {
        String token = request.getParameter("token");
        return delegado.comprobarToken(token);
    }

    private Respuesta actualizarCuenta(HttpServletRequest request) {
        String claveNueva = request.getParameter("clave");
        String token = request.getParameter("token");
        return delegado.actualizarCuenta(claveNueva, token);
    }

    private Respuesta comprobarSesion(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        UsuarioDto usuario = (UsuarioDto) sesion.getAttribute("usuario");
        Respuesta respuesta = new Respuesta(EMensajes.SESION_ACTIVA);
        respuesta.setDatos(usuario);
        return respuesta;
    }

}
