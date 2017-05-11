$(function () {
    var Votaciones = {
        form: $('#formNuevaVotacion'),
        modal: $('#modalCogVotacion'),
        init: function () {
            Votaciones.eventos();
            Votaciones.consultarTipoVotaciones();
            Votaciones.configurarVista();
            Votaciones.listarVotaciones();
        },
        eventos: function () {
            Votaciones.form.validator().on('submit', Votaciones.onSubmitForm);
            $('#btnNuevaVotacion').on('click', Votaciones.nuevaVotacion);
            $('#btnCerraModalListaCandidatos').on('click', function () {
                $('#modalCogVotacion').modal("show");
            });
            $('#btnVerCandidatos').on('click', Votaciones.verCandidatos);
            $("#tablaVotaciones").off('click', '.btn-control');
            $("#tablaVotaciones").on('click', '.btn-control', Votaciones.onClickBotonControl);
        },
        verCandidatos: function () {
            $('#modalCogVotacion').modal("hide");
            var modal = $('#modalListaCandidatos');
            modal.modal("show");
            modal.find('#alertaCandidatos').find('#text').html('<i class="fa fa-fw fa-refresh fa-spin"></i> Consultando candidatos');
            modal.find('#alertaCandidatos').show();
            Votaciones.consultarCandidatos();
        },
        consultarCandidatos: function () {
            __dom.imprimirAlerta('<i class="fa fa-fw fa-refresh fa-spin"></i> Consultando candidatos...', 'info', false, $('#alertaCandidatos'));
            __app.get('candidatovotacion/listartodos', {idVotacion: $('#consVotacion').val()}, Votaciones.onConsultarCandidatos);
        },
        onConsultarCandidatos: function (respuesta) {
            $('#alertaCandidatos').hide();
            var datos = __app.parsearRespuesta(respuesta);
            if (!datos) {
                __dom.imprimirAlerta('<i class="fa fa-fw fa-warning"></i> Aún no hay candidatos', 'warning', false, $('#alertaCandidatos'));
                datos = [];
            }
            if (datos.length === 0) {
                __dom.imprimirAlerta('<i class="fa fa-fw fa-warning"></i> Aún no hay candidatos', 'warning', false, $('#alertaCandidatos'));
            }
            Votaciones.llenarTablaCandidatos(datos);
        },
        llenarTablaCandidatos: function (datos) {
            if (Votaciones.tablaCandidatos) {
                Votaciones.tablaCandidatos.clear().draw();
                Votaciones.tablaCandidatos.rows.add(datos);
                Votaciones.tablaCandidatos.columns.adjust().draw();
                return;
            }
            if (!Votaciones.tablaCandidatos && datos.length === 0) {
                return;
            }
            var obtenerIndice = function (obj) {
                var num = $('#tablaCandidatos tbody').find('tr').length;
                return (num > 0) ? num : (num + 1);
            };
            Votaciones.tablaCandidatos = __dom.configurarTabla($('#tablaCandidatos'), {
                datos: datos,
                columnas: [
                    {title: "#", data: obtenerIndice},
                    {title: "Candidato", data: "consUsuarioVotacion.nombreUsuario"},
                    {title: "Votos", data: "cantidadVotos"},
                ]
            });
        },
        nuevaVotacion: function () {
            var sesion = __sesion.obtenerSesion();
            if (sesion) {
                $('#txtAuthor').val(sesion.nombreUsuario);
            }
            $('#btnVerCandidatos').hide();
            Votaciones.form.attr('data-accion', 'C').find('.modal-title').html('<i class="fa fa-fw fa-plus"></i> Crear');
            Votaciones.modal.find('#btnGuardar').show();
            Votaciones.modal.find('#alertaVotaciones').hide();
            Votaciones.modal.find('.has-error').removeClass("has-error has-danger");
            Votaciones.modal.find('input:not(.no-disabled),select,button').prop("disabled", false).val("").trigger('change.select2');
        },
        validarRangosFechas: function (form) {
            var valid = 0;
            var fecha1 = form.find('#txtFechaInicioInscripcion');
            var fecha2 = form.find('#txtFechaFinInscripcion');
            //Se valida si la fecha fin es mayor que la fecha inicio.
            if (!__dom.fechaMayorQue(fecha1.val(), fecha2.val())) {
                fecha2.parent().addClass('has-error');
                __dom.imprimirAlertaUl("La fecha fin de inscripción debe ser mayor a la fecha de inicio.", "danger", $('#alertaVotaciones'), true);
                valid--;
            } else {
                fecha2.parent().removeClass('has-error');
            }
            fecha1 = form.find('#txtFechaInicioVotacion');
            fecha2 = form.find('#txtFechaFinVotacion');
            if (!__dom.fechaMayorQue(fecha1.val(), fecha2.val())) {
                fecha2.parent().addClass("has-error");
                __dom.imprimirAlertaUl("La fecha fin de votación debe ser mayor a la fecha de inicio.", "danger", $('#alertaVotaciones'), (valid === 0));
                valid--;
            } else {
                fecha2.parent().removeClass("has-error");
            }
            return valid === 0;
        },
        onSubmitForm: function (e) {
            if (e.isDefaultPrevented()) {
                return;
            }
            __app.detenerEvento(e);
            var form = $(this);
            if (!Votaciones.validarRangosFechas(form))
            {
                return;
            }


            //Detectar acción
            var url = null;
            var obj = null;
            obj = form.getFormData();
            switch (form.attr('data-accion')) {
                case "A":
                    url = "votacion/actualizar";
                    break;
                case "V":
                    return;
                default:
                    delete obj.consVotacion;
                    url = "votacion/insertar";
                    break;
            }
            __dom.imprimirAlertaUl('<i class="fa fa-fw fa-refresh fa-spin"></i> Enviando...', "info", $('#alertaVotaciones'), true);
            obj = {"votacion": JSON.stringify(obj)};
            __app.post(url, obj, Votaciones.onPostVotacionCompleto);
        },
        onPostVotacionCompleto: function (respuesta) {
            var valid = (__app.validarRespuesta(respuesta));
            var titulo = valid ? "Correcto" : "Error";
            var tipo = valid ? "success" : "danger";
            __dom.imprimirAlertaUl("<b>" + titulo + ":</b> " + respuesta.mensaje, tipo, $('#alertaVotaciones'), true);
            if (valid) {
                Votaciones.listarVotaciones();
                if ($('#formNuevaVotacion').attr('data-accion') == "C") {
                    $('#formNuevaVotacion input, select').val("").trigger('change.select2');
                }
//                $('#formNuevaVotacion').removeAttr('data-accion');
            }
        },
        onClickBotonControl: function () {
            var btn = $(this);
            Votaciones.modal.find('.has-error').removeClass("has-error has-danger");
            Votaciones.modal.find('#alertaVotaciones').hide();
            Votaciones.modal.find('#alertaVotaciones').hide();
            var row = btn.parent().parent();
            var datos = Votaciones.tablaVotaciones.row(row).data();
            if (btn.hasClass("btn-ver")) {
                Votaciones.ver(datos);
            } else if (btn.hasClass("btn-editar")) {
                Votaciones.editar(datos);
            } else if (btn.hasClass("btn-eliminar")) {
                Votaciones.eliminar(datos);
            }
        },
        ver: function (datos) {
            $('#btnVerCandidatos').show();
            Votaciones.form.fillForm(datos).attr('data-accion', 'V')
                    .find('input:not(.no-disabled),select').prop("disabled", true)
                    .trigger('change.select2');
            Votaciones.form.find('.modal-title').html('<i class="fa fa-fw fa-eye"></i> Detalles');
            Votaciones.form.find('#btnGuardar').hide();
            Votaciones.modal.modal("show");
        },
        editar: function (datos) {
            $('#btnVerCandidatos').hide();
            Votaciones.form.fillForm(datos).attr('data-accion', "A")
                    .find('input:not(.no-disabled),select').prop("disabled", false)
                    .trigger('change.select2');
            Votaciones.form.find('.modal-title').html('<i class="fa fa-fw fa-edit"></i> Editar');
            Votaciones.form.find('#btnGuardar').show();
            Votaciones.modal.modal("show");
        },
        eliminar: function (datos) {
            __dom.confirmar({mensaje: "Se eliminará el registro permanentemente, ¿desea continuar?"}, function () {
                __app.post('votacion/eliminar', {id: datos.consVotacion}, function (respuesta) {
                    var valido = __app.respuestaExistosa(respuesta);
                    var titulo = valido ? "Eliminado" : "Error";
                    var tipo = valido ? "success" : "error";
                    __dom.imprimirToast(titulo, respuesta.mensaje, tipo);
                    Votaciones.listarVotaciones();
                });
            });
        },
        consultarTipoVotaciones: function () {
            __app.get("tipovotacion/listartodos", null, Votaciones.onConsultarTipoVotaciones);
        },
        onConsultarTipoVotaciones: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            if (datos) {
                __dom.llenarCombo($('#cmbIdTipoVotacion'), datos, {text: "nombreTipoVotacion", value: "idTipoVotacion"});
            }
        },
        configurarVista: function () {
            $('select').select2({width: "100%"});
            //Se configuran los calendarios...
            __dom.configurarCalendario($('#txtFechaInicioInscripcion'));
            __dom.configurarCalendario($('#txtFechaFinInscripcion'));
            __dom.configurarCalendario($('#txtFechaInicioVotacion'));
            __dom.configurarCalendario($('#txtFechaFinVotacion'));
        },
        listarVotaciones: function () {
            __app.get("votacion/listartodos", null, Votaciones.onListarVotacionesCompleto);
        },
        onListarVotacionesCompleto: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            if (datos) {
                Votaciones.llenarTabla(datos);
            }
        },
        /**
         * Configura la tabla y a su vez, también la llena.
         * @param {type} datos
         * @returns {undefined}
         */
        llenarTabla: function (datos) {
            if (Votaciones.tablaVotaciones) {
                Votaciones.tablaVotaciones.clear().draw();
                Votaciones.tablaVotaciones.rows.add(datos);
                Votaciones.tablaVotaciones.columns.adjust().draw();
                return;
            }

            var fFecha1 = function (obj) {
                return __dom.formatearFecha(obj.fechaInicioVotacion);
            };
            var fFecha2 = function (obj) {
                return __dom.formatearFecha(obj.fechaFinVotacion);
            };

            var obtenerBotones = function () {
                return '<a class="btn btn-success btn-control btn-ver" href="javascript:;"><i class="fa fa-search-plus "></i></a>'
                        + '<a class="btn btn-editar btn-control'
                        + ' btn-info" href="javascript:;"><i class="fa fa-edit "></i></a>'
                        + '<a class="btn btn-danger btn-eliminar btn-control" href="javascript:;"><i class="fa fa-trash-o "></i></a>';
            };
            var detectarEstado = function (obj) {
                var estado = "";
                switch (obj.estadoVotacion) {
                    case "S": //Selección de candidatos.
                        estado = '<span data-value="N" class="badge cursor-pointer badge-info">Selección</span>';
                        break;
                    case "E": //En curso.
                        estado = '<span data-value="N" class="badge cursor-pointer badge-success">En curso</span>';
                        ;
                        break;
                    case "F": //Finalizado.
                        estado = '<span data-value="N" class="badge cursor-pointer badge-default">Finalizada</span>';
                        break;
                    case "C": //Cancelado.
                        estado = '<span data-value="N" class="badge cursor-pointer badge-danger">Cancelada</span>';
                        break;
                }
                return estado;
            };
            Votaciones.tablaVotaciones = __dom.configurarTabla($("#tablaVotaciones"), {
                datos: datos,
                columnas: [
                    {title: "Tipo votación", data: "idTipoVotacion.nombreTipoVotacion"},
                    {title: "Fecha Inicio", data: fFecha1},
                    {title: "Fecha Fin", data: fFecha2},
                    {title: "Estado", data: detectarEstado},
                    {title: "Opciones", data: obtenerBotones}
                ]
            });
        },
        listarPorEstado: function () {

        },
    };
    Votaciones.init();
});