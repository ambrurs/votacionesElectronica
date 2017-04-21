/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jhon1
 */
@Entity
@Table(name = "estado_votacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoVotacion.findAll", query = "SELECT e FROM EstadoVotacion e")
    , @NamedQuery(name = "EstadoVotacion.findByConsEstadoVotacion", query = "SELECT e FROM EstadoVotacion e WHERE e.consEstadoVotacion = :consEstadoVotacion")
    , @NamedQuery(name = "EstadoVotacion.findByActivo", query = "SELECT e FROM EstadoVotacion e WHERE e.activo = :activo")
    , @NamedQuery(name = "EstadoVotacion.findByFechaRegistro", query = "SELECT e FROM EstadoVotacion e WHERE e.fechaRegistro = :fechaRegistro")})
public class EstadoVotacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONS_ESTADO_VOTACION")
    private Integer consEstadoVotacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVO")
    private Character activo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @JoinColumn(name = "ID_TIPO_ESTADO_VOTACION", referencedColumnName = "ID_TIPO_ESTADO_VOTACION")
    @ManyToOne(optional = false)
    private TipoEstadoVotacion idTipoEstadoVotacion;
    @JoinColumn(name = "CONS_VOTACION", referencedColumnName = "CONS_VOTACION")
    @ManyToOne(optional = false)
    private Votacion consVotacion;

    public EstadoVotacion() {
    }

    public EstadoVotacion(Integer consEstadoVotacion) {
        this.consEstadoVotacion = consEstadoVotacion;
    }

    public EstadoVotacion(Integer consEstadoVotacion, Character activo, Date fechaRegistro) {
        this.consEstadoVotacion = consEstadoVotacion;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getConsEstadoVotacion() {
        return consEstadoVotacion;
    }

    public void setConsEstadoVotacion(Integer consEstadoVotacion) {
        this.consEstadoVotacion = consEstadoVotacion;
    }

    public Character getActivo() {
        return activo;
    }

    public void setActivo(Character activo) {
        this.activo = activo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public TipoEstadoVotacion getIdTipoEstadoVotacion() {
        return idTipoEstadoVotacion;
    }

    public void setIdTipoEstadoVotacion(TipoEstadoVotacion idTipoEstadoVotacion) {
        this.idTipoEstadoVotacion = idTipoEstadoVotacion;
    }

    public Votacion getConsVotacion() {
        return consVotacion;
    }

    public void setConsVotacion(Votacion consVotacion) {
        this.consVotacion = consVotacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consEstadoVotacion != null ? consEstadoVotacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoVotacion)) {
            return false;
        }
        EstadoVotacion other = (EstadoVotacion) object;
        if ((this.consEstadoVotacion == null && other.consEstadoVotacion != null) || (this.consEstadoVotacion != null && !this.consEstadoVotacion.equals(other.consEstadoVotacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.poli.gerencia.votacion.modelo.vo.EstadoVotacion[ consEstadoVotacion=" + consEstadoVotacion + " ]";
    }
    
}
