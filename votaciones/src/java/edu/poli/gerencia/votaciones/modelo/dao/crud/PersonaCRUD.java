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
import edu.poli.gerencia.votaciones.modelo.vo.Persona;
import edu.poli.gerencia.votaciones.modelo.vo.TipoDocumento;
import edu.poli.gerencia.votaciones.modelo.vo.TipoUsuario;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class PersonaCRUD implements IGenericoDAO<Persona> {

    protected Connection cnn;

    public PersonaCRUD(Connection cnn) {
        this.cnn = cnn;
    }

    @Override
    public void insertar(Persona persona) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO persona (CONS_USUARIO, ID_TIPO_DOCUMENTO, CONS_PERSONA_ASOCIADA, IMAGEN_PERFIL, SEGUNDO_APELLIDO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, NOMBRE_EMPRESA, CORREO, NUMERO_DOCUMENTO) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            ps = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setPersona(ps, persona, true);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                persona.setConsPersona(rs.getInt(1));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void actualizar(Persona persona) throws SQLException {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE persona SET CONS_PERSONA = ?, CONS_USUARIO = ?, ID_TIPO_DOCUMENTO = ?, CONS_PERSONA_ASOCIADA = ?, IMAGEN_PERFIL = ?, SEGUNDO_APELLIDO = ?, PRIMER_NOMBRE = ?, SEGUNDO_NOMBRE = ?, PRIMER_APELLIDO = ?, NOMBRE_EMPRESA = ?, CORREO = ?, NUMERO_DOCUMENTO = ? WHERE CONS_PERSONA = ?";
            ps = cnn.prepareStatement(sql);
            setPersona(ps, persona, true);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public void eliminar(Persona persona) throws SQLException {
        PreparedStatement ps = null;
        try {
            int i = 1;
            String sql = "DELETE FROM persona WHERE CONS_PERSONA = ? AND CONS_USUARIO = ? AND ID_TIPO_DOCUMENTO = ? AND CONS_PERSONA_ASOCIADA = ? AND IMAGEN_PERFIL = ? AND SEGUNDO_APELLIDO = ? AND PRIMER_NOMBRE = ? AND SEGUNDO_NOMBRE = ? AND PRIMER_APELLIDO = ? AND NOMBRE_EMPRESA = ? AND CORREO = ? AND NUMERO_DOCUMENTO = ?";
            ps = cnn.prepareStatement(sql);
            setPersona(ps, persona, false);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<Persona> listarTodos() throws SQLException {
        PreparedStatement ps = null;
        List<Persona> list = new ArrayList<Persona>();
        try {
            String sql = "SELECT CONS_PERSONA, CONS_USUARIO, ID_TIPO_DOCUMENTO, CONS_PERSONA_ASOCIADA, IMAGEN_PERFIL, SEGUNDO_APELLIDO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, NOMBRE_EMPRESA, CORREO, NUMERO_DOCUMENTO FROM persona";
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getPersona(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public Persona buscarPorId(Integer id) throws SQLException {
        PreparedStatement ps = null;
        Persona obj = null;
        try {
            String sql = "SELECT CONS_PERSONA, CONS_USUARIO, ID_TIPO_DOCUMENTO, CONS_PERSONA_ASOCIADA, IMAGEN_PERFIL, SEGUNDO_APELLIDO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, NOMBRE_EMPRESA, CORREO, NUMERO_DOCUMENTO FROM persona WHERE CONS_PERSONA = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getPersona(rs);
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
            String sql = "DELETE FROM persona WHERE CONS_PERSONA = ?";
            ps = cnn.prepareStatement(sql);
            ps.setObject(i++, id);
            ps.executeUpdate();
        } finally {
            ConnectionDB.desconectar(ps);
        }
    }

    @Override
    public List<Persona> listaConsultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        List<Persona> list = new ArrayList<Persona>();
        try {
            ps = cnn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println(ps.toString());
            while (rs.next()) {
                list.add(getPersonaAdaptable(rs));
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }

    @Override
    public Persona consultar(String sql) throws SQLException {
        PreparedStatement ps = null;
        Persona obj = null;
        try {
            ps = cnn.prepareStatement(sql);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = getPersonaAdaptable(rs);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return obj;
    }

    public static Persona getPersona(ResultSet rs) throws SQLException {
        Persona persona = new Persona();
        persona.setConsPersona(rs.getInt("CONS_PERSONA"));
        Usuario consUsuario = new Usuario();
        consUsuario.setConsUsuario(rs.getInt("CONS_USUARIO"));
        persona.setConsUsuario(consUsuario);
        TipoDocumento idTipoDocumento = new TipoDocumento();
        idTipoDocumento.setIdTipoDocumento(rs.getInt("ID_TIPO_DOCUMENTO"));
        persona.setIdTipoDocumento(idTipoDocumento);
        persona.setConsPersonaAsociada(rs.getInt("CONS_PERSONA_ASOCIADA"));
        persona.setImagenPerfil(rs.getString("IMAGEN_PERFIL"));
        persona.setSegundoApellido(rs.getString("SEGUNDO_APELLIDO"));
        persona.setPrimerNombre(rs.getString("PRIMER_NOMBRE"));
        persona.setSegundoNombre(rs.getString("SEGUNDO_NOMBRE"));
        persona.setPrimerApellido(rs.getString("PRIMER_APELLIDO"));
        persona.setNombreEmpresa(rs.getString("NOMBRE_EMPRESA"));
        persona.setCorreo(rs.getString("CORREO"));
        persona.setNumeroDocumento(rs.getString("NUMERO_DOCUMENTO"));

        return persona;
    }

    public static Persona getPersonaAdaptable(ResultSet rs) throws SQLException {
        Persona persona = new Persona();
        //ResultSetMetaData rsmd = rs.getMetaData();
        //int columns = rsmd.getColumnCount();
        //Para una próxima versión, mejorar el parseo del objeto en cuanto a rendimiento...
        //for (int i = 1; i <= columns; i++) {
        //System.out.println(rsmd.getTableName(i) + "." + rsmd.getColumnName(i));
        persona.setConsPersona(hasColumn(rs, "CONS_PERSONA") ? rs.getInt("CONS_PERSONA") : null);
        Usuario consUsuario1 = new Usuario();
        consUsuario1.setConsUsuario(hasColumn(rs, "CONS_USUARIO") ? rs.getInt("CONS_USUARIO") : null);
        TipoUsuario idTipoUsuario2 = new TipoUsuario();
        idTipoUsuario2.setIdTipoUsuario(hasColumn(rs, "ID_TIPO_USUARIO") ? rs.getInt("ID_TIPO_USUARIO") : null);
        idTipoUsuario2.setDescripcion(hasColumn(rs, "DESCRIPCION") ? rs.getString("DESCRIPCION") : null);
        idTipoUsuario2.setPublico(hasColumn(rs, "PUBLICO") ? rs.getBoolean("PUBLICO") : null);
        consUsuario1.setIdTipoUsuario(idTipoUsuario2);
        consUsuario1.setNombreUsuario(hasColumn(rs, "NOMBRE_USUARIO") ? rs.getString("NOMBRE_USUARIO") : null);
        consUsuario1.setContrasena(hasColumn(rs, "CONTRASENA") ? rs.getString("CONTRASENA") : null);
        consUsuario1.setActivo(hasColumn(rs, "ACTIVO") ? rs.getString("ACTIVO") : null);
        consUsuario1.setUltimoIngreso(hasColumn(rs, "ULTIMO_INGRESO") ? rs.getDate("ULTIMO_INGRESO") : null);
        consUsuario1.setFechaRegistro(hasColumn(rs, "FECHA_REGISTRO") ? rs.getDate("FECHA_REGISTRO") : null);
        System.out.println("FECHA REGISTRO: "+consUsuario1.getFechaRegistro());
        consUsuario1.setFechaActualizacion(hasColumn(rs, "FECHA_ACTUALIZACION") ? rs.getDate("FECHA_ACTUALIZACION") : null);
        persona.setConsUsuario(consUsuario1);
        TipoDocumento idTipoDocumento3 = new TipoDocumento();
        idTipoDocumento3.setIdTipoDocumento(hasColumn(rs, "ID_TIPO_DOCUMENTO") ? rs.getInt("ID_TIPO_DOCUMENTO") : null);
        idTipoDocumento3.setIdTipoDocumento(hasColumn(rs, "ID_TIPO_DOCUMENTO") ? rs.getInt("ID_TIPO_DOCUMENTO") : null);
        idTipoDocumento3.setNombreDocumento(hasColumn(rs, "NOMBRE_DOCUMENTO") ? rs.getString("NOMBRE_DOCUMENTO") : null);
        persona.setIdTipoDocumento(idTipoDocumento3);
        persona.setConsPersonaAsociada(hasColumn(rs, "CONS_PERSONA_ASOCIADA") ? rs.getInt("CONS_PERSONA_ASOCIADA") : null);
        persona.setImagenPerfil(hasColumn(rs, "IMAGEN_PERFIL") ? rs.getString("IMAGEN_PERFIL") : null);
        persona.setSegundoApellido(hasColumn(rs, "SEGUNDO_APELLIDO") ? rs.getString("SEGUNDO_APELLIDO") : null);
        persona.setPrimerNombre(hasColumn(rs, "PRIMER_NOMBRE") ? rs.getString("PRIMER_NOMBRE") : null);
        persona.setSegundoNombre(hasColumn(rs, "SEGUNDO_NOMBRE") ? rs.getString("SEGUNDO_NOMBRE") : null);
        persona.setPrimerApellido(hasColumn(rs, "PRIMER_APELLIDO") ? rs.getString("PRIMER_APELLIDO") : null);
        persona.setNombreEmpresa(hasColumn(rs, "NOMBRE_EMPRESA") ? rs.getString("NOMBRE_EMPRESA") : null);
        persona.setCorreo(hasColumn(rs, "CORREO") ? rs.getString("CORREO") : null);
        persona.setNumeroDocumento(hasColumn(rs, "NUMERO_DOCUMENTO") ? rs.getString("NUMERO_DOCUMENTO") : null);

        //}
        return persona;
    }

    private void setPersona(PreparedStatement ps, Persona persona, boolean flag) throws SQLException {
        int i = 1;
        if (persona.getConsPersona() != null) {
            ps.setObject(i++, (persona.getConsPersona() != null) ? persona.getConsPersona() : null);
        }
        ps.setObject(i++, (persona.getConsUsuario().getConsUsuario() != null) ? persona.getConsUsuario().getConsUsuario() : null);
        ps.setObject(i++, (persona.getIdTipoDocumento().getIdTipoDocumento() != null) ? persona.getIdTipoDocumento().getIdTipoDocumento() : null);
        ps.setObject(i++, (persona.getConsPersonaAsociada() != null) ? persona.getConsPersonaAsociada() : null);
        ps.setString(i++, (persona.getImagenPerfil() != null) ? persona.getImagenPerfil() : null);
        ps.setString(i++, (persona.getSegundoApellido() != null) ? persona.getSegundoApellido() : null);
        ps.setString(i++, (persona.getPrimerNombre() != null) ? persona.getPrimerNombre() : null);
        ps.setString(i++, (persona.getSegundoNombre() != null) ? persona.getSegundoNombre() : null);
        ps.setString(i++, (persona.getPrimerApellido() != null) ? persona.getPrimerApellido() : null);
        ps.setString(i++, (persona.getNombreEmpresa() != null) ? persona.getNombreEmpresa() : null);
        ps.setString(i++, (persona.getCorreo() != null) ? persona.getCorreo() : null);
        ps.setString(i++, (persona.getNumeroDocumento() != null) ? persona.getNumeroDocumento() : null);
        if (persona.getConsPersona() != null) {
            if (flag) {
                ps.setObject(i++, (persona.getConsPersona() != null) ? persona.getConsPersona() : null);
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
