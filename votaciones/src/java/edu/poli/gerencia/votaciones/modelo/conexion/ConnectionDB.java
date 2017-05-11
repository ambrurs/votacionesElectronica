package edu.poli.gerencia.votaciones.modelo.conexion;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.poli.gerencia.votaciones.modelo.vo.Database;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;

public class ConnectionDB {

    public static Connection conectar(Database db) throws VotacionesException {
        try {
            Class.forName(db.getType().getForName());
            String url = db.getType().getSentence() + db.getUrl();
            System.out.println(url + ":" + db.getUsername() + "," + db.getPassword());
            Connection cnn = DriverManager.getConnection(url, db.getUsername(), db.getPassword());
            cnn.setAutoCommit(false);
            System.out.println("Conectado...");
            return cnn;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_DRIVER_DB);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_CONEXION_BD);
        }
    }

    public static void desconectar(Connection cnn) {
        if (cnn != null) {
            try {
                cnn.rollback();
                cnn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void desconectar(Connection cnn, PreparedStatement sentence) {
        try {
            if (sentence != null) {
                sentence.close();
            }
            desconectar(cnn);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void desconectar(PreparedStatement sentence) {
        desconectar(null, sentence);
    }

    public static void commit(Connection cnn) throws SQLException {
        if (cnn != null) {
            cnn.commit();
        }
    }

    public static void rollback(Connection cnn) {
        try {
            if (cnn != null) {
                cnn.rollback();
            }
        } catch (Exception ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
