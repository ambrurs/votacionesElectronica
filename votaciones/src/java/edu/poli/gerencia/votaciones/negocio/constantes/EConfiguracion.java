package edu.poli.gerencia.votaciones.negocio.constantes;


/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */

public enum EConfiguracion {

    DATE_FORMAT_FULL("yyyy/MM/dd HH:mm:ss", ""),
    DATE_FORMAT_SHORT("yyyy/MM/dd", "");
    private String codigo;
    private String value;

    private EConfiguracion(String codigo, String value) {
        this.codigo = codigo;
        this.value = value;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

