/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.delegado;

import edu.poli.gerencia.votacion.modelo.conexion.ConexionBD;
import edu.poli.gerencia.votacion.modelo.dao.TipoDocumentoDAO;
import edu.poli.gerencia.votacion.modelo.vo.TipoDocumento;
import edu.poli.gerencia.votacion.negocio.constantes.EMensajes;
import edu.poli.gerencia.votacion.negocio.recursos.Respuesta;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author jhon1
 */
public class TipoDocumentoDelegado {

    private static TipoDocumentoDelegado instancia = new TipoDocumentoDelegado();

    public TipoDocumentoDelegado() {
    }

    public static TipoDocumentoDelegado getInstancia() {
        return instancia;
    }

    public Respuesta listarTodos() {
        EntityManager em = null;
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_PUDO_PROCESAR);
        try {
            em = ConexionBD.getEntityManager();
            TipoDocumentoDAO dao = new TipoDocumentoDAO(em);
            List list = dao.buscarTodos();
            if (true) {
                respuesta = new Respuesta(EMensajes.CONSULTAR);
                respuesta.setDatos(list);
            } else {
                respuesta = new Respuesta(EMensajes.NO_HAY_RESULTADOS);
            }
        } catch (Exception e) {
            Logger.getLogger(TipoDocumentoDelegado.class.getName()).log(Level.SEVERE, null, e);
        }
        return respuesta;
    }
}
