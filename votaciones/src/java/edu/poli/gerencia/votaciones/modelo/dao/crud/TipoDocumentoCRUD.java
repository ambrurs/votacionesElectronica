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
import edu.poli.gerencia.votaciones.modelo.vo.TipoDocumento;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class TipoDocumentoCRUD implements IGenericoDAO<TipoDocumento> {

    protected Connection cnn;

    public TipoDocumentoCRUD(Connection cnn) {
        this.cnn = cnn;
    }

    @Override
    public void insertar(TipoDocumento tipoDocumento) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO tipo_documento (NOMBRE_DOCUMENTO) VALUES (?)";
            ps = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setTipoDocumento(ps, tipoDocumento, true);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                tipoDocumento.setIdTipoDocumento(rs.getInt(1));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void actualizar(TipoDocumento tipoDocumento) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE tipo_documento SET ID_TIPO_DOCUMENTO = ?, NOMBRE_DOCUMENTO = ? WHERE ID_TIPO_DOCUMENTO = ?";
            ps = cnn.prepareStatement(sql);
            int i = setTipoDocumento(ps, tipoDocumento, false);
            ps.setObject(i++, (tipoDocumento.getIdTipoDocumento() != null) ? tipoDocumento.getIdTipoDocumento() : null);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void eliminar(TipoDocumento tipoDocumento) throws SQLException {
        PreparedStatement ps = null;
        try {
            int i = 1;
            String sql = "DELETE FROM tipo_documento WHERE ID_TIPO_DOCUMENTO = ? AND NOMBRE_DOCUMENTO = ?";
            ps = cnn.prepareStatement(sql);
            setTipoDocumento(ps, tipoDocumento, false);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<TipoDocumento> listarTodos() throws SQLException {
        PreparedStatement ps = null;
        List<TipoDocumento> list = new ArrayList<TipoDocumento>();
        try {
            String sql = "SELECT ID_TIPO_DOCUMENTO, NOMBRE_DOCUMENTO FROM tipo_documento";
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getTipoDocumento(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public TipoDocumento buscarPorId(Integer id) throws SQLException {
        PreparedStatement ps = null;
        TipoDocumento obj = null;
        try {
            String sql = "SELECT ID_TIPO_DOCUMENTO, NOMBRE_DOCUMENTO FROM tipo_documento WHERE ID_TIPO_DOCUMENTO = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getTipoDocumento(rs);
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
            String sql = "DELETE FROM tipo_documento WHERE ID_TIPO_DOCUMENTO = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(i++, id);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<TipoDocumento> listaConsultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        List<TipoDocumento> list = new ArrayList<TipoDocumento>();
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getTipoDocumentoAdaptable(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public TipoDocumento consultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        TipoDocumento obj = null;
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getTipoDocumentoAdaptable(rs);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return obj;
    }

    public static TipoDocumento getTipoDocumento(ResultSet rs) throws SQLException {
        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setIdTipoDocumento(rs.getInt("ID_TIPO_DOCUMENTO"));
        tipoDocumento.setNombreDocumento(rs.getString("NOMBRE_DOCUMENTO"));

        return tipoDocumento;
    }

    public static TipoDocumento getTipoDocumentoAdaptable(ResultSet rs) throws SQLException {
        TipoDocumento tipoDocumento = new TipoDocumento();
        //ResultSetMetaData rsmd = rs.getMetaData();
        //int columns = rsmd.getColumnCount();
        //Para una próxima versión, mejorar el parseo del objeto en cuanto a rendimiento...
        //for (int i = 1; i <= columns; i++) {
        //System.out.println(rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
        tipoDocumento.setIdTipoDocumento(hasColumn(rs, "ID_TIPO_DOCUMENTO") ? rs.getInt("ID_TIPO_DOCUMENTO") : null);
        tipoDocumento.setNombreDocumento(hasColumn(rs, "NOMBRE_DOCUMENTO") ? rs.getString("NOMBRE_DOCUMENTO") : null);

        //}
        return tipoDocumento;
    }

    private Integer setTipoDocumento(PreparedStatement ps, TipoDocumento tipoDocumento, boolean flag) throws SQLException {
        int i = 1;        
        if (tipoDocumento.getIdTipoDocumento() != null) {
            ps.setObject(i++, (tipoDocumento.getIdTipoDocumento() != null) ? tipoDocumento.getIdTipoDocumento() : null);
        }
        ps.setString(i++, (tipoDocumento.getNombreDocumento() != null) ? tipoDocumento.getNombreDocumento() : null);
        return i;
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

    public Integer getLastId() throws SQLException {
        PreparedStatement ps = null;
        Integer id = 0;
        try {
            ps = cnn.prepareStatement("SELECT MAX(ID_TIPO_DOCUMENTO) AS id FROM tipo_documento");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return id;
    }

}
