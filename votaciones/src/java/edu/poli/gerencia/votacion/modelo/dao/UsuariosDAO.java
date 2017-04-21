/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.dao;

import edu.poli.gerencia.votacion.modelo.vo.Persona;
import edu.poli.gerencia.votacion.modelo.vo.Usuario;
import edu.poli.gerencia.votacion.negocio.constantes.EMensajes;
import edu.poli.gerencia.votacion.negocio.recursos.Respuesta;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author jhon1
 */
public class UsuariosDAO extends GenericoDAO<Usuario> {
    
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public UsuariosDAO(EntityManager em) {
        super(Usuario.class);
        this.em = em;
    }
    
    public Usuario iniciarSesion(String usuario, String clave) {
        Query q = null;
        try {
            int i = 0;
            q = em.createNativeQuery("SELECT * FROM usuario WHERE NOMBRE_USUARIO = ? AND CONTRASENA = ?", Usuario.class);
            q.setParameter(i++, usuario);
            q.setParameter(i++, clave);
            return (Usuario) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean existeUsuario(Usuario usuario, Persona persona) throws Exception {
        Query q = null;
        int i = 0;
        q = em.createNativeQuery("SELECT * FROM usuario u INNER JOIN PERSONA p ON u.CONS_USUARIO = p.CONS_USUARIO WHERE NOMBRE_USUARIO u = ? OR p.NUMERO_DOCUMENTO = ? OR p.CORREO = ?", Persona.class);
        q.setParameter(i++, usuario.getNombreUsuario());
        q.setParameter(i++, persona.getNumeroDocumento());
        q.setParameter(i++, persona.getNumeroDocumento());
        Persona personaTemp = (Persona) q.getSingleResult();
        return personaTemp != null;
    }
    
    public Usuario consultaUsuarioPorId(long idUsuario) {
        Query q = null;
        try {
            q = em.createNativeQuery("SELECT * FROM usuarios WHERE CONS_USUARIO = ?", Usuario.class);
            q.setParameter(1, idUsuario);
            return (Usuario) q.getSingleResult();
        } catch (Exception e) {
            System.err.println("Se ha producido un error en: UsuariosDAO.consultaUsuarioPorId()" + e.getMessage());
            return null;
        }
    }
    
    public Persona existeUsuario(String credencial) {
        Query q = null;        
        try {
            int i = 1;
            q = em.createNativeQuery("SELECT * FROM usuario u INNER JOIN persona p ON u.CONS_USUARIO = p.CONS_USUARIO WHERE u.CONS_USUARIO = ? OR p.CORREO = ? OR p.NUMERO_DOCUMENTO = ? LIMIT 1", Persona.class);
            q.setParameter(i++, credencial);
            q.setParameter(i++, credencial);
            q.setParameter(i++, credencial);
            if (q.getResultList().size() > 0) {
                return (Persona) q.getSingleResult();                
            } else {
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }        
    }
    
}
