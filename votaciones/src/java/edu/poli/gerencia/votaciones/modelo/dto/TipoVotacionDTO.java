package edu.poli.gerencia.votaciones.modelo.dto;

import java.io.Serializable;
import edu.poli.gerencia.votaciones.modelo.vo.TipoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class TipoVotacionDTO implements Serializable {

    
    private Integer idTipoVotacion;
    private String nombreTipoVotacion;
    
    public TipoVotacionDTO(){
    }
    
    public TipoVotacionDTO(Integer idTipoVotacion){
        this.idTipoVotacion = idTipoVotacion;
    }
    
    public TipoVotacionDTO(Integer idTipoVotacion, String nombreTipoVotacion){
        this.idTipoVotacion = idTipoVotacion;
	this.nombreTipoVotacion = nombreTipoVotacion;

    }
    
    public Integer getIdTipoVotacion(){
        return idTipoVotacion;
    }

    public void setIdTipoVotacion(Integer idTipoVotacion){
        this.idTipoVotacion = idTipoVotacion;
    }

    public String getNombreTipoVotacion(){
        return nombreTipoVotacion;
    }

    public void setNombreTipoVotacion(String nombreTipoVotacion){
        this.nombreTipoVotacion = nombreTipoVotacion;
    }

    
    public String toString(){
        return "idTipoVotacion :: "+this.idTipoVotacion+"; "+
	"nombreTipoVotacion :: "+this.nombreTipoVotacion+"; "+
	"";
    }
    
}
