/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.vo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jhon1
 */
@Embeddable
public class VotacionUsuarioCandidatoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "CONS_USUARIO_CANDIDATO")
    private int consUsuarioCandidato;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANDIDATO_VOTACION_CONS_CANDIDATO")
    private int candidatoVotacionConsCandidato;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USUARIO_CONS_USUARIO")
    private int usuarioConsUsuario;

    public VotacionUsuarioCandidatoPK() {
    }

    public VotacionUsuarioCandidatoPK(int consUsuarioCandidato, int candidatoVotacionConsCandidato, int usuarioConsUsuario) {
        this.consUsuarioCandidato = consUsuarioCandidato;
        this.candidatoVotacionConsCandidato = candidatoVotacionConsCandidato;
        this.usuarioConsUsuario = usuarioConsUsuario;
    }

    public int getConsUsuarioCandidato() {
        return consUsuarioCandidato;
    }

    public void setConsUsuarioCandidato(int consUsuarioCandidato) {
        this.consUsuarioCandidato = consUsuarioCandidato;
    }

    public int getCandidatoVotacionConsCandidato() {
        return candidatoVotacionConsCandidato;
    }

    public void setCandidatoVotacionConsCandidato(int candidatoVotacionConsCandidato) {
        this.candidatoVotacionConsCandidato = candidatoVotacionConsCandidato;
    }

    public int getUsuarioConsUsuario() {
        return usuarioConsUsuario;
    }

    public void setUsuarioConsUsuario(int usuarioConsUsuario) {
        this.usuarioConsUsuario = usuarioConsUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) consUsuarioCandidato;
        hash += (int) candidatoVotacionConsCandidato;
        hash += (int) usuarioConsUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotacionUsuarioCandidatoPK)) {
            return false;
        }
        VotacionUsuarioCandidatoPK other = (VotacionUsuarioCandidatoPK) object;
        if (this.consUsuarioCandidato != other.consUsuarioCandidato) {
            return false;
        }
        if (this.candidatoVotacionConsCandidato != other.candidatoVotacionConsCandidato) {
            return false;
        }
        if (this.usuarioConsUsuario != other.usuarioConsUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.poli.gerencia.votacion.modelo.vo.VotacionUsuarioCandidatoPK[ consUsuarioCandidato=" + consUsuarioCandidato + ", candidatoVotacionConsCandidato=" + candidatoVotacionConsCandidato + ", usuarioConsUsuario=" + usuarioConsUsuario + " ]";
    }
    
}
