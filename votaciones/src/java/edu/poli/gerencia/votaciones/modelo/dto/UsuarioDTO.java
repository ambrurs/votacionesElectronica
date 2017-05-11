package edu.poli.gerencia.votaciones.modelo.dto;

import java.io.Serializable;
import java.util.Date;
import edu.poli.gerencia.votaciones.modelo.vo.TipoUsuario;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class UsuarioDTO implements Serializable {

    
    private Integer consUsuario;
    private TipoUsuario idTipoUsuario;
    private String nombreUsuario;
    private String contrasena;
    private String activo;
    private Date ultimoIngreso;
    private Date fechaRegistro;
    private Date fechaActualizacion;
    
    public UsuarioDTO(){
    }
    
    public UsuarioDTO(Integer consUsuario){
        this.consUsuario = consUsuario;
    }
    
    public UsuarioDTO(Integer consUsuario, TipoUsuario idTipoUsuario, String nombreUsuario, String contrasena, String activo, Date ultimoIngreso, Date fechaRegistro, Date fechaActualizacion){
        this.consUsuario = consUsuario;
	this.idTipoUsuario = idTipoUsuario;
	this.nombreUsuario = nombreUsuario;
	this.contrasena = contrasena;
	this.activo = activo;
	this.ultimoIngreso = ultimoIngreso;
	this.fechaRegistro = fechaRegistro;
	this.fechaActualizacion = fechaActualizacion;

    }
    
    public Integer getConsUsuario(){
        return consUsuario;
    }

    public void setConsUsuario(Integer consUsuario){
        this.consUsuario = consUsuario;
    }

    public TipoUsuario getIdTipoUsuario(){
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(TipoUsuario idTipoUsuario){
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getNombreUsuario(){
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena(){
        return contrasena;
    }

    public void setContrasena(String contrasena){
        this.contrasena = contrasena;
    }

    public String getActivo(){
        return activo;
    }

    public void setActivo(String activo){
        this.activo = activo;
    }

    public Date getUltimoIngreso(){
        return ultimoIngreso;
    }

    public void setUltimoIngreso(Date ultimoIngreso){
        this.ultimoIngreso = ultimoIngreso;
    }

    public Date getFechaRegistro(){
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro){
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaActualizacion(){
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion){
        this.fechaActualizacion = fechaActualizacion;
    }

    
    public String toString(){
        return "consUsuario :: "+this.consUsuario+"; "+
	"idTipoUsuario :: "+this.idTipoUsuario+"; "+
	"nombreUsuario :: "+this.nombreUsuario+"; "+
	"contrasena :: "+this.contrasena+"; "+
	"activo :: "+this.activo+"; "+
	"ultimoIngreso :: "+this.ultimoIngreso+"; "+
	"fechaRegistro :: "+this.fechaRegistro+"; "+
	"fechaActualizacion :: "+this.fechaActualizacion+"; "+
	"";
    }
    
}
