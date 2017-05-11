package edu.poli.gerencia.votaciones.modelo.dto;

import java.io.Serializable;
import edu.poli.gerencia.votaciones.modelo.vo.TipoDocumento;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class TipoDocumentoDTO implements Serializable {

    
    private Integer idTipoDocumento;
    private String nombreDocumento;
    
    public TipoDocumentoDTO(){
    }
    
    public TipoDocumentoDTO(Integer idTipoDocumento){
        this.idTipoDocumento = idTipoDocumento;
    }
    
    public TipoDocumentoDTO(Integer idTipoDocumento, String nombreDocumento){
        this.idTipoDocumento = idTipoDocumento;
	this.nombreDocumento = nombreDocumento;

    }
    
    public Integer getIdTipoDocumento(){
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Integer idTipoDocumento){
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getNombreDocumento(){
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento){
        this.nombreDocumento = nombreDocumento;
    }

    
    public String toString(){
        return "idTipoDocumento :: "+this.idTipoDocumento+"; "+
	"nombreDocumento :: "+this.nombreDocumento+"; "+
	"";
    }
    
}
