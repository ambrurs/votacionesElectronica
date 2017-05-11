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
import edu.poli.gerencia.votaciones.modelo.vo.EstadoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.TipoEstadoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.TipoUsuario;
import edu.poli.gerencia.votaciones.modelo.vo.TipoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class EstadoVotacionCRUD implements IGenericoDAO<EstadoVotacion> {

    protected Connection cnn;

    public EstadoVotacionCRUD(Connection cnn) {
        this.cnn = cnn;
    }

    @Override
    public void insertar(EstadoVotacion estadoVotacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO estado_votacion (CONS_ESTADO_VOTACION, ACTIVO, FECHA_REGISTRO, ID_TIPO_ESTADO_VOTACION, CONS_VOTACION) VALUES (?,?,?,?,?)";
            ps = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setEstadoVotacion(ps, estadoVotacion, true);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                estadoVotacion.setConsEstadoVotacion(rs.getInt(1));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void actualizar(EstadoVotacion estadoVotacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE estado_votacion SET CONS_ESTADO_VOTACION = ?, ACTIVO = ?, FECHA_REGISTRO = ?, ID_TIPO_ESTADO_VOTACION = ?, CONS_VOTACION = ? WHERE CONS_ESTADO_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            setEstadoVotacion(ps, estadoVotacion, true);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void eliminar(EstadoVotacion estadoVotacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            int i = 1;
            String sql = "DELETE FROM estado_votacion WHERE CONS_ESTADO_VOTACION = ? AND ACTIVO = ? AND FECHA_REGISTRO = ? AND ID_TIPO_ESTADO_VOTACION = ? AND CONS_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            setEstadoVotacion(ps, estadoVotacion, false);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<EstadoVotacion> listarTodos() throws SQLException {
        PreparedStatement ps = null;
        List<EstadoVotacion> list = new ArrayList<EstadoVotacion>();
        try {
            String sql = "SELECT CONS_ESTADO_VOTACION, ACTIVO, FECHA_REGISTRO, ID_TIPO_ESTADO_VOTACION, CONS_VOTACION FROM estado_votacion";
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getEstadoVotacion(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public EstadoVotacion buscarPorId(Integer id) throws SQLException {
        PreparedStatement ps = null;
        EstadoVotacion obj = null;
        try {
            String sql = "SELECT CONS_ESTADO_VOTACION, ACTIVO, FECHA_REGISTRO, ID_TIPO_ESTADO_VOTACION, CONS_VOTACION FROM estado_votacion WHERE CONS_ESTADO_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getEstadoVotacion(rs);
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
            String sql = "DELETE FROM estado_votacion WHERE CONS_ESTADO_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(i++, id);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<EstadoVotacion> listaConsultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        List<EstadoVotacion> list = new ArrayList<EstadoVotacion>();
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getEstadoVotacionAdaptable(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public EstadoVotacion consultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        EstadoVotacion obj = null;
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getEstadoVotacionAdaptable(rs);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return obj;
    }

    public static EstadoVotacion getEstadoVotacion(ResultSet rs) throws SQLException {
        EstadoVotacion estadoVotacion = new EstadoVotacion();
        estadoVotacion.setConsEstadoVotacion(rs.getInt("CONS_ESTADO_VOTACION"));
        estadoVotacion.setActivo(rs.getString("ACTIVO"));
        estadoVotacion.setFechaRegistro(rs.getTimestamp("FECHA_REGISTRO"));
        TipoEstadoVotacion idTipoEstadoVotacion = new TipoEstadoVotacion();
        idTipoEstadoVotacion.setIdTipoEstadoVotacion(rs.getInt("ID_TIPO_ESTADO_VOTACION"));
        estadoVotacion.setIdTipoEstadoVotacion(idTipoEstadoVotacion);
        Votacion consVotacion = new Votacion();
        consVotacion.setConsVotacion(rs.getInt("CONS_VOTACION"));
        estadoVotacion.setConsVotacion(consVotacion);

        return estadoVotacion;
    }

    public static EstadoVotacion getEstadoVotacionAdaptable(ResultSet rs) throws SQLException {
        EstadoVotacion estadoVotacion = new EstadoVotacion();
        //ResultSetMetaData rsmd = rs.getMetaData();
        //int columns = rsmd.getColumnCount();
        //Para una próxima versión, mejorar el parseo del objeto en cuanto a rendimiento...
        //for (int i = 1; i <= columns; i++) {
        //System.out.println(rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
        estadoVotacion.setConsEstadoVotacion(hasColumn(rs, "CONS_ESTADO_VOTACION") ? rs.getInt("CONS_ESTADO_VOTACION") : null);
        estadoVotacion.setActivo(hasColumn(rs, "ACTIVO") ? rs.getString("ACTIVO") : null);
        estadoVotacion.setFechaRegistro(hasColumn(rs, "FECHA_REGISTRO") ? rs.getTimestamp("FECHA_REGISTRO") : null);
        TipoEstadoVotacion idTipoEstadoVotacion1 = new TipoEstadoVotacion();
        idTipoEstadoVotacion1.setIdTipoEstadoVotacion(hasColumn(rs, "ID_TIPO_ESTADO_VOTACION") ? rs.getInt("ID_TIPO_ESTADO_VOTACION") : null);
        idTipoEstadoVotacion1.setIdTipoEstadoVotacion(hasColumn(rs, "ID_TIPO_ESTADO_VOTACION") ? rs.getInt("ID_TIPO_ESTADO_VOTACION") : null);
        idTipoEstadoVotacion1.setNombreEstado(hasColumn(rs, "NOMBRE_ESTADO") ? rs.getString("NOMBRE_ESTADO") : null);
        estadoVotacion.setIdTipoEstadoVotacion(idTipoEstadoVotacion1);
        Votacion consVotacion2 = new Votacion();
        consVotacion2.setConsVotacion(hasColumn(rs, "CONS_VOTACION") ? rs.getInt("CONS_VOTACION") : null);
        consVotacion2.setConsVotacion(hasColumn(rs, "CONS_VOTACION") ? rs.getInt("CONS_VOTACION") : null);
        consVotacion2.setFechaInicioInscripcion(hasColumn(rs, "FECHA_INICIO_INSCRIPCION") ? rs.getDate("FECHA_INICIO_INSCRIPCION") : null);
        consVotacion2.setFechaFinInscripcion(hasColumn(rs, "FECHA_FIN_INSCRIPCION") ? rs.getDate("FECHA_FIN_INSCRIPCION") : null);
        TipoVotacion idTipoVotacion3 = new TipoVotacion();
        idTipoVotacion3.setIdTipoVotacion(hasColumn(rs, "ID_TIPO_VOTACION") ? rs.getInt("ID_TIPO_VOTACION") : null);
        idTipoVotacion3.setIdTipoVotacion(hasColumn(rs, "ID_TIPO_VOTACION") ? rs.getInt("ID_TIPO_VOTACION") : null);
        idTipoVotacion3.setNombreTipoVotacion(hasColumn(rs, "NOMBRE_TIPO_VOTACION") ? rs.getString("NOMBRE_TIPO_VOTACION") : null);
        consVotacion2.setIdTipoVotacion(idTipoVotacion3);
        consVotacion2.setFechaInicioVotacion(hasColumn(rs, "FECHA_INICIO_VOTACION") ? rs.getDate("FECHA_INICIO_VOTACION") : null);
        consVotacion2.setFechaFinVotacion(hasColumn(rs, "FECHA_FIN_VOTACION") ? rs.getDate("FECHA_FIN_VOTACION") : null);
        Usuario consUsuarioCreacion4 = new Usuario();
        consUsuarioCreacion4.setConsUsuario(hasColumn(rs, "CONS_USUARIO_CREACION") ? rs.getInt("CONS_USUARIO_CREACION") : null);
        consUsuarioCreacion4.setConsUsuario(hasColumn(rs, "CONS_USUARIO") ? rs.getInt("CONS_USUARIO") : null);
        TipoUsuario idTipoUsuario5 = new TipoUsuario();
        idTipoUsuario5.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario5.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario5.setDescripcion(hasColumn(rs, "DESCRIPCION") ? rs.getString("DESCRIPCION") : null);
        idTipoUsuario5.setPublico(hasColumn(rs, "PUBLICO") ? rs.getBoolean("PUBLICO") : null);
        consUsuarioCreacion4.setIdTipoUsuario(idTipoUsuario5);
        consUsuarioCreacion4.setNombreUsuario(hasColumn(rs, "NOMBRE_USUARIO") ? rs.getString("NOMBRE_USUARIO") : null);
        consUsuarioCreacion4.setContrasena(hasColumn(rs, "CONTRASENA") ? rs.getString("CONTRASENA") : null);
        consUsuarioCreacion4.setActivo(hasColumn(rs, "ACTIVO") ? rs.getString("ACTIVO") : null);
        consUsuarioCreacion4.setUltimoIngreso(hasColumn(rs, "ULTIMO_INGRESO") ? rs.getDate("ULTIMO_INGRESO") : null);
        consUsuarioCreacion4.setFechaRegistro(hasColumn(rs, "FECHA_REGISTRO") ? rs.getDate("FECHA_REGISTRO") : null);
        consUsuarioCreacion4.setFechaActualizacion(hasColumn(rs, "FECHA_ACTUALIZACION") ? rs.getDate("FECHA_ACTUALIZACION") : null);
        consVotacion2.setConsUsuarioCreacion(consUsuarioCreacion4);
        estadoVotacion.setConsVotacion(consVotacion2);

        //}
        return estadoVotacion;
    }

    private void setEstadoVotacion(PreparedStatement ps, EstadoVotacion estadoVotacion, boolean flag) throws SQLException {
        int i = 1;
        if (estadoVotacion.getConsEstadoVotacion() != null) {
            ps.setObject(i++, (estadoVotacion.getConsEstadoVotacion() != null) ? estadoVotacion.getConsEstadoVotacion() : null);
        }
        ps.setString(i++, (estadoVotacion.getActivo() != null) ? estadoVotacion.getActivo() : null);
        ps.setTimestamp(i++, (estadoVotacion.getFechaRegistro() != null) ? DateUtil.parseTimestamp(estadoVotacion.getFechaRegistro()) : null);
        ps.setObject(i++, (estadoVotacion.getIdTipoEstadoVotacion().getIdTipoEstadoVotacion() != null) ? estadoVotacion.getIdTipoEstadoVotacion().getIdTipoEstadoVotacion() : null);
        ps.setObject(i++, (estadoVotacion.getConsVotacion().getConsVotacion() != null) ? estadoVotacion.getConsVotacion().getConsVotacion() : null);

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
