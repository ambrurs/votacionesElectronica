package edu.poli.gerencia.votaciones.modelo.dao;

import java.sql.SQLException;
import java.util.List;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public interface IGenericoDAO<T> {

    public abstract void insertar(T entidad) throws SQLException;

    public abstract void actualizar(T entidad) throws SQLException;    

    public abstract void eliminar(T entidad) throws SQLException;

    public abstract void eliminar(Integer id) throws SQLException;

    public List<T> listarTodos() throws SQLException;    

    public T buscarPorId(Integer id) throws SQLException;

    public T consultar(String sql) throws SQLException;

    public List<T> listaConsultar(String sql) throws SQLException;

}
