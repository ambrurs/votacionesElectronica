package edu.poli.gerencia.votaciones.negocio.delegados;

import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.modelo.vo.TipoUsuario;
import edu.poli.gerencia.votaciones.modelo.dao.TipoUsuarioDAO;
import java.sql.Connection;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */

public class TipoUsuarioDelegado extends GenericoDelegado<TipoUsuario> {

    private final TipoUsuarioDAO tipoUsuarioDAO;

    public TipoUsuarioDelegado(Connection cnn) throws VotacionesException {
        super(cnn);
        tipoUsuarioDAO = new TipoUsuarioDAO(cnn);
        genericoDAO = tipoUsuarioDAO;
    }

}
