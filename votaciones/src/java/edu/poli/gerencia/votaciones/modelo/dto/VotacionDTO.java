package edu.poli.gerencia.votaciones.modelo.dto;

import java.io.Serializable;
import java.util.Date;
import edu.poli.gerencia.votaciones.modelo.vo.TipoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class VotacionDTO implements Serializable {

    
    private Integer consVotacion;
    private Date fechaInicioInscripcion;
    private Date fechaFinInscripcion;
    private TipoVotacion idTipoVotacion;
    private Date fechaInicioVotacion;
    private Date fechaFinVotacion;
    private Usuario consUsuarioCreacion;
    
    public VotacionDTO(){
    }
    
    public VotacionDTO(Integer consVotacion){
        this.consVotacion = consVotacion;
    }
    
    public VotacionDTO(Integer consVotacion, Date fechaInicioInscripcion, Date fechaFinInscripcion, TipoVotacion idTipoVotacion, Date fechaInicioVotacion, Date fechaFinVotacion, Usuario consUsuarioCreacion){
        this.consVotacion = consVotacion;
	this.fechaInicioInscripcion = fechaInicioInscripcion;
	this.fechaFinInscripcion = fechaFinInscripcion;
	this.idTipoVotacion = idTipoVotacion;
	this.fechaInicioVotacion = fechaInicioVotacion;
	this.fechaFinVotacion = fechaFinVotacion;
	this.consUsuarioCreacion = consUsuarioCreacion;

    }
    
    public Integer getConsVotacion(){
        return consVotacion;
    }

    public void setConsVotacion(Integer consVotacion){
        this.consVotacion = consVotacion;
    }

    public Date getFechaInicioInscripcion(){
        return fechaInicioInscripcion;
    }

    public void setFechaInicioInscripcion(Date fechaInicioInscripcion){
        this.fechaInicioInscripcion = fechaInicioInscripcion;
    }

    public Date getFechaFinInscripcion(){
        return fechaFinInscripcion;
    }

    public void setFechaFinInscripcion(Date fechaFinInscripcion){
        this.fechaFinInscripcion = fechaFinInscripcion;
    }

    public TipoVotacion getIdTipoVotacion(){
        return idTipoVotacion;
    }

    public void setIdTipoVotacion(TipoVotacion idTipoVotacion){
        this.idTipoVotacion = idTipoVotacion;
    }

    public Date getFechaInicioVotacion(){
        return fechaInicioVotacion;
    }

    public void setFechaInicioVotacion(Date fechaInicioVotacion){
        this.fechaInicioVotacion = fechaInicioVotacion;
    }

    public Date getFechaFinVotacion(){
        return fechaFinVotacion;
    }

    public void setFechaFinVotacion(Date fechaFinVotacion){
        this.fechaFinVotacion = fechaFinVotacion;
    }

    public Usuario getConsUsuarioCreacion(){
        return consUsuarioCreacion;
    }

    public void setConsUsuarioCreacion(Usuario consUsuarioCreacion){
        this.consUsuarioCreacion = consUsuarioCreacion;
    }

    
    public String toString(){
        return "consVotacion :: "+this.consVotacion+"; "+
	"fechaInicioInscripcion :: "+this.fechaInicioInscripcion+"; "+
	"fechaFinInscripcion :: "+this.fechaFinInscripcion+"; "+
	"idTipoVotacion :: "+this.idTipoVotacion+"; "+
	"fechaInicioVotacion :: "+this.fechaInicioVotacion+"; "+
	"fechaFinVotacion :: "+this.fechaFinVotacion+"; "+
	"consUsuarioCreacion :: "+this.consUsuarioCreacion+"; "+
	"";
    }
    
}
