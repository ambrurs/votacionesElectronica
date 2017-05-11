package edu.poli.gerencia.votaciones.modelo.dao;

import edu.poli.gerencia.votaciones.modelo.conexion.ConnectionDB;
import edu.poli.gerencia.votaciones.modelo.dao.crud.UsuarioCRUD;
import static edu.poli.gerencia.votaciones.modelo.dao.crud.UsuarioCRUD.getUsuario;
import edu.poli.gerencia.votaciones.modelo.vo.Persona;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsuarioDAO extends UsuarioCRUD {

    public UsuarioDAO(Connection cnn) {
        super(cnn);
    }

    public Persona existe(Persona persona) throws SQLException {
        PreparedStatement ps = null;
        Persona obj = null;
        int i = 1;
        try {
            String sql = "SELECT u.CONS_USUARIO, u.NOMBRE_USUARIO, u.ACTIVO, p.NUMERO_DOCUMENTO, p.PRIMER_NOMBRE, p.NOMBRE_EMPRESA, p.CORREO FROM usuario u INNER JOIN persona p ON u.CONS_USUARIO = p.CONS_USUARIO WHERE (u.NOMBRE_USUARIO = ? OR p.NUMERO_DOCUMENTO = ? OR p.CORREO = ?) OR (p.NOMBRE_EMPRESA = ?)";
            ps = cnn.prepareStatement(sql);
            ps.setString(i++, persona.getConsUsuario().getNombreUsuario());
            ps.setString(i++, persona.getNumeroDocumento());
            ps.setString(i++, persona.getCorreo());
            ps.setString(i++, persona.getNombreEmpresa());
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = new Persona();
                obj.setNombreEmpresa(rs.getString("NOMBRE_EMPRESA"));
                obj.setPrimerNombre(rs.getString("PRIMER_NOMBRE"));
                obj.setCorreo(rs.getString("CORREO"));
                obj.setNumeroDocumento(rs.getString("NUMERO_DOCUMENTO"));
                Usuario usuario = new Usuario();
                usuario.setConsUsuario(rs.getInt("CONS_USUARIO"));
                usuario.setNombreUsuario(rs.getString("NOMBRE_USUARIO"));
                usuario.setActivo(rs.getString("ACTIVO"));
                obj.setConsUsuario(usuario);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return obj;
    }

    public boolean comprobarToken(String token) throws SQLException {
        PreparedStatement ps = null;
        int i = 1;
        try {
            String sql = "SELECT * FROM tokens_accion WHERE TOKEN = ? AND UNIX_TIMESTAMP() - UNIX_TIMESTAMP(fecha_creacion) < 259200 AND estado = 1";
            ps = cnn.prepareStatement(sql);
            ps.setString(i++, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return false;
    }

    public Usuario iniciarSesion(String usuario, String clave) throws SQLException {
        PreparedStatement ps = null;
        int i = 1;
        Usuario obj = null;
        try {
            String sql = "SELECT CONS_USUARIO, ID_TIPO_USUARIO, NOMBRE_USUARIO, CONTRASENA, ACTIVO FROM usuario WHERE NOMBRE_USUARIO = ? AND CONTRASENA = ?";
            ps = cnn.prepareStatement(sql);
            ps.setString(i++, usuario);
            ps.setString(i++, clave);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getUsuarioAdaptable(rs);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return obj;
    }

    public void actualizarClave(Integer idUsuario, String claveNueva) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE usuario SET CONTRASENA = ? WHERE CONS_USUARIO = ?";
            if (cnn != null) {
                System.out.println("Hay conexión");
            }
            ps = cnn.prepareStatement(sql);
            ps.setString(1, claveNueva);
            ps.setInt(2, idUsuario);
            System.out.println(ps.toString());
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }

    }

}
