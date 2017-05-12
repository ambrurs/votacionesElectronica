package edu.poli.gerencia.votaciones.negocio.delegados;

import edu.poli.gerencia.votaciones.modelo.dao.CandidatoVotacionDAO;
import edu.poli.gerencia.votaciones.modelo.dao.PersonaDAO;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;
import edu.poli.gerencia.votaciones.modelo.dao.VotacionDAO;
import edu.poli.gerencia.votaciones.modelo.vo.CandidatoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Persona;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.utiles.SesionUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class VotacionDelegado extends GenericoDelegado<Votacion> {

    private final VotacionDAO votacionDAO;

    public VotacionDelegado(Connection cnn) throws VotacionesException {
        super(cnn);
        votacionDAO = new VotacionDAO(cnn);
        genericoDAO = votacionDAO;
    }

    public List<Votacion> listarVotaciones() throws VotacionesException {
        try {
            return votacionDAO.listaConsultar("SELECT v.*, u.NOMBRE_USUARIO, tv.NOMBRE_TIPO_VOTACION"
                    + " FROM votacion v INNER JOIN tipo_votacion tv ON"
                    + " v.ID_TIPO_VOTACION = tv.ID_TIPO_VOTACION INNER JOIN usuario"
                    + " u ON v.CONS_USUARIO_CREACION = u.CONS_USUARIO"
                    + "");
        } catch (SQLException ex) {
            Logger.getLogger(VotacionDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.CONSULTAR);
        }
    }

    public void actualizar(Votacion votacion) throws VotacionesException {
        try {
            Votacion votacionTemp = votacionDAO.buscarPorId(votacion.getConsVotacion());
            votacion.setConsUsuarioCreacion(votacionTemp.getConsUsuarioCreacion());
            votacionTemp.equalize(votacion);
            votacionDAO.actualizar(votacionTemp);
        } catch (SQLException ex) {
            Logger.getLogger(GenericoDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_ACTUALIZAR);
        }
    }

    public List<Votacion> listarVotaciones(Usuario usuario) throws VotacionesException {
        try {
            String sql = null;
            sql = "SELECT p.*, u.ID_TIPO_USUARIO FROM persona p "
                    + "INNER JOIN usuario u ON u.CONS_USUARIO = p.CONS_USUARIO "
                    + "WHERE p.CONS_USUARIO = " + usuario.getConsUsuario();
            PersonaDAO pDAO = new PersonaDAO(cnn);
            Persona persona = pDAO.consultar(sql);
            Integer idTipoUsuario = persona.getConsUsuario().getIdTipoUsuario().getIdTipoUsuario();
            Integer idUsuario = 0;
            switch (idTipoUsuario) {
                case 1: //Administrador.
                    idUsuario = persona.getConsUsuario().getConsUsuario();
                    break;
                case 2: //Empresa.
                    idUsuario = persona.getConsUsuario().getConsUsuario();
                    break;
                case 3: //Empleado.
                    idUsuario = persona.getConsPersonaAsociada();
                    persona = pDAO.buscarPorId(idUsuario);
                    idUsuario = persona.getConsUsuario().getConsUsuario();
                    break;
            }
            sql = "SELECT v.*, tv.NOMBRE_TIPO_VOTACION FROM votacion v \n"
                    + "INNER JOIN tipo_votacion tv ON v.ID_TIPO_VOTACION = tv.ID_TIPO_VOTACION \n"
                    + "WHERE v.CONS_USUARIO_CREACION = " + idUsuario;
            return votacionDAO.listaConsultar(sql);
        } catch (Exception ex) {
            Logger.getLogger(GenericoDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_CONSULTAR);
        }
    }

    public Map<String, Object> consultarDetallesVotacion(Integer id, Usuario usuario, Connection cnn) throws VotacionesException {
        try {
            Map<String, Object> map = new HashMap<>();
            String sql = "SELECT v.*, tv.NOMBRE_TIPO_VOTACION FROM votacion v INNER JOIN tipo_votacion tv "
                    + "ON v.ID_TIPO_VOTACION = tv.ID_TIPO_VOTACION "
                    + "WHERE v.CONS_VOTACION = " + id;
            Votacion votacion = votacionDAO.consultar(sql);
            map.put("votacion", votacion);
            if (votacion != null) {
                if (votacion.getCantidadCandidatos() > 0) {
                    //Consultamos los candidatos de la votación.
                    CandidatoVotacionDAO cvDAO = new CandidatoVotacionDAO(cnn);
                    PersonaDAO pDAO = new PersonaDAO(cnn);
                    sql = "SELECT p.*, u.NOMBRE_USUARIO FROM persona p INNER JOIN usuario u ON u.CONS_USUARIO = p.CONS_USUARIO \n"
                            + "INNER JOIN candidato_votacion cv ON u.CONS_USUARIO = cv.CONS_USUARIO_VOTACION \n"
                            + "WHERE cv.VOTACION_CONS_VOTACION = " + votacion.getConsVotacion() + " AND u.ACTIVO = 'S'";
                    map.put("listaCandidatos", pDAO.listaConsultar(sql));
                    //Consultamos si el usuaro actual es candidato.
                    sql = "SELECT cv.* FROM candidato_votacion cv WHERE cv.VOTACION_CONS_VOTACION = " + votacion.getConsVotacion() + " AND cv.CONS_USUARIO_VOTACION = " + usuario.getConsUsuario();
                    map.put("usuarioActualEsCandidato", (cvDAO.listaConsultar(sql).size() > 0));
                    //Consultamos si el usuario no ha votado antes.
                    sql = "SELECT cv.* FROM votacion_usuario_candidato vuc \n"
                            + "INNER JOIN candidato_votacion cv ON vuc.CANDIDATO_VOTACION_CONS_CANDIDATO = cv.CONS_CANDIDATO \n"
                            + "WHERE cv.VOTACION_CONS_VOTACION = " + votacion.getConsVotacion() + " AND vuc.USUARIO_CONS_USUARIO = " + usuario.getConsUsuario();
                    map.put("usuarioActualHaVotado", (cvDAO.listaConsultar(sql).size() > 0));
                }
            }
            return map;
        } catch (SQLException ex) {
            Logger.getLogger(GenericoDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_CONSULTAR);
        }
    }

}
