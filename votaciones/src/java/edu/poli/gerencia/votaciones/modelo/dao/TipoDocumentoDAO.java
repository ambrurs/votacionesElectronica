package edu.poli.gerencia.votaciones.modelo.dao;

import edu.poli.gerencia.votaciones.modelo.dao.crud.TipoDocumentoCRUD;
import java.sql.Connection;

public class TipoDocumentoDAO extends TipoDocumentoCRUD {

    public TipoDocumentoDAO(Connection cnn) {
        super(cnn);
    }
}
