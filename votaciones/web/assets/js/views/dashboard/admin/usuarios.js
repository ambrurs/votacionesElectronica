$(function () {
    var Usuarios = {
        tabla: $('#tablaUsuarios'),
        modalUsuario: $('#modalUsuario'),
        init: function () {
            Usuarios.eventos();
            Usuarios.configurarVista();
            Usuarios.consultarUsuarios();
            Usuarios.consultarTiposDocumento();
            Usuarios.consultarTiposUsuario();
        },
        eventos: function () {
            $('#btnNuevoUsuario').on('click', Usuarios.nuevoUsuario);
            Usuarios.modalUsuario.off('click', '.link-nuevo');
            Usuarios.modalUsuario.on('click', '.link-nuevo', Usuarios.nuevoUsuario);
            $(Usuarios.tabla).off('click', '.btn-control', Usuarios.onControlEdicion);
            $(Usuarios.tabla).on('click', '.btn-control', Usuarios.onControlEdicion);
            $('#cmbTipoUsuario').on('change', Usuarios.onChangeComboTipoUsuario);
            $('#btnAceptar').on('click', Usuarios.onClickCambiarEstado);
            $(Usuarios.tabla).off('click', 'span.badge');
            $(Usuarios.tabla).on('click', 'span.badge', Usuarios.onCambiarEstado);
            $('#formUsuario').validator().on('submit', Usuarios.onSubmitForm);
        },
        /**
         * Consultará los usuarios en orden descentente por fecha de registro.
         * @returns {undefined}
         */
        consultarUsuarios: function () {
            __app.get("usuariosregistrados", null, Usuarios.onConsultarUsuarios);
        },
        onConsultarUsuarios: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            Usuarios.llenarTabla(datos);
        },
        llenarTabla: function (datos) {
            if (Usuarios.tablaDatatables) {
                Usuarios.tablaDatatables.clear().draw();
                Usuarios.tablaDatatables.rows.add(datos);
                Usuarios.tablaDatatables.columns.adjust().draw();
                return;
            }

            var formatearFechaRegistro = function (obj) {
                return __dom.formatearFecha(obj.consUsuario.fechaRegistro);
            };
            var descripcionEstado = function (obj) {
                var estado = "---";
                switch (obj.consUsuario.activo) {
                    case "N":
                        estado = '<span data-value="N" class="badge cursor-pointer badge-warning">Pendiente</span>';
                        break;
                    case "S":
                        estado = '<span data-value="S" class="badge cursor-pointer badge-success">Activo</span>';
                        break;
                    case "I":
                        estado = '<span data-value="I" class="badge cursor-pointer badge-default">Inactivo</span>';
                        break;
                    case "B":
                        estado = '<span data-table="B" class="badge cursor-pointer badge-danger">Bloqueado</span>';
                        break;
                }
                return estado;
            };
            var obtenerBotones = function () {
                return '<a class="btn btn-success btn-control btn-ver" href="javascript:;">'
                        + '<i class="fa fa-search-plus "></i></a><a class="btn btn-editar btn-control'
                        + ' btn-info" href="javascript:;"><i class="fa fa-edit "></i></a>';
//                        + '<a class="btn btn-danger btn-eliminar btn-control" href="javascript:;"><i class="fa fa-trash-o "></i></a>';
            };
            Usuarios.tablaDatatables = __dom.configurarTabla(Usuarios.tabla, {
                datos: datos,
                columnas: [
                    {title: "Usuario", data: "consUsuario.nombreUsuario"},
                    {title: "Fecha registro", data: formatearFechaRegistro},
                    {title: "Rol", data: "consUsuario.idTipoUsuario.descripcion"},
                    {title: "Estado", data: descripcionEstado},
                    {title: "Opciones", data: obtenerBotones}
                ]
            });
        },
        onCambiarEstadoCompleto: function (respuesta) {
            console.log(respuesta);
            if (__app.respuestaExistosa(respuesta)) {
                Usuarios.consultarUsuarios();
                __dom.imprimirToast("Actualizado", "Se ha actualizado el estado correctamente.", "success");
            } else {
                __dom.imprimirToast("Error", "No se pudo actualizar el estado.", "error");
            }
        },
        onClickCambiarEstado: function () {
            console.log("CAMBIAR");
            var btn = $(this);
            var modal = $('#modalCambiarEstado');
            var estado = modal.find('input[name="estadoRadio"]:checked').val();
            if (estado.trim() != "") {
                __app.post("cambiarestadousuario", {estado: estado, usuario: btn.attr('data-id')}, Usuarios.onCambiarEstadoCompleto);
            }
        },
        onControlEdicion: function () {
            var btn = $(this);
            var row = btn.parent().parent();
            var datos = Usuarios.tablaDatatables.row(row).data();
            if (btn.hasClass("btn-ver")) {
                Usuarios.verUsuario(datos);
            } else if (btn.hasClass("btn-editar")) {
                Usuarios.editarUsuario(datos);
            } else if (btn.hasClass("btn-eliminar")) {
                Usuarios.eliminarUsuario(datos);
            }

        },
        nuevoUsuario: function () {
            $('#alertActualizar').hide();
            var modal = Usuarios.modalUsuario.modal("show").attr("data-action", "C");//C = Crear.
            modal.find('#titulo').html('<i class="fa fa-fw fa-user-plus"></i> Nuevo usuario');
            modal.find("input,select").val("").prop("disabled", false).trigger('change.select2');
            modal.find('#btnGuardar').show();
        },
        verUsuario: function (datos) {
            $('#alertActualizar').hide();
            Usuarios.modalUsuario.find('#titulo').html('<i class="fa fa-fw fa-eye"></i> Ver usuario');
            Usuarios.modalUsuario.modal("show");
            var form = Usuarios.modalUsuario.find('#formUsuario');
            form.find('select, input').prop("disabled", true);
            form.fillForm(datos).find('select').trigger('change.select2');
            Usuarios.modalUsuario.find('#btnGuardar').hide();
        },
        editarUsuario: function (datos) {
            $('#alertActualizar').hide();
            var modal = Usuarios.modalUsuario.modal("show").attr('data-action', "A"); //A = Actualizar.
            modal.find('#titulo').html('<i class="fa fa-fw fa-edit"></i> Editar usuario');
            var form = modal.find('#formUsuario');
            form.fillForm(datos).find('select').trigger('change.select2');
            form.find('input, select').prop("disabled", false).trigger('change.select2');
            modal.find('#btnGuardar').show();
        },
        eliminarUsuario: function (datos) {
            __dom.confirmar({message: "Realmente deseas eliminar el registro", closeOnConfirm: false}, function () {
                swal("Eliminado", "Por implementar...", "success");
            });
        },
        /**
         * Consulta los tipos de documento.         
         */
        consultarTiposDocumento: function () {
            __app.get("tipodocumento/listartodos", null, Usuarios.onListarTiposDocumento, null);
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
        },
        /**
         * Consulta los tipos de usuario.         
         */
        consultarTiposUsuario: function () {
            __app.get("tipousuario/listartodos", null, Usuarios.onConsultarTiposUsuario);
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
        onCambiarEstado: function () {
            var modal = $('#modalCambiarEstado');
            modal.modal("show");
            var registro = Usuarios.tablaDatatables.row($(this).parent().parent()).data();
            modal.find('#btnAceptar').attr('data-id', registro.consUsuario.consUsuario);
            modal.find('input[name="estadoRadio"]').prop("checked", false);
            modal.find('input[name="estadoRadio"][value="' + $(this).attr("data-value") + '"]').prop("checked", true);
        },
        onSubmitForm: function (e) {
            if (e.isDefaultPrevented()) {
                return;
            }
            var form = $(this);
            form.find('input, select').prop("disabled", true).trigger('change.select2');
            __app.detenerEvento(e);
            //Detectar acción
            var url = null;
            var obj = form.getFormData();
            switch (Usuarios.modalUsuario.attr('data-action')) {
                case "C":
                    url = "usuario/insertar";
                    delete obj.consPersona;
                    delete obj.consUsuario.consUsuario;
                    break;
                case "A":
                    url = "usuario/actualizar";
                    break;
            }
            obj = {persona: JSON.stringify(obj)};
            __dom.imprimirAlerta('<i class="fa fa-fw fa-refresh fa-spin"></i> ' + "Enviando...", "info", true, $('#alertActualizar'));
            __app.post(url, obj, Usuarios.onPostUsuarioCompleto);
        },
        onPostUsuarioCompleto: function (respuesta) {
            $('#formUsuario').find('input, select').prop("disabled", false).trigger('change.select2');
            if (__app.respuestaExistosa(respuesta)) {
                __dom.imprimirAlerta(respuesta.mensaje, "success", true, $('#alertActualizar'));
                if (Usuarios.modalUsuario.attr('data-action') === "C") {
                    Usuarios.modalUsuario.attr('data-action', "A").find('#titulo').html('<i class="fa fa-fw fa-edit"></i> Editar usuario / <a href="javascript:;" class="small link-nuevo" ><i class="fa fa-fw fa-plus"> </i>Nuevo</a>');
                    $('#consPersona').val(respuesta.datos.consPersona);
                    $('#consUsuario').val(respuesta.datos.consUsuario.consUsuario);
                }
                Usuarios.consultarUsuarios();
            } else {
                __dom.imprimirAlerta(respuesta.mensaje, "danger", true, $('#alertActualizar'));
            }
        },
        configurarVista: function () {
            $('select:not(#cmbEmpresa)').select2({width: '100%', lang: 'es'});
            Usuarios.configurarComboEmpresa();
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
                        Usuarios.empresas = data;
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
    };
    Usuarios.init();
});