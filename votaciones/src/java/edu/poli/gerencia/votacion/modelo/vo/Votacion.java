/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jhon1
 */
@Entity
@Table(name = "votacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Votacion.findAll", query = "SELECT v FROM Votacion v")
    , @NamedQuery(name = "Votacion.findByConsVotacion", query = "SELECT v FROM Votacion v WHERE v.consVotacion = :consVotacion")
    , @NamedQuery(name = "Votacion.findByFechaInicioInscripcion", query = "SELECT v FROM Votacion v WHERE v.fechaInicioInscripcion = :fechaInicioInscripcion")
    , @NamedQuery(name = "Votacion.findByFechaFinInscripcion", query = "SELECT v FROM Votacion v WHERE v.fechaFinInscripcion = :fechaFinInscripcion")
    , @NamedQuery(name = "Votacion.findByFechaInicioVotacion", query = "SELECT v FROM Votacion v WHERE v.fechaInicioVotacion = :fechaInicioVotacion")
    , @NamedQuery(name = "Votacion.findByFechaFinVotacion", query = "SELECT v FROM Votacion v WHERE v.fechaFinVotacion = :fechaFinVotacion")})
public class Votacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONS_VOTACION")
    private Integer consVotacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_INICIO_INSCRIPCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioInscripcion;
    @Column(name = "FECHA_FIN_INSCRIPCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinInscripcion;
    @Column(name = "FECHA_INICIO_VOTACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioVotacion;
    @Column(name = "FECHA_FIN _VOTACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinVotacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "votacionConsVotacion")
    private Collection<CandidatoVotacion> candidatoVotacionCollection;
    @JoinColumn(name = "ID_TIPO_VOTACION", referencedColumnName = "ID_TIPO_VOTACION")
    @ManyToOne(optional = false)
    private TipoVotacion idTipoVotacion;
    @JoinColumn(name = "CONS_USUARIO_CREACION", referencedColumnName = "CONS_USUARIO")
    @ManyToOne(optional = false)
    private Usuario consUsuarioCreacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "consVotacion")
    private Collection<EstadoVotacion> estadoVotacionCollection;

    public Votacion() {
    }

    public Votacion(Integer consVotacion) {
        this.consVotacion = consVotacion;
    }

    public Votacion(Integer consVotacion, Date fechaInicioInscripcion) {
        this.consVotacion = consVotacion;
        this.fechaInicioInscripcion = fechaInicioInscripcion;
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

    @XmlTransient
    public Collection<CandidatoVotacion> getCandidatoVotacionCollection() {
        return candidatoVotacionCollection;
    }

    public void setCandidatoVotacionCollection(Collection<CandidatoVotacion> candidatoVotacionCollection) {
        this.candidatoVotacionCollection = candidatoVotacionCollection;
    }

    public TipoVotacion getIdTipoVotacion() {
        return idTipoVotacion;
    }

    public void setIdTipoVotacion(TipoVotacion idTipoVotacion) {
        this.idTipoVotacion = idTipoVotacion;
    }

    public Usuario getConsUsuarioCreacion() {
        return consUsuarioCreacion;
    }

    public void setConsUsuarioCreacion(Usuario consUsuarioCreacion) {
        this.consUsuarioCreacion = consUsuarioCreacion;
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
        hash += (consVotacion != null ? consVotacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Votacion)) {
            return false;
        }
        Votacion other = (Votacion) object;
        if ((this.consVotacion == null && other.consVotacion != null) || (this.consVotacion != null && !this.consVotacion.equals(other.consVotacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.poli.gerencia.votacion.modelo.vo.Votacion[ consVotacion=" + consVotacion + " ]";
    }
    
}
