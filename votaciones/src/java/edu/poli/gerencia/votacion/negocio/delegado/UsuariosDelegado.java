/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.delegado;

import edu.poli.gerencia.votacion.modelo.conexion.ConexionBD;
import edu.poli.gerencia.votacion.modelo.dao.PersonaDAO;
import edu.poli.gerencia.votacion.modelo.dao.UsuariosDAO;
import edu.poli.gerencia.votacion.modelo.vo.Persona;
import edu.poli.gerencia.votacion.modelo.vo.TipoUsuario;
import edu.poli.gerencia.votacion.modelo.vo.Usuario;
import edu.poli.gerencia.votacion.negocio.constantes.EMensajes;
import edu.poli.gerencia.votacion.negocio.recursos.Email;
import edu.poli.gerencia.votacion.negocio.recursos.Respuesta;
import edu.poli.gerencia.votacion.negocio.util.Util;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jhon1
 */
public class UsuariosDelegado {

    private static UsuariosDelegado instancia = new UsuariosDelegado();

    public UsuariosDelegado() {
    }

    public static UsuariosDelegado getInstancia() {
        return instancia;
    }

    public Respuesta ingresar(String usuario, String clave) {
        EntityManager em = null;
        Respuesta respuesta = new Respuesta(EMensajes.ERROR_LOGIN);
        try {
            clave = Util.md5(clave);
            em = ConexionBD.getEntityManager();
            Usuario usuarioVO = new Usuario();
            UsuariosDAO dao = new UsuariosDAO(em);
            usuarioVO = dao.iniciarSesion(usuario, clave);
            if (usuarioVO != null) {
                usuarioVO.setContrasena(null);
                respuesta = new Respuesta(EMensajes.OK_LOGIN);
                respuesta.setDatos(usuarioVO);
            }
        } catch (Exception e) {
            Logger.getLogger(UsuariosDelegado.class.getName()).log(Level.SEVERE, null, e);
            respuesta = new Respuesta(EMensajes.ERROR_LOGIN);
        }
        return respuesta;
    }

    public Respuesta cerrarSesion(HttpServletRequest request) {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_PUDO_PROCESAR);
        try {
            HttpSession sesion = request.getSession();
            sesion.invalidate();
            respuesta = new Respuesta(EMensajes.CORRECTO);
        } catch (Exception e) {
            Logger.getLogger(UsuariosDelegado.class.getName()).log(Level.SEVERE, null, e);
        }
        return respuesta;
    }

    public Respuesta registrarUsuario(Usuario usuario, Persona persona) {
        EntityManager em = null;
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_PUDO_PROCESAR);
        try {
            em = ConexionBD.getEntityManager();
            UsuariosDAO usuariosDAO = new UsuariosDAO(em);
            PersonaDAO personaDAO = new PersonaDAO(em);
            usuario.setActivo('S');
            TipoUsuario tp = new TipoUsuario();
            tp.setIdTipoUsuario(3);
            usuario.setIdTipoUsuario(tp);
            usuario.setContrasena(Util.md5(usuario.getContrasena()));
            if (usuariosDAO.existeUsuario(usuario, persona)) {

            }
            em.getTransaction().begin();
            usuariosDAO.crear(usuario);
            persona.setConsUsuario(usuario);
            personaDAO.crear(persona);
            em.getTransaction().commit();
            respuesta = new Respuesta(EMensajes.REGISTRO_USUARIO);
            respuesta.setDatos(usuario.getConsUsuario());
        } catch (Exception e) {
            Logger.getLogger(UsuariosDelegado.class.getName()).log(Level.SEVERE, null, e);
        }
        return respuesta;
    }

    public Respuesta recuperarCuenta(String credencial) {
        EntityManager em = null;
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_PUDO_PROCESAR);
        try {
            em = ConexionBD.getEntityManager();
            UsuariosDAO dao = new UsuariosDAO(em);
            Persona persona = dao.existeUsuario(credencial);
            if (persona != null) {
                respuesta = new Respuesta(EMensajes.CONSULTAR);
                Email email = new Email();
                email.recuperarCuenta(persona.getCorreo());
            } else {
                respuesta = new Respuesta(EMensajes.NO_HAY_RESULTADOS);
            }
        } catch (Exception e) {
            Logger.getLogger(UsuariosDelegado.class.getName()).log(Level.SEVERE, null, e);
        }
        return respuesta;
    }

}
