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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jhon1
 */
@Entity
@Table(name = "candidato_votacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CandidatoVotacion.findAll", query = "SELECT c FROM CandidatoVotacion c")
    , @NamedQuery(name = "CandidatoVotacion.findByConsCandidato", query = "SELECT c FROM CandidatoVotacion c WHERE c.consCandidato = :consCandidato")
    , @NamedQuery(name = "CandidatoVotacion.findByCantidadVotos", query = "SELECT c FROM CandidatoVotacion c WHERE c.cantidadVotos = :cantidadVotos")})
public class CandidatoVotacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONS_CANDIDATO")
    private Integer consCandidato;
    @Column(name = "CANTIDAD_VOTOS")
    private Integer cantidadVotos;
    @JoinColumn(name = "CONS_USUARIO_VOTACION", referencedColumnName = "CONS_USUARIO")
    @ManyToOne(optional = false)
    private Usuario consUsuarioVotacion;
    @JoinColumn(name = "VOTACION_CONS_VOTACION", referencedColumnName = "CONS_VOTACION")
    @ManyToOne(optional = false)
    private Votacion votacionConsVotacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidatoVotacion")
    private Collection<VotacionUsuarioCandidato> votacionUsuarioCandidatoCollection;

    public CandidatoVotacion() {
    }

    public CandidatoVotacion(Integer consCandidato) {
        this.consCandidato = consCandidato;
    }

    public Integer getConsCandidato() {
        return consCandidato;
    }

    public void setConsCandidato(Integer consCandidato) {
        this.consCandidato = consCandidato;
    }

    public Integer getCantidadVotos() {
        return cantidadVotos;
    }

    public void setCantidadVotos(Integer cantidadVotos) {
        this.cantidadVotos = cantidadVotos;
    }

    public Usuario getConsUsuarioVotacion() {
        return consUsuarioVotacion;
    }

    public void setConsUsuarioVotacion(Usuario consUsuarioVotacion) {
        this.consUsuarioVotacion = consUsuarioVotacion;
    }

    public Votacion getVotacionConsVotacion() {
        return votacionConsVotacion;
    }

    public void setVotacionConsVotacion(Votacion votacionConsVotacion) {
        this.votacionConsVotacion = votacionConsVotacion;
    }

    @XmlTransient
    public Collection<VotacionUsuarioCandidato> getVotacionUsuarioCandidatoCollection() {
        return votacionUsuarioCandidatoCollection;
    }

    public void setVotacionUsuarioCandidatoCollection(Collection<VotacionUsuarioCandidato> votacionUsuarioCandidatoCollection) {
        this.votacionUsuarioCandidatoCollection = votacionUsuarioCandidatoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consCandidato != null ? consCandidato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CandidatoVotacion)) {
            return false;
        }
        CandidatoVotacion other = (CandidatoVotacion) object;
        if ((this.consCandidato == null && other.consCandidato != null) || (this.consCandidato != null && !this.consCandidato.equals(other.consCandidato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.poli.gerencia.votacion.modelo.vo.CandidatoVotacion[ consCandidato=" + consCandidato + " ]";
    }
    
}
