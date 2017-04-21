/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.util;

import edu.poli.gerencia.votacion.negocio.constantes.EConfiguracion;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author hrey
 */
public class DateUtil {

    public static java.util.Date fechaActualSistema() {
        return new java.util.Date();
    }

    public static java.sql.Date parseDate(java.util.Date fecha) {
        if (fecha == null) {
            return null;
        }
        return new java.sql.Date(fecha.getTime());
    }

    public static java.sql.Timestamp parseTimestamp(java.util.Date fecha) {
        if (fecha == null) {
            return null;
        }
        return new java.sql.Timestamp(fecha.getTime());
    }

    public static java.util.Date crearFechaPorDia(int dia) {
        Calendar cc = Calendar.getInstance();
        cc.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), dia);
        return cc.getTime();
    }

    public static Date parseDate(String fechastring) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            return format.parse(fechastring);
        } catch (ParseException ex) {
            return new Date();
        }
    }

    public static Date parseFechaEstandar(String fecha) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(EConfiguracion.FORMATO_FECHA_COMPLETA.getValor());
            return format.parse(fecha);
        } catch (ParseException ex) {
            return new Date();
        }
    }

    public static java.util.Date agregarMesesFecha(java.util.Date fecha, int cantidadMeses) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        calendario.add(Calendar.MONTH, cantidadMeses);
        return calendario.getTime();

    }

    public static java.util.Date agregarMesesFechaActual(int cantidadMeses) {
        return agregarMesesFecha(new Date(), cantidadMeses);
    }

    public static boolean esFechaVacia(Date fecha) {
        return !(fecha == null || fecha.equals(DateUtil.parseDate("1900/01/01")));
    }

    public static Date getFechaVacia() {
        return parseDate("1900/01/01");
    }

    public static String getStringFecha(Date fecha) {
        return new SimpleDateFormat(EConfiguracion.FORMATO_FECHA_CORTA.getValor()).format(fecha);
    }

    public static Date agregarHoraAFecha(Date fecha, Date fechaActualSistema) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        Calendar calendarioFechaActual = Calendar.getInstance();
        calendarioFechaActual.setTime(fechaActualSistema);
        calendario.set(Calendar.HOUR_OF_DAY, calendarioFechaActual.get(Calendar.HOUR_OF_DAY));
        calendario.set(Calendar.MINUTE, calendarioFechaActual.get(Calendar.MINUTE));
        return calendario.getTime();
    }

    public static String getFechaCortaSQL(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1); //porque el Calendar, resta una unidad al mes
        return cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE);
    }

}
