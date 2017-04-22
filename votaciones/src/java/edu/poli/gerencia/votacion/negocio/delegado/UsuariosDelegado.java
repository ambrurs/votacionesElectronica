/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.delegado;

import edu.poli.gerencia.votacion.modelo.conexion.ConexionBD;
import edu.poli.gerencia.votacion.modelo.dao.PersonaDAO;
import edu.poli.gerencia.votacion.modelo.dao.TokensAccionDAO;
import edu.poli.gerencia.votacion.modelo.dao.UsuariosDAO;
import edu.poli.gerencia.votacion.modelo.dto.UsuarioDto;
import edu.poli.gerencia.votacion.modelo.vo.Persona;
import edu.poli.gerencia.votacion.modelo.vo.TipoUsuario;
import edu.poli.gerencia.votacion.modelo.vo.TokensAccion;
import edu.poli.gerencia.votacion.modelo.vo.Usuario;
import edu.poli.gerencia.votacion.negocio.constantes.EMensajes;
import edu.poli.gerencia.votacion.negocio.recursos.Email;
import edu.poli.gerencia.votacion.negocio.recursos.Respuesta;
import edu.poli.gerencia.votacion.negocio.util.App;
import edu.poli.gerencia.votacion.negocio.util.Util;
import java.util.Date;
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
            UsuariosDAO dao = new UsuariosDAO(em);
            UsuarioDto usuarioDto = new UsuarioDto();
            Persona personaVO = dao.iniciarSesion(usuario, clave);
            if (personaVO != null) {
                usuarioDto.setConsUsuario(personaVO.getConsUsuario().getConsUsuario());
                usuarioDto.setNombreUsuario(personaVO.getConsUsuario().getNombreUsuario());
                usuarioDto.setIdTipoUsuario(personaVO.getConsUsuario().getIdTipoUsuario().getIdTipoUsuario());
                usuarioDto.setActivo(personaVO.getConsUsuario().getActivo());
                usuarioDto.setIdTipoDocumento(personaVO.getIdTipoDocumento().getIdTipoDocumento());
                usuarioDto.setNombreUsuario(personaVO.getPrimerNombre());
                usuarioDto.setPrimerNombre(personaVO.getPrimerNombre());
                usuarioDto.setSegundoNombre(personaVO.getSegundoNombre());
                usuarioDto.setPrimerApellido(personaVO.getPrimerApellido());
                respuesta = new Respuesta(EMensajes.OK_LOGIN);
                respuesta.setDatos(usuarioDto);
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
                //Generamos el token...
                TokensAccionDAO tokenDAO = new TokensAccionDAO(em);
                TokensAccion token = new TokensAccion();
                token.setToken(Util.md5(persona.getCorreo()));
                token.setFechaCreacion(new Date());
                token.setAsunto("recuperar_cuenta"); //Recuperar Cuenta.
                token.setEstado(Boolean.FALSE); //Uso...
                token.setIdAutor(persona.getConsUsuario().getConsUsuario());
                em.getTransaction().begin();
                tokenDAO.crear(token);
                em.getTransaction().commit();
                //Se envia el correo electrónico, este proceso se hace en un hilo.
                email.recuperarCuenta(persona);
            } else {
                respuesta = new Respuesta(EMensajes.NO_HAY_RESULTADOS);
            }
        } catch (Exception e) {
            Logger.getLogger(UsuariosDelegado.class.getName()).log(Level.SEVERE, null, e);
        }
        return respuesta;
    }

    public Respuesta comprobarToken(String token) {
        EntityManager em = null;
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_PUDO_PROCESAR);
        try {
            em = ConexionBD.getEntityManager();
            TokensAccionDAO dao = new TokensAccionDAO(em);
            TokensAccion tokenVO = dao.existeToken(token);
            if (tokenVO != null) {
                respuesta = new Respuesta(EMensajes.CONSULTAR);
            } else {
                respuesta = new Respuesta(EMensajes.NO_HAY_RESULTADOS);
            }
        } catch (Exception e) {
            Logger.getLogger(UsuariosDelegado.class.getName()).log(Level.SEVERE, null, e);
        }
        return respuesta;
    }

    public Respuesta actualizarCuenta(String claveNueva, String token) {
        EntityManager em = null;
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_PUDO_PROCESAR);
        try {
            em = ConexionBD.getEntityManager();
            TokensAccionDAO dao = new TokensAccionDAO(em);
            TokensAccion tokenVO = dao.existeToken(token);
            if (tokenVO != null) {
                UsuariosDAO usuarioDAO = new UsuariosDAO(em);
                Usuario usuario = usuarioDAO.consultaUsuarioPorId(tokenVO.getIdAutor());
                usuario.setContrasena(Util.md5(claveNueva));
                em.getTransaction().begin();
                tokenVO.setActualizado(new Date());
                tokenVO.setEstado(Boolean.TRUE);
                dao.editar(tokenVO);
                usuarioDAO.editar(usuario);
                respuesta = new Respuesta(EMensajes.EDITAR);
                em.getTransaction().commit();
            } else {
                App.init();
                respuesta = new Respuesta(-1, "Lo sentimos, el enlace de acceso no es válido o ha vencido. Si desea obtener un nuevo enlace de recuperación de cuenta, haga <a href=\"" + App.urlApp + "/recoveraccount/\">clic aquí</a>", null);
            }
        } catch (Exception e) {
            Logger.getLogger(UsuariosDelegado.class.getName()).log(Level.SEVERE, null, e);
        }
        return respuesta;
    }

}
