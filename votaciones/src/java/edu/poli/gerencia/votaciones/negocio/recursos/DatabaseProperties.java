package edu.poli.gerencia.votaciones.negocio.recursos;

import java.util.Properties;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */

public class DatabaseProperties extends LoadProperties {

    private static Properties p;

    public static String get(String key) {
        return getKey(p, key, "/database.properties");
    }

}
