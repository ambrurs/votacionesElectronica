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
import java.util.Date;
import edu.poli.gerencia.votaciones.modelo.vo.CandidatoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.TipoUsuario;
import edu.poli.gerencia.votaciones.modelo.vo.TipoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class CandidatoVotacionCRUD implements IGenericoDAO<CandidatoVotacion> {

    protected Connection cnn;

    public CandidatoVotacionCRUD(Connection cnn) {
        this.cnn = cnn;
    }

    @Override
    public void insertar(CandidatoVotacion candidatoVotacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO candidato_votacion (VOTACION_CONS_VOTACION, CONS_USUARIO_VOTACION, CANTIDAD_VOTOS) VALUES (?,?,?)";
            ps = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setCandidatoVotacion(ps, candidatoVotacion, true);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                candidatoVotacion.setConsCandidato(rs.getInt(1));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void actualizar(CandidatoVotacion candidatoVotacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE candidato_votacion SET CONS_CANDIDATO = ?, VOTACION_CONS_VOTACION = ?, CONS_USUARIO_VOTACION = ?, CANTIDAD_VOTOS = ? WHERE CONS_CANDIDATO = ?";
            ps = cnn.prepareStatement(sql);
            setCandidatoVotacion(ps, candidatoVotacion, true);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void eliminar(CandidatoVotacion candidatoVotacion) throws SQLException {
        PreparedStatement ps = null;
        try {
            int i = 1;
            String sql = "DELETE FROM candidato_votacion WHERE VOTACION_CONS_VOTACION = ? AND CONS_USUARIO_VOTACION = ?";
            ps = cnn.prepareStatement(sql);
            ps.setInt(i++, candidatoVotacion.getVotacionConsVotacion().getConsVotacion());
            ps.setInt(i++, candidatoVotacion.getConsUsuarioVotacion().getConsUsuario());
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<CandidatoVotacion> listarTodos() throws SQLException {
        PreparedStatement ps = null;
        List<CandidatoVotacion> list = new ArrayList<CandidatoVotacion>();
        try {
            String sql = "SELECT CONS_CANDIDATO, VOTACION_CONS_VOTACION, CONS_USUARIO_VOTACION, CANTIDAD_VOTOS FROM candidato_votacion";
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getCandidatoVotacion(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public CandidatoVotacion buscarPorId(Integer id) throws SQLException {
        PreparedStatement ps = null;
        CandidatoVotacion obj = null;
        try {
            String sql = "SELECT CONS_CANDIDATO, VOTACION_CONS_VOTACION, CONS_USUARIO_VOTACION, CANTIDAD_VOTOS FROM candidato_votacion WHERE CONS_CANDIDATO = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getCandidatoVotacion(rs);
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
            String sql = "DELETE FROM candidato_votacion WHERE CONS_CANDIDATO = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(i++, id);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<CandidatoVotacion> listaConsultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        List<CandidatoVotacion> list = new ArrayList<CandidatoVotacion>();
        try {
            ps = cnn.prepareStatement(sql);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getCandidatoVotacionAdaptable(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public CandidatoVotacion consultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        CandidatoVotacion obj = null;
        try {
            ps = cnn.prepareStatement(sql);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getCandidatoVotacionAdaptable(rs);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return obj;
    }

    public static CandidatoVotacion getCandidatoVotacion(ResultSet rs) throws SQLException {
        CandidatoVotacion candidatoVotacion = new CandidatoVotacion();
        candidatoVotacion.setConsCandidato(rs.getInt("CONS_CANDIDATO"));
        Votacion votacionConsVotacion = new Votacion();
        votacionConsVotacion.setConsVotacion(rs.getInt("VOTACION_CONS_VOTACION"));
        candidatoVotacion.setVotacionConsVotacion(votacionConsVotacion);
        Usuario consUsuarioVotacion = new Usuario();
        consUsuarioVotacion.setConsUsuario(rs.getInt("CONS_USUARIO_VOTACION"));
        candidatoVotacion.setConsUsuarioVotacion(consUsuarioVotacion);
        candidatoVotacion.setCantidadVotos(rs.getInt("CANTIDAD_VOTOS"));

        return candidatoVotacion;
    }

    public static CandidatoVotacion getCandidatoVotacionAdaptable(ResultSet rs) throws SQLException {
        CandidatoVotacion candidatoVotacion = new CandidatoVotacion();
        //ResultSetMetaData rsmd = rs.getMetaData();
        //int columns = rsmd.getColumnCount();
        //Para una próxima versión, mejorar el parseo del objeto en cuanto a rendimiento...
        //for (int i = 1; i <= columns; i++) {
        //System.out.println(rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
        candidatoVotacion.setConsCandidato(hasColumn(rs, "CONS_CANDIDATO") ? rs.getInt("CONS_CANDIDATO") : null);
        Votacion votacionConsVotacion1 = new Votacion();
        votacionConsVotacion1.setConsVotacion(hasColumn(rs, "CONS_VOTACION") ? rs.getInt("CONS_VOTACION") : null);
        votacionConsVotacion1.setConsVotacion(hasColumn(rs, "VOTACION_CONS_VOTACION") ? rs.getInt("VOTACION_CONS_VOTACION") : null);
        votacionConsVotacion1.setFechaInicioInscripcion(hasColumn(rs, "FECHA_INICIO_INSCRIPCION") ? rs.getDate("FECHA_INICIO_INSCRIPCION") : null);
        votacionConsVotacion1.setFechaFinInscripcion(hasColumn(rs, "FECHA_FIN_INSCRIPCION") ? rs.getDate("FECHA_FIN_INSCRIPCION") : null);
        TipoVotacion idTipoVotacion2 = new TipoVotacion();
        idTipoVotacion2.setIdTipoVotacion(hasColumn(rs, "ID_TIPO_VOTACION") ? rs.getInt("ID_TIPO_VOTACION") : null);
        idTipoVotacion2.setIdTipoVotacion(hasColumn(rs, "ID_TIPO_VOTACION") ? rs.getInt("ID_TIPO_VOTACION") : null);
        idTipoVotacion2.setNombreTipoVotacion(hasColumn(rs, "NOMBRE_TIPO_VOTACION") ? rs.getString("NOMBRE_TIPO_VOTACION") : null);
        votacionConsVotacion1.setIdTipoVotacion(idTipoVotacion2);
        votacionConsVotacion1.setFechaInicioVotacion(hasColumn(rs, "FECHA_INICIO_VOTACION") ? rs.getDate("FECHA_INICIO_VOTACION") : null);
        votacionConsVotacion1.setFechaFinVotacion(hasColumn(rs, "FECHA_FIN_VOTACION") ? rs.getDate("FECHA_FIN_VOTACION") : null);
        Usuario consUsuarioCreacion3 = new Usuario();
        consUsuarioCreacion3.setConsUsuario(hasColumn(rs, "CONS_USUARIO") ? rs.getInt("CONS_USUARIO") : null);
        consUsuarioCreacion3.setConsUsuario(hasColumn(rs, "CONS_USUARIO_CREACION") ? rs.getInt("CONS_USUARIO_CREACION") : null);
        TipoUsuario idTipoUsuario4 = new TipoUsuario();
        idTipoUsuario4.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario4.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario4.setDescripcion(hasColumn(rs, "DESCRIPCION") ? rs.getString("DESCRIPCION") : null);
        idTipoUsuario4.setPublico(hasColumn(rs, "PUBLICO") ? rs.getBoolean("PUBLICO") : null);
        consUsuarioCreacion3.setIdTipoUsuario(idTipoUsuario4);
        consUsuarioCreacion3.setNombreUsuario(hasColumn(rs, "NOMBRE_USUARIO") ? rs.getString("NOMBRE_USUARIO") : null);
        consUsuarioCreacion3.setContrasena(hasColumn(rs, "CONTRASENA") ? rs.getString("CONTRASENA") : null);
        consUsuarioCreacion3.setActivo(hasColumn(rs, "ACTIVO") ? rs.getString("ACTIVO") : null);
        consUsuarioCreacion3.setUltimoIngreso(hasColumn(rs, "ULTIMO_INGRESO") ? rs.getDate("ULTIMO_INGRESO") : null);
        consUsuarioCreacion3.setFechaRegistro(hasColumn(rs, "FECHA_REGISTRO") ? rs.getDate("FECHA_REGISTRO") : null);
        consUsuarioCreacion3.setFechaActualizacion(hasColumn(rs, "FECHA_ACTUALIZACION") ? rs.getDate("FECHA_ACTUALIZACION") : null);
        votacionConsVotacion1.setConsUsuarioCreacion(consUsuarioCreacion3);
        candidatoVotacion.setVotacionConsVotacion(votacionConsVotacion1);
        Usuario consUsuarioVotacion5 = new Usuario();
        consUsuarioVotacion5.setConsUsuario(hasColumn(rs, "CONS_USUARIO") ? rs.getInt("CONS_USUARIO") : null);
        consUsuarioVotacion5.setConsUsuario(hasColumn(rs, "CONS_USUARIO_VOTACION") ? rs.getInt("CONS_USUARIO_VOTACION") : null);
        TipoUsuario idTipoUsuario6 = new TipoUsuario();
        idTipoUsuario6.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario6.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario6.setDescripcion(hasColumn(rs, "DESCRIPCION") ? rs.getString("DESCRIPCION") : null);
        idTipoUsuario6.setPublico(hasColumn(rs, "PUBLICO") ? rs.getBoolean("PUBLICO") : null);
        consUsuarioVotacion5.setIdTipoUsuario(idTipoUsuario6);
        consUsuarioVotacion5.setNombreUsuario(hasColumn(rs, "NOMBRE_USUARIO") ? rs.getString("NOMBRE_USUARIO") : null);
        consUsuarioVotacion5.setContrasena(hasColumn(rs, "CONTRASENA") ? rs.getString("CONTRASENA") : null);
        consUsuarioVotacion5.setActivo(hasColumn(rs, "ACTIVO") ? rs.getString("ACTIVO") : null);
        consUsuarioVotacion5.setUltimoIngreso(hasColumn(rs, "ULTIMO_INGRESO") ? rs.getDate("ULTIMO_INGRESO") : null);
        consUsuarioVotacion5.setFechaRegistro(hasColumn(rs, "FECHA_REGISTRO") ? rs.getDate("FECHA_REGISTRO") : null);
        consUsuarioVotacion5.setFechaActualizacion(hasColumn(rs, "FECHA_ACTUALIZACION") ? rs.getDate("FECHA_ACTUALIZACION") : null);
        candidatoVotacion.setConsUsuarioVotacion(consUsuarioVotacion5);
        candidatoVotacion.setCantidadVotos(hasColumn(rs, "CANTIDAD_VOTOS") ? rs.getInt("CANTIDAD_VOTOS") : null);

        //}
        return candidatoVotacion;
    }

    private void setCandidatoVotacion(PreparedStatement ps, CandidatoVotacion candidatoVotacion, boolean flag) throws SQLException {
        int i = 1;
        if (candidatoVotacion.getConsCandidato() != null) {
            ps.setObject(i++, (candidatoVotacion.getConsCandidato() != null) ? candidatoVotacion.getConsCandidato() : null);
        }
        ps.setObject(i++, (candidatoVotacion.getVotacionConsVotacion().getConsVotacion() != null) ? candidatoVotacion.getVotacionConsVotacion().getConsVotacion() : null);
        ps.setObject(i++, (candidatoVotacion.getConsUsuarioVotacion().getConsUsuario() != null) ? candidatoVotacion.getConsUsuarioVotacion().getConsUsuario() : null);
        ps.setObject(i++, (candidatoVotacion.getCantidadVotos() != null) ? candidatoVotacion.getCantidadVotos() : null);
        if (candidatoVotacion.getConsCandidato() != null) {
            if (flag) {
                ps.setObject(i++, (candidatoVotacion.getConsCandidato() != null) ? candidatoVotacion.getConsCandidato() : null);
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
