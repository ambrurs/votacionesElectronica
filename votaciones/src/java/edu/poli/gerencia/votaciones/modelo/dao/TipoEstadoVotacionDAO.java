package edu.poli.gerencia.votaciones.modelo.dao;

import edu.poli.gerencia.votaciones.modelo.dao.crud.TipoEstadoVotacionCRUD;
import java.sql.Connection;

public class TipoEstadoVotacionDAO extends TipoEstadoVotacionCRUD {

    public TipoEstadoVotacionDAO(Connection cnn) {
        super(cnn);
    }
}
