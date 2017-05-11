package edu.poli.gerencia.votaciones.modelo.vo;

import java.io.Serializable;
import java.util.Date;
import edu.poli.gerencia.votaciones.modelo.vo.TipoUsuario;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class Usuario implements Serializable {

    
    private Integer consUsuario;
    private TipoUsuario idTipoUsuario;
    private String nombreUsuario;
    private String contrasena;
    private String activo;
    private Date ultimoIngreso;
    private Date fechaRegistro;
    private Date fechaActualizacion;
    
    public Usuario(){
    }
    
    public Usuario(Integer consUsuario){
        this.consUsuario = consUsuario;
    }
    
    public Usuario(Integer consUsuario, TipoUsuario idTipoUsuario, String nombreUsuario, String contrasena, String activo, Date ultimoIngreso, Date fechaRegistro, Date fechaActualizacion){
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

    
    public Usuario equalize(Usuario usuario){
        if(usuario.getConsUsuario() != null){this.setConsUsuario(usuario.getConsUsuario());}
	if(usuario.getIdTipoUsuario() != null){this.setIdTipoUsuario(usuario.getIdTipoUsuario());}
	if(usuario.getNombreUsuario() != null){this.setNombreUsuario(usuario.getNombreUsuario());}
	if(usuario.getContrasena() != null){this.setContrasena(usuario.getContrasena());}
	if(usuario.getActivo() != null){this.setActivo(usuario.getActivo());}
	if(usuario.getUltimoIngreso() != null){this.setUltimoIngreso(usuario.getUltimoIngreso());}
	if(usuario.getFechaRegistro() != null){this.setFechaRegistro(usuario.getFechaRegistro());}
	if(usuario.getFechaActualizacion() != null){this.setFechaActualizacion(usuario.getFechaActualizacion());}
	
        return this;
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
