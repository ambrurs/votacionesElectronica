package edu.poli.gerencia.votaciones.modelo.vo;

import java.io.Serializable;
import edu.poli.gerencia.votaciones.modelo.vo.TipoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public class TipoVotacion implements Serializable {

    private Integer idTipoVotacion;
    private String nombreTipoVotacion;
    private Integer idPersona;

    public TipoVotacion() {
    }

    public TipoVotacion(Integer idTipoVotacion) {
        this.idTipoVotacion = idTipoVotacion;
    }

    public TipoVotacion(Integer idTipoVotacion, String nombreTipoVotacion) {
        this.idTipoVotacion = idTipoVotacion;
        this.nombreTipoVotacion = nombreTipoVotacion;

    }

    public Integer getIdTipoVotacion() {
        return idTipoVotacion;
    }

    public void setIdTipoVotacion(Integer idTipoVotacion) {
        this.idTipoVotacion = idTipoVotacion;
    }

    public String getNombreTipoVotacion() {
        return nombreTipoVotacion;
    }

    public void setNombreTipoVotacion(String nombreTipoVotacion) {
        this.nombreTipoVotacion = nombreTipoVotacion;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public TipoVotacion equalize(TipoVotacion tipoVotacion) {
        if (tipoVotacion.getIdTipoVotacion() != null) {
            this.setIdTipoVotacion(tipoVotacion.getIdTipoVotacion());
        }
        if (tipoVotacion.getNombreTipoVotacion() != null) {
            this.setNombreTipoVotacion(tipoVotacion.getNombreTipoVotacion());
        }

        return this;
    }

    public String toString() {
        return "idTipoVotacion :: " + this.idTipoVotacion + "; "
                + "nombreTipoVotacion :: " + this.nombreTipoVotacion + "; "
                + "";
    }

}
