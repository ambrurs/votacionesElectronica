package edu.poli.gerencia.votaciones.negocio.constantes;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public enum EAcciones {

    //<editor-fold defaultstate="collapsed" desc="Acciones basicas de las entidades">
    INSERTAR("/insertar"),
    ACTUALIZAR("/actualizar"),
    ELIMINAR("/eliminar"),
    FILTRAR("/filtrar"),
    LISTAR_TODOS("/listartodos"),
    BUSCAR_POR_ID("/buscarporid"),
    //</editor-fold>        

    //<editor-fold defaultstate="collapsed" desc="Acciones personalizadas">
    RECUPERAR_CUENTA("/recuperarcuenta"), //</editor-fold>
    COMPROBAR_TOKEN("/comprobartoken"), //</editor-fold>
    INICIAR_SESION("/iniciarsesion"),
    COMPROBAR_SESION("/comprobarsesion"),
    ACTUALIZAR_CLAVE("/actualizarclave"),
    CONSULTAR_NOTIFICACIONES("/consultarnotificaciones"),
    CERRAR_SESION("/cerrarsesion"),
    LISTAR_TIPOS_USUARIOS("/listatipousuarios"),
    USUARIOS_REGISTRADOS("/usuariosregistrados"),
    CAMBIAR_ESTADO_USUARIO("/cambiarestadousuario"),
    CONSULTAR_INFORMACION_CUENTA("/consultarinformacioncuenta"),
    LISTAR_VOTACIONES("/listarvotaciones"),
    DETALLES_VOTACION("/detallesvotacion"),
    LANZAR_JOB_VOTACIONES("/lanzarjobvotaciones"),
    ;

    private String entidad;

    private EAcciones(String entidad) {
        this.entidad = entidad;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public static EAcciones parse(String entidad) {
        EAcciones[] entidads = EAcciones.values();
        for (EAcciones eAcciones : entidads) {
            if (eAcciones.getEntidad().equalsIgnoreCase("/" + entidad)) {
                return eAcciones;
            }
        }
        return EAcciones.INSERTAR;
    }

}
