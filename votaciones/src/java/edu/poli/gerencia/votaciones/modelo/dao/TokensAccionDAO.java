package edu.poli.gerencia.votaciones.modelo.dao;

import edu.poli.gerencia.votaciones.modelo.dao.crud.TokensAccionCRUD;
import java.sql.Connection;

public class TokensAccionDAO extends TokensAccionCRUD {

    public TokensAccionDAO(Connection cnn) {
        super(cnn);
    }
}
