/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.dao;

import edu.poli.gerencia.votacion.modelo.vo.TokensAccion;
import edu.poli.gerencia.votacion.modelo.vo.Votacion;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author jhon1
 */
public class TokensAccionDAO extends GenericoDAO<TokensAccion> {

    EntityManager em;

    public TokensAccionDAO(EntityManager em) {
        super(TokensAccion.class);
        this.em = em;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TokensAccion existeToken(String token) {
        Query q = null;
        try {
            q = em.createNativeQuery("SELECT * FROM tokens_accion WHERE token = ? AND estado = 0", TokensAccion.class);
            q.setParameter(1, token);
            if (q.getResultList().size() > 0) {                
                return (TokensAccion) q.getSingleResult();
            } else {
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

}
