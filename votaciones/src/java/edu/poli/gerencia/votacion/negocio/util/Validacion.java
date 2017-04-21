/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dyd.libreriacontroltotal.negocio.util;

import edu.poli.gerencia.votacion.negocio.constantes.EConfiguracion;
import edu.poli.gerencia.votacion.negocio.constantes.EMensajes;
import edu.poli.gerencia.votacion.negocio.excepciones.SistemaException;
import edu.poli.gerencia.votacion.negocio.util.DateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hrey
 */
public class Validacion {
    
    public static String recuperarPadre(String... padre){
        String strPadre= null;
        if(padre != null && padre.length>0){
            strPadre= padre[0]; 
        }
        return (strPadre);
    }
    
    public static void esNumeroObligatorio(String parametro, String nombreCampo) throws SistemaException {
        boolean esNumero = esNumero(parametro);
        if (!esNumero) {
            SistemaException excepcion = new SistemaException(EMensajes.ERROR_TIPO_DATO);
            excepcion.setMensaje(excepcion.getMensaje().replaceAll("__CAMPO__", nombreCampo));
            throw excepcion;
        }
    }
    
    public static void validarIdValido(String parametro, String nombreCampo, int... idInvalidos) throws SistemaException {
        validarVacioObligatorio(parametro, nombreCampo);
        boolean esNumero = esNumero(parametro);
        if (!esNumero) {
            SistemaException excepcion = new SistemaException(EMensajes.ERROR_TIPO_DATO);
            excepcion.setMensaje(excepcion.getMensaje().replaceAll("__CAMPO__", nombreCampo));
            throw excepcion;
        }
        List l = Arrays.asList(idInvalidos);
        if (l.contains(Integer.valueOf(parametro))) {
            SistemaException excepcion = new SistemaException(EMensajes.ERROR_CONTENIDO);
            excepcion.setMensaje(excepcion.getMensaje().replaceAll("__DATO__", nombreCampo));
            throw excepcion;
        }
    }
    
    public static void validarLongitudMaxima(String paramentro, String nombreCampo, int longitudMaxima) throws SistemaException {
        //validarVacioObligatorio(paramentro, nombreCampo);
        if (paramentro.length() > longitudMaxima) {
            SistemaException excepcion = new SistemaException(EMensajes.ERROR_LONGITUD_MAXIMA);
            excepcion.setMensaje(excepcion.getMensaje().replaceAll("__CAMPO__", nombreCampo));
            throw excepcion;
        }
    }
    
    public static void validarMaximoValor(String parametro, String nombreCampo, double valorMaximo) throws SistemaException {
        if (!esNumero(parametro)) {
            SistemaException excepcion = new SistemaException(EMensajes.ERROR_DATO);
            excepcion.setMensaje(excepcion.getMensaje().replaceAll("__DATO__", nombreCampo));
            throw excepcion;
        }
        if (Double.parseDouble(parametro) > valorMaximo) {
            SistemaException excepcion = new SistemaException(EMensajes.ERROR_LONGITUD_MAXIMA);
            excepcion.setMensaje(excepcion.getMensaje().replaceAll("__CAMPO__", nombreCampo));
            throw excepcion;
        }
    }
    
    public static void validarVacioObligatorio(String parametro, String nombreCampo) throws SistemaException {
        SistemaException excepcion;
        if (esVacio(parametro)) {
            excepcion = new SistemaException(EMensajes.ERROR_DATO);
            excepcion.setMensaje(excepcion.getMensaje().replaceAll("__DATO__", nombreCampo));
            throw excepcion;
        }
    }
    
    public static boolean esNumero(String parametro) {
        if (parametro == null) {
            return false;
        }
        boolean esNumero = parametro.trim().matches("[\\d]+(\\.[\\d]*)?");
        return esNumero;
    }
    
    public static boolean esVacio(String parametro) {
        if (parametro == null) {
            return true;
        }
        return parametro.trim().isEmpty();
    }
    
    public static boolean esFecha(String fecha, String patron) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat(patron);
            formato.parse(fecha);
            return true;
        } catch (ParseException ex) {
            Logger.getLogger(Validacion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static boolean esFechaEstandar(String fecha) {
        return esFecha(fecha, EConfiguracion.FORMATO_FECHA_COMPLETA.getValor());
    }
    
    public static void validarFechaObligatorio(String fecha, String campo) throws SistemaException {
        if (!esFechaEstandar(fecha)) {
            validarVacioObligatorio("", campo);
        }
    }
    
    public static void validarFechas(String fechaInicial, String fechaFinal) throws SistemaException {
        if (Validacion.esVacio(fechaInicial)) {
            return;
        }
        if (!Validacion.esVacio(fechaInicial) && Validacion.esVacio(fechaFinal)) {
            Validacion.validarVacioObligatorio("", "fecha final");
        }
        validarFechaObligatorio(fechaInicial, "fecha inicial");
        validarFechaObligatorio(fechaFinal, "fecha final");
        if (!DateUtil.parseFechaEstandar(fechaInicial).before(DateUtil.parseFechaEstandar(fechaFinal))) {
            throw new SistemaException(EMensajes.ERROR_FECHAS_INICIAL_FINAL);
        }
        
    }
    
    public static void validarNoNuloVacio(Object objeto, String nombreCampo) throws SistemaException {
        SistemaException excepcion;
        if (objeto == null) {
            excepcion = new SistemaException(EMensajes.ERROR_DATO);
            excepcion.setMensaje(excepcion.getMensaje().replaceAll("__DATO__", nombreCampo));
            throw excepcion;
        }
        if (objeto instanceof String ) {
            String ob = (String) objeto;
            validarVacioObligatorio(ob, nombreCampo);
        }
        
    }
    
}
