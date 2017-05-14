package edu.poli.gerencia.votaciones.negocio.delegados;

import edu.poli.gerencia.votaciones.modelo.dao.CandidatoVotacionDAO;
import edu.poli.gerencia.votaciones.modelo.dao.VotacionDAO;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.modelo.vo.VotacionUsuarioCandidato;
import edu.poli.gerencia.votaciones.modelo.dao.VotacionUsuarioCandidatoDAO;
import edu.poli.gerencia.votaciones.modelo.vo.CandidatoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class VotacionUsuarioCandidatoDelegado extends GenericoDelegado<VotacionUsuarioCandidato> {

    private final VotacionUsuarioCandidatoDAO votacionUsuarioCandidatoDAO;

    public VotacionUsuarioCandidatoDelegado(Connection cnn) throws VotacionesException {
        super(cnn);
        votacionUsuarioCandidatoDAO = new VotacionUsuarioCandidatoDAO(cnn);
        genericoDAO = votacionUsuarioCandidatoDAO;
    }

    public void insertar(VotacionUsuarioCandidato vuc, Integer idVotacion) throws VotacionesException {
        try {
            //Consultamos el registro de participación del candidato (candidato_votacion).
            CandidatoVotacionDAO cvDAO = new CandidatoVotacionDAO(cnn);
            CandidatoVotacion cv = cvDAO.consultar("SELECT * FROM candidato_votacion cv WHERE "
                    + "cv.CONS_USUARIO_VOTACION = " + vuc.getCandidatoVotacionConsCandidato().getConsCandidato() + " AND "
                    + "cv.VOTACION_CONS_VOTACION = " + idVotacion);
            vuc.setCandidatoVotacionConsCandidato(cv);
            if (cv != null) {
                //Actualizamos el número de votos.
                cv.setCantidadVotos(cv.getCantidadVotos() + 1);
                cvDAO.actualizar(cv);
                //Actualizamos el número de votaciones...
                VotacionDAO vDAO = new VotacionDAO(cnn);
                Votacion v = vDAO.buscarPorId(idVotacion);
                v.setCantidadVotos(v.getCantidadVotos() + 1);
                //Validamos que el usuario no haya votado antes.
                String sql = "SELECT * FROM votacion_usuario_candidato WHERE "
                        + "CANDIDATO_VOTACION_CONS_CANDIDATO = " + vuc.getCandidatoVotacionConsCandidato().getConsCandidato() + " AND "
                        + "USUARIO_CONS_USUARIO = " + vuc.getUsuarioConsUsuario().getConsUsuario();
                VotacionUsuarioCandidato vTemp = votacionUsuarioCandidatoDAO.consultar(sql);
                if (vTemp == null) {
                    vDAO.actualizar(v);
                    votacionUsuarioCandidatoDAO.insertar(vuc);
                } else {
                    throw new VotacionesException(EMensajes.ERROR_YA_HAS_VOTADO);
                }
            } else {
                throw new VotacionesException(EMensajes.ERROR_DATO).reemplazarParteMensaje("__DATO__", "CV Es inválido.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new VotacionesException(EMensajes.ERROR_INSERTAR);
        }
    }

}
