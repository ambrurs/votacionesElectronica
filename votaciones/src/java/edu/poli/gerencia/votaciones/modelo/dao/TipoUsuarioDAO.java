package edu.poli.gerencia.votaciones.modelo.dao;

import edu.poli.gerencia.votaciones.modelo.dao.crud.TipoUsuarioCRUD;
import java.sql.Connection;

public class TipoUsuarioDAO extends TipoUsuarioCRUD {

    public TipoUsuarioDAO(Connection cnn) {
        super(cnn);
    }
}
