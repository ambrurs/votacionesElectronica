package edu.poli.gerencia.votaciones.modelo.dto;

import java.io.Serializable;
import java.util.Date;
import edu.poli.gerencia.votaciones.modelo.vo.TokensAccion;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class TokensAccionDTO implements Serializable {

    
    private Integer id;
    private String asunto;
    private String token;
    private Integer idAutor;
    private Date fechaCreacion;
    private Date actualizado;
    private Boolean estado;
    
    public TokensAccionDTO(){
    }
    
    public TokensAccionDTO(Integer id){
        this.id = id;
    }
    
    public TokensAccionDTO(Integer id, String asunto, String token, Integer idAutor, Date fechaCreacion, Date actualizado, Boolean estado){
        this.id = id;
	this.asunto = asunto;
	this.token = token;
	this.idAutor = idAutor;
	this.fechaCreacion = fechaCreacion;
	this.actualizado = actualizado;
	this.estado = estado;

    }
    
    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getAsunto(){
        return asunto;
    }

    public void setAsunto(String asunto){
        this.asunto = asunto;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public Integer getIdAutor(){
        return idAutor;
    }

    public void setIdAutor(Integer idAutor){
        this.idAutor = idAutor;
    }

    public Date getFechaCreacion(){
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }

    public Date getActualizado(){
        return actualizado;
    }

    public void setActualizado(Date actualizado){
        this.actualizado = actualizado;
    }

    public Boolean getEstado(){
        return estado;
    }

    public void setEstado(Boolean estado){
        this.estado = estado;
    }

    
    public String toString(){
        return "id :: "+this.id+"; "+
	"asunto :: "+this.asunto+"; "+
	"token :: "+this.token+"; "+
	"idAutor :: "+this.idAutor+"; "+
	"fechaCreacion :: "+this.fechaCreacion+"; "+
	"actualizado :: "+this.actualizado+"; "+
	"estado :: "+this.estado+"; "+
	"";
    }
    
}
