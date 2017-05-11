package edu.poli.gerencia.votaciones.modelo.vo;

import java.io.Serializable;
import edu.poli.gerencia.votaciones.modelo.vo.CandidatoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;
import edu.poli.gerencia.votaciones.modelo.vo.VotacionUsuarioCandidato;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class VotacionUsuarioCandidato implements Serializable {

    
    private Integer consUsuarioCandidato;
    private CandidatoVotacion candidatoVotacionConsCandidato;
    private Usuario usuarioConsUsuario;
    
    public VotacionUsuarioCandidato(){
    }
    
    public VotacionUsuarioCandidato(Integer consUsuarioCandidato){
        this.consUsuarioCandidato = consUsuarioCandidato;
    }
    
    public VotacionUsuarioCandidato(Integer consUsuarioCandidato, CandidatoVotacion candidatoVotacionConsCandidato, Usuario usuarioConsUsuario){
        this.consUsuarioCandidato = consUsuarioCandidato;
	this.candidatoVotacionConsCandidato = candidatoVotacionConsCandidato;
	this.usuarioConsUsuario = usuarioConsUsuario;

    }
    
    public Integer getConsUsuarioCandidato(){
        return consUsuarioCandidato;
    }

    public void setConsUsuarioCandidato(Integer consUsuarioCandidato){
        this.consUsuarioCandidato = consUsuarioCandidato;
    }

    public CandidatoVotacion getCandidatoVotacionConsCandidato(){
        return candidatoVotacionConsCandidato;
    }

    public void setCandidatoVotacionConsCandidato(CandidatoVotacion candidatoVotacionConsCandidato){
        this.candidatoVotacionConsCandidato = candidatoVotacionConsCandidato;
    }

    public Usuario getUsuarioConsUsuario(){
        return usuarioConsUsuario;
    }

    public void setUsuarioConsUsuario(Usuario usuarioConsUsuario){
        this.usuarioConsUsuario = usuarioConsUsuario;
    }

    
    public VotacionUsuarioCandidato equalize(VotacionUsuarioCandidato votacionUsuarioCandidato){
        if(votacionUsuarioCandidato.getConsUsuarioCandidato() != null){this.setConsUsuarioCandidato(votacionUsuarioCandidato.getConsUsuarioCandidato());}
	if(votacionUsuarioCandidato.getCandidatoVotacionConsCandidato() != null){this.setCandidatoVotacionConsCandidato(votacionUsuarioCandidato.getCandidatoVotacionConsCandidato());}
	if(votacionUsuarioCandidato.getUsuarioConsUsuario() != null){this.setUsuarioConsUsuario(votacionUsuarioCandidato.getUsuarioConsUsuario());}
	
        return this;
    }
    
    public String toString(){
        return "consUsuarioCandidato :: "+this.consUsuarioCandidato+"; "+
	"candidatoVotacionConsCandidato :: "+this.candidatoVotacionConsCandidato+"; "+
	"usuarioConsUsuario :: "+this.usuarioConsUsuario+"; "+
	"";
    }
    
}
