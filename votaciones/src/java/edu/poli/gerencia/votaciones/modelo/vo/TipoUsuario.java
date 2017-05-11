package edu.poli.gerencia.votaciones.modelo.vo;

import java.io.Serializable;
import edu.poli.gerencia.votaciones.modelo.vo.TipoUsuario;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class TipoUsuario implements Serializable {

    
    private Integer idTipoUsuario;
    private String descripcion;
    private Boolean publico;
    
    public TipoUsuario(){
    }
    
    public TipoUsuario(Integer idTipoUsuario){
        this.idTipoUsuario = idTipoUsuario;
    }
    
    public TipoUsuario(Integer idTipoUsuario, String descripcion, Boolean publico){
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

    
    public TipoUsuario equalize(TipoUsuario tipoUsuario){
        if(tipoUsuario.getIdTipoUsuario() != null){this.setIdTipoUsuario(tipoUsuario.getIdTipoUsuario());}
	if(tipoUsuario.getDescripcion() != null){this.setDescripcion(tipoUsuario.getDescripcion());}
	if(tipoUsuario.getPublico() != null){this.setPublico(tipoUsuario.getPublico());}
	
        return this;
    }
    
    public String toString(){
        return "idTipoUsuario :: "+this.idTipoUsuario+"; "+
	"descripcion :: "+this.descripcion+"; "+
	"publico :: "+this.publico+"; "+
	"";
    }
    
}
