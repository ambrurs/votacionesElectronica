package edu.poli.gerencia.votaciones.negocio.utiles;

import edu.poli.gerencia.votaciones.negocio.constantes.EConfiguracion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class GsonUtil {

    public static Gson getInstance() {
        return new GsonBuilder().setDateFormat(EConfiguracion.DATE_FORMAT_FULL.getCodigo()).serializeNulls().create();
    }
    public static Gson getInstanceShort() {
        return new GsonBuilder().setDateFormat(EConfiguracion.DATE_FORMAT_SHORT.getCodigo()).serializeNulls().create();
    }
}
