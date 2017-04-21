/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.util;

import edu.poli.gerencia.votacion.negocio.constantes.EMensajes;
import edu.poli.gerencia.votacion.negocio.excepciones.SistemaException;
import edu.poli.gerencia.votacion.negocio.util.Base64;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author administrador
 */
public class UtilVotaciones {

    public static String getCifradoSHA1(String texto) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA1");
            byte[] array = md.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.err.println("Error " + e.getMessage());
        }
        return "";
    }

    public static String getDigitoVerificacion(String numeronit) {

        String cadenaInvertida = "";
        for (int x = numeronit.length() - 1; x >= 0; x--) {
            cadenaInvertida = cadenaInvertida + numeronit.charAt(x);
        }

        char miTemp;
        int residuoModulo;
        int valorChequeo;
        int[] arregloPrimos = new int[15];
        arregloPrimos[0] = 3;
        arregloPrimos[1] = 7;
        arregloPrimos[2] = 13;
        arregloPrimos[3] = 17;
        arregloPrimos[4] = 19;
        arregloPrimos[5] = 23;
        arregloPrimos[6] = 29;
        arregloPrimos[7] = 37;
        arregloPrimos[8] = 41;
        arregloPrimos[9] = 43;
        arregloPrimos[10] = 47;
        arregloPrimos[11] = 53;
        arregloPrimos[12] = 59;
        arregloPrimos[13] = 67;
        arregloPrimos[14] = 71;
        valorChequeo = 0;
        residuoModulo = 0;
        /*for (int i = 0; i < numeronit.length(); i++) {
         miTemp = numeronit.substring((numeronit.length() - 1) - i);
         valorChequeo = valorChequeo + (Integer.parseInt(miTemp) * arregloPrimos[i]);
         }*/

        for (int i = 0; i < cadenaInvertida.length(); i++) {
            miTemp = cadenaInvertida.charAt(i);
            valorChequeo = valorChequeo + (Integer.parseInt(String.valueOf(miTemp)) * arregloPrimos[i]);
        }
        //Log.info(valorChequeo);

        residuoModulo = valorChequeo % 11;
        if (residuoModulo > 1) {
            return String.valueOf((11 - residuoModulo));
        }
        return String.valueOf(residuoModulo);

    }

    private static final String ALGORITMO = "AES";
    //private static final byte[] keyValue = new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};
    private static final byte[] keyValue = new byte[]{'A', 'P', 'P', 'F', 'u', 't', 'u', 'r', 'e', 'c', 'c', 'D', 'Y', 'D', 'S', 'A'};

    public static String cifrar(String Data) throws SistemaException {
        try {
            Key key = generarLlave();
            Cipher c = Cipher.getInstance(ALGORITMO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(Data.getBytes());
            String encryptedValue = Base64.encode(encVal);
            return encryptedValue;
        } catch (Exception ex) {
            Logger.getLogger(UtilVotaciones.class.getName()).log(Level.SEVERE, null, ex);
            throw new SistemaException(EMensajes.ERROR_CIFRAR);
        }
    }

    /*public static String encrypt(String Data) throws Exception {
     Key key = generateKey();
     Cipher c = Cipher.getInstance(ALGO);
     c.init(Cipher.ENCRYPT_MODE, key);
     byte[] encVal = c.doFinal(Data.getBytes());
     //String encryptedValue = new BASE64Encoder().encode(encVal);
     String encryptedValue = Base64.getEncoder().encodeToString(encVal);
     return encryptedValue;
     }*/
    public static String descifrar(String encryptedData) throws SistemaException {
        try {
            Key key = generarLlave();
            Cipher c = Cipher.getInstance(ALGORITMO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = Base64.decodeBytes(encryptedData);
            byte[] decValue = c.doFinal(decordedValue);
            String decryptedValue = new String(decValue, Charset.forName("UTF-8"));
            return decryptedValue;
        } catch (Exception ex) {
            Logger.getLogger(UtilVotaciones.class.getName()).log(Level.SEVERE, null, ex);
            throw new SistemaException(EMensajes.ERROR_DESCIFRAR);
        }
    }

    /*public static String decrypt(String encryptedData) throws Exception {
     Key key = generateKey();
     Cipher c = Cipher.getInstance(ALGO);
     c.init(Cipher.DECRYPT_MODE, key);
     byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
     //byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
     byte[] decValue = c.doFinal(decordedValue);
     String decryptedValue = new String(decValue);
     return decryptedValue;
     }*/
    private static Key generarLlave() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGORITMO);
        return key;
    }

    public static void mezclarObjetos(Serializable entidad, Serializable dto) throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = entidad.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            // get value
            Object value = field.get(entidad);
            // check the values are different, then update 
            field.set(dto, value);
            field.setAccessible(false);
        }
    }
}
