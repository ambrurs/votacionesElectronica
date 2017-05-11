package edu.poli.gerencia.votaciones.modelo.dto;

import java.io.Serializable;
import edu.poli.gerencia.votaciones.modelo.vo.EstadoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.TipoEstadoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class TipoEstadoVotacionDTO implements Serializable {

    
    private Integer idTipoEstadoVotacion;
    private String nombreEstado;
    
    public TipoEstadoVotacionDTO(){
    }
    
    public TipoEstadoVotacionDTO(Integer idTipoEstadoVotacion){
        this.idTipoEstadoVotacion = idTipoEstadoVotacion;
    }
    
    public TipoEstadoVotacionDTO(Integer idTipoEstadoVotacion, String nombreEstado){
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

    
    public String toString(){
        return "idTipoEstadoVotacion :: "+this.idTipoEstadoVotacion+"; "+
	"nombreEstado :: "+this.nombreEstado+"; "+
	"";
    }
    
}
