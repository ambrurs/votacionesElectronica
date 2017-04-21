/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.dao;

import edu.poli.gerencia.votacion.modelo.vo.TipoUsuario;
import javax.persistence.EntityManager;

/**
 *
 * @author jhon1
 */
public class TipoUsuarioDAO extends GenericoDAO<TipoUsuario> {

    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoUsuarioDAO(EntityManager em) {
        super(TipoUsuario.class);
        this.em = em;
    }
    
    
}
