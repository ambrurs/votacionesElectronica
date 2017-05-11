package edu.poli.gerencia.votaciones.modelo.dao.crud;

import edu.poli.gerencia.votaciones.modelo.conexion.ConnectionDB;
import edu.poli.gerencia.votaciones.modelo.dao.IGenericoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSetMetaData;
import edu.poli.gerencia.votaciones.modelo.vo.TipoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class TipoVotacionCRUD implements IGenericoDAO<TipoVotacion> {

    protected Connection cnn;

    public TipoVotacionCRUD(Connection cnn) {
        this.cnn = cnn;
    }

    @Override
    public void insertar(TipoVotacion tipoVotacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO tipo_votacion (NOMBRE_TIPO_VOTACION, ID_PERSONA) VALUES (?,?)";
            ps = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setTipoVotacion(ps, tipoVotacion, true);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                tipoVotacion.setIdTipoVotacion(rs.getInt(1));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void actualizar(TipoVotacion tipoVotacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE tipo_votacion SET ID_TIPO_VOTACION = ?, NOMBRE_TIPO_VOTACION = ? WHERE ID_TIPO_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            setTipoVotacion(ps, tipoVotacion, true);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void eliminar(TipoVotacion tipoVotacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            int i = 1;
            String sql = "DELETE FROM tipo_votacion WHERE ID_TIPO_VOTACION = ? AND NOMBRE_TIPO_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            setTipoVotacion(ps, tipoVotacion, false);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<TipoVotacion> listarTodos() throws SQLException {
        PreparedStatement ps = null;
        List<TipoVotacion> list = new ArrayList<TipoVotacion>();
        try {
            String sql = "SELECT ID_TIPO_VOTACION, NOMBRE_TIPO_VOTACION FROM tipo_votacion";
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getTipoVotacion(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public TipoVotacion buscarPorId(Integer id) throws SQLException {
        PreparedStatement ps = null;
        TipoVotacion obj = null;
        try {
            String sql = "SELECT ID_TIPO_VOTACION, NOMBRE_TIPO_VOTACION FROM tipo_votacion WHERE ID_TIPO_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getTipoVotacion(rs);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return obj;
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        PreparedStatement ps = null;
        try {
            int i = 1;
            String sql = "DELETE FROM tipo_votacion WHERE ID_TIPO_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(i++, id);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<TipoVotacion> listaConsultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        List<TipoVotacion> list = new ArrayList<TipoVotacion>();
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getTipoVotacionAdaptable(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public TipoVotacion consultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        TipoVotacion obj = null;
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getTipoVotacionAdaptable(rs);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return obj;
    }

    public static TipoVotacion getTipoVotacion(ResultSet rs) throws SQLException {
        TipoVotacion tipoVotacion = new TipoVotacion();
        tipoVotacion.setIdTipoVotacion(rs.getInt("ID_TIPO_VOTACION"));
        tipoVotacion.setNombreTipoVotacion(rs.getString("NOMBRE_TIPO_VOTACION"));

        return tipoVotacion;
    }

    public static TipoVotacion getTipoVotacionAdaptable(ResultSet rs) throws SQLException {
        TipoVotacion tipoVotacion = new TipoVotacion();
        //ResultSetMetaData rsmd = rs.getMetaData();
        //int columns = rsmd.getColumnCount();
        //Para una próxima versión, mejorar el parseo del objeto en cuanto a rendimiento...
        //for (int i = 1; i <= columns; i++) {
        //System.out.println(rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
        tipoVotacion.setIdTipoVotacion(hasColumn(rs, "ID_TIPO_VOTACION") ? rs.getInt("ID_TIPO_VOTACION") : null);
        tipoVotacion.setNombreTipoVotacion(hasColumn(rs, "NOMBRE_TIPO_VOTACION") ? rs.getString("NOMBRE_TIPO_VOTACION") : null);

        //}
        return tipoVotacion;
    }

    private void setTipoVotacion(PreparedStatement ps, TipoVotacion tipoVotacion, boolean flag) throws SQLException {
        int i = 1;
        if (tipoVotacion.getIdTipoVotacion() != null) {
            ps.setObject(i++, (tipoVotacion.getIdTipoVotacion() != null) ? tipoVotacion.getIdTipoVotacion() : null);
        }
        ps.setString(i++, (tipoVotacion.getNombreTipoVotacion() != null) ? tipoVotacion.getNombreTipoVotacion() : null);
        ps.setObject(i++, (tipoVotacion.getIdPersona() != null) ? tipoVotacion.getIdPersona() : null);
        if (tipoVotacion.getIdTipoVotacion() != null) {
            if (flag) {
                ps.setObject(i++, (tipoVotacion.getIdTipoVotacion() != null) ? tipoVotacion.getIdTipoVotacion() : null);
            }
        }

    }

    public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(rsmd.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }

}
