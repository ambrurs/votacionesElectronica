package edu.poli.gerencia.votaciones.negocio.delegados;

import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.modelo.dao.IGenericoDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public abstract class GenericoDelegado<T> {

    protected Connection cnn;
    protected IGenericoDAO genericoDAO;

    public GenericoDelegado(Connection cnn) throws VotacionesException {
        this.cnn = cnn;
    }

    public void insertar(T t) throws VotacionesException {
        try {
            genericoDAO.insertar(t);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new VotacionesException(EMensajes.ERROR_INSERTAR);
        }
    }

    public void actualizar(T t) throws VotacionesException {
        try {
            genericoDAO.actualizar(t);
        } catch (SQLException ex) {
            Logger.getLogger(GenericoDelegado.class.getName()).log(Level.SEVERE, null, ex);
            throw new VotacionesException(EMensajes.ERROR_ACTUALIZAR);
        }
    }

    public List<T> listarTodos() throws VotacionesException {
        try {
            return genericoDAO.listarTodos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new VotacionesException(EMensajes.ERROR_CONSULTAR);
        }
    }

    public T buscarPorId(Integer id) throws VotacionesException {
        try {
            return (T) genericoDAO.buscarPorId(id);
        } catch (SQLException e) {
            throw new VotacionesException(EMensajes.ERROR_CONSULTAR);
        }
    }

    public void eliminar(T t) throws VotacionesException {
        try {
            genericoDAO.eliminar(t);
        } catch (Exception e) {
            throw new VotacionesException(EMensajes.ERROR_ELIMINAR);
        }
    }

    public void eliminar(Integer id) throws VotacionesException {
        try {
            genericoDAO.eliminar(id);
        } catch (Exception e) {
            throw new VotacionesException(EMensajes.ERROR_ELIMINAR);
        }
    }
}
