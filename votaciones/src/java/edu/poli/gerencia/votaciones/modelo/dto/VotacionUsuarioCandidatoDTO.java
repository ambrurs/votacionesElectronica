package edu.poli.gerencia.votaciones.modelo.dto;

import java.io.Serializable;
import edu.poli.gerencia.votaciones.modelo.vo.CandidatoVotacion;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import edu.poli.gerencia.votaciones.modelo.vo.Votacion;
import edu.poli.gerencia.votaciones.modelo.vo.VotacionUsuarioCandidato;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class VotacionUsuarioCandidatoDTO implements Serializable {

    
    private Integer consUsuarioCandidato;
    private CandidatoVotacion candidatoVotacionConsCandidato;
    private Usuario usuarioConsUsuario;
    
    public VotacionUsuarioCandidatoDTO(){
    }
    
    public VotacionUsuarioCandidatoDTO(Integer consUsuarioCandidato){
        this.consUsuarioCandidato = consUsuarioCandidato;
    }
    
    public VotacionUsuarioCandidatoDTO(Integer consUsuarioCandidato, CandidatoVotacion candidatoVotacionConsCandidato, Usuario usuarioConsUsuario){
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

    
    public String toString(){
        return "consUsuarioCandidato :: "+this.consUsuarioCandidato+"; "+
	"candidatoVotacionConsCandidato :: "+this.candidatoVotacionConsCandidato+"; "+
	"usuarioConsUsuario :: "+this.usuarioConsUsuario+"; "+
	"";
    }
    
}
