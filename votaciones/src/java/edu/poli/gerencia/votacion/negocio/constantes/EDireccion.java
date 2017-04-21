/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.constantes;

/**
 *
 * @author jhon1
 */
public enum EDireccion {

    //sesion
    INICIAR_SESION("/iniciarsesion", "Autenticación en el sistema."),
    CERRAR_SESION("/cerrarsesion", "Cerrar Sesion"),
    //Fin sesion
    //Usuario
    REGISTRAR_USUARIO("/registrarusuario", "Registrar un usuario"),
    ACTUALIZAR_USUARIO("/actualizarusuario", "Actualizar un usuario"),
    RECUPERAR_CUENTA("/recuperarcuenta","Comprueba que un usuario exista, y envia un correo de recuperación de cuenta al correo electrónico registrado."),
    //Fin Usuario
    //Persona
    REGISTRAR_PERSONA("/registrarpersona", "Registrar persona"),
    ACTUALIZAR_PERSONA("/registrarpersona", "Registrar persona"),
    ELIMINAR_PERSONA("/registrarpersona", "Registrar persona"),
    //Fin Persona
    //TipoDocumento
    LISTAR_TIPODOCUMENTO("/listartipodocumento","Lista todos los tipos de documento"),
    INSERTAR_TIPODOCUMENTO("/insertartipodocumento","Inserta un tipo de documento nuevo"),
    ACTUALIZAR_TIPODOCUMENTO("/actualizartipodocumento","Actualiza un tipo de documento"),
    ELIMINAR_TIPODOCUMENTO("/eliminartipodocumento","Elimina un tipo de documento"),
    //Fin Tipo Documento
    DESCONOCIDO(null, "Desconocido.");

    private String url;
    private String descripcion;

    private EDireccion(String url, String descripcion) {
        this.url = url;
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static EDireccion getDireccion(String url) {
        EDireccion direcciones[] = values();
        for (EDireccion direccion : direcciones) {
            if (direccion.getUrl().equalsIgnoreCase(url)) {
                return direccion;
            }
        }
        return null;
    }
}
