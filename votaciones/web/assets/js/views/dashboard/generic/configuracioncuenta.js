$(function () {
    var CogCuenta = {
        init: function () {
            CogCuenta.consultarTiposDocumento();
            CogCuenta.configurarVista();
            CogCuenta.eventos();
        },
        eventos: function () {
            $('#formUsuario').validator().on('submit', CogCuenta.onSubmitForm);
            //Eventos botones control cambio de clave.
            $('#btnCambiarContrasena').on('click', function () {
                $('#panelContrasena').removeClass('hidden').find('input').prop("required", true);
                $('#btnCambiarContrasena').hide();
                $('#txtClave').focus();
            });
            $('#btnCancelarCambiarClave').on('click', function () {
                $('#panelContrasena').addClass("hidden").find("input").val("").prop("required", false);
                $('#btnCambiarContrasena').show();
            });
        },
        configurarVista: function () {
            $('select').select2({width: '100%', lang: 'es'});
        },
        consultarInformacionCuenta: function () {
            __app.get("consultarinformacioncuenta", null, CogCuenta.onConsultarInformacionCuenta);
        },
        onConsultarInformacionCuenta: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            if (datos) {
                $('#formUsuario').fillForm(datos).find('select').trigger('change.select2');
            } else {
                __dom.imprimirToast("Error", "Error al consultar los datos de la cuenta.", "error");
            }
        },
        onPostUsuarioCompleto: function (respuesta) {
            $('#formUsuario').find('input, select').prop("disabled", false).trigger('change.select2');
            if (__app.respuestaExistosa(respuesta)) {
                __dom.imprimirAlerta(respuesta.mensaje, "success", true, $('#alertActualizar'));
            } else {
                __dom.imprimirAlerta(respuesta.mensaje, "danger", true, $('#alertActualizar'));
            }
        },
        /**
         * Consulta los tipos de documento.         
         */
        consultarTiposDocumento: function () {
            __app.get("tipodocumento/listartodos", null, CogCuenta.onListarTiposDocumento);
        },
        /**
         * Es convocado una vez finalizada la consulta de los tipos de documento,
         * este método se encargará de llenar el respectivo combo.
         * @param {Object} respuesta         
         */
        onListarTiposDocumento: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            if (datos) {
                __dom.llenarCombo($('#cmbTipoDocumento'), datos, {"text": "nombreDocumento", "value": "idTipoDocumento"});
            }
            CogCuenta.consultarInformacionCuenta();
        },
        onSubmitForm: function (e) {
            if (e.isDefaultPrevented()) {
                return;
            }
            var form = $(this);
            form.find('input, select').prop("disabled", true).trigger('change.select2');
            __app.detenerEvento(e);
            //Detectar acción
            var url = "usuario/actualizar";
            var obj = form.getFormData();
            if (obj.consUsuario.contrasena.trim() === "") {
                delete obj.consUsuario.contrasena;
            } else {
                obj.consUsuario.contrasena = md5(obj.consUsuario.contrasena);
            }
            obj = {persona: JSON.stringify(obj)};
            __dom.imprimirAlerta('<i class="fa fa-fw fa-refresh fa-spin"></i> ' + "Enviando...", "info", true, $('#alertActualizar'));
            __app.post(url, obj, CogCuenta.onPostUsuarioCompleto);
        }
    };
    CogCuenta.init();
});