/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.dao;

import edu.poli.gerencia.votacion.modelo.vo.EstadoVotacion;
import javax.persistence.EntityManager;

/**
 *
 * @author jhon1
 */
public class EstadoVotacionDAO extends GenericoDAO<EstadoVotacion> {

    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoVotacionDAO(EntityManager em) {
        super(EstadoVotacion.class);
        this.em = em;
    }

}
