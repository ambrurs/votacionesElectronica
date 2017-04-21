/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.constantes;

/**
 *
 * @author hrey
 */
public enum EConfiguracion {

    FORMATO_FECHA_COMPLETA("yyyy/MM/dd HH:mm:ss", ""),
    FORMATO_FECHA_CORTA("yyyy/MM/dd", "");
    private String valor;
    private String descripcion;

    private EConfiguracion(String valor, String descripcion) {
        this.valor = valor;
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
