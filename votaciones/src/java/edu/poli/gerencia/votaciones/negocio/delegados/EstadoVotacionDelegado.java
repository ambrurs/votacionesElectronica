package edu.poli.gerencia.votaciones.negocio.delegados;

import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.modelo.vo.EstadoVotacion;
import edu.poli.gerencia.votaciones.modelo.dao.EstadoVotacionDAO;
import java.sql.Connection;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */

public class EstadoVotacionDelegado extends GenericoDelegado<EstadoVotacion> {

    private final EstadoVotacionDAO estadoVotacionDAO;

    public EstadoVotacionDelegado(Connection cnn) throws VotacionesException {
        super(cnn);
        estadoVotacionDAO = new EstadoVotacionDAO(cnn);
        genericoDAO = estadoVotacionDAO;
    }

}
