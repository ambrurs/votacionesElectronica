package edu.poli.gerencia.votaciones.modelo.dao;

import edu.poli.gerencia.votaciones.modelo.dao.crud.VotacionCRUD;
import java.sql.Connection;

public class VotacionDAO extends VotacionCRUD {

    public VotacionDAO(Connection cnn) {
        super(cnn);
    }
}
