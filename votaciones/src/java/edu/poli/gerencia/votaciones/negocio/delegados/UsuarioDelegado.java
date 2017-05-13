package edu.poli.gerencia.votaciones.negocio.delegados;

import edu.poli.gerencia.votacion.negocio.recursos.Email;
import edu.poli.gerencia.votacion.negocio.utiles.Util;
import edu.poli.gerencia.votaciones.modelo.dao.PersonaDAO;
import edu.poli.gerencia.votaciones.modelo.dao.TokensAccionDAO;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.modelo.dao.UsuarioDAO;
import edu.poli.gerencia.votaciones.modelo.vo.Persona;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.modelo.vo.TipoUsuario;
import edu.poli.gerencia.votaciones.modelo.vo.TokensAccion;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class UsuarioDelegado extends GenericoDelegado<Usuario> {

    private final UsuarioDAO usuarioDAO;

    public UsuarioDelegado(Connection cnn) throws VotacionesException {
        super(cnn);
        usuarioDAO = new UsuarioDAO(cnn);
        genericoDAO = usuarioDAO;
    }

    public Persona insertar(Persona persona) throws VotacionesException {
        try {
            //Comprobamos que no exista el usuario.            
            Persona personaTemp = usuarioDAO.existe(persona);
            if (personaTemp != null) {
                //Comprobamos cual de los campos ha sido repetido, y retornamos la excepción.
                if (persona.getNumeroDocumento().equals(personaTemp.getNumeroDocumento())) {
                    throw new VotacionesException(EMensajes.ERROR_REGISTRO_EXISTE)
                            .reemplazarParteMensaje("__DATO__", "documento");
                }
                if (persona.getConsUsuario().getNombreUsuario().equals(personaTemp.getConsUsuario().getNombreUsuario())) {
                    throw new VotacionesException(EMensajes.ERROR_REGISTRO_EXISTE)
                            .reemplazarParteMensaje("__DATO__", "nombre de usuario");
                }
                if (persona.getCorreo().equals(personaTemp.getCorreo())) {
                    throw new VotacionesException(EMensajes.ERROR_REGISTRO_EXISTE)
                            .reemplazarParteMensaje("__DATO__", "correo electrónico");
                }
                if(persona == null){
                    throw new VotacionesException(EMensajes.ERROR_INSERTAR);
                }
                boolean existeEmpresa = persona.getNombreEmpresa() == personaTemp.getNombreEmpresa();
                boolean registrandoEmpresa = persona.getConsUsuario().getIdTipoUsuario().getIdTipoUsuario() == 2;
                if (existeEmpresa && registrandoEmpresa) {
                    throw new VotacionesException(EMensajes.ERROR_REGISTRO_EXISTE)
                            .reemplazarParteMensaje("__DATO__", "nombre de empresa");
                }
            }
            if (persona.getConsUsuario().getContrasena() == null) {
                persona.getConsUsuario().setContrasena(Util.md5(persona.getNumeroDocumento()));
            }
            if (persona.getConsUsuario().getActivo() == null) {
                persona.getConsUsuario().setActivo("N");
            }
            persona.getConsUsuario().setFechaRegistro(new Date());
            //Insertamos el usuario.    
            usuarioDAO.insertar(persona.getConsUsuario());
            //Insertamos la persona.
            PersonaDAO personaDAO = new PersonaDAO(cnn);
            personaDAO.insertar(persona);
            return personaDAO.buscarPorId(persona.getConsPersona());
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_INSERTAR);
        }

    }

    public Persona existe(String credencial) throws VotacionesException {
        try {
            Persona personaTemp = new Persona();
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(credencial);
            personaTemp.setConsUsuario(usuario);
            personaTemp.setCorreo(credencial);
            personaTemp.setNumeroDocumento(credencial);
            personaTemp.setNombreEmpresa(credencial);
            Persona persona = usuarioDAO.existe(personaTemp);
            //Si existe, creamos un token de recuperación...
            if (persona != null) {
                try {
                    TokensAccionDelegado tokenDAO = new TokensAccionDelegado(cnn);
                    TokensAccion t = new TokensAccion();
                    t.setAsunto("recuperar_cuenta");
                    t.setIdAutor(persona.getConsUsuario().getConsUsuario());
                    t.setEstado(Boolean.TRUE);
                    t.setFechaCreacion(new Date());
                    t.setToken(Util.md5(persona.getCorreo()));
                    tokenDAO.insertar(t);
                } catch (Exception ex) {
                    Logger.getLogger(UsuarioDelegado.class.getName()).log(Level.SEVERE, null, ex);
                    throw new VotacionesException(EMensajes.ERROR_TOKEN_EMAIL);
                }
            }
            return persona;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_CONSULTAR);
        }
    }

    public boolean comprobarToken(String token) throws VotacionesException {
        try {
            return usuarioDAO.comprobarToken(token);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_CONSULTAR);
        }
    }

    public Usuario iniciarSesion(String sUsuario, String clave) throws VotacionesException {
        try {
            Usuario usuario = usuarioDAO.iniciarSesion(sUsuario, clave);
            usuario.setUltimoIngreso(new Date());
            usuarioDAO.actualizar(usuario);
            return usuario;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_AUTENTICACION);
        }
    }

    public void actualizarClave(Integer idUsuario, String claveNueva, String token) throws VotacionesException {
        try {
            //Actualizamos la clave del usuario.
            usuarioDAO.actualizarClave(idUsuario, claveNueva);
            //Actualizamos el token, para que no se pueda volver a usar.
            TokensAccionDAO tkDAO = new TokensAccionDAO(cnn);
            String sql = "SELECT * FROM tokens_accion WHERE token = '" + token
                    + "' AND estado = 1 AND UNIX_TIMESTAMP() - UNIX_TIMESTAMP(fecha_creacion)"
                    + " < 259200 ORDER BY fecha_creacion DESC";
            TokensAccion tokensAccion = tkDAO.consultar(sql);
            if (tokensAccion != null) {
                tokensAccion.setEstado(Boolean.FALSE);
                tokensAccion.setActualizado(new Date());
                tkDAO.actualizar(tokensAccion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDelegado.class.getName()).log(Level.SEVERE, "actualizarClave", ex);
            throw new VotacionesException(EMensajes.ERROR_AUTENTICACION);
        }
    }

    public Usuario consultarPorTokenAccion(String token) throws VotacionesException {
        try {
            return usuarioDAO.consultar("SELECT u.*, tk.fecha_creacion FROM usuario u INNER JOIN tokens_accion tk ON u.CONS_USUARIO = tk.id_autor WHERE tk.token = '" + token + "' AND UNIX_TIMESTAMP() - UNIX_TIMESTAMP(tk.fecha_creacion) < 259200 ORDER BY tk.fecha_creacion DESC");
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_TOKEN_EMAIL);
        }
    }

    /**
     * Este método lo he configurado para que devuelva un mapa de datos, está
     * pensado para realizar todas las consultas pertinentes que tengan que ver
     * con notificacioens, mensajes y demás alertas al usuario.
     *
     * @param usuario
     * @return Map<String, Object>
     * @throws VotacionesException
     */
    public Map<String, Object> consultarNotificaciones() throws VotacionesException {
        try {
            Map<String, Object> map = new HashMap<>();
            PersonaDAO personaDAO = new PersonaDAO(cnn);
            map.put("usuariosregistrados", personaDAO.listaConsultar(
                    "SELECT p.* FROM persona p"
                    + " INNER JOIN usuario u ON p.CONS_USUARIO = u.CONS_USUARIO"
                    + " WHERE (u.ID_TIPO_USUARIO = 2 OR u.ID_TIPO_USUARIO = 3) AND ACTIVO = 'N'"
                    + " GROUP BY u.CONS_USUARIO"
                    + ""));
            return map;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_CONSULTAR);
        }
    }

    public List<Persona> consultarUsuariosRegistrados() throws VotacionesException {
        PersonaDAO personaDAO = new PersonaDAO(cnn);
        try {
            return personaDAO.listaConsultar(
                    "SELECT p.*, u.NOMBRE_USUARIO, u.FECHA_REGISTRO, tp.ID_TIPO_USUARIO, tp.DESCRIPCION, u.ACTIVO FROM persona p"
                    + " INNER JOIN usuario u ON p.CONS_USUARIO = u.CONS_USUARIO"
                    + " INNER JOIN tipo_usuario tp ON u.ID_TIPO_USUARIO = tp.ID_TIPO_USUARIO "
                    + " WHERE u.ID_TIPO_USUARIO = 2 OR u.ID_TIPO_USUARIO = 3"
                    + " GROUP BY u.CONS_USUARIO"
                    + " ORDER BY u.FECHA_REGISTRO DESC");
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_CONSULTAR);
        }
    }

    public Respuesta cambiarEstadoUsuario(Integer idUsuario, String estado, String context) throws VotacionesException {
        Respuesta respuesta = new Respuesta(EMensajes.ERROR_ACTUALIZAR);
        try {
            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
            if (usuario != null) {
                usuario.setActivo(estado);
                usuarioDAO.actualizar(usuario);
                if (usuario.getActivo().equals("S")) {
                    PersonaDAO personaDAO = new PersonaDAO(cnn);
                    Persona persona = personaDAO.consultar("SELECT p.* FROM persona p WHERE CONS_USUARIO = " + usuario.getConsUsuario());
                    //Se envia el correo asincronamente.
                    Thread tareaAsincrona = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Email email = new Email();
                            email.setPathProject(context);
                            try {
                                email.activarCuenta(persona);
                            } catch (VotacionesException ex) {
                                Logger.getLogger(UsuarioDelegado.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    tareaAsincrona.start();
                }
            }
            respuesta = new Respuesta(EMensajes.ACTUALIZAR);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_ACTUALIZAR);
        }
        return respuesta;
    }

    public void actualizar(Persona persona) throws VotacionesException {
        try {
            if (persona != null) {
                PersonaDAO personaDao = new PersonaDAO(cnn);
                Persona personaTemp = personaDao.buscarPorId(persona.getConsPersona());
                Usuario usuario = usuarioDAO.buscarPorId(personaTemp.getConsUsuario().getConsUsuario());
                if (personaTemp != null && usuario != null) {
                    usuario.equalize(persona.getConsUsuario());
                    personaTemp.equalize(persona);
                    usuarioDAO.actualizar(usuario);
                    personaDao.actualizar(personaTemp);
                    return;
                }
            }
            throw new VotacionesException(EMensajes.ERROR_ACTUALIZAR);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_ACTUALIZAR);
        }
    }

    public Persona consultarInformacionCuenta(Usuario usuario) throws VotacionesException {
        try {
            PersonaDAO personaDAO = new PersonaDAO(cnn);
            String sql = "SELECT p.*, u.NOMBRE_USUARIO, u.ID_TIPO_USUARIO FROM persona p INNER JOIN usuario u ON"
                    + " p.CONS_USUARIO = u.CONS_USUARIO WHERE u.CONS_USUARIO = " + usuario.getConsUsuario();
            Persona persona = personaDAO.consultar(sql);
            return persona;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_CONSULTAR);
        }
    }

}
