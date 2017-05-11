package edu.poli.gerencia.votaciones.negocio.utiles;

import java.nio.charset.StandardCharsets;
import javax.xml.bind.DatatypeConverter;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */

public class Base64 {

    public static String encode(String parametro) {
        return DatatypeConverter.printBase64Binary(parametro.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(String parametro) {
        return new String(DatatypeConverter.parseBase64Binary(parametro));
    }

    public static String encode(byte[] parametro) {
        //return DatatypeConverter.printBase64Binary(new String(parametro).getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printBase64Binary(parametro);
    }

    public static String decode(byte[] parametro) {
        return new String(DatatypeConverter.parseBase64Binary(new String(parametro)));
    }

    public static byte[] decodeBytes(byte[] parametro) {
        return DatatypeConverter.parseBase64Binary(new String(parametro));
    }

    public static byte[] decodeBytes(String parametro) {
        return DatatypeConverter.parseBase64Binary(parametro);
    }
}
