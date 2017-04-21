/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.dao;

import javax.persistence.EntityManager;
import edu.poli.gerencia.votacion.modelo.vo.TipoVotacion;

/**
 *
 * @author jhon1
 */
public class TipoVotacionDAO extends GenericoDAO<TipoVotacion> {
    private EntityManager em;
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }

    public TipoVotacionDAO(EntityManager em) {
        super(TipoVotacion.class);
        this.em = em;
    }
    
}
