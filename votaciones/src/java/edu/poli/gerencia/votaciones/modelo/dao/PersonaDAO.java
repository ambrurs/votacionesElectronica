package edu.poli.gerencia.votaciones.modelo.dao;

import edu.poli.gerencia.votaciones.modelo.conexion.ConnectionDB;
import edu.poli.gerencia.votaciones.modelo.dao.crud.PersonaCRUD;
import static edu.poli.gerencia.votaciones.modelo.dao.crud.PersonaCRUD.getPersona;
import edu.poli.gerencia.votaciones.modelo.dto.PersonaDTO;
import edu.poli.gerencia.votaciones.modelo.vo.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO extends PersonaCRUD {

    public PersonaDAO(Connection cnn) {
        super(cnn);
    }

    public List<PersonaDTO> filtrar(String filtro) throws SQLException {
        PreparedStatement ps = null;
        List<PersonaDTO> list = new ArrayList<PersonaDTO>();
        try {
            String sql = "SELECT p.CONS_PERSONA, p.NOMBRE_EMPRESA, p.NUMERO_DOCUMENTO "
                    + "FROM persona p INNER JOIN usuario u ON u.CONS_USUARIO = p.CONS_USUARIO"
                    + " WHERE (p.NOMBRE_EMPRESA LIKE '%" + filtro + "%' "
                    + "OR p.NUMERO_DOCUMENTO LIKE '%" + filtro + "%') AND u.ID_TIPO_USUARIO = 2";
            ps = cnn.prepareStatement(sql);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PersonaDTO personaDTO = new PersonaDTO();
                personaDTO.setConsPersona(rs.getInt("CONS_PERSONA"));
                personaDTO.setNombreEmpresa(rs.getString("NOMBRE_EMPRESA"));
                personaDTO.setNumeroDocumento(rs.getString("NUMERO_DOCUMENTO"));
                list.add(personaDTO);
            }
        } finally {
            ConnectionDB.desconectar(ps);
        }
        return list;
    }
}
