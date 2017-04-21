/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.excepciones;

import edu.poli.gerencia.votacion.negocio.constantes.EMensajes;

/**
 *
 * @author jhon1
 */
public class SistemaException extends Exception {

    private String mensaje;
    private int codigo;
    private String codigoSistema;

    private EMensajes eMensaje;

    public SistemaException(EMensajes mensaje) {
        super(mensaje.getDescripcion());
        this.eMensaje = mensaje;
        this.mensaje = mensaje.getDescripcion();
        this.codigo = mensaje.getCodigo();
        this.codigoSistema = mensaje.getCodigoSistema();
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCodigoSistema() {
        return codigoSistema;
    }

    public void setCodigoSistema(String codigoSistema) {
        this.codigoSistema = codigoSistema;
    }

    public EMensajes geteMensaje() {
        return eMensaje;
    }

    public void seteMensaje(EMensajes eMensaje) {
        this.eMensaje = eMensaje;
    }

}
