package edu.poli.gerencia.votacion.negocio.util;

/**
 *
 * @author jhonjaider1000
 */
import java.net.Socket;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Util {
    
    public static void main(String[] args) {
        System.out.println(generaCodigoAleatorio());
    }

    /**
     *
     * @param clave Recibe la clave que queremos encriptar.
     * @return retorna un String con una cadena de caracteres codificados en MD5
     * de la cadena recibida, en este caso la clave.
     * @throws Exception Lanzará una exception cuando suceda algo.
     */
    public static String md5(String clave) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(clave.getBytes());
        int size = b.length;
        StringBuffer h = new StringBuffer(size);
        //algoritmo y arreglo md5
        for (int i = 0; i < size; i++) {
            int u = b[i] & 255;
            if (u < 16) {
                h.append("0" + Integer.toHexString(u));
            } else {
                h.append(Integer.toHexString(u));
            }
        }
        //clave encriptada
        return h.toString();
    }

    /**
     *
     */
    public static String generarClaveAleatoria() {
        String clave = "";
        String[] palabra = {"able", "about", "above", "according", "account", "across", "act", "action", "adam", "advanced", "after", "almost", "boat", "bodies", "book", "broken", "business", "camp", "command", "company", "condition", "country", "character", "church", "dangerous", "english", "escape", "evening", "everything", "fact", "family", "foamous", "father", "feeling", "going", "government", "heavy", "help", "himself", "history", "leave", "length", "rigth", "left", "officers", "part", "once", "pass", "sound", "speacks", "subjects", "strong", "true", "twenty", "understand", "united", "victory", "wanted", "verloc", "walked", "water", "years", "youth", "zeal"};
        try {
            Random random = new Random();
            System.out.println("Length cadena: " + palabra.length);
            clave += palabra[random.nextInt(63)];
            clave += random.nextInt(10);
            clave += random.nextInt(10);
            clave += random.nextInt(10);
            clave += random.nextInt(10);
        } catch (Exception e) {
            System.err.println("Se ha producido un error al generar la calve aleatoria.");
        }
        return clave;
    }
    
    public static String generaCodigoAleatorio(){
        String clave = "";
        try {
            String[] keywords = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9","0","#"};
            Random random = new Random();
            clave += keywords[random.nextInt(35)];            
            clave += keywords[random.nextInt(35)];            
            clave += keywords[random.nextInt(35)];            
            clave += keywords[random.nextInt(35)];            
            clave += keywords[random.nextInt(35)];            
            clave += keywords[random.nextInt(35)];            
        } catch (Exception e) {
            System.err.println("Se ha producido un error al generar el código aleatorio.");
        }
        return clave;
    }

    /**
     * Validar conexión a internet.
     *
     * @return retorna verdadero o falso, según se el caso(Estado de la red).
     */
    public static boolean validarConexion() {
        String pagina = "www.google.com";
        Integer puerto = 80;
        try {
            Socket s = new Socket(pagina, puerto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Calcula la diferencia entre dos fechas. Devuelve el resultado en días,
     * meses o años según sea el valor del parámetro 'tipo'
     *
     * @param fechaInicio Fecha inicial
     * @param fechaFin Fecha final
     * @param tipo 0=TotalAños; 1=TotalMeses; 2=TotalDías; 3=MesesDelAnio;
     * 4=DiasDelMes
     * @return numero de días, meses o años de diferencia Tomado de:
     * http://felinfo.blogspot.com/2009/09/calcular-la-diferencia-en-dias-entre.html
     * Alterado por: John Jaider Vanegas. Inicialmente el método recibia 3
     * párametros pero he dejado que el mismo método capture la fecha actual del
     * sistema que es la fecha fin que necesito calcular con la posteriormente
     * recibida(fechaInicio).
     */
    public static String diferencia(Date fechaInicio) {
        //Parametrizamos
        int tipo = 0;
        Date fechaFin = new Date();
        // Fecha inicio
        Calendar calendarInicio = Calendar.getInstance();
        calendarInicio.setTime(fechaInicio);
        int diaInicio = calendarInicio.get(Calendar.DAY_OF_MONTH);
        int mesInicio = calendarInicio.get(Calendar.MONTH) + 1; // 0 Enero, 11 Diciembre
        int anioInicio = calendarInicio.get(Calendar.YEAR);

        // Fecha fin
        Calendar calendarFin = Calendar.getInstance();
        calendarFin.setTime(fechaFin);
        int diaFin = calendarFin.get(Calendar.DAY_OF_MONTH);
        int mesFin = calendarFin.get(Calendar.MONTH) + 1; // 0 Enero, 11 Diciembre
        int anioFin = calendarFin.get(Calendar.YEAR);

        int anios = 0;
        int mesesPorAnio = 0;
        int diasPorMes = 0;
        int diasTipoMes = 0;

        //
        // Calculo de días del mes
        //
        if (mesInicio == 2) {
            // Febrero
            if ((anioFin % 4 == 0) && ((anioFin % 100 != 0) || (anioFin % 400 == 0))) {
                // Bisiesto
                diasTipoMes = 29;
            } else {
                // No bisiesto
                diasTipoMes = 28;
            }
        } else if (mesInicio <= 7) {
            // De Enero a Julio los meses pares tienen 30 y los impares 31
            if (mesInicio % 2 == 0) {
                diasTipoMes = 30;
            } else {
                diasTipoMes = 31;
            }
        } else if (mesInicio > 7) {
            // De Julio a Diciembre los meses pares tienen 31 y los impares 30
            if (mesInicio % 2 == 0) {
                diasTipoMes = 31;
            } else {
                diasTipoMes = 30;
            }
        }

        //
        // Calculo de diferencia de año, mes y dia
        //
        if ((anioInicio > anioFin) || (anioInicio == anioFin && mesInicio > mesFin)
                || (anioInicio == anioFin && mesInicio == mesFin && diaInicio > diaFin)) {
            // La fecha de inicio es posterior a la fecha fin
            // System.out.println("La fecha de inicio ha de ser anterior a la fecha fin");
            return -1 + "";
        } else {
            if (mesInicio <= mesFin) {
                anios = anioFin - anioInicio;
                if (diaInicio <= diaFin) {
                    mesesPorAnio = mesFin - mesInicio;
                    diasPorMes = diaFin - diaInicio;
                } else {
                    if (mesFin == mesInicio) {
                        anios = anios - 1;
                    }
                    mesesPorAnio = (mesFin - mesInicio - 1 + 12) % 12;
                    diasPorMes = diasTipoMes - (diaInicio - diaFin);
                }
            } else {
                anios = anioFin - anioInicio - 1;
                System.out.println(anios);
                if (diaInicio > diaFin) {
                    mesesPorAnio = mesFin - mesInicio - 1 + 12;
                    diasPorMes = diasTipoMes - (diaInicio - diaFin);
                } else {
                    mesesPorAnio = mesFin - mesInicio + 12;
                    diasPorMes = diaFin - diaInicio;
                }
            }
        }
	//System.out.println("Han transcurrido " + anios + " Años, " + mesesPorAnio + " Meses y " + diasPorMes + " Días.");		

        //
        // Totales
        //
        long returnValue = -1;

        switch (tipo) {
            case 0:
                // Total Años
                returnValue = anios;
                // System.out.println("Total años: " + returnValue + " Años.");
                break;

            case 1:
                // Total Meses
                returnValue = anios * 12 + mesesPorAnio;
                // System.out.println("Total meses: " + returnValue + " Meses.");
                break;

            case 2:
                // Total Dias (se calcula a partir de los milisegundos por día)
                long millsecsPerDay = 86400000; // Milisegundos al día
                returnValue = (fechaFin.getTime() - fechaInicio.getTime()) / millsecsPerDay;
                // System.out.println("Total días: " + returnValue + " Días.");
                break;

            case 3:
                // Meses del año
                returnValue = mesesPorAnio;
                // System.out.println("Meses del año: " + returnValue);
                break;

            case 4:
                // Dias del mes
                returnValue = diasPorMes;
                // System.out.println("Dias del mes: " + returnValue);
                break;

            default:
                break;
        }

        //Modificaciones - John Vanegas.
        String retorno = "";
        //Comparar para retorno
        if (returnValue != 0) {
            if (returnValue == 1) {
                retorno = returnValue + " año.";
            } else {
                retorno = returnValue + " años.";
            }
        } else {
            retorno = "Reciente.";
        }
        return retorno;
    }

}
