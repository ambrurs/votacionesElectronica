/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.vo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jhon1
 */
@Entity
@Table(name = "tipo_estado_votacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEstadoVotacion.findAll", query = "SELECT t FROM TipoEstadoVotacion t")
    , @NamedQuery(name = "TipoEstadoVotacion.findByIdTipoEstadoVotacion", query = "SELECT t FROM TipoEstadoVotacion t WHERE t.idTipoEstadoVotacion = :idTipoEstadoVotacion")
    , @NamedQuery(name = "TipoEstadoVotacion.findByNombreEstado", query = "SELECT t FROM TipoEstadoVotacion t WHERE t.nombreEstado = :nombreEstado")})
public class TipoEstadoVotacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TIPO_ESTADO_VOTACION")
    private Integer idTipoEstadoVotacion;
    @Size(max = 50)
    @Column(name = "NOMBRE_ESTADO")
    private String nombreEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoEstadoVotacion")
    private Collection<EstadoVotacion> estadoVotacionCollection;

    public TipoEstadoVotacion() {
    }

    public TipoEstadoVotacion(Integer idTipoEstadoVotacion) {
        this.idTipoEstadoVotacion = idTipoEstadoVotacion;
    }

    public Integer getIdTipoEstadoVotacion() {
        return idTipoEstadoVotacion;
    }

    public void setIdTipoEstadoVotacion(Integer idTipoEstadoVotacion) {
        this.idTipoEstadoVotacion = idTipoEstadoVotacion;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    @XmlTransient
    public Collection<EstadoVotacion> getEstadoVotacionCollection() {
        return estadoVotacionCollection;
    }

    public void setEstadoVotacionCollection(Collection<EstadoVotacion> estadoVotacionCollection) {
        this.estadoVotacionCollection = estadoVotacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoEstadoVotacion != null ? idTipoEstadoVotacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEstadoVotacion)) {
            return false;
        }
        TipoEstadoVotacion other = (TipoEstadoVotacion) object;
        if ((this.idTipoEstadoVotacion == null && other.idTipoEstadoVotacion != null) || (this.idTipoEstadoVotacion != null && !this.idTipoEstadoVotacion.equals(other.idTipoEstadoVotacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.poli.gerencia.votacion.modelo.vo.TipoEstadoVotacion[ idTipoEstadoVotacion=" + idTipoEstadoVotacion + " ]";
    }
    
}
