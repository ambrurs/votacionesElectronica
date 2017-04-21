/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.dao;

import edu.poli.gerencia.votacion.modelo.vo.Votacion;
import javax.persistence.EntityManager;

/**
 *
 * @author jhon1
 */
public class VotacionDAO extends GenericoDAO<Votacion> {

    EntityManager em;

    public VotacionDAO(Class<Votacion> entityClass) {
        super(Votacion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
