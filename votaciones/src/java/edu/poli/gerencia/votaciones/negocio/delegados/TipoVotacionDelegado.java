package edu.poli.gerencia.votaciones.negocio.delegados;

import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.modelo.vo.TipoVotacion;
import edu.poli.gerencia.votaciones.modelo.dao.TipoVotacionDAO;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class TipoVotacionDelegado extends GenericoDelegado<TipoVotacion> {

    private final TipoVotacionDAO tipoVotacionDAO;

    public TipoVotacionDelegado(Connection cnn) throws VotacionesException {
        super(cnn);
        tipoVotacionDAO = new TipoVotacionDAO(cnn);
        genericoDAO = tipoVotacionDAO;
    }

    public List<TipoVotacion> listarTodos(Integer idPersona) throws VotacionesException {
        try {
            return tipoVotacionDAO.listaConsultar("SELECT * FROM tipo_votacion WHERE ID_PERSONA = " + idPersona);
        } catch (SQLException ex) {
            throw new VotacionesException(EMensajes.ERROR_CONSULTAR);
        }
    }

}
