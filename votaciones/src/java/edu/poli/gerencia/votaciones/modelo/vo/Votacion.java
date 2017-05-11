package edu.poli.gerencia.votaciones.modelo.vo;

import java.io.Serializable;
import java.util.Date;
import edu.poli.gerencia.votaciones.modelo.vo.TipoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class Votacion implements Serializable {

    private Integer consVotacion;
    private Date fechaInicioInscripcion;
    private Date fechaFinInscripcion;
    private TipoVotacion idTipoVotacion;
    private Date fechaInicioVotacion;
    private Date fechaFinVotacion;
    private Usuario consUsuarioCreacion;
    private String estadoVotacion;
    private Integer cantidadVotos;
    private Integer cantidadCandidatos;

    public Votacion() {
    }

    public Votacion(Integer consVotacion) {
        this.consVotacion = consVotacion;
    }

    public Votacion(Integer consVotacion, Date fechaInicioInscripcion, Date fechaFinInscripcion, TipoVotacion idTipoVotacion, Date fechaInicioVotacion, Date fechaFinVotacion, Usuario consUsuarioCreacion) {
        this.consVotacion = consVotacion;
        this.fechaInicioInscripcion = fechaInicioInscripcion;
        this.fechaFinInscripcion = fechaFinInscripcion;
        this.idTipoVotacion = idTipoVotacion;
        this.fechaInicioVotacion = fechaInicioVotacion;
        this.fechaFinVotacion = fechaFinVotacion;
        this.consUsuarioCreacion = consUsuarioCreacion;

    }

    public Integer getConsVotacion() {
        return consVotacion;
    }

    public void setConsVotacion(Integer consVotacion) {
        this.consVotacion = consVotacion;
    }

    public Date getFechaInicioInscripcion() {
        return fechaInicioInscripcion;
    }

    public void setFechaInicioInscripcion(Date fechaInicioInscripcion) {
        this.fechaInicioInscripcion = fechaInicioInscripcion;
    }

    public Date getFechaFinInscripcion() {
        return fechaFinInscripcion;
    }

    public void setFechaFinInscripcion(Date fechaFinInscripcion) {
        this.fechaFinInscripcion = fechaFinInscripcion;
    }

    public TipoVotacion getIdTipoVotacion() {
        return idTipoVotacion;
    }

    public void setIdTipoVotacion(TipoVotacion idTipoVotacion) {
        this.idTipoVotacion = idTipoVotacion;
    }

    public Date getFechaInicioVotacion() {
        return fechaInicioVotacion;
    }

    public void setFechaInicioVotacion(Date fechaInicioVotacion) {
        this.fechaInicioVotacion = fechaInicioVotacion;
    }

    public Date getFechaFinVotacion() {
        return fechaFinVotacion;
    }

    public void setFechaFinVotacion(Date fechaFinVotacion) {
        this.fechaFinVotacion = fechaFinVotacion;
    }

    public Usuario getConsUsuarioCreacion() {
        return consUsuarioCreacion;
    }

    public void setConsUsuarioCreacion(Usuario consUsuarioCreacion) {
        this.consUsuarioCreacion = consUsuarioCreacion;
    }

    public String getEstadoVotacion() {
        return estadoVotacion;
    }

    public void setEstadoVotacion(String estadoVotacion) {
        this.estadoVotacion = estadoVotacion;
    }

    public Integer getCantidadVotos() {
        return cantidadVotos;
    }

    public void setCantidadVotos(Integer cantidadVotos) {
        this.cantidadVotos = cantidadVotos;
    }

    public Integer getCantidadCandidatos() {
        return cantidadCandidatos;
    }

    public void setCantidadCandidatos(Integer cantidadCandidatos) {
        this.cantidadCandidatos = cantidadCandidatos;
    }

    public Votacion equalize(Votacion votacion) {
        if (votacion.getConsVotacion() != null) {
            this.setConsVotacion(votacion.getConsVotacion());
        }
        if (votacion.getFechaInicioInscripcion() != null) {
            this.setFechaInicioInscripcion(votacion.getFechaInicioInscripcion());
        }
        if (votacion.getFechaFinInscripcion() != null) {
            this.setFechaFinInscripcion(votacion.getFechaFinInscripcion());
        }
        if (votacion.getIdTipoVotacion() != null) {
            this.setIdTipoVotacion(votacion.getIdTipoVotacion());
        }
        if (votacion.getFechaInicioVotacion() != null) {
            this.setFechaInicioVotacion(votacion.getFechaInicioVotacion());
        }
        if (votacion.getFechaFinVotacion() != null) {
            this.setFechaFinVotacion(votacion.getFechaFinVotacion());
        }
        if (votacion.getConsUsuarioCreacion() != null) {
            this.setConsUsuarioCreacion(votacion.getConsUsuarioCreacion());
        }
        if (votacion.getEstadoVotacion() != null) {
            this.setEstadoVotacion(votacion.getEstadoVotacion());
        }
        if (votacion.getCantidadVotos() != null) {
            this.setCantidadVotos(votacion.getCantidadVotos());
        }
        if (votacion.getCantidadCandidatos() != null) {
            this.setCantidadCandidatos(votacion.getCantidadCandidatos());
        }

        return this;
    }

    public String toString() {
        return "consVotacion :: " + this.consVotacion + "; "
                + "fechaInicioInscripcion :: " + this.fechaInicioInscripcion + "; "
                + "fechaFinInscripcion :: " + this.fechaFinInscripcion + "; "
                + "idTipoVotacion :: " + this.idTipoVotacion + "; "
                + "fechaInicioVotacion :: " + this.fechaInicioVotacion + "; "
                + "fechaFinVotacion :: " + this.fechaFinVotacion + "; "
                + "consUsuarioCreacion :: " + this.consUsuarioCreacion + "; "
                + "";
    }

}
