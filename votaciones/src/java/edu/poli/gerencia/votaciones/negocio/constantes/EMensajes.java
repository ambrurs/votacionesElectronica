package edu.poli.gerencia.votaciones.negocio.constantes;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public enum EMensajes {

    INSERTAR(1, "Se registró correctamente el registro", null),
    ACTUALIZAR(1, "Se modificó correctamente el registro", null),
    ELIMINAR(1, "Se eliminó correctamente el registro", null),
    CONSULTAR(1, "Se consultó correctamente el registro", null),
    SESION_ACTIVA(1, "La Sesion esta activa", null),
    EXITO(1, "Accion Realizada Correctamente", null),
    ERROR(-1, "No se pudo ejecutar la acción correctamente", null),
    CONEXION_EXITOSA(1, "Conectado", null),
    CERRAR_SESION(100, "Sesión serrada correctamente", null),
    RECURSO_NO_ENCONTRADO(-1, "Recurso no encontrado", null),
    AUTENTICADO(1, "Autenticado correctamente.", null),
    NO_SE_ENCONTRARON_REGISTROS(0, "No se encontraron registros", null),
    ERROR_INSERTAR(-1, "Error al insertar el registro", null),
    ERROR_ACTUALIZAR(-1, "Error al modificar el registro", null),
    ERROR_ELIMINAR(-1, "Error al eliminar el registro", null),
    ERROR_CONSULTAR(-1, "Error al consultar los registro", null),
    ERROR_FATAL(-1, "Error: Hubo un error interno", "E00100"),
    ERROR_TOKEN_EMAIL(-1, "Error al registrar la solicitud.", null),
    ERROR_AUTENTICACION(-2, "El usuario y/o clave incorrectos.", null),
    CUENTA_PENDIENTE_POR_ACTIVACION(-2, "La cuenta aún está pendiente de confirmación.", null),
    CUENTA_INACTIVA(-2, "La cuenta está inactiva.", null),
    CUENTA_BLOQUEADA(-2, "La cuenta ha sido bloquead.a", null),
    SESION_EXPIRADA(-3, "La sesión ha finalizado", null),
    ACCESO_DENEGADO(-4, "Acceso denegado", null),
    ERROR_IP_USUSARIO(-5, "La dirección ip del equipo no corresponde con la dirección ip de la clave", null),
    ERROR_CONEXION_BD(-6, "No hay conexión con la base de datos", null),
    ERROR_PROCESO_BD(-6, "No hay conexión con la base de datos", null),
    ERROR_PROPERTIES_BD(-6, "No hay Archivo de configuracion de Base de Datos (database.properties)", null),
    ERROR_DRIVER_DB(-6, "No se encontró la librería de conexión", null),
    ERROR_DATO(-7, "Error:  __DATO__ no puede estar vacío ", null),
    ERROR_CONIDO(-7, "Error:  el valor en __DATO__ no es valido ", null),
    ERROR_TYPO_DATO(-7, "Error: En el campo __CAMPO__", null),
    ERROR_LONGITUD_MAXIMA(-7, "Error: En el tamaño del campo __CAMPO__", null),
    ERROR_CONVERSION_FECHA(-9, "Error: No se pudo convertir la fecha", null),
    ERROR_CAMBIANDO_CLAVE(-10, "Error: La clave se debe cambiar", "E02032"),
    ERROR_PRIVILEGIO(-11, "Error: El usuario no tiene privilegios para ésta opción", null),
    ERROR_ENCRIPTAR(-12, "Error: No se pudo cifrar la cadena", null),
    ERROR_DESCIFRAR(-13, "Error: No se pudo descifrar la cadena", null),
    ERROR_CLAVE_IGUAL(-15, "Error la nueva clave no puede ser igual al anterior", null),
    ERROR_FECHA_INICIAL_FINAL(-16, "La fecha inicial no puede ser mayor a la final ", null),
    ERROR_INSERTAR_DUPLICADO(-1, "Error regisrto duplicado ", null),
    ERROR_REGISTRO_EXISTE(0, "Ya existe un registro, con el mismo __DATO__", null),
    ERROR_CONECTAR_IMPRESORA(-1, "No se podido establecer conexion a las impresoras del equipo", null),
    ERROR_YA_HAS_VOTADO(-3, "Ya has votado antes", null),
    ;

    private int codigo;
    private String descripcion;
    private String codigoVotaciones;

    private EMensajes(int codigo, String descripcion, String codigoVotaciones) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.codigoVotaciones = codigoVotaciones;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoVotaciones() {
        return codigoVotaciones;
    }

    public void setCodigoVotaciones(String codigoVotaciones) {
        this.codigoVotaciones = codigoVotaciones;
    }

    public static EMensajes parse(String descripcion) {
        EMensajes list[] = EMensajes.values();
        for (EMensajes constant : list) {
            if (constant.getDescripcion().equals(descripcion)) {
                return constant;
            }
        }
        return EMensajes.NO_SE_ENCONTRARON_REGISTROS;
    }

}
