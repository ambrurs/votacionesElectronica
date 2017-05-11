package edu.poli.gerencia.votaciones.negocio.delegados;

import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.modelo.vo.CandidatoVotacion;
import edu.poli.gerencia.votaciones.modelo.dao.CandidatoVotacionDAO;
import edu.poli.gerencia.votaciones.modelo.dao.VotacionDAO;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class CandidatoVotacionDelegado extends GenericoDelegado<CandidatoVotacion> {
    
    private final CandidatoVotacionDAO candidatoVotacionDAO;
    
    public CandidatoVotacionDelegado(Connection cnn) throws VotacionesException {
        super(cnn);
        candidatoVotacionDAO = new CandidatoVotacionDAO(cnn);
        genericoDAO = candidatoVotacionDAO;
    }
    
    public List<CandidatoVotacion> listarTodos(Integer id) throws VotacionesException {
        try {
            String sql = "SELECT cv.*, u.NOMBRE_USUARIO, v.ID_TIPO_VOTACION FROM candidato_votacion cv INNER JOIN usuario u\n"
                    + " ON cv.CONS_USUARIO_VOTACION = u.CONS_USUARIO INNER JOIN votacion v\n"
                    + " ON cv.VOTACION_CONS_VOTACION = v.CONS_VOTACION \n"
                    + " WHERE cv.VOTACION_CONS_VOTACION = " + id;
            return candidatoVotacionDAO.listaConsultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CandidatoVotacionDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_CONSULTAR);
        }
    }
    
    public void insertar(CandidatoVotacion cv) throws VotacionesException {
        try {
            CandidatoVotacion cv2 = candidatoVotacionDAO.consultar("SELECT * FROM candidato_votacion cv "
                    + "WHERE cv.VOTACION_CONS_VOTACION = " + cv.getVotacionConsVotacion().getConsVotacion()
                    + " AND cv.CONS_USUARIO_VOTACION = " + cv.getConsUsuarioVotacion().getConsUsuario());
            if (cv2 == null) {
                VotacionDAO vDAO = new VotacionDAO(cnn);
                Votacion v = vDAO.buscarPorId(cv.getVotacionConsVotacion().getConsVotacion());
                v.setCantidadCandidatos(v.getCantidadCandidatos() + 1);
                System.out.println("Votación: " + v);
                vDAO.actualizar(v);
                candidatoVotacionDAO.insertar(cv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CandidatoVotacionDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_INSERTAR);
        }
    }
    
    public void eliminar(Integer idVotacion, Usuario usuario) throws VotacionesException {
        try {
            String sql = "SELECT * FROM candidato_votacion cv WHERE cv.CONS_USUARIO_VOTACION = " + usuario.getConsUsuario() + " AND cv.VOTACION_CONS_VOTACION = " + idVotacion;
            CandidatoVotacion cv = candidatoVotacionDAO.consultar(sql);
            if (cv != null) {
                VotacionDAO vDAO = new VotacionDAO(cnn);
                Votacion v = vDAO.buscarPorId(cv.getVotacionConsVotacion().getConsVotacion());
                v.setCantidadCandidatos(v.getCantidadCandidatos() - 1);
                vDAO.actualizar(v);
                candidatoVotacionDAO.eliminar(cv);
            } else {
                throw new VotacionesException(EMensajes.ACCESO_DENEGADO);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CandidatoVotacionDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_ELIMINAR);
        }
    }
    
}
