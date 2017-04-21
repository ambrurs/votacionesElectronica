/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.util;

import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author hrey
 */
public class Log {

    static Logger log = null;
    private final static String RUTA_RECURSOS = "/com/dyd/libreriacontroltotal/negocio/recursos/log4j-config.properties";

    static {
        try {
            System.out.println("inicio configuración del archivo LOG");
            InputStream in = new Log().getClass().getResourceAsStream(RUTA_RECURSOS);
            Properties propiedades = new Properties();
            propiedades.load(in);
            PropertyConfigurator.configure(propiedades);
            System.out.println("Log configurado");
            log = Logger.getRootLogger();
        } catch (Exception e) {

        }
    }

    public static void info(String mensaje) {
        if (log == null) {
            return;
        }
        log.info(mensaje);
    }

    public static void info(Object obj, Throwable ex) {
        if (log == null) {
            return;
        }
        log.info(obj, ex);
    }

    public static void error(Object obj) {
        if (log == null) {
            return;
        }
        log.error(obj);
    }

    public static void error(Object obj, Throwable ex) {
        if (log == null) {
            return;
        }
        log.error(obj, ex);
    }

    public static void advertencia(Object obj) {
        if (log == null) {
            return;
        }
        log.warn(obj);
    }

    public static void advertencia(Object obj, Throwable ex) {
        if (log == null) {
            return;
        }
        log.warn(obj, ex);
    }
}
