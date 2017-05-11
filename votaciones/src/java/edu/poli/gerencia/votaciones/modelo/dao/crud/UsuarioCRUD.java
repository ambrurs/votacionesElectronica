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
import edu.poli.gerencia.votaciones.negocio.utiles.DateUtil;
import edu.poli.gerencia.votaciones.modelo.vo.TipoUsuario;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class UsuarioCRUD implements IGenericoDAO<Usuario> {

    protected Connection cnn;

    public UsuarioCRUD(Connection cnn) {
        this.cnn = cnn;
    }

    @Override
    public void insertar(Usuario usuario) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO usuario (ID_TIPO_USUARIO, NOMBRE_USUARIO, CONTRASENA, ACTIVO, ULTIMO_INGRESO, FECHA_REGISTRO, FECHA_ACTUALIZACION) VALUES (?,?,?,?,?,?,?)";
            ps = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setUsuario(ps, usuario, true);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                usuario.setConsUsuario(rs.getInt(1));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void actualizar(Usuario usuario) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE usuario SET CONS_USUARIO = ?, ID_TIPO_USUARIO = ?, NOMBRE_USUARIO = ?, CONTRASENA = ?, ACTIVO = ?, ULTIMO_INGRESO = ?, FECHA_REGISTRO = ?, FECHA_ACTUALIZACION = ? WHERE CONS_USUARIO = ?";
            ps = cnn.prepareStatement(sql);
            setUsuario(ps, usuario, true);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void eliminar(Usuario usuario) throws SQLException {
        PreparedStatement ps = null;
        try {
            int i = 1;
            String sql = "DELETE FROM usuario WHERE CONS_USUARIO = ? AND ID_TIPO_USUARIO = ? AND NOMBRE_USUARIO = ? AND CONTRASENA = ? AND ACTIVO = ? AND ULTIMO_INGRESO = ? AND FECHA_REGISTRO = ? AND FECHA_ACTUALIZACION = ?";
            ps = cnn.prepareStatement(sql);
            setUsuario(ps, usuario, false);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<Usuario> listarTodos() throws SQLException {
        PreparedStatement ps = null;
        List<Usuario> list = new ArrayList<Usuario>();
        try {
            String sql = "SELECT CONS_USUARIO, ID_TIPO_USUARIO, NOMBRE_USUARIO, CONTRASENA, ACTIVO, ULTIMO_INGRESO, FECHA_REGISTRO, FECHA_ACTUALIZACION FROM usuario";
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getUsuario(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public Usuario buscarPorId(Integer id) throws SQLException {
        PreparedStatement ps = null;
        Usuario obj = null;
        try {
            String sql = "SELECT CONS_USUARIO, ID_TIPO_USUARIO, NOMBRE_USUARIO, CONTRASENA, ACTIVO, ULTIMO_INGRESO, FECHA_REGISTRO, FECHA_ACTUALIZACION FROM usuario WHERE CONS_USUARIO = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getUsuario(rs);
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
            String sql = "DELETE FROM usuario WHERE CONS_USUARIO = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(i++, id);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<Usuario> listaConsultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        List<Usuario> list = new ArrayList<Usuario>();
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getUsuarioAdaptable(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public Usuario consultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        Usuario obj = null;
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getUsuarioAdaptable(rs);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return obj;
    }

    public static Usuario getUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setConsUsuario(rs.getInt("CONS_USUARIO"));
        TipoUsuario idTipoUsuario = new TipoUsuario();
        idTipoUsuario.setIdTipoUsuario(rs.getInt("ID_TIPO_USUARIO"));
        usuario.setIdTipoUsuario(idTipoUsuario);
        usuario.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
        usuario.setContrasena(rs.getString("CONTRASENA"));
        usuario.setActivo(rs.getString("ACTIVO"));
        usuario.setUltimoIngreso(rs.getTimestamp("ULTIMO_INGRESO"));
        usuario.setFechaRegistro(rs.getTimestamp("FECHA_REGISTRO"));
        usuario.setFechaActualizacion(rs.getTimestamp("FECHA_ACTUALIZACION"));

        return usuario;
    }

    public static Usuario getUsuarioAdaptable(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        //ResultSetMetaData rsmd = rs.getMetaData();
        //int columns = rsmd.getColumnCount();
        //Para una próxima versión, mejorar el parseo del objeto en cuanto a rendimiento...
        //for (int i = 1; i <= columns; i++) {
        //System.out.println(rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
        usuario.setConsUsuario(hasColumn(rs, "CONS_USUARIO") ? rs.getInt("CONS_USUARIO") : null);
        TipoUsuario idTipoUsuario1 = new TipoUsuario();
        idTipoUsuario1.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario1.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario1.setDescripcion(hasColumn(rs, "DESCRIPCION") ? rs.getString("DESCRIPCION") : null);
        idTipoUsuario1.setPublico(hasColumn(rs, "PUBLICO") ? rs.getBoolean("PUBLICO") : null);
        usuario.setIdTipoUsuario(idTipoUsuario1);
        usuario.setNombreUsuario(hasColumn(rs, "NOMBRE_USUARIO") ? rs.getString("NOMBRE_USUARIO") : null);
        usuario.setContrasena(hasColumn(rs, "CONTRASENA") ? rs.getString("CONTRASENA") : null);
        usuario.setActivo(hasColumn(rs, "ACTIVO") ? rs.getString("ACTIVO") : null);
        usuario.setUltimoIngreso(hasColumn(rs, "ULTIMO_INGRESO") ? rs.getTimestamp("ULTIMO_INGRESO") : null);
        usuario.setFechaRegistro(hasColumn(rs, "FECHA_REGISTRO") ? rs.getTimestamp("FECHA_REGISTRO") : null);
        usuario.setFechaActualizacion(hasColumn(rs, "FECHA_ACTUALIZACION") ? rs.getTimestamp("FECHA_ACTUALIZACION") : null);

        //}
        return usuario;
    }

    private void setUsuario(PreparedStatement ps, Usuario usuario, boolean flag) throws SQLException {
        int i = 1;
        if (usuario.getConsUsuario() != null) {
            ps.setObject(i++, (usuario.getConsUsuario() != null) ? usuario.getConsUsuario() : null);
        }
        ps.setObject(i++, (usuario.getIdTipoUsuario().getIdTipoUsuario() != null) ? usuario.getIdTipoUsuario().getIdTipoUsuario() : null);
        ps.setString(i++, (usuario.getNombreUsuario() != null) ? usuario.getNombreUsuario() : null);
        ps.setString(i++, (usuario.getContrasena() != null) ? usuario.getContrasena() : null);
        ps.setString(i++, (usuario.getActivo() != null) ? usuario.getActivo() : null);
        ps.setTimestamp(i++, (usuario.getUltimoIngreso() != null) ? DateUtil.parseTimestamp(usuario.getUltimoIngreso()) : null);
        ps.setTimestamp(i++, (usuario.getFechaRegistro() != null) ? DateUtil.parseTimestamp(usuario.getFechaRegistro()) : null);
        ps.setTimestamp(i++, (usuario.getFechaActualizacion() != null) ? DateUtil.parseTimestamp(usuario.getFechaActualizacion()) : null);
        if (usuario.getConsUsuario() != null) {
            if (flag) {
                ps.setObject(i++, (usuario.getConsUsuario() != null) ? usuario.getConsUsuario() : null);
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
