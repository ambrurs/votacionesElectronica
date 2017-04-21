/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.dao;

import edu.poli.gerencia.votacion.modelo.vo.VotacionUsuarioCandidato;
import javax.persistence.EntityManager;

/**
 *
 * @author jhon1
 */
public class VotacionUsuarioCandidatoDAO extends GenericoDAO<VotacionUsuarioCandidato> {

    EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VotacionUsuarioCandidatoDAO(EntityManager em) {
        super(VotacionUsuarioCandidato.class);
        this.em = em;
    }

}
