/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.servlets;

import edu.poli.gerencia.votacion.negocio.constantes.EDireccion;
import edu.poli.gerencia.votacion.negocio.excepciones.SistemaException;
import edu.poli.gerencia.votacion.negocio.recursos.Respuesta;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jhon1
 */
public interface IServlet {

    public Respuesta procesar(EDireccion direccion, HttpServletRequest request) throws Exception;
}
