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
import edu.poli.gerencia.votaciones.modelo.vo.TipoUsuario;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class TipoUsuarioCRUD implements IGenericoDAO<TipoUsuario> {

    protected Connection cnn;

    public TipoUsuarioCRUD(Connection cnn) {
        this.cnn = cnn;
    }

    @Override
    public void insertar(TipoUsuario tipoUsuario) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO tipo_usuario (ID_TIPO_USUARIO, DESCRIPCION, PUBLICO) VALUES (?,?,?)";
            ps = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setTipoUsuario(ps, tipoUsuario, true);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                tipoUsuario.setIdTipoUsuario(rs.getInt(1));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void actualizar(TipoUsuario tipoUsuario) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE tipo_usuario SET ID_TIPO_USUARIO = ?, DESCRIPCION = ?, PUBLICO = ? WHERE ID_TIPO_USUARIO = ?";
            ps = cnn.prepareStatement(sql);
            int i = setTipoUsuario(ps, tipoUsuario, false);
            ps.setObject(i++, (tipoUsuario.getIdTipoUsuario() != null) ? tipoUsuario.getIdTipoUsuario() : null);
            System.out.println(ps.toString());
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void eliminar(TipoUsuario tipoUsuario) throws SQLException {
        PreparedStatement ps = null;
        try {
            int i = 1;
            String sql = "DELETE FROM tipo_usuario WHERE ID_TIPO_USUARIO = ? AND DESCRIPCION = ? AND PUBLICO = ?";
            ps = cnn.prepareStatement(sql);
            setTipoUsuario(ps, tipoUsuario, false);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<TipoUsuario> listarTodos() throws SQLException {
        PreparedStatement ps = null;
        List<TipoUsuario> list = new ArrayList<TipoUsuario>();
        try {
            String sql = "SELECT ID_TIPO_USUARIO, DESCRIPCION, PUBLICO FROM tipo_usuario";
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getTipoUsuario(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public TipoUsuario buscarPorId(Integer id) throws SQLException {
        PreparedStatement ps = null;
        TipoUsuario obj = null;
        try {
            String sql = "SELECT ID_TIPO_USUARIO, DESCRIPCION, PUBLICO FROM tipo_usuario WHERE ID_TIPO_USUARIO = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getTipoUsuario(rs);
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
            String sql = "DELETE FROM tipo_usuario WHERE ID_TIPO_USUARIO = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(i++, id);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<TipoUsuario> listaConsultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        List<TipoUsuario> list = new ArrayList<TipoUsuario>();
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getTipoUsuarioAdaptable(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public TipoUsuario consultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        TipoUsuario obj = null;
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getTipoUsuarioAdaptable(rs);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return obj;
    }

    public Integer getLastId() throws SQLException {
        PreparedStatement ps = null;
        Integer id = 0;
        try {
            ps = cnn.prepareStatement("SELECT MAX(id_tipo_usuario) AS id FROM tipo_usuario");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return id;
    }

    public static TipoUsuario getTipoUsuario(ResultSet rs) throws SQLException {
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setIdTipoUsuario(rs.getInt("ID_TIPO_USUARIO"));
        tipoUsuario.setDescripcion(rs.getString("DESCRIPCION"));
        tipoUsuario.setPublico(rs.getBoolean("PUBLICO"));

        return tipoUsuario;
    }

    public static TipoUsuario getTipoUsuarioAdaptable(ResultSet rs) throws SQLException {
        TipoUsuario tipoUsuario = new TipoUsuario();
        //ResultSetMetaData rsmd = rs.getMetaData();
        //int columns = rsmd.getColumnCount();
        //Para una próxima versión, mejorar el parseo del objeto en cuanto a rendimiento...
        //for (int i = 1; i <= columns; i++) {
        //System.out.println(rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
        tipoUsuario.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        tipoUsuario.setDescripcion(hasColumn(rs, "DESCRIPCION") ? rs.getString("DESCRIPCION") : null);
        tipoUsuario.setPublico(hasColumn(rs, "PUBLICO") ? rs.getBoolean("PUBLICO") : null);

        //}
        return tipoUsuario;
    }

    private int setTipoUsuario(PreparedStatement ps, TipoUsuario tipoUsuario, boolean flag) throws SQLException {
        int i = 1;
        if (flag) {
            tipoUsuario.setIdTipoUsuario(getLastId() + 1);
        }
        if (tipoUsuario.getIdTipoUsuario() != null) {
            ps.setObject(i++, (tipoUsuario.getIdTipoUsuario() != null) ? tipoUsuario.getIdTipoUsuario() : null);
        }
        ps.setString(i++, (tipoUsuario.getDescripcion() != null) ? tipoUsuario.getDescripcion() : null);
        ps.setBoolean(i++, (tipoUsuario.getPublico() != null) ? tipoUsuario.getPublico() : null);
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

}
