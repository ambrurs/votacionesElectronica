package edu.poli.gerencia.votaciones.modelo.vo;

import java.io.Serializable;
import java.util.Date;
import edu.poli.gerencia.votaciones.modelo.vo.EstadoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.TipoEstadoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class EstadoVotacion implements Serializable {

    
    private Integer consEstadoVotacion;
    private String activo;
    private Date fechaRegistro;
    private TipoEstadoVotacion idTipoEstadoVotacion;
    private Votacion consVotacion;
    
    public EstadoVotacion(){
    }
    
    public EstadoVotacion(Integer consEstadoVotacion){
        this.consEstadoVotacion = consEstadoVotacion;
    }
    
    public EstadoVotacion(Integer consEstadoVotacion, String activo, Date fechaRegistro, TipoEstadoVotacion idTipoEstadoVotacion, Votacion consVotacion){
        this.consEstadoVotacion = consEstadoVotacion;
	this.activo = activo;
	this.fechaRegistro = fechaRegistro;
	this.idTipoEstadoVotacion = idTipoEstadoVotacion;
	this.consVotacion = consVotacion;

    }
    
    public Integer getConsEstadoVotacion(){
        return consEstadoVotacion;
    }

    public void setConsEstadoVotacion(Integer consEstadoVotacion){
        this.consEstadoVotacion = consEstadoVotacion;
    }

    public String getActivo(){
        return activo;
    }

    public void setActivo(String activo){
        this.activo = activo;
    }

    public Date getFechaRegistro(){
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro){
        this.fechaRegistro = fechaRegistro;
    }

    public TipoEstadoVotacion getIdTipoEstadoVotacion(){
        return idTipoEstadoVotacion;
    }

    public void setIdTipoEstadoVotacion(TipoEstadoVotacion idTipoEstadoVotacion){
        this.idTipoEstadoVotacion = idTipoEstadoVotacion;
    }

    public Votacion getConsVotacion(){
        return consVotacion;
    }

    public void setConsVotacion(Votacion consVotacion){
        this.consVotacion = consVotacion;
    }

    
    public EstadoVotacion equalize(EstadoVotacion estadoVotacion){
        if(estadoVotacion.getConsEstadoVotacion() != null){this.setConsEstadoVotacion(estadoVotacion.getConsEstadoVotacion());}
	if(estadoVotacion.getActivo() != null){this.setActivo(estadoVotacion.getActivo());}
	if(estadoVotacion.getFechaRegistro() != null){this.setFechaRegistro(estadoVotacion.getFechaRegistro());}
	if(estadoVotacion.getIdTipoEstadoVotacion() != null){this.setIdTipoEstadoVotacion(estadoVotacion.getIdTipoEstadoVotacion());}
	if(estadoVotacion.getConsVotacion() != null){this.setConsVotacion(estadoVotacion.getConsVotacion());}
	
        return this;
    }
    
    public String toString(){
        return "consEstadoVotacion :: "+this.consEstadoVotacion+"; "+
	"activo :: "+this.activo+"; "+
	"fechaRegistro :: "+this.fechaRegistro+"; "+
	"idTipoEstadoVotacion :: "+this.idTipoEstadoVotacion+"; "+
	"consVotacion :: "+this.consVotacion+"; "+
	"";
    }
    
}
