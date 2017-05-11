package edu.poli.gerencia.votaciones.modelo.dao;

import edu.poli.gerencia.votaciones.modelo.dao.crud.VotacionUsuarioCandidatoCRUD;
import java.sql.Connection;

public class VotacionUsuarioCandidatoDAO extends VotacionUsuarioCandidatoCRUD {

    public VotacionUsuarioCandidatoDAO(Connection cnn) {
        super(cnn);
    }
}
