/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.delegado;

import edu.poli.gerencia.votacion.modelo.conexion.ConexionBD;
import edu.poli.gerencia.votacion.modelo.dao.PersonaDAO;
import edu.poli.gerencia.votacion.modelo.vo.Persona;
import edu.poli.gerencia.votacion.negocio.constantes.EMensajes;
import edu.poli.gerencia.votacion.negocio.recursos.Respuesta;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author jhon1
 */
public class PersonaDelegado {

    private static PersonaDelegado instancia = new PersonaDelegado();

    public PersonaDelegado() {
    }

    public static PersonaDelegado getInstancia() {
        return instancia;
    }

    public Respuesta registrarPersona(Persona persona) {
        EntityManager em = null;
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_PUDO_PROCESAR);
        try {
            em = ConexionBD.getEntityManager();
            PersonaDAO dao = new PersonaDAO(em);
            em.getTransaction().begin();
            dao.crear(persona);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(PersonaDelegado.class.getName()).log(Level.SEVERE, null, e);
        }
        return respuesta;
    }

    public Respuesta actualizarPersona(Persona persona) {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_PUDO_PROCESAR);
        EntityManager em = null;
        try {
            em = ConexionBD.getEntityManager();
            PersonaDAO dao = new PersonaDAO(em);
            Persona vo = dao.consultarPersonaPorId(persona.getConsPersona());
            if (persona != null) {
                vo.setIdTipoDocumento(persona.getIdTipoDocumento());
                vo.setPrimerNombre(persona.getPrimerNombre());
                vo.setSegundoNombre(persona.getSegundoNombre());
                vo.setPrimerApellido(persona.getPrimerApellido());
                vo.setNombreEmpresa(persona.getNombreEmpresa());
                vo.setCorreo(persona.getCorreo());
                vo.setNumeroDocumento(persona.getNumeroDocumento());
                dao.editar(vo);
                respuesta = new Respuesta(EMensajes.EDITAR);
            }
        } catch (Exception e) {
            Logger.getLogger(PersonaDelegado.class.getName()).log(Level.SEVERE, null, e);
        }
        return respuesta;
    }

    public Respuesta eliminarPersona(Persona persona) {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_PUDO_PROCESAR);
        EntityManager em = null;
        try {
            em = ConexionBD.getEntityManager();
            PersonaDAO dao = new PersonaDAO(em);
            Persona vo = dao.consultarPersonaPorId(persona.getConsPersona());
            if (persona != null) {
                dao.eliminar(vo);
                respuesta = new Respuesta(EMensajes.ELIMINAR);
            }
        } catch (Exception e) {
            Logger.getLogger(PersonaDelegado.class.getName()).log(Level.SEVERE, null, e);
        }
        return respuesta;
    }

}
