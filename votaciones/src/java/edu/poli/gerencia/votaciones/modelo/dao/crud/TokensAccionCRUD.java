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
import edu.poli.gerencia.votaciones.modelo.vo.TokensAccion;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class TokensAccionCRUD implements IGenericoDAO<TokensAccion> {

    protected Connection cnn;

    public TokensAccionCRUD(Connection cnn) {
        this.cnn = cnn;
    }

    @Override
    public void insertar(TokensAccion tokensAccion) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO tokens_accion (asunto, token, id_autor, fecha_creacion, actualizado, estado) VALUES (?,?,?,?,?,?)";
            ps = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setTokensAccion(ps, tokensAccion, true);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                tokensAccion.setId(rs.getInt(1));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void actualizar(TokensAccion tokensAccion) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE tokens_accion SET id = ?, asunto = ?, token = ?, id_autor = ?, fecha_creacion = ?, actualizado = ?, estado = ? WHERE id = ?";
            ps = cnn.prepareStatement(sql);
            setTokensAccion(ps, tokensAccion, true);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void eliminar(TokensAccion tokensAccion) throws SQLException {
        PreparedStatement ps = null;
        try {
            int i = 1;
            String sql = "DELETE FROM tokens_accion WHERE id = ? AND asunto = ? AND token = ? AND id_autor = ? AND fecha_creacion = ? AND actualizado = ? AND estado = ?";
            ps = cnn.prepareStatement(sql);
            setTokensAccion(ps, tokensAccion, false);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<TokensAccion> listarTodos() throws SQLException {
        PreparedStatement ps = null;
        List<TokensAccion> list = new ArrayList<TokensAccion>();
        try {
            String sql = "SELECT id, asunto, token, id_autor, fecha_creacion, actualizado, estado FROM tokens_accion";
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getTokensAccion(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public TokensAccion buscarPorId(Integer id) throws SQLException {
        PreparedStatement ps = null;
        TokensAccion obj = null;
        try {
            String sql = "SELECT id, asunto, token, id_autor, fecha_creacion, actualizado, estado FROM tokens_accion WHERE id = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getTokensAccion(rs);
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
            String sql = "DELETE FROM tokens_accion WHERE id = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(i++, id);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<TokensAccion> listaConsultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        List<TokensAccion> list = new ArrayList<TokensAccion>();
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getTokensAccionAdaptable(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public TokensAccion consultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        TokensAccion obj = null;
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getTokensAccionAdaptable(rs);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return obj;
    }

    public static TokensAccion getTokensAccion(ResultSet rs) throws SQLException {
        TokensAccion tokensAccion = new TokensAccion();
        tokensAccion.setId(rs.getInt("id"));
	tokensAccion.setAsunto(rs.getString("asunto"));
	tokensAccion.setToken(rs.getString("token"));
	tokensAccion.setIdAutor(rs.getInt("id_autor"));
	tokensAccion.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
	tokensAccion.setActualizado(rs.getTimestamp("actualizado"));
	tokensAccion.setEstado(rs.getBoolean("estado"));
        
        return tokensAccion;
    }

    public static TokensAccion getTokensAccionAdaptable(ResultSet rs) throws SQLException {
        TokensAccion tokensAccion = new TokensAccion();
        //ResultSetMetaData rsmd = rs.getMetaData();
        //int columns = rsmd.getColumnCount();
        //Para una próxima versión, mejorar el parseo del objeto en cuanto a rendimiento...
        //for (int i = 1; i <= columns; i++) {
        //System.out.println(rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
        tokensAccion.setId(hasColumn(rs, "id") ? rs.getInt("id") : null);
	tokensAccion.setAsunto(hasColumn(rs, "asunto") ? rs.getString("asunto") : null);
	tokensAccion.setToken(hasColumn(rs, "token") ? rs.getString("token") : null);
	tokensAccion.setIdAutor(hasColumn(rs, "id_autor") ? rs.getInt("id_autor") : null);
	tokensAccion.setFechaCreacion(hasColumn(rs, "fecha_creacion") ? rs.getTimestamp("fecha_creacion") : null);
	tokensAccion.setActualizado(hasColumn(rs, "actualizado") ? rs.getTimestamp("actualizado") : null);
	tokensAccion.setEstado(hasColumn(rs, "estado") ? rs.getBoolean("estado") : null);

        //}
        return tokensAccion;
    }

    private void setTokensAccion(PreparedStatement ps, TokensAccion tokensAccion, boolean flag) throws SQLException {
        int i = 1;       
        if(tokensAccion.getId() != null) {ps.setObject(i++, (tokensAccion.getId() != null) ? tokensAccion.getId() : null);}
ps.setString(i++, (tokensAccion.getAsunto() != null) ? tokensAccion.getAsunto() : null);
	ps.setString(i++, (tokensAccion.getToken() != null) ? tokensAccion.getToken() : null);
	ps.setObject(i++, (tokensAccion.getIdAutor() != null) ? tokensAccion.getIdAutor() : null);
	ps.setTimestamp(i++, (tokensAccion.getFechaCreacion() != null) ? DateUtil.parseTimestamp(tokensAccion.getFechaCreacion()) : null);
	ps.setTimestamp(i++, (tokensAccion.getActualizado() != null) ? DateUtil.parseTimestamp(tokensAccion.getActualizado()) : null);
	ps.setBoolean(i++, (tokensAccion.getEstado() != null) ? tokensAccion.getEstado() : null);
	if(tokensAccion.getId() != null) { if (flag) {ps.setObject(i++, (tokensAccion.getId() != null) ? tokensAccion.getId() : null);}}

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
