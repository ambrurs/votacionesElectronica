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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByConsUsuario", query = "SELECT u FROM Usuario u WHERE u.consUsuario = :consUsuario")
    , @NamedQuery(name = "Usuario.findByNombreUsuario", query = "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    , @NamedQuery(name = "Usuario.findByContrasena", query = "SELECT u FROM Usuario u WHERE u.contrasena = :contrasena")
    , @NamedQuery(name = "Usuario.findByActivo", query = "SELECT u FROM Usuario u WHERE u.activo = :activo")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CONS_USUARIO")
    private Integer consUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 350)
    @Column(name = "CONTRASENA")
    private String contrasena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVO")
    private Character activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "consUsuario")
    private Collection<Persona> personaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "consUsuarioVotacion")
    private Collection<CandidatoVotacion> candidatoVotacionCollection;
    @JoinColumn(name = "ID_TIPO_USUARIO", referencedColumnName = "ID_TIPO_USUARIO")
    @ManyToOne(optional = false)
    private TipoUsuario idTipoUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<VotacionUsuarioCandidato> votacionUsuarioCandidatoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "consUsuarioCreacion")
    private Collection<Votacion> votacionCollection;

    public Usuario() {
    }

    public Usuario(Integer consUsuario) {
        this.consUsuario = consUsuario;
    }

    public Usuario(Integer consUsuario, String nombreUsuario, String contrasena, Character activo) {
        this.consUsuario = consUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.activo = activo;
    }

    public Integer getConsUsuario() {
        return consUsuario;
    }

    public void setConsUsuario(Integer consUsuario) {
        this.consUsuario = consUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Character getActivo() {
        return activo;
    }

    public void setActivo(Character activo) {
        this.activo = activo;
    }

    @XmlTransient
    public Collection<Persona> getPersonaCollection() {
        return personaCollection;
    }

    public void setPersonaCollection(Collection<Persona> personaCollection) {
        this.personaCollection = personaCollection;
    }

    @XmlTransient
    public Collection<CandidatoVotacion> getCandidatoVotacionCollection() {
        return candidatoVotacionCollection;
    }

    public void setCandidatoVotacionCollection(Collection<CandidatoVotacion> candidatoVotacionCollection) {
        this.candidatoVotacionCollection = candidatoVotacionCollection;
    }

    public TipoUsuario getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(TipoUsuario idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    @XmlTransient
    public Collection<VotacionUsuarioCandidato> getVotacionUsuarioCandidatoCollection() {
        return votacionUsuarioCandidatoCollection;
    }

    public void setVotacionUsuarioCandidatoCollection(Collection<VotacionUsuarioCandidato> votacionUsuarioCandidatoCollection) {
        this.votacionUsuarioCandidatoCollection = votacionUsuarioCandidatoCollection;
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
        hash += (consUsuario != null ? consUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.consUsuario == null && other.consUsuario != null) || (this.consUsuario != null && !this.consUsuario.equals(other.consUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.poli.gerencia.votacion.modelo.vo.Usuario[ consUsuario=" + consUsuario + " ]";
    }
    
}
