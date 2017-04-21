/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.conexion;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author jhon1
 */
public class ConexionBD {

    public ConexionBD() {
    }

    public static EntityManager getEntityManager() throws Exception {
        return Persistence.createEntityManagerFactory("votacionesPU").createEntityManager();
    }
}
