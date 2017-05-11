$(function () {
    var TipoDocumento = {
        init: function () {
            TipoDocumento.eventos();
            TipoDocumento.consultarTipoDocumentos();
            TipoDocumento.configurarVista();
        },
        eventos: function () {
            $('#formTipoDocumentos').validator().on('submit', TipoDocumento.onSubmitForm);
            $("#tablaTipoVotaciones").off('click', '.btn-control');
            $("#tablaTipoDocumentos").on('click', '.btn-control', TipoDocumento.onClickBotonControl);
        },
        configurarVista: function () {
            $('select').select2({width: "100%"});
        },
        onClickBotonControl: function () {
            var btn = $(this);
            var row = btn.parent().parent();
            var datos = TipoDocumento.tablaTipoDocumentos.row(row).data();
            console.log(datos);
            if (btn.hasClass("btn-editar")) {
                TipoDocumento.editar(datos);
            } else if (btn.hasClass("btn-eliminar")) {
                TipoDocumento.eliminar(datos);
            }
        },
        editar: function (datos) {
            $('#formTipoDocumentos').fillForm(datos).attr('data-action', 'A');
            $('#formTipoDocumentos').find('input#txtNombreDocumento').focus();
        },
        eliminar: function (datos) {
            __dom.confirmar({mensaje: "El registro se eliminará permanentemente, ¿desea continuar?"}, function () {
                var id = datos.idTipoDocumento;
                __app.post("tipodocumento/eliminar", {id: id}, function (respuesta) {
                    var valid = (__app.validarRespuesta(respuesta));
                    var titulo = valid ? "Eliminado" : "Error";
                    var tipo = valid ? "success" : "error";
                    __dom.imprimirToast(titulo, respuesta.mensaje, tipo);
                    TipoDocumento.consultarTipoDocumentos();
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
                    url = "tipodocumento/actualizar";
                    break;
                default:
                    delete obj.idTipoDocumento;
                    url = "tipodocumento/insertar";
                    break;
            }
            form.find('input, button').prop("disabled", true);
            form.find('button[type="submit"]').html('<i class="fa fa-fw fa-refresh fa-spin"></i> Enviando...');
            obj = {"tipoDocumento": JSON.stringify(obj)};
            __app.post(url, obj, TipoDocumento.onPostTipoDocumentoCompleto);
        },
        /**
         * Recibe la respuesta del route insertar o actualizar e imprime una alerta para
         * indicar al usuario lo sucedido.
         * @param {Object} respuesta     
         */
        onPostTipoDocumentoCompleto: function (respuesta) {
            var valid = (__app.validarRespuesta(respuesta));
            var titulo = valid ? "Guardado" : "Error";
            var tipo = valid ? "success" : "error";
            __dom.imprimirToast(titulo, respuesta.mensaje, tipo);
            $('#formTipoDocumentos input, button').prop("disabled", false);
            $('#formTipoDocumentos button[type="submit"]').html('<i class="fa fa-fw fa-save"></i> Guardar');
            if (valid) {
                $('#formTipoDocumentos').removeAttr('data-action');
                $('#formTipoDocumentos input, select').val("").trigger('change.select2');
                TipoDocumento.consultarTipoDocumentos();
            }
        },
        /**
         * Consulta los tipos de usuario.         
         */
        consultarTipoDocumentos: function () {
            __app.get("tipodocumento/listartodos", null, TipoDocumento.onConsultarTipoDocumentos);
        },
        /**
         * LLena el combo tipos de usuario.
         * @param {Object} respuesta         
         */
        onConsultarTipoDocumentos: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            if (datos) {
                TipoDocumento.llenarTablaTipoDocumentos(datos);
            }
        },
        llenarTablaTipoDocumentos: function (datos) {
            if (TipoDocumento.tablaTipoDocumentos) {
                TipoDocumento.tablaTipoDocumentos.clear().draw();
                TipoDocumento.tablaTipoDocumentos.rows.add(datos);
                TipoDocumento.tablaTipoDocumentos.columns.adjust().draw();
                return;
            }
            var obtenerBotones = function () {
                return '<a class="btn btn-editar btn-control'
                        + ' btn-info" href="javascript:;"><i class="fa fa-edit "></i></a>'
                        + '<a class="btn btn-danger btn-eliminar btn-control" href="javascript:;"><i class="fa fa-trash-o "></i></a>';
            };
            TipoDocumento.tablaTipoDocumentos = __dom.configurarTabla($("#tablaTipoDocumentos"), {
                datos: datos,
                columnas: [
                    {title: "Nombre documento", data: "nombreDocumento"},
                    {title: "Opciones", data: obtenerBotones}
                ]
            });
        },
    };
    TipoDocumento.init();
});