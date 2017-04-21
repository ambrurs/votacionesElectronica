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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jhon1
 */
@Entity
@Table(name = "tipo_votacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoVotacion.findAll", query = "SELECT t FROM TipoVotacion t")
    , @NamedQuery(name = "TipoVotacion.findByIdTipoVotacion", query = "SELECT t FROM TipoVotacion t WHERE t.idTipoVotacion = :idTipoVotacion")
    , @NamedQuery(name = "TipoVotacion.findByNombreTipoVotacion", query = "SELECT t FROM TipoVotacion t WHERE t.nombreTipoVotacion = :nombreTipoVotacion")})
public class TipoVotacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TIPO_VOTACION")
    private Integer idTipoVotacion;
    @Size(max = 100)
    @Column(name = "NOMBRE_TIPO_VOTACION")
    private String nombreTipoVotacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoVotacion")
    private Collection<Votacion> votacionCollection;

    public TipoVotacion() {
    }

    public TipoVotacion(Integer idTipoVotacion) {
        this.idTipoVotacion = idTipoVotacion;
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

    @XmlTransient
    public Collection<Votacion> getVotacionCollection() {
        return votacionCollection;
    }

    public void setVotacionCollection(Collection<Votacion> votacionCollection) {
        this.votacionCollection = votacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoVotacion != null ? idTipoVotacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoVotacion)) {
            return false;
        }
        TipoVotacion other = (TipoVotacion) object;
        if ((this.idTipoVotacion == null && other.idTipoVotacion != null) || (this.idTipoVotacion != null && !this.idTipoVotacion.equals(other.idTipoVotacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.poli.gerencia.votacion.modelo.vo.TipoVotacion[ idTipoVotacion=" + idTipoVotacion + " ]";
    }
    
}
