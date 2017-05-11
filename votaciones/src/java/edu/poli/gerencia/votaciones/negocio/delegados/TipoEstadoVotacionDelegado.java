package edu.poli.gerencia.votaciones.negocio.delegados;

import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.modelo.vo.TipoEstadoVotacion;
import edu.poli.gerencia.votaciones.modelo.dao.TipoEstadoVotacionDAO;
import java.sql.Connection;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */

public class TipoEstadoVotacionDelegado extends GenericoDelegado<TipoEstadoVotacion> {

    private final TipoEstadoVotacionDAO tipoEstadoVotacionDAO;

    public TipoEstadoVotacionDelegado(Connection cnn) throws VotacionesException {
        super(cnn);
        tipoEstadoVotacionDAO = new TipoEstadoVotacionDAO(cnn);
        genericoDAO = tipoEstadoVotacionDAO;
    }

}
