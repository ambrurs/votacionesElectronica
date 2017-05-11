package edu.poli.gerencia.votaciones.negocio.delegados;

import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.modelo.vo.Persona;
import edu.poli.gerencia.votaciones.modelo.dao.PersonaDAO;
import edu.poli.gerencia.votaciones.modelo.dto.PersonaDTO;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class PersonaDelegado extends GenericoDelegado<Persona> {

    private final PersonaDAO personaDAO;

    public PersonaDelegado(Connection cnn) throws VotacionesException {
        super(cnn);
        personaDAO = new PersonaDAO(cnn);
        genericoDAO = personaDAO;
    }

    public List<PersonaDTO> filtrar(String filtro) throws VotacionesException {
        try {
            return personaDAO.filtrar(filtro);
        } catch (SQLException ex) {
            Logger.getLogger(PersonaDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.CONSULTAR);
        }
    }

}
