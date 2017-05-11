package edu.poli.gerencia.votaciones.negocio.utiles;

import edu.poli.gerencia.votaciones.negocio.constantes.EConfiguracion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class DateUtil {

    public static java.util.Date now() {
        return new java.util.Date();
    }

    public static java.sql.Date parseDate(java.util.Date fecha) {
        if (fecha == null) {
            return null;
        }
        return new java.sql.Date(fecha.getTime());
    }

    public static java.sql.Timestamp parseTimestamp(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Timestamp(date.getTime());
    }

    public static java.util.Date dateByDay(int day) {
        Calendar cc = Calendar.getInstance();
        cc.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), day);
        return cc.getTime();
    }

    public static Date parseDate(String datestring) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            return format.parse(datestring);
        } catch (ParseException ex) {
            return new Date();
        }
    }

    public static Date parseDateStandar(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(EConfiguracion.DATE_FORMAT_FULL.getCodigo());
            return format.parse(date);
        } catch (ParseException ex) {
            return new Date();
        }
    }

    public static java.util.Date addMonths(java.util.Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    public static java.util.Date addMonthNowDate(int month) {
        return addMonths(new Date(), month);
    }

    public static boolean isNull(Date date) {
        return !(date == null || date.equals(DateUtil.parseDate("1900/01/01")));
    }

    public static Date getDateNull() {
        return parseDate("1900/01/01");
    }

    public static String dateToString(Date date) {
        return new SimpleDateFormat(EConfiguracion.DATE_FORMAT_SHORT.getCodigo()).format(date);
    }

    public static Date addHourToDate(Date date, Date dateNow) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(dateNow);
        calendar.set(Calendar.HOUR_OF_DAY, calendarNow.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendarNow.get(Calendar.MINUTE));
        return calendar.getTime();
    }

    public static String getDateMinSQL(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1); //porque el Calendar, resta una unidad al mes
        return cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE);
    }

}
