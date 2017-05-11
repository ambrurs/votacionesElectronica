package edu.poli.gerencia.votaciones.modelo.dao;

import edu.poli.gerencia.votaciones.modelo.dao.crud.TipoVotacionCRUD;
import java.sql.Connection;

public class TipoVotacionDAO extends TipoVotacionCRUD {

    public TipoVotacionDAO(Connection cnn) {
        super(cnn);
    }
}
