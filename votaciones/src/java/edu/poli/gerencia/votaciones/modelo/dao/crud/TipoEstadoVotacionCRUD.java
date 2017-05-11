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
import edu.poli.gerencia.votaciones.modelo.vo.EstadoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.TipoEstadoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class TipoEstadoVotacionCRUD implements IGenericoDAO<TipoEstadoVotacion> {

    protected Connection cnn;

    public TipoEstadoVotacionCRUD(Connection cnn) {
        this.cnn = cnn;
    }

    @Override
    public void insertar(TipoEstadoVotacion tipoEstadoVotacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO tipo_estado_votacion (NOMBRE_ESTADO) VALUES (?)";
            ps = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setTipoEstadoVotacion(ps, tipoEstadoVotacion, true);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                tipoEstadoVotacion.setIdTipoEstadoVotacion(rs.getInt(1));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void actualizar(TipoEstadoVotacion tipoEstadoVotacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE tipo_estado_votacion SET ID_TIPO_ESTADO_VOTACION = ?, NOMBRE_ESTADO = ? WHERE ID_TIPO_ESTADO_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            setTipoEstadoVotacion(ps, tipoEstadoVotacion, true);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void eliminar(TipoEstadoVotacion tipoEstadoVotacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            int i = 1;
            String sql = "DELETE FROM tipo_estado_votacion WHERE ID_TIPO_ESTADO_VOTACION = ? AND NOMBRE_ESTADO = ?";
            ps = cnn.prepareStatement(sql);
            setTipoEstadoVotacion(ps, tipoEstadoVotacion, false);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<TipoEstadoVotacion> listarTodos() throws SQLException {
        PreparedStatement ps = null;
        List<TipoEstadoVotacion> list = new ArrayList<TipoEstadoVotacion>();
        try {
            String sql = "SELECT ID_TIPO_ESTADO_VOTACION, NOMBRE_ESTADO FROM tipo_estado_votacion";
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getTipoEstadoVotacion(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public TipoEstadoVotacion buscarPorId(Integer id) throws SQLException {
        PreparedStatement ps = null;
        TipoEstadoVotacion obj = null;
        try {
            String sql = "SELECT ID_TIPO_ESTADO_VOTACION, NOMBRE_ESTADO FROM tipo_estado_votacion WHERE ID_TIPO_ESTADO_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getTipoEstadoVotacion(rs);
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
            String sql = "DELETE FROM tipo_estado_votacion WHERE ID_TIPO_ESTADO_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(i++, id);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<TipoEstadoVotacion> listaConsultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        List<TipoEstadoVotacion> list = new ArrayList<TipoEstadoVotacion>();
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getTipoEstadoVotacionAdaptable(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public TipoEstadoVotacion consultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        TipoEstadoVotacion obj = null;
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getTipoEstadoVotacionAdaptable(rs);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return obj;
    }

    public static TipoEstadoVotacion getTipoEstadoVotacion(ResultSet rs) throws SQLException {
        TipoEstadoVotacion tipoEstadoVotacion = new TipoEstadoVotacion();
        tipoEstadoVotacion.setIdTipoEstadoVotacion(rs.getInt("ID_TIPO_ESTADO_VOTACION"));
	tipoEstadoVotacion.setNombreEstado(rs.getString("NOMBRE_ESTADO"));
        
        return tipoEstadoVotacion;
    }

    public static TipoEstadoVotacion getTipoEstadoVotacionAdaptable(ResultSet rs) throws SQLException {
        TipoEstadoVotacion tipoEstadoVotacion = new TipoEstadoVotacion();
        //ResultSetMetaData rsmd = rs.getMetaData();
        //int columns = rsmd.getColumnCount();
        //Para una próxima versión, mejorar el parseo del objeto en cuanto a rendimiento...
        //for (int i = 1; i <= columns; i++) {
        //System.out.println(rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
        tipoEstadoVotacion.setIdTipoEstadoVotacion(hasColumn(rs, "ID_TIPO_ESTADO_VOTACION") ? rs.getInt("ID_TIPO_ESTADO_VOTACION") : null);
	tipoEstadoVotacion.setNombreEstado(hasColumn(rs, "NOMBRE_ESTADO") ? rs.getString("NOMBRE_ESTADO") : null);

        //}
        return tipoEstadoVotacion;
    }

    private void setTipoEstadoVotacion(PreparedStatement ps, TipoEstadoVotacion tipoEstadoVotacion, boolean flag) throws SQLException {
        int i = 1;       
        if(tipoEstadoVotacion.getIdTipoEstadoVotacion() != null) {ps.setObject(i++, (tipoEstadoVotacion.getIdTipoEstadoVotacion() != null) ? tipoEstadoVotacion.getIdTipoEstadoVotacion() : null);}
ps.setString(i++, (tipoEstadoVotacion.getNombreEstado() != null) ? tipoEstadoVotacion.getNombreEstado() : null);
	if(tipoEstadoVotacion.getIdTipoEstadoVotacion() != null) { if (flag) {ps.setObject(i++, (tipoEstadoVotacion.getIdTipoEstadoVotacion() != null) ? tipoEstadoVotacion.getIdTipoEstadoVotacion() : null);}}

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
