package edu.poli.gerencia.votaciones.modelo.vo;

import java.io.Serializable;
import edu.poli.gerencia.votaciones.modelo.vo.Persona;
import edu.poli.gerencia.votaciones.modelo.vo.TipoDocumento;
import edu.poli.gerencia.votaciones.modelo.vo.Usuario;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class Persona implements Serializable {

    
    private Integer consPersona;
    private Usuario consUsuario;
    private TipoDocumento idTipoDocumento;
    private Integer consPersonaAsociada;
    private String imagenPerfil;
    private String segundoApellido;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String nombreEmpresa;
    private String correo;
    private String numeroDocumento;
    
    public Persona(){
    }
    
    public Persona(Integer consPersona){
        this.consPersona = consPersona;
    }
    
    public Persona(Integer consPersona, Usuario consUsuario, TipoDocumento idTipoDocumento, Integer consPersonaAsociada, String imagenPerfil, String segundoApellido, String primerNombre, String segundoNombre, String primerApellido, String nombreEmpresa, String correo, String numeroDocumento){
        this.consPersona = consPersona;
	this.consUsuario = consUsuario;
	this.idTipoDocumento = idTipoDocumento;
	this.consPersonaAsociada = consPersonaAsociada;
	this.imagenPerfil = imagenPerfil;
	this.segundoApellido = segundoApellido;
	this.primerNombre = primerNombre;
	this.segundoNombre = segundoNombre;
	this.primerApellido = primerApellido;
	this.nombreEmpresa = nombreEmpresa;
	this.correo = correo;
	this.numeroDocumento = numeroDocumento;

    }
    
    public Integer getConsPersona(){
        return consPersona;
    }

    public void setConsPersona(Integer consPersona){
        this.consPersona = consPersona;
    }

    public Usuario getConsUsuario(){
        return consUsuario;
    }

    public void setConsUsuario(Usuario consUsuario){
        this.consUsuario = consUsuario;
    }

    public TipoDocumento getIdTipoDocumento(){
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(TipoDocumento idTipoDocumento){
        this.idTipoDocumento = idTipoDocumento;
    }

    public Integer getConsPersonaAsociada(){
        return consPersonaAsociada;
    }

    public void setConsPersonaAsociada(Integer consPersonaAsociada){
        this.consPersonaAsociada = consPersonaAsociada;
    }

    public String getImagenPerfil(){
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil){
        this.imagenPerfil = imagenPerfil;
    }

    public String getSegundoApellido(){
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido){
        this.segundoApellido = segundoApellido;
    }

    public String getPrimerNombre(){
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre){
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre(){
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre){
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido(){
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido){
        this.primerApellido = primerApellido;
    }

    public String getNombreEmpresa(){
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa){
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getCorreo(){
        return correo;
    }

    public void setCorreo(String correo){
        this.correo = correo;
    }

    public String getNumeroDocumento(){
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento){
        this.numeroDocumento = numeroDocumento;
    }

    
    public Persona equalize(Persona persona){
        if(persona.getConsPersona() != null){this.setConsPersona(persona.getConsPersona());}
	if(persona.getConsUsuario() != null){this.setConsUsuario(persona.getConsUsuario());}
	if(persona.getIdTipoDocumento() != null){this.setIdTipoDocumento(persona.getIdTipoDocumento());}
	if(persona.getConsPersonaAsociada() != null){this.setConsPersonaAsociada(persona.getConsPersonaAsociada());}
	if(persona.getImagenPerfil() != null){this.setImagenPerfil(persona.getImagenPerfil());}
	if(persona.getSegundoApellido() != null){this.setSegundoApellido(persona.getSegundoApellido());}
	if(persona.getPrimerNombre() != null){this.setPrimerNombre(persona.getPrimerNombre());}
	if(persona.getSegundoNombre() != null){this.setSegundoNombre(persona.getSegundoNombre());}
	if(persona.getPrimerApellido() != null){this.setPrimerApellido(persona.getPrimerApellido());}
	if(persona.getNombreEmpresa() != null){this.setNombreEmpresa(persona.getNombreEmpresa());}
	if(persona.getCorreo() != null){this.setCorreo(persona.getCorreo());}
	if(persona.getNumeroDocumento() != null){this.setNumeroDocumento(persona.getNumeroDocumento());}
	
        return this;
    }
    
    public String toString(){
        return "consPersona :: "+this.consPersona+"; "+
	"consUsuario :: "+this.consUsuario+"; "+
	"idTipoDocumento :: "+this.idTipoDocumento+"; "+
	"consPersonaAsociada :: "+this.consPersonaAsociada+"; "+
	"imagenPerfil :: "+this.imagenPerfil+"; "+
	"segundoApellido :: "+this.segundoApellido+"; "+
	"primerNombre :: "+this.primerNombre+"; "+
	"segundoNombre :: "+this.segundoNombre+"; "+
	"primerApellido :: "+this.primerApellido+"; "+
	"nombreEmpresa :: "+this.nombreEmpresa+"; "+
	"correo :: "+this.correo+"; "+
	"numeroDocumento :: "+this.numeroDocumento+"; "+
	"";
    }
    
}
