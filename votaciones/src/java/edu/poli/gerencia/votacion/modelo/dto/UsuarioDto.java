/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.modelo.dto;

/**
 *
 * @author jhon1
 */
public class UsuarioDto {

    private Integer consUsuario;
    private String nombreUsuario;
    private Integer idTipoUsuario;
    private Character activo;
    private Integer idTipoDocumento;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;

    public UsuarioDto() {
    }

    public UsuarioDto(Integer consUsuario, String nombreUsuario, Integer idTipoUsuario, Character activo, Integer idTipoDocumento, String primerNombre, String segundoNombre, String primerApellido) {
        this.consUsuario = consUsuario;
        this.nombreUsuario = nombreUsuario;
        this.idTipoUsuario = idTipoUsuario;
        this.activo = activo;
        this.idTipoDocumento = idTipoDocumento;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
    }

    public Integer getConsUsuario() {
        return consUsuario;
    }

    public void setConsUsuario(Integer consUsuario) {
        this.consUsuario = consUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public Character getActivo() {
        return activo;
    }

    public void setActivo(Character activo) {
        this.activo = activo;
    }

    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

}
