package edu.poli.gerencia.votaciones.negocio.recursos;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */

public class LoadProperties {

    protected static String getKey(Properties p, String key, String file) {
        try {
            if (p == null) {
                p = new Properties();
                InputStream is = LoadProperties.class.getResourceAsStream(file);
                p.load(is);
            }
            return p.getProperty(key);
        } catch (Exception ex) {
            Logger.getLogger(LoadProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}

