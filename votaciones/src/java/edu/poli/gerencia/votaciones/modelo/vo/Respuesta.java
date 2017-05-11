package edu.poli.gerencia.votaciones.modelo.vo;

import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import java.util.List;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class Respuesta {

    private int codigo;
    private String mensaje;
    private String codigoVotaciones;
    private Object datos;

    public Respuesta() {
    }

    public Respuesta(EMensajes eMensajes) {
        setMensaje(eMensajes);
    }

    public Respuesta(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public Respuesta(int codigo, String mensaje, Object datos) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.datos = datos;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Object getDatos() {
        return datos;
    }

    public void setDatos(Object datos) {
        if (EMensajes.parse(mensaje) == EMensajes.NO_SE_ENCONTRARON_REGISTROS) {
            processDatos(datos);
        }
        this.datos = datos;
    }

    private void processDatos(Object datos) {
        if (datos instanceof List) {
            List list = (List) datos;
            setMensaje((list.isEmpty()) ? EMensajes.NO_SE_ENCONTRARON_REGISTROS : EMensajes.CONSULTAR);
            return;
        }
        setMensaje(datos == null ? EMensajes.NO_SE_ENCONTRARON_REGISTROS : EMensajes.CONSULTAR);
    }

    public void setMensaje(EMensajes eMensajes) {
        this.codigo = eMensajes.getCodigo();
        this.mensaje = eMensajes.getDescripcion();
        this.codigoVotaciones = eMensajes.getCodigoVotaciones();
    }    

    public String getCodigoVotaciones() {
        return codigoVotaciones;
    }

    public void setCodigoVotaciones(String codigoVotaciones) {
        this.codigoVotaciones = codigoVotaciones;
    }

}
