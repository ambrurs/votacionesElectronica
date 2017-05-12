$(function () {
    var TipoEstadoVotacion = {
        init: function () {
            TipoEstadoVotacion.eventos();
            TipoEstadoVotacion.consultarTipoEstadosVotacion();
            TipoEstadoVotacion.configurarVista();
        },
        eventos: function () {
            $('#formTipoEstadoVotaciones').validator().on('submit', TipoEstadoVotacion.onSubmitForm);
            $("#tablaTipoEstadosVotacion").off('click', '.btn-control');
            $("#tablaTipoEstadosVotacion").on('click', '.btn-control', TipoEstadoVotacion.onClickBotonControl);
        },
        configurarVista: function () {
            $('select').select2({width: "100%"});
        },
        onClickBotonControl: function () {
            var btn = $(this);
            var row = btn.parent().parent();
            var datos = TipoEstadoVotacion.tablaTipoEstadosVotacion.row(row).data();
            if (btn.hasClass("btn-editar")) {
                TipoEstadoVotacion.editar(datos);
            } else if (btn.hasClass("btn-eliminar")) {
                TipoEstadoVotacion.eliminar(datos);
            }
        },
        editar: function (datos) {
            $('#formTipoEstadoVotaciones').fillForm(datos).attr('data-action', 'A');
            $('#formTipoEstadoVotaciones').find('input#txtNombreEstado').focus();
        },
        eliminar: function (datos) {
            __dom.confirmar({mensaje: "El registro se eliminará permanentemente, ¿desea continuar?"}, function () {
                var id = datos.idTipoEstadoVotacion;
                __app.post("tipoestadovotacion/eliminar", {id: id}, function (respuesta) {
                    var valid = (__app.validarRespuesta(respuesta));
                    var titulo = valid ? "Eliminado" : "Error";
                    var tipo = valid ? "success" : "error";
                    __dom.imprimirToast(titulo, respuesta.mensaje, tipo);
                    TipoEstadoVotacion.consultarTipoEstadosVotacion();
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
                    url = "tipoestadovotacion/actualizar";
                    break;
                default:
                    delete obj.idTipoEstadoVotacion;
                    url = "tipoestadovotacion/insertar";
                    break;
            }
            form.find('input, button').prop("disabled", true);
            form.find('button[type="submit"]').html('<i class="fa fa-fw fa-refresh fa-spin"></i> Enviando...');
            obj = {"tipoEstadoVotacion": JSON.stringify(obj)};
            __app.post(url, obj, TipoEstadoVotacion.onPostTipoEstadoVotacionCompleto);
        },
        /**
         * Recibe la respuesta del route insertar o actualizar e imprime una alerta para
         * indicar al usuario lo sucedido.
         * @param {Object} respuesta     
         */
        onPostTipoEstadoVotacionCompleto: function (respuesta) {
            var valid = (__app.validarRespuesta(respuesta));
            var titulo = valid ? "Guardado" : "Error";
            var tipo = valid ? "success" : "error";
            __dom.imprimirToast(titulo, respuesta.mensaje, tipo);
            $('#formTipoEstadoVotaciones input, button').prop("disabled", false);
            $('#formTipoEstadoVotaciones button[type="submit"]').html('<i class="fa fa-fw fa-save"></i> Guardar');
            if (valid) {
                $('#formTipoEstadoVotaciones').removeAttr('data-action');
                $('#formTipoEstadoVotaciones input').val("");
                TipoEstadoVotacion.consultarTipoEstadosVotacion();
            }
        },
        /**
         * Consulta los tipos de usuario.         
         */
        consultarTipoEstadosVotacion: function () {
            __app.get("tipoestadovotacion/listartodos", null, TipoEstadoVotacion.onConsultarTipoDocumentos);
        },
        /**
         * LLena el combo tipos de usuario.
         * @param {Object} respuesta         
         */
        onConsultarTipoDocumentos: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            if (datos) {
                TipoEstadoVotacion.llenarTablaTipoDocumentos(datos);
            }
        },
        llenarTablaTipoDocumentos: function (datos) {
            if (TipoEstadoVotacion.tablaTipoEstadosVotacion) {
                TipoEstadoVotacion.tablaTipoEstadosVotacion.clear().draw();
                TipoEstadoVotacion.tablaTipoEstadosVotacion.rows.add(datos);
                TipoEstadoVotacion.tablaTipoEstadosVotacion.columns.adjust().draw();
                return;
            }
            var obtenerBotones = function () {
                return '<a class="btn btn-editar btn-control'
                        + ' btn-info" href="javascript:;"><i class="fa fa-edit "></i></a>'
                        + '<a class="btn btn-danger btn-eliminar btn-control" href="javascript:;"><i class="fa fa-trash-o "></i></a>';
            };
            TipoEstadoVotacion.tablaTipoEstadosVotacion = __dom.configurarTabla($("#tablaTipoEstadosVotacion"), {
                datos: datos,
                columnas: [
                    {title: "Nombre estado", data: "nombreEstado"},
                    {title: "Opciones", data: obtenerBotones}
                ]
            });
        },
    };
    TipoEstadoVotacion.init();
});