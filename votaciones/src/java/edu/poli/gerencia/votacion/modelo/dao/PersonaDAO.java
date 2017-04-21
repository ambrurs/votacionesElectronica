/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.dao;

import edu.poli.gerencia.votacion.modelo.vo.Persona;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author jhon1
 */
public class PersonaDAO extends GenericoDAO<Persona> {

    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonaDAO(EntityManager em) {
        super(Persona.class);
        this.em = em;
    }

    public Persona consultarPersonaPorId(long idPersona) {
        Query q = null;
        try {
            q = em.createNativeQuery("SELECT * FROM persona WHERE CONS_PERSONA = ?", Persona.class);
            q.setParameter(1, idPersona);
            return (Persona) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Se ha producido un error en PersonaDAO.consultarPorID");
            Logger.getLogger(PersonaDAO.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
}
