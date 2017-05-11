$(function () {
    var Registro = {
        //Controles:
        cmbTipoDocumento: $('#cmbTipoDocumento'),
        //Variables:
        empresas: null,
        // Arranca las configuraciones, eventos y demás comportamientos de la vista.                  
        init: function () {
            Registro.eventos();
            Registro.consultarTiposDocumento();
            Registro.configurarVista();
            Registro.consultarTiposUsuario();
        },
        // Configura los enventos de los controles de la vista.                  
        eventos: function () {
            $('#form_registro').validator().on('submit', Registro.onSubmitFormRegistro);
            $('#cmbTipoUsuario').on('change', Registro.onChangeComboTipoUsuario);
            $('#cmbEmpresa').on('change', function () {
                $('#txtNombreEmpresa').val($(this).find('option:selected').text().split("-")[1]);
            });
        },
        // Configura los controles de la vista...                  
        configurarVista: function () {
            $('select:not(#cmbEmpresa)').select2({width: '100%', lang: 'es'});
            Registro.configurarComboEmpresa();
        },
        configurarComboEmpresa: function () {
            $('select#cmbEmpresa').select2({
                ajax: {
                    url: __app.base("persona/filtrar"),
                    dataType: 'json',
                    delay: 50,
                    lang: "es",
                    data: function (params) {
                        return {
                            q: params.term
                        };
                    },
                    processResults: function (data, params) {
                        data = data.datos;
                        params.page = params.page || 1;
                        Registro.empresas = data;
                        return {
                            results: $.map(data, function (item) {
                                return {
                                    text: item.numeroDocumento + " - " + item.nombreEmpresa,
                                    id: item.consPersona
                                }
                            }),
                            pagination: {
                                more: (params.page * 15) < data.total_count
                            }
                        };
                    },
                    cache: true
                },
                placeholder: "Seleccione",
                multiple: false,
                minimumInputLength: 3,
                quietMillis: 100,
            }).next().hide();
        },
        onChangeComboTipoUsuario: function () {
            var combo = $(this);
            console.info("CHANGE " + combo.val());
            var txtNombreEmpresa = $('#txtNombreEmpresa');
            var cmbEmpresa = $('#cmbEmpresa');
            switch (combo.val()) {
                case "2": //Empresa                    
                    cmbEmpresa.next().hide().prop("disabled", true);
                    txtNombreEmpresa.removeClass('hidden').prop("disabled", false);
                    cmbEmpresa.prop("required", false);
                    txtNombreEmpresa.prop("required", true);
                    break;
                case "3": //Empleado                    
                    txtNombreEmpresa.addClass('hidden').prop("disabled", true);
                    cmbEmpresa.next().show().width("100%");
                    window.setTimeout(function () {
                        cmbEmpresa.prop("disabled", false);
                        cmbEmpresa.trigger("change.select2");
                        txtNombreEmpresa.prop("required", false);
                        cmbEmpresa.prop("required", true);
                    }, 15);
                    break;
            }
        },
        onSubmitFormRegistro: function (e) {
            if (e.isDefaultPrevented()) {
                return;
            }
            var form = $(this);
            __app.detenerEvento(e);
            var obj = Registro.obtenerObjetoRegistro(form);
            __app.post('usuario/insertar', obj, Registro.onRegistrarCompleto);
        },
        onRegistrarCompleto: function (respuesta) {
            if (__app.respuestaExistosa(respuesta)) {
                if (respuesta.codigo > 0) {
                    var mensaje = 'Registrado correctamente, un administrador activará más tarde tu cuenta. Enviaremos un correo de notificación cuando esto suceda, gracias por registrarte.';
                    Registro.imprimirAlerta('<i class="fa fa-fw fa-info-circle"></i> ' + mensaje, "success");
                    $('#form_registro').find('input,select').val("").trigger('change.select2');
                }
            } else {
                Registro.imprimirAlerta(respuesta.mensaje, "danger");
            }
        },
        imprimirAlerta: function (mensaje, tipo) {
            var alert = $('#alert');
            alert.attr('class', 'alert alert-' + tipo + ' alert-dismissable').find('#text').html(mensaje).hide().slideDown(300);
        },
        /**
         * Obtiene los datos del formulario.
         * @param {Element} form
         * @returns {Object}
         */
        obtenerObjetoRegistro: function (form) {
            var obj = form.getFormData();
            obj.contrasena = md5(obj.contrasena);
            if ($('#cmbTipoUsuario').val() == 3) {
                obj.consPersonaAsociada = {consPersona: form.find('#cmbEmpresa').val()};
                obj.nombreEmpresa = null;
            }
            obj.persona = JSON.stringify(obj);
            return obj;
        },
        /**
         * Consulta los tipos de documento.         
         */
        consultarTiposDocumento: function () {
            __app.get("tipodocumento/listartodos", null, Registro.onListarTiposDocumento, null);
        },
        /**
         * Es convocado una vez finalizada la consulta de los tipos de documento,
         * este método se encargará de llenar el respectivo combo.
         * @param {Object} respuesta         
         */
        onListarTiposDocumento: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            if (datos) {
                __dom.llenarCombo(Registro.cmbTipoDocumento, datos, {"text": "nombreDocumento", "value": "idTipoDocumento"});
            }
        },
        /**
         * Consulta los tipos de usuario.         
         */
        consultarTiposUsuario: function () {
            __app.get("listatipousuarios", null, Registro.onConsultarTiposUsuario);
        },
        /**
         * LLena el combo tipos de usuario.
         * @param {Object} respuesta         
         */
        onConsultarTiposUsuario: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            if (datos) {
                __dom.llenarCombo($('#cmbTipoUsuario'), datos, {text: "descripcion", value: "idTipoUsuario"});
            }
        },
    };
    Registro.init();
});