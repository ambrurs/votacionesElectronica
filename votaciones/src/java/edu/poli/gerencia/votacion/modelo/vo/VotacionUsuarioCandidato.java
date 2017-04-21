/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.vo;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jhon1
 */
@Entity
@Table(name = "votacion_usuario_candidato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VotacionUsuarioCandidato.findAll", query = "SELECT v FROM VotacionUsuarioCandidato v")
    , @NamedQuery(name = "VotacionUsuarioCandidato.findByConsUsuarioCandidato", query = "SELECT v FROM VotacionUsuarioCandidato v WHERE v.votacionUsuarioCandidatoPK.consUsuarioCandidato = :consUsuarioCandidato")
    , @NamedQuery(name = "VotacionUsuarioCandidato.findByCandidatoVotacionConsCandidato", query = "SELECT v FROM VotacionUsuarioCandidato v WHERE v.votacionUsuarioCandidatoPK.candidatoVotacionConsCandidato = :candidatoVotacionConsCandidato")
    , @NamedQuery(name = "VotacionUsuarioCandidato.findByUsuarioConsUsuario", query = "SELECT v FROM VotacionUsuarioCandidato v WHERE v.votacionUsuarioCandidatoPK.usuarioConsUsuario = :usuarioConsUsuario")})
public class VotacionUsuarioCandidato implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VotacionUsuarioCandidatoPK votacionUsuarioCandidatoPK;
    @JoinColumn(name = "CANDIDATO_VOTACION_CONS_CANDIDATO", referencedColumnName = "CONS_CANDIDATO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CandidatoVotacion candidatoVotacion;
    @JoinColumn(name = "USUARIO_CONS_USUARIO", referencedColumnName = "CONS_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public VotacionUsuarioCandidato() {
    }

    public VotacionUsuarioCandidato(VotacionUsuarioCandidatoPK votacionUsuarioCandidatoPK) {
        this.votacionUsuarioCandidatoPK = votacionUsuarioCandidatoPK;
    }

    public VotacionUsuarioCandidato(int consUsuarioCandidato, int candidatoVotacionConsCandidato, int usuarioConsUsuario) {
        this.votacionUsuarioCandidatoPK = new VotacionUsuarioCandidatoPK(consUsuarioCandidato, candidatoVotacionConsCandidato, usuarioConsUsuario);
    }

    public VotacionUsuarioCandidatoPK getVotacionUsuarioCandidatoPK() {
        return votacionUsuarioCandidatoPK;
    }

    public void setVotacionUsuarioCandidatoPK(VotacionUsuarioCandidatoPK votacionUsuarioCandidatoPK) {
        this.votacionUsuarioCandidatoPK = votacionUsuarioCandidatoPK;
    }

    public CandidatoVotacion getCandidatoVotacion() {
        return candidatoVotacion;
    }

    public void setCandidatoVotacion(CandidatoVotacion candidatoVotacion) {
        this.candidatoVotacion = candidatoVotacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (votacionUsuarioCandidatoPK != null ? votacionUsuarioCandidatoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotacionUsuarioCandidato)) {
            return false;
        }
        VotacionUsuarioCandidato other = (VotacionUsuarioCandidato) object;
        if ((this.votacionUsuarioCandidatoPK == null && other.votacionUsuarioCandidatoPK != null) || (this.votacionUsuarioCandidatoPK != null && !this.votacionUsuarioCandidatoPK.equals(other.votacionUsuarioCandidatoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.poli.gerencia.votacion.modelo.vo.VotacionUsuarioCandidato[ votacionUsuarioCandidatoPK=" + votacionUsuarioCandidatoPK + " ]";
    }
    
}
