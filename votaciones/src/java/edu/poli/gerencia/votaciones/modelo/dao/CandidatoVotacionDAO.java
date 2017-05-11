package edu.poli.gerencia.votaciones.modelo.dao;

import edu.poli.gerencia.votaciones.modelo.dao.crud.CandidatoVotacionCRUD;
import java.sql.Connection;

public class CandidatoVotacionDAO extends CandidatoVotacionCRUD {

    public CandidatoVotacionDAO(Connection cnn) {
        super(cnn);
    }
}
