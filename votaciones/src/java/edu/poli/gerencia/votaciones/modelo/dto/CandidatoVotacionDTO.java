package edu.poli.gerencia.votaciones.modelo.dto;

import java.io.Serializable;
import edu.poli.gerencia.votaciones.modelo.vo.CandidatoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class CandidatoVotacionDTO implements Serializable {

    
    private Integer consCandidato;
    private Votacion votacionConsVotacion;
    private Usuario consUsuarioVotacion;
    private Integer cantidadVotos;
    
    public CandidatoVotacionDTO(){
    }
    
    public CandidatoVotacionDTO(Integer consCandidato){
        this.consCandidato = consCandidato;
    }
    
    public CandidatoVotacionDTO(Integer consCandidato, Votacion votacionConsVotacion, Usuario consUsuarioVotacion, Integer cantidadVotos){
        this.consCandidato = consCandidato;
	this.votacionConsVotacion = votacionConsVotacion;
	this.consUsuarioVotacion = consUsuarioVotacion;
	this.cantidadVotos = cantidadVotos;

    }
    
    public Integer getConsCandidato(){
        return consCandidato;
    }

    public void setConsCandidato(Integer consCandidato){
        this.consCandidato = consCandidato;
    }

    public Votacion getVotacionConsVotacion(){
        return votacionConsVotacion;
    }

    public void setVotacionConsVotacion(Votacion votacionConsVotacion){
        this.votacionConsVotacion = votacionConsVotacion;
    }

    public Usuario getConsUsuarioVotacion(){
        return consUsuarioVotacion;
    }

    public void setConsUsuarioVotacion(Usuario consUsuarioVotacion){
        this.consUsuarioVotacion = consUsuarioVotacion;
    }

    public Integer getCantidadVotos(){
        return cantidadVotos;
    }

    public void setCantidadVotos(Integer cantidadVotos){
        this.cantidadVotos = cantidadVotos;
    }

    
    public String toString(){
        return "consCandidato :: "+this.consCandidato+"; "+
	"votacionConsVotacion :: "+this.votacionConsVotacion+"; "+
	"consUsuarioVotacion :: "+this.consUsuarioVotacion+"; "+
	"cantidadVotos :: "+this.cantidadVotos+"; "+
	"";
    }
    
}
