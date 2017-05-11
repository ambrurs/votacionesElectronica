package edu.poli.gerencia.votaciones.modelo.dto;

import java.io.Serializable;
import edu.poli.gerencia.votaciones.modelo.vo.TipoUsuario;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class TipoUsuarioDTO implements Serializable {

    
    private Integer idTipoUsuario;
    private String descripcion;
    private Boolean publico;
    
    public TipoUsuarioDTO(){
    }
    
    public TipoUsuarioDTO(Integer idTipoUsuario){
        this.idTipoUsuario = idTipoUsuario;
    }
    
    public TipoUsuarioDTO(Integer idTipoUsuario, String descripcion, Boolean publico){
        this.idTipoUsuario = idTipoUsuario;
	this.descripcion = descripcion;
	this.publico = publico;

    }
    
    public Integer getIdTipoUsuario(){
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(Integer idTipoUsuario){
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public Boolean getPublico(){
        return publico;
    }

    public void setPublico(Boolean publico){
        this.publico = publico;
    }

    
    public String toString(){
        return "idTipoUsuario :: "+this.idTipoUsuario+"; "+
	"descripcion :: "+this.descripcion+"; "+
	"publico :: "+this.publico+"; "+
	"";
    }
    
}
