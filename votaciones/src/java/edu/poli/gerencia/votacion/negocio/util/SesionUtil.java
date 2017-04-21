/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.util;

//import edu.poli.gerencia.votacion.modelo.dto.AuditoriaDTO;
//import edu.poli.gerencia.votacion.modelo.dto.TokenDTO;
import edu.poli.gerencia.votacion.negocio.constantes.EConfiguracion;
import edu.poli.gerencia.votacion.negocio.constantes.EMensajes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.poli.gerencia.votacion.negocio.excepciones.SistemaException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hrey
 */
public class SesionUtil {

    public static Long aumentarTiempoExpiracion(Long inicioSesion) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(new Date(inicioSesion));
        calendario.add(Calendar.MINUTE, 30);
        return calendario.getTime().getTime();
    }
//
//    public static TokenDTO getToken(String tokenCifrado) throws SistemaException {
//        Gson gson = new GsonBuilder().setDateFormat(EConfiguracion.FORMATO_FECHA_COMPLETA.getValor()).serializeNulls().create();
//        String tokenDescifrado = UtilControlTotal.descifrar(tokenCifrado);
//        TokenDTO token = gson.fromJson(tokenDescifrado, TokenDTO.class);
//        return token;
//    }
//
//    public static AuditoriaDTO getAuditoria(String token) throws SistemaException {
//        TokenDTO tokenDTO = getToken(token);
//        return getAuditoria(tokenDTO);
//    }

//    public static AuditoriaDTO getAuditoria(TokenDTO tokenDTO) throws SistemaException {
//        AuditoriaDTO auditoria = new AuditoriaDTO();
//        auditoria.setIp(tokenDTO.getIp());
//        auditoria.setToken(tokenDTO);
//        return auditoria;
//    }

//    public static void validarSesionActiva(TokenDTO token) throws SistemaException {
//        if (new Date().getTime() > token.getTiempoExpiracion()) {
//            throw new SistemaException(EMensajes.ERROR_SESION_EXPIRADO);
//        }
//    }

//    public static String token(TokenDTO token) {
//        if (token == null) {
//            return null;
//        }
//        try {
//            token.setTiempoExpiracion(SesionUtil.aumentarTiempoExpiracion(new Date().getTime()));
//            String json = GsonUtil.getInstance().toJson(token);
//            String tokenCifrado = UtilControlTotal.cifrar(json);
//            return tokenCifrado;
//        } catch (SistemaException ex) {
//            Logger.getLogger(SesionUtil.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }
}
