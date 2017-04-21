/*
    Mensajes del sistema.
 */
package edu.poli.gerencia.votacion.negocio.constantes;

/**
 *
 * @author jhonjaider1000
 */
public enum EMensajes {
    REGISTRO_USUARIO(1, "Se registró correctamente el registro", null),
    INSERTAR(1, "Se insertó correctamente el registro", null),
    EDITAR(1, "Se modificó correctamente el registro", null),
    ELIMINAR(1, "Se eliminó correctamente el registro", null),
    CONSULTAR(1, "Se consultó correctamente el registro", null),
    SESION_ACTIVA(1, "La sesión está activa", null),
    OK_LOGIN(1, "Autenticado correctamente", null),
    ERROR_LOGIN(-1, "Error al autenticar", null),    
    OK(1, "Acción realizada correctamente", null),
    OK_CONEXION(1, "Conectado", null),
    RECURSO_NO_ENCONTRADO(-400, "Recurso no encontrado", null),
    NO_HAY_RESULTADOS(0, "No se encontraron registros", null),
    CORRECTO(1, "Se ha procesado la petición correctamente", null),
    NO_SE_PUDO_PROCESAR(-500, "No se pudo procesar la peticion, como el código lo indica es un error 500 (error interno en el sistama)", null),
    ERROR_INSERTAR(-1, "Error al insertar el registro", null),
    ERROR_MODIFICAR(-1, "Error al modificar el registro", null),
    ERROR_ELIMINAR(-1, "Error al eliminar el registro", null),
    ERROR_CONSULTAR(-1, "Error al consultar los registros", null),
    ERROR_FATAL(-1, "Error: Hubo un error inesperado", "E00100"),
    ERROR_INICIO_SESION(-2, "El usuario y/o clave son incorrectos", null),
    ERROR_SESION_EXPIRADO(-3, "La sesión ha finalizado", null),
    ERROR_SESION_PERMISOS(-4, "Acceso denegado", null),
    ERROR_CONEXION_BD(-6, "No hay conexión con la base de datos", null),
    ERROR_PROPERTIES_BD(-6, "No hay archivo de configuración de Base de Datos", null),
    ERROR_DRIVER_BD(-6, "No se encontró la librería de conexión", null),
    ERROR_DATO(-7, "Error: __DATO__ no puede estar vació", null),
    ERROR_CONTENIDO(-7, "Error: El valor en __DATO__ no es válido", null),
    ERROR_TIPO_DATO(-7, "Error: En el campo __CAMPO__", null),
    ERROR_LONGITUD_MAXIMA(-7, "Error: En el tamaño del campo __CAMPO__", null),
    ERROR_CONVERSION_FECHA(-9, "Error no se pudo convertir la fecha", null),
    ERROR_CAMBIAR_CLAVE(-10, "Error: La clave se debe cambiar", "E02032"),
    ERROR_PRIVILEGIO(-11, "Error: EL usuario no tiene privilegios para esta opción", null),
    ERROR_CIFRAR(-12, "Error: No se pudo cifrar la cadena", null),
    ERROR_DESCIFRAR(-13, "Error: No se pudo descifrar la cadena", null),
    ERROR_CLAVE_IGUAL(-15, "Error la clave no puede ser igual a la anterior", null),
    ERROR_INSERTAR_DUPLICADO(-1, "Error registro duplicado", null),
    ERROR_FECHAS_INICIAL_FINAL(-16, "La fecha inicial no puede ser mayor a la final ", null),
    ERROR_EMAIL_EXISTE(0, "El correo electrónic ya se encuentra registrado", null),
    DESCONOCIDO(-1, "Desconocido", null);
    private int codigo;
    private String descripcion;
    private String codigoSistema;

    private EMensajes(int codigo, String descripcion, String codigoSistema) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.codigoSistema = codigoSistema;
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

    public String getCodigoSistema() {
        return codigoSistema;
    }

    public void setCodigoSistema(String codigoSistema) {
        this.codigoSistema = codigoSistema;
    }

    public static EMensajes parse(String descripcion) {
        EMensajes listaConstantes[] = EMensajes.values();
        for (EMensajes constante : listaConstantes) {
            if (constante.getDescripcion().equals(descripcion)) {
                return constante;
            }
        }
        return EMensajes.NO_HAY_RESULTADOS;
    }
}
