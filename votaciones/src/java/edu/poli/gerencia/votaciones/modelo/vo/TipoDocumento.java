package edu.poli.gerencia.votaciones.modelo.vo;

import java.io.Serializable;
import edu.poli.gerencia.votaciones.modelo.vo.TipoDocumento;



/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */


public class TipoDocumento implements Serializable {

    
    private Integer idTipoDocumento;
    private String nombreDocumento;
    
    public TipoDocumento(){
    }
    
    public TipoDocumento(Integer idTipoDocumento){
        this.idTipoDocumento = idTipoDocumento;
    }
    
    public TipoDocumento(Integer idTipoDocumento, String nombreDocumento){
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

    
    public TipoDocumento equalize(TipoDocumento tipoDocumento){
        if(tipoDocumento.getIdTipoDocumento() != null){this.setIdTipoDocumento(tipoDocumento.getIdTipoDocumento());}
	if(tipoDocumento.getNombreDocumento() != null){this.setNombreDocumento(tipoDocumento.getNombreDocumento());}
	
        return this;
    }
    
    public String toString(){
        return "idTipoDocumento :: "+this.idTipoDocumento+"; "+
	"nombreDocumento :: "+this.nombreDocumento+"; "+
	"";
    }
    
}
