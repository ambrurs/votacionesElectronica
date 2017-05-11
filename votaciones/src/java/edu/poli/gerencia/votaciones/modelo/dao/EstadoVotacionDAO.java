package edu.poli.gerencia.votaciones.modelo.dao;

import edu.poli.gerencia.votaciones.modelo.dao.crud.EstadoVotacionCRUD;
import java.sql.Connection;

public class EstadoVotacionDAO extends EstadoVotacionCRUD {

    public EstadoVotacionDAO(Connection cnn) {
        super(cnn);
    }
}
