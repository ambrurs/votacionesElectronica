/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.recursos;

import edu.poli.gerencia.votacion.negocio.constantes.EMensajes;

/**
 *
 * @author jhon1
 */
public class Respuesta {

    private int codigo;
    private String mensaje;
    private Object datos;

    public Respuesta(int codigo, String mensaje, Object datos) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.datos = datos;
    }
    
    public Respuesta(EMensajes mensaje){
        setMensaje(mensaje);
    }

    public void setMensaje(EMensajes mensaje) {
        this.codigo = mensaje.getCodigo();
        this.mensaje = mensaje.getDescripcion();        
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Object getDatos() {
        return datos;
    }

    public void setDatos(Object datos) {
        this.datos = datos;
    }

}
