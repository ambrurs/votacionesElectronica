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
import edu.poli.gerencia.votaciones.modelo.vo.VotacionUsuarioCandidato;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class VotacionUsuarioCandidatoCRUD implements IGenericoDAO<VotacionUsuarioCandidato> {

    protected Connection cnn;

    public VotacionUsuarioCandidatoCRUD(Connection cnn) {
        this.cnn = cnn;
    }

    @Override
    public void insertar(VotacionUsuarioCandidato votacionUsuarioCandidato) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO votacion_usuario_candidato (CANDIDATO_VOTACION_CONS_CANDIDATO, USUARIO_CONS_USUARIO) VALUES (?,?)";
            ps = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setVotacionUsuarioCandidato(ps, votacionUsuarioCandidato, true);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                votacionUsuarioCandidato.setConsUsuarioCandidato(rs.getInt(1));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void actualizar(VotacionUsuarioCandidato votacionUsuarioCandidato) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE votacion_usuario_candidato SET CONS_USUARIO_CANDIDATO = ?, CANDIDATO_VOTACION_CONS_CANDIDATO = ?, USUARIO_CONS_USUARIO = ? WHERE CONS_USUARIO_CANDIDATO = ?";
            ps = cnn.prepareStatement(sql);
            setVotacionUsuarioCandidato(ps, votacionUsuarioCandidato, true);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void eliminar(VotacionUsuarioCandidato votacionUsuarioCandidato) throws SQLException {
        PreparedStatement ps = null;
        try {
            int i = 1;
            String sql = "DELETE FROM votacion_usuario_candidato WHERE CONS_USUARIO_CANDIDATO = ? AND CANDIDATO_VOTACION_CONS_CANDIDATO = ? AND USUARIO_CONS_USUARIO = ?";
            ps = cnn.prepareStatement(sql);
            setVotacionUsuarioCandidato(ps, votacionUsuarioCandidato, false);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<VotacionUsuarioCandidato> listarTodos() throws SQLException {
        PreparedStatement ps = null;
        List<VotacionUsuarioCandidato> list = new ArrayList<VotacionUsuarioCandidato>();
        try {
            String sql = "SELECT CONS_USUARIO_CANDIDATO, CANDIDATO_VOTACION_CONS_CANDIDATO, USUARIO_CONS_USUARIO FROM votacion_usuario_candidato";
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getVotacionUsuarioCandidato(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public VotacionUsuarioCandidato buscarPorId(Integer id) throws SQLException {
        PreparedStatement ps = null;
        VotacionUsuarioCandidato obj = null;
        try {
            String sql = "SELECT CONS_USUARIO_CANDIDATO, CANDIDATO_VOTACION_CONS_CANDIDATO, USUARIO_CONS_USUARIO FROM votacion_usuario_candidato WHERE CONS_USUARIO_CANDIDATO = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getVotacionUsuarioCandidato(rs);
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
            String sql = "DELETE FROM votacion_usuario_candidato WHERE CONS_USUARIO_CANDIDATO = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(i++, id);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<VotacionUsuarioCandidato> listaConsultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        List<VotacionUsuarioCandidato> list = new ArrayList<VotacionUsuarioCandidato>();
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getVotacionUsuarioCandidatoAdaptable(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public VotacionUsuarioCandidato consultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        VotacionUsuarioCandidato obj = null;
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getVotacionUsuarioCandidatoAdaptable(rs);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return obj;
    }

    public static VotacionUsuarioCandidato getVotacionUsuarioCandidato(ResultSet rs) throws SQLException {
        VotacionUsuarioCandidato votacionUsuarioCandidato = new VotacionUsuarioCandidato();
        votacionUsuarioCandidato.setConsUsuarioCandidato(rs.getInt("CONS_USUARIO_CANDIDATO"));
        CandidatoVotacion candidatoVotacionConsCandidato = new CandidatoVotacion();
        candidatoVotacionConsCandidato.setConsCandidato(rs.getInt("CANDIDATO_VOTACION_CONS_CANDIDATO"));
        votacionUsuarioCandidato.setCandidatoVotacionConsCandidato(candidatoVotacionConsCandidato);
        Usuario usuarioConsUsuario = new Usuario();
        usuarioConsUsuario.setConsUsuario(rs.getInt("USUARIO_CONS_USUARIO"));
        votacionUsuarioCandidato.setUsuarioConsUsuario(usuarioConsUsuario);

        return votacionUsuarioCandidato;
    }

    public static VotacionUsuarioCandidato getVotacionUsuarioCandidatoAdaptable(ResultSet rs) throws SQLException {
        VotacionUsuarioCandidato votacionUsuarioCandidato = new VotacionUsuarioCandidato();
        //ResultSetMetaData rsmd = rs.getMetaData();
        //int columns = rsmd.getColumnCount();
        //Para una próxima versión, mejorar el parseo del objeto en cuanto a rendimiento...
        //for (int i = 1; i <= columns; i++) {
        //System.out.println(rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
        votacionUsuarioCandidato.setConsUsuarioCandidato(hasColumn(rs, "CONS_USUARIO_CANDIDATO") ? rs.getInt("CONS_USUARIO_CANDIDATO") : null);
        CandidatoVotacion candidatoVotacionConsCandidato1 = new CandidatoVotacion();
        candidatoVotacionConsCandidato1.setConsCandidato(hasColumn(rs, "CANDIDATO_VOTACION_CONS_CANDIDATO") ? rs.getInt("CANDIDATO_VOTACION_CONS_CANDIDATO") : null);
        candidatoVotacionConsCandidato1.setConsCandidato(hasColumn(rs, "CONS_CANDIDATO") ? rs.getInt("CONS_CANDIDATO") : null);
        Votacion votacionConsVotacion2 = new Votacion();
        votacionConsVotacion2.setConsVotacion(hasColumn(rs, "VOTACION_CONS_VOTACION") ? rs.getInt("VOTACION_CONS_VOTACION") : null);
        votacionConsVotacion2.setConsVotacion(hasColumn(rs, "CONS_VOTACION") ? rs.getInt("CONS_VOTACION") : null);
        votacionConsVotacion2.setFechaInicioInscripcion(hasColumn(rs, "FECHA_INICIO_INSCRIPCION") ? rs.getDate("FECHA_INICIO_INSCRIPCION") : null);
        votacionConsVotacion2.setFechaFinInscripcion(hasColumn(rs, "FECHA_FIN_INSCRIPCION") ? rs.getDate("FECHA_FIN_INSCRIPCION") : null);
        TipoVotacion idTipoVotacion3 = new TipoVotacion();
        idTipoVotacion3.setIdTipoVotacion(hasColumn(rs, "ID_TIPO_VOTACION") ? rs.getInt("ID_TIPO_VOTACION") : null);
        idTipoVotacion3.setIdTipoVotacion(hasColumn(rs, "ID_TIPO_VOTACION") ? rs.getInt("ID_TIPO_VOTACION") : null);
        idTipoVotacion3.setNombreTipoVotacion(hasColumn(rs, "NOMBRE_TIPO_VOTACION") ? rs.getString("NOMBRE_TIPO_VOTACION") : null);
        votacionConsVotacion2.setIdTipoVotacion(idTipoVotacion3);
        votacionConsVotacion2.setFechaInicioVotacion(hasColumn(rs, "FECHA_INICIO_VOTACION") ? rs.getDate("FECHA_INICIO_VOTACION") : null);
        votacionConsVotacion2.setFechaFinVotacion(hasColumn(rs, "FECHA_FIN_VOTACION") ? rs.getDate("FECHA_FIN_VOTACION") : null);
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
        votacionConsVotacion2.setConsUsuarioCreacion(consUsuarioCreacion4);
        candidatoVotacionConsCandidato1.setVotacionConsVotacion(votacionConsVotacion2);
        Usuario consUsuarioVotacion6 = new Usuario();
        consUsuarioVotacion6.setConsUsuario(hasColumn(rs, "CONS_USUARIO_VOTACION") ? rs.getInt("CONS_USUARIO_VOTACION") : null);
        consUsuarioVotacion6.setConsUsuario(hasColumn(rs, "CONS_USUARIO") ? rs.getInt("CONS_USUARIO") : null);
        TipoUsuario idTipoUsuario7 = new TipoUsuario();
        idTipoUsuario7.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario7.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario7.setDescripcion(hasColumn(rs, "DESCRIPCION") ? rs.getString("DESCRIPCION") : null);
        idTipoUsuario7.setPublico(hasColumn(rs, "PUBLICO") ? rs.getBoolean("PUBLICO") : null);
        consUsuarioVotacion6.setIdTipoUsuario(idTipoUsuario7);
        consUsuarioVotacion6.setNombreUsuario(hasColumn(rs, "NOMBRE_USUARIO") ? rs.getString("NOMBRE_USUARIO") : null);
        consUsuarioVotacion6.setContrasena(hasColumn(rs, "CONTRASENA") ? rs.getString("CONTRASENA") : null);
        consUsuarioVotacion6.setActivo(hasColumn(rs, "ACTIVO") ? rs.getString("ACTIVO") : null);
        consUsuarioVotacion6.setUltimoIngreso(hasColumn(rs, "ULTIMO_INGRESO") ? rs.getDate("ULTIMO_INGRESO") : null);
        consUsuarioVotacion6.setFechaRegistro(hasColumn(rs, "FECHA_REGISTRO") ? rs.getDate("FECHA_REGISTRO") : null);
        consUsuarioVotacion6.setFechaActualizacion(hasColumn(rs, "FECHA_ACTUALIZACION") ? rs.getDate("FECHA_ACTUALIZACION") : null);
        candidatoVotacionConsCandidato1.setConsUsuarioVotacion(consUsuarioVotacion6);
        candidatoVotacionConsCandidato1.setCantidadVotos(hasColumn(rs, "CANTIDAD_VOTOS") ? rs.getInt("CANTIDAD_VOTOS") : null);
        votacionUsuarioCandidato.setCandidatoVotacionConsCandidato(candidatoVotacionConsCandidato1);
        Usuario usuarioConsUsuario8 = new Usuario();
        usuarioConsUsuario8.setConsUsuario(hasColumn(rs, "USUARIO_CONS_USUARIO") ? rs.getInt("USUARIO_CONS_USUARIO") : null);
        usuarioConsUsuario8.setConsUsuario(hasColumn(rs, "CONS_USUARIO") ? rs.getInt("CONS_USUARIO") : null);
        TipoUsuario idTipoUsuario9 = new TipoUsuario();
        idTipoUsuario9.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario9.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario9.setDescripcion(hasColumn(rs, "DESCRIPCION") ? rs.getString("DESCRIPCION") : null);
        idTipoUsuario9.setPublico(hasColumn(rs, "PUBLICO") ? rs.getBoolean("PUBLICO") : null);
        usuarioConsUsuario8.setIdTipoUsuario(idTipoUsuario9);
        usuarioConsUsuario8.setNombreUsuario(hasColumn(rs, "NOMBRE_USUARIO") ? rs.getString("NOMBRE_USUARIO") : null);
        usuarioConsUsuario8.setContrasena(hasColumn(rs, "CONTRASENA") ? rs.getString("CONTRASENA") : null);
        usuarioConsUsuario8.setActivo(hasColumn(rs, "ACTIVO") ? rs.getString("ACTIVO") : null);
        usuarioConsUsuario8.setUltimoIngreso(hasColumn(rs, "ULTIMO_INGRESO") ? rs.getDate("ULTIMO_INGRESO") : null);
        usuarioConsUsuario8.setFechaRegistro(hasColumn(rs, "FECHA_REGISTRO") ? rs.getDate("FECHA_REGISTRO") : null);
        usuarioConsUsuario8.setFechaActualizacion(hasColumn(rs, "FECHA_ACTUALIZACION") ? rs.getDate("FECHA_ACTUALIZACION") : null);
        votacionUsuarioCandidato.setUsuarioConsUsuario(usuarioConsUsuario8);

        //}
        return votacionUsuarioCandidato;
    }

    private void setVotacionUsuarioCandidato(PreparedStatement ps, VotacionUsuarioCandidato votacionUsuarioCandidato, boolean flag) throws SQLException {
        int i = 1;
        if (votacionUsuarioCandidato.getConsUsuarioCandidato() != null) {
            ps.setObject(i++, (votacionUsuarioCandidato.getConsUsuarioCandidato() != null) ? votacionUsuarioCandidato.getConsUsuarioCandidato() : null);
        }
        ps.setObject(i++, (votacionUsuarioCandidato.getCandidatoVotacionConsCandidato().getConsCandidato() != null) ? votacionUsuarioCandidato.getCandidatoVotacionConsCandidato().getConsCandidato() : null);
        ps.setObject(i++, (votacionUsuarioCandidato.getUsuarioConsUsuario().getConsUsuario() != null) ? votacionUsuarioCandidato.getUsuarioConsUsuario().getConsUsuario() : null);

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
