$(function () {
    var TipoUsuario = {
        init: function () {
            TipoUsuario.eventos();
            TipoUsuario.consultarTiposUsuario();
            TipoUsuario.configurarVista();
        },
        eventos: function () {
            $('#formtipousuario').validator().on('submit', TipoUsuario.onSubmitForm);
            $("#tablaTipoVotaciones").off('click', '.btn-control');
            $("#tablaTiposUsuario").on('click', '.btn-control', TipoUsuario.onClickBotonControl);
        },
        configurarVista: function () {
            $('select').select2({width: "100%"});
        },
        onClickBotonControl: function () {
            var btn = $(this);
            var row = btn.parent().parent();
            var datos = TipoUsuario.tablaTiposUsuario.row(row).data();
            if (btn.hasClass("btn-editar")) {
                TipoUsuario.editar(datos);
            } else if (btn.hasClass("btn-eliminar")) {
                TipoUsuario.eliminar(datos);
            }
        },
        editar: function (datos) {
            $('#formtipousuario').fillForm(datos).attr('data-action', 'A').find('select').val((datos.publico) ? "true" : "false").trigger('change.select2');
            $('#formtipousuario').find('input#txtDescripcion').focus();
        },
        eliminar: function (datos) {
            __dom.confirmar({mensaje: "El registro se eliminará permanentemente, ¿desea continuar?"}, function () {
                var id = datos.idTipoUsuario;
                __app.post("tipousuario/eliminar", {id: id}, function (respuesta) {
                    var valid = (__app.validarRespuesta(respuesta));
                    var titulo = valid ? "Eliminado" : "Error";
                    var tipo = valid ? "success" : "error";
                    __dom.imprimirToast(titulo, respuesta.mensaje, tipo);
                    TipoUsuario.consultarTiposUsuario();
                });
            });
        },
        onSubmitForm: function (e) {
            if (e.isDefaultPrevented()) {
                return;
            }
            var form = $(this);
            __app.detenerEvento(e);
            //Detectar acción
            var url = null;
            var obj = null;
            obj = form.getFormData();
            switch (form.attr('data-action')) {
                case "A":
                    url = "tipousuario/actualizar";
                    break;
                default:
                    delete obj.idTipoUsuario;
                    url = "tipousuario/insertar";
                    break;
            }
            form.find('input, button').prop("disabled", true);
            form.find('button[type="submit"]').html('<i class="fa fa-fw fa-refresh fa-spin"></i> Enviando...');
            obj = {"tipoUsuario": JSON.stringify(obj)};
            __app.post(url, obj, TipoUsuario.onPostTipoUsuarioCompleto);
        },
        /**
         * Recibe la respuesta del route insertar o actualizar e imprime una alerta para
         * indicar al usuario lo sucedido.
         * @param {Object} respuesta     
         */
        onPostTipoUsuarioCompleto: function (respuesta) {
            var valid = (__app.validarRespuesta(respuesta));
            var titulo = valid ? "Guardado" : "Error";
            var tipo = valid ? "success" : "error";
            __dom.imprimirToast(titulo, respuesta.mensaje, tipo);
            $('#formtipousuario input, button').prop("disabled", false);
            $('#formtipousuario button[type="submit"]').html('<i class="fa fa-fw fa-save"></i> Guardar');
            if (valid) {
                $('#formtipousuario').removeAttr('data-action');
                $('#formtipousuario input, select').val("").trigger('change.select2');
                TipoUsuario.consultarTiposUsuario();
            }
        },
        /**
         * Consulta los tipos de usuario.         
         */
        consultarTiposUsuario: function () {
            __app.get("tipousuario/listartodos", null, TipoUsuario.onConsultarTiposUsuario);
        },
        /**
         * LLena el combo tipos de usuario.
         * @param {Object} respuesta         
         */
        onConsultarTiposUsuario: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            if (datos) {
                TipoUsuario.llenarTablaTiposUsuario(datos);
            }
        },
        llenarTablaTiposUsuario: function (datos) {
            if (TipoUsuario.tablaTiposUsuario) {
                TipoUsuario.tablaTiposUsuario.clear().draw();
                TipoUsuario.tablaTiposUsuario.rows.add(datos);
                TipoUsuario.tablaTiposUsuario.columns.adjust().draw();
                return;
            }
            var obtenerBotones = function () {
                return '<a class="btn btn-editar btn-control'
                        + ' btn-info" href="javascript:;"><i class="fa fa-edit "></i></a>'
                        + '<a class="btn btn-danger btn-eliminar btn-control" href="javascript:;"><i class="fa fa-trash-o "></i></a>';
            };
            var formatearPublico = function (obj) {
                switch (obj.publico) {
                    case true:
                        return "Sí";
                    case false:
                        return "No";
                }
            };
            TipoUsuario.tablaTiposUsuario = __dom.configurarTabla($("#tablaTiposUsuario"), {
                datos: datos,
                columnas: [
                    {title: "Descripción", data: "descripcion"},
                    {title: "Público", data: formatearPublico},
                    {title: "Opciones", data: obtenerBotones}
                ]
            });
        },
    };
    TipoUsuario.init();
});