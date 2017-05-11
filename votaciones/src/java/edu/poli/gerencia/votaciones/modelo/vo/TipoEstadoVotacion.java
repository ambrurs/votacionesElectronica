package edu.poli.gerencia.votaciones.modelo.vo;

import java.io.Serializable;
import edu.poli.gerencia.votaciones.modelo.vo.EstadoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.TipoEstadoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class TipoEstadoVotacion implements Serializable {

    
    private Integer idTipoEstadoVotacion;
    private String nombreEstado;
    
    public TipoEstadoVotacion(){
    }
    
    public TipoEstadoVotacion(Integer idTipoEstadoVotacion){
        this.idTipoEstadoVotacion = idTipoEstadoVotacion;
    }
    
    public TipoEstadoVotacion(Integer idTipoEstadoVotacion, String nombreEstado){
        this.idTipoEstadoVotacion = idTipoEstadoVotacion;
	this.nombreEstado = nombreEstado;

    }
    
    public Integer getIdTipoEstadoVotacion(){
        return idTipoEstadoVotacion;
    }

    public void setIdTipoEstadoVotacion(Integer idTipoEstadoVotacion){
        this.idTipoEstadoVotacion = idTipoEstadoVotacion;
    }

    public String getNombreEstado(){
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado){
        this.nombreEstado = nombreEstado;
    }

    
    public TipoEstadoVotacion equalize(TipoEstadoVotacion tipoEstadoVotacion){
        if(tipoEstadoVotacion.getIdTipoEstadoVotacion() != null){this.setIdTipoEstadoVotacion(tipoEstadoVotacion.getIdTipoEstadoVotacion());}
	if(tipoEstadoVotacion.getNombreEstado() != null){this.setNombreEstado(tipoEstadoVotacion.getNombreEstado());}
	
        return this;
    }
    
    public String toString(){
        return "idTipoEstadoVotacion :: "+this.idTipoEstadoVotacion+"; "+
	"nombreEstado :: "+this.nombreEstado+"; "+
	"";
    }
    
}
