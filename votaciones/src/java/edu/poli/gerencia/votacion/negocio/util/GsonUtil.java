/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.util;

import edu.poli.gerencia.votacion.negocio.constantes.EConfiguracion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author hrey
 */
public class GsonUtil {

    public static Gson getInstance() {
        return new GsonBuilder().setDateFormat(EConfiguracion.FORMATO_FECHA_COMPLETA.getValor()).serializeNulls().create();
    }
    public static Gson getInstanceCorta() {
        return new GsonBuilder().setDateFormat(EConfiguracion.FORMATO_FECHA_CORTA.getValor()).serializeNulls().create();
    }
}
