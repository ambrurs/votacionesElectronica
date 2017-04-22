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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jhon1
 */
@Entity
@Table(name = "tokens_accion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TokensAccion.findAll", query = "SELECT t FROM TokensAccion t")
    , @NamedQuery(name = "TokensAccion.findById", query = "SELECT t FROM TokensAccion t WHERE t.id = :id")
    , @NamedQuery(name = "TokensAccion.findByAsunto", query = "SELECT t FROM TokensAccion t WHERE t.asunto = :asunto")
    , @NamedQuery(name = "TokensAccion.findByToken", query = "SELECT t FROM TokensAccion t WHERE t.token = :token")
    , @NamedQuery(name = "TokensAccion.findByIdAutor", query = "SELECT t FROM TokensAccion t WHERE t.idAutor = :idAutor")
    , @NamedQuery(name = "TokensAccion.findByFechaCreacion", query = "SELECT t FROM TokensAccion t WHERE t.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "TokensAccion.findByActualizado", query = "SELECT t FROM TokensAccion t WHERE t.actualizado = :actualizado")
    , @NamedQuery(name = "TokensAccion.findByEstado", query = "SELECT t FROM TokensAccion t WHERE t.estado = :estado")})
public class TokensAccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "asunto")
    private String asunto;
    @Size(max = 350)
    @Column(name = "token")
    private String token;
    @Column(name = "id_autor")
    private Integer idAutor;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;
    @Column(name = "estado")
    private Boolean estado;

    public TokensAccion() {
    }

    public TokensAccion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getActualizado() {
        return actualizado;
    }

    public void setActualizado(Date actualizado) {
        this.actualizado = actualizado;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TokensAccion)) {
            return false;
        }
        TokensAccion other = (TokensAccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.poli.gerencia.votacion.modelo.vo.TokensAccion[ id=" + id + " ]";
    }
    
}
