package edu.poli.gerencia.votaciones.negocio.excepciones;

import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class VotacionesException extends Exception {

    private String mensaje;
    private int codigo;
    private String codigoVotaciones;

    private EMensajes eMensajes;

    public VotacionesException(EMensajes eMensajes) {
        super(eMensajes.getDescripcion());
        this.eMensajes = eMensajes;
        this.mensaje = eMensajes.getDescripcion();
        this.codigo = eMensajes.getCodigo();
        this.codigoVotaciones = eMensajes.getCodigoVotaciones();
    }

    public VotacionesException reemplazarParteMensaje(String query, String replace) {
        this.mensaje = this.mensaje.replaceAll(query, replace);
        return this;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public EMensajes getEMensajes() {
        return eMensajes;
    }

    public void seteMensajes(EMensajes eMensajes) {
        this.eMensajes = eMensajes;
    }

    public String getCodigoVotaciones() {
        return codigoVotaciones;
    }

    public void setCodigoVotaciones(String codigoVotaciones) {
        this.codigoVotaciones = codigoVotaciones;
    }

}
