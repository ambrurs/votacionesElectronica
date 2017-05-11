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
import edu.poli.gerencia.votaciones.modelo.vo.TipoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class VotacionCRUD implements IGenericoDAO<Votacion> {

    protected Connection cnn;

    public VotacionCRUD(Connection cnn) {
        this.cnn = cnn;
    }

    @Override
    public void insertar(Votacion votacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            System.out.println(votacion);
            String sql = "INSERT INTO votacion (FECHA_INICIO_INSCRIPCION, FECHA_FIN_INSCRIPCION, ID_TIPO_VOTACION, FECHA_INICIO_VOTACION, FECHA_FIN_VOTACION, CONS_USUARIO_CREACION, ESTADO_VOTACION, CANTIDAD_VOTOS, CANTIDAD_CANDIDATOS) VALUES (?,?,?,?,?,?,?,?,?)";
            ps = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setVotacion(ps, votacion, true);
            System.out.println(ps.toString());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                votacion.setConsVotacion(rs.getInt(1));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void actualizar(Votacion votacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE votacion SET CONS_VOTACION = ?, FECHA_INICIO_INSCRIPCION = ?, FECHA_FIN_INSCRIPCION = ?, ID_TIPO_VOTACION = ?, FECHA_INICIO_VOTACION = ?, FECHA_FIN_VOTACION = ?, CONS_USUARIO_CREACION = ?, ESTADO_VOTACION = ?, CANTIDAD_VOTOS = ?, CANTIDAD_CANDIDATOS = ? WHERE CONS_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            setVotacion(ps, votacion, true);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void eliminar(Votacion votacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            int i = 1;
            String sql = "DELETE FROM votacion WHERE CONS_VOTACION = ? AND FECHA_INICIO_INSCRIPCION = ? AND FECHA_FIN_INSCRIPCION = ? AND ID_TIPO_VOTACION = ? AND FECHA_INICIO_VOTACION = ? AND FECHA_FIN_VOTACION = ? AND CONS_USUARIO_CREACION = ?";
            ps = cnn.prepareStatement(sql);
            setVotacion(ps, votacion, false);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<Votacion> listarTodos() throws SQLException {
        PreparedStatement ps = null;
        List<Votacion> list = new ArrayList<Votacion>();
        try {
            String sql = "SELECT CONS_VOTACION, FECHA_INICIO_INSCRIPCION, FECHA_FIN_INSCRIPCION, ID_TIPO_VOTACION, FECHA_INICIO_VOTACION, FECHA_FIN_VOTACION, CONS_USUARIO_CREACION FROM votacion";
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getVotacion(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public Votacion buscarPorId(Integer id) throws SQLException {
        PreparedStatement ps = null;
        Votacion obj = null;
        try {
            String sql = "SELECT CONS_VOTACION, FECHA_INICIO_INSCRIPCION, FECHA_FIN_INSCRIPCION, ID_TIPO_VOTACION, FECHA_INICIO_VOTACION, FECHA_FIN_VOTACION, CONS_USUARIO_CREACION, ESTADO_VOTACION, CANTIDAD_VOTOS, CANTIDAD_CANDIDATOS FROM votacion WHERE CONS_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getVotacion(rs);
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
            String sql = "DELETE FROM votacion WHERE CONS_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(i++, id);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<Votacion> listaConsultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        List<Votacion> list = new ArrayList<Votacion>();
        try {
            ps = cnn.prepareStatement(sql);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getVotacionAdaptable(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public Votacion consultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        Votacion obj = null;
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getVotacionAdaptable(rs);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return obj;
    }

    public static Votacion getVotacion(ResultSet rs) throws SQLException {
        Votacion votacion = new Votacion();
        votacion.setConsVotacion(rs.getInt("CONS_VOTACION"));
        votacion.setFechaInicioInscripcion(rs.getTimestamp("FECHA_INICIO_INSCRIPCION"));
        votacion.setFechaFinInscripcion(rs.getTimestamp("FECHA_FIN_INSCRIPCION"));
        TipoVotacion idTipoVotacion = new TipoVotacion();
        idTipoVotacion.setIdTipoVotacion(rs.getInt("ID_TIPO_VOTACION"));
        votacion.setIdTipoVotacion(idTipoVotacion);
        votacion.setFechaInicioVotacion(rs.getTimestamp("FECHA_INICIO_VOTACION"));
        votacion.setFechaFinVotacion(rs.getTimestamp("FECHA_FIN_VOTACION"));
        Usuario consUsuarioCreacion = new Usuario();
        consUsuarioCreacion.setConsUsuario(rs.getInt("CONS_USUARIO_CREACION"));
        votacion.setConsUsuarioCreacion(consUsuarioCreacion);
        votacion.setEstadoVotacion(rs.getString("ESTADO_VOTACION"));
        votacion.setCantidadCandidatos(rs.getInt("CANTIDAD_CANDIDATOS"));
        votacion.setCantidadVotos(rs.getInt("CANTIDAD_VOTOS"));

        return votacion;
    }

    public static Votacion getVotacionAdaptable(ResultSet rs) throws SQLException {
        Votacion votacion = new Votacion();
        //ResultSetMetaData rsmd = rs.getMetaData();
        //int columns = rsmd.getColumnCount();
        //Para una próxima versión, mejorar el parseo del objeto en cuanto a rendimiento...
        //for (int i = 1; i <= columns; i++) {
        //System.out.println(rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
        votacion.setConsVotacion(hasColumn(rs, "CONS_VOTACION") ? rs.getInt("CONS_VOTACION") : null);
        votacion.setFechaInicioInscripcion(hasColumn(rs, "FECHA_INICIO_INSCRIPCION") ? rs.getTimestamp("FECHA_INICIO_INSCRIPCION") : null);
        votacion.setFechaFinInscripcion(hasColumn(rs, "FECHA_FIN_INSCRIPCION") ? rs.getTimestamp("FECHA_FIN_INSCRIPCION") : null);
        TipoVotacion idTipoVotacion1 = new TipoVotacion();
        idTipoVotacion1.setIdTipoVotacion(hasColumn(rs, "ID_TIPO_VOTACION") ? rs.getInt("ID_TIPO_VOTACION") : null);
        idTipoVotacion1.setIdTipoVotacion(hasColumn(rs, "ID_TIPO_VOTACION") ? rs.getInt("ID_TIPO_VOTACION") : null);
        idTipoVotacion1.setNombreTipoVotacion(hasColumn(rs, "NOMBRE_TIPO_VOTACION") ? rs.getString("NOMBRE_TIPO_VOTACION") : null);
        votacion.setIdTipoVotacion(idTipoVotacion1);
        votacion.setFechaInicioVotacion(hasColumn(rs, "FECHA_INICIO_VOTACION") ? rs.getTimestamp("FECHA_INICIO_VOTACION") : null);
        votacion.setFechaFinVotacion(hasColumn(rs, "FECHA_FIN_VOTACION") ? rs.getTimestamp("FECHA_FIN_VOTACION") : null);
        Usuario consUsuarioCreacion2 = new Usuario();
        consUsuarioCreacion2.setConsUsuario(hasColumn(rs, "CONS_USUARIO_CREACION") ? rs.getInt("CONS_USUARIO_CREACION") : null);
        consUsuarioCreacion2.setConsUsuario(hasColumn(rs, "CONS_USUARIO") ? rs.getInt("CONS_USUARIO") : null);
        TipoUsuario idTipoUsuario3 = new TipoUsuario();
        idTipoUsuario3.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario3.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario3.setDescripcion(hasColumn(rs, "DESCRIPCION") ? rs.getString("DESCRIPCION") : null);
        idTipoUsuario3.setPublico(hasColumn(rs, "PUBLICO") ? rs.getBoolean("PUBLICO") : null);
        consUsuarioCreacion2.setIdTipoUsuario(idTipoUsuario3);
        consUsuarioCreacion2.setNombreUsuario(hasColumn(rs, "NOMBRE_USUARIO") ? rs.getString("NOMBRE_USUARIO") : null);
        consUsuarioCreacion2.setContrasena(hasColumn(rs, "CONTRASENA") ? rs.getString("CONTRASENA") : null);
        consUsuarioCreacion2.setActivo(hasColumn(rs, "ACTIVO") ? rs.getString("ACTIVO") : null);
        consUsuarioCreacion2.setUltimoIngreso(hasColumn(rs, "ULTIMO_INGRESO") ? rs.getDate("ULTIMO_INGRESO") : null);
        consUsuarioCreacion2.setFechaRegistro(hasColumn(rs, "FECHA_REGISTRO") ? rs.getDate("FECHA_REGISTRO") : null);
        consUsuarioCreacion2.setFechaActualizacion(hasColumn(rs, "FECHA_ACTUALIZACION") ? rs.getDate("FECHA_ACTUALIZACION") : null);
        votacion.setConsUsuarioCreacion(consUsuarioCreacion2);
        votacion.setEstadoVotacion(hasColumn(rs, "ESTADO_VOTACION") ? rs.getString("ESTADO_VOTACION") : null);
        votacion.setCantidadVotos(hasColumn(rs, "CANTIDAD_VOTOS") ? rs.getInt("CANTIDAD_VOTOS") : null);
        votacion.setCantidadCandidatos(hasColumn(rs, "CANTIDAD_CANDIDATOS") ? rs.getInt("CANTIDAD_CANDIDATOS") : null);

        //}
        return votacion;
    }

    private void setVotacion(PreparedStatement ps, Votacion votacion, boolean flag) throws SQLException {
        int i = 1;
        if (votacion.getConsVotacion() != null) {
            ps.setObject(i++, (votacion.getConsVotacion() != null) ? votacion.getConsVotacion() : null);
        }
        ps.setTimestamp(i++, (votacion.getFechaInicioInscripcion() != null) ? DateUtil.parseTimestamp(votacion.getFechaInicioInscripcion()) : null);
        ps.setTimestamp(i++, (votacion.getFechaFinInscripcion() != null) ? DateUtil.parseTimestamp(votacion.getFechaFinInscripcion()) : null);
        ps.setObject(i++, (votacion.getIdTipoVotacion().getIdTipoVotacion() != null) ? votacion.getIdTipoVotacion().getIdTipoVotacion() : null);
        ps.setTimestamp(i++, (votacion.getFechaInicioVotacion() != null) ? DateUtil.parseTimestamp(votacion.getFechaInicioVotacion()) : null);
        ps.setTimestamp(i++, (votacion.getFechaFinVotacion() != null) ? DateUtil.parseTimestamp(votacion.getFechaFinVotacion()) : null);
        ps.setObject(i++, (votacion.getConsUsuarioCreacion().getConsUsuario() != null) ? votacion.getConsUsuarioCreacion().getConsUsuario() : null);
        ps.setString(i++, (votacion.getEstadoVotacion() != null) ? votacion.getEstadoVotacion() : null);
        ps.setObject(i++, (votacion.getCantidadVotos() != null) ? votacion.getCantidadVotos() : null);
        ps.setObject(i++, (votacion.getCantidadCandidatos() != null) ? votacion.getCantidadCandidatos() : null);
        if (votacion.getConsVotacion() != null && flag) {
            ps.setObject(i++, votacion.getConsVotacion());
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
