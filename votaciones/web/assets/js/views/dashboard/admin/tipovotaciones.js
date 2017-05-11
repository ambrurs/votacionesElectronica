$(function () {
    var TipoVotaciones = {
        init: function () {
            TipoVotaciones.eventos();
            TipoVotaciones.consultarTipoVotaciones();
            TipoVotaciones.configurarVista();
        },
        eventos: function () {
            $('#formTipoVotaciones').validator().on('submit', TipoVotaciones.onSubmitForm);
            $("#tablaTipoVotaciones").off('click', '.btn-control');
            $("#tablaTipoVotaciones").on('click', '.btn-control', TipoVotaciones.onClickBotonControl);
        },
        configurarVista: function () {
            $('select').select2({width: "100%"});
        },
        onClickBotonControl: function () {
            var btn = $(this);
            var row = btn.parent().parent();
            var datos = TipoVotaciones.tablaTipoVotaciones.row(row).data();
            console.log(datos);
            if (btn.hasClass("btn-editar")) {
                TipoVotaciones.editar(datos);
            } else if (btn.hasClass("btn-eliminar")) {
                TipoVotaciones.eliminar(datos);
            }
        },
        editar: function (datos) {
            $('#formTipoVotaciones').fillForm(datos).attr('data-action', 'A');
            $('#formTipoVotaciones').find('input#txtNombreTipoVotacion').focus();
        },
        eliminar: function (datos) {
            __dom.confirmar({mensaje: "El registro se eliminará permanentemente, ¿desea continuar?"}, function () {
                var id = datos.idTipoVotacion;
                __app.post("tipovotacion/eliminar", {id: id}, function (respuesta) {
                    var valid = (__app.validarRespuesta(respuesta));
                    var titulo = valid ? "Eliminado" : "Error";
                    var tipo = valid ? "success" : "error";
                    __dom.imprimirToast(titulo, respuesta.mensaje, tipo);
                    TipoVotaciones.consultarTipoVotaciones();
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
                    url = "tipovotacion/actualizar";
                    break;
                default:
                    delete obj.idTipoVotacion;
                    url = "tipovotacion/insertar";
                    break;
            }
            form.find('input, button').prop("disabled", true);
            form.find('button[type="submit"]').html('<i class="fa fa-fw fa-refresh fa-spin"></i> Enviando...');
            obj = {"tipoVotacion": JSON.stringify(obj)};
            __app.post(url, obj, TipoVotaciones.onPostTipoVotacionCompleto);
        },
        /**
         * Recibe la respuesta del route insertar o actualizar e imprime una alerta para
         * indicar al usuario lo sucedido.
         * @param {Object} respuesta     
         */
        onPostTipoVotacionCompleto: function (respuesta) {
            var valid = (__app.validarRespuesta(respuesta));
            var titulo = valid ? "Guardado" : "Error";
            var tipo = valid ? "success" : "error";
            __dom.imprimirToast(titulo, respuesta.mensaje, tipo);
            $('#formTipoVotaciones input, button').prop("disabled", false);
            $('#formTipoVotaciones button[type="submit"]').html('<i class="fa fa-fw fa-save"></i> Guardar');
            if (valid) {
                $('#formTipoVotaciones').removeAttr('data-action');
                $('#formTipoVotaciones input, select').val("").trigger('change.select2');
                TipoVotaciones.consultarTipoVotaciones();
            }
        },
        /**
         * Consulta los tipos de usuario.         
         */
        consultarTipoVotaciones: function () {
            __app.get("tipovotacion/listartodos", null, TipoVotaciones.onConsultarTipoVotaciones);
        },
        /**
         * LLena el combo tipos de usuario.
         * @param {Object} respuesta         
         */
        onConsultarTipoVotaciones: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            if (datos) {
                TipoVotaciones.llenarTablaTipoVotaciones(datos);
            }
        },
        llenarTablaTipoVotaciones: function (datos) {
            if (TipoVotaciones.tablaTipoVotaciones) {
                TipoVotaciones.tablaTipoVotaciones.clear().draw();
                TipoVotaciones.tablaTipoVotaciones.rows.add(datos);
                TipoVotaciones.tablaTipoVotaciones.columns.adjust().draw();
                return;
            }
            var obtenerBotones = function () {
                return '<a class="btn btn-editar btn-control'
                        + ' btn-info" href="javascript:;"><i class="fa fa-edit "></i></a>'
                        + '<a class="btn btn-danger btn-eliminar btn-control" href="javascript:;"><i class="fa fa-trash-o "></i></a>';
            };
            TipoVotaciones.tablaTipoVotaciones = __dom.configurarTabla($("#tablaTipoVotaciones"), {
                datos: datos,
                columnas: [
                    {title: "Descripción", data: "nombreTipoVotacion"},
                    {title: "Opciones", data: obtenerBotones}
                ]
            });
        },
    };
    TipoVotaciones.init();
});