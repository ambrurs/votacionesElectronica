/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.dao;

import edu.poli.gerencia.votacion.modelo.vo.TipoDocumento;
import javax.persistence.EntityManager;

/**
 *
 * @author jhon1
 */
public class TipoDocumentoDAO extends GenericoDAO<TipoDocumento>{
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoDocumentoDAO(EntityManager em) {
        super(TipoDocumento.class);
        this.em = em;
    }
    
    
    
}
