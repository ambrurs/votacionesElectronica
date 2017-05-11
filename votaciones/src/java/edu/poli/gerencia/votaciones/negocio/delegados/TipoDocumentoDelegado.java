package edu.poli.gerencia.votaciones.negocio.delegados;

import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.modelo.vo.TipoDocumento;
import edu.poli.gerencia.votaciones.modelo.dao.TipoDocumentoDAO;
import java.sql.Connection;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */

public class TipoDocumentoDelegado extends GenericoDelegado<TipoDocumento> {

    private final TipoDocumentoDAO tipoDocumentoDAO;

    public TipoDocumentoDelegado(Connection cnn) throws VotacionesException {
        super(cnn);
        tipoDocumentoDAO = new TipoDocumentoDAO(cnn);
        genericoDAO = tipoDocumentoDAO;
    }

}
