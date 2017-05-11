package edu.poli.gerencia.votaciones.negocio.utiles;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */

public class UtilURL {

    public static String lastPart(String data, String separator) {
        if (data == null) {
            return "";
        }
        String[] parts = data.split(separator);
        return parts[parts.length - 1];
    }

    public static String penultimatePart (String data, String separator) {
        if (data == null) {
            return "";
        }
        String[] parts = data.split(separator);
        return parts[parts.length - 1];
    }

    public static String lastParteURL(String url) {
        return lastPart(url, "/");
    }

    public static String penultimatePartURL(String url) {
        return penultimatePart(url, "/");
    }

    public static String urlMain(String url) {
        return url.replaceAll("/" + lastParteURL(url), "") + ".html";
    }

}
