$(function () {
    var DetallesVotacion = {
        idVotacion: 0,
        idTipoUsuario: 0,
        init: function () {
            DetallesVotacion.idTipoUsuario = __sesion.obtenerSesion().idTipoUsuario.idTipoUsuario;
            DetallesVotacion.eventos();
            DetallesVotacion.consultarDetallesVotacion();
        },
        eventos: function () {
            $('#btnPostular').on('click', DetallesVotacion.inscribirComoCandidato);
            $('#btnEnviarVoto').on('click', DetallesVotacion.enviarVoto);
            $('#contentInfoVotacion').on('click', '.item-candidato', function () {
                var indice = $(this).attr('data-indice');
                var datos = DetallesVotacion.listaCandidatos[indice];
                DetallesVotacion.votar(datos);
            });
        },
        reiniciarFormulario: function () {
            __dom.imprimirAlerta('<i class="fa fa-fw fa-refresh fa-spin"></i> Consultando detalles de votación', 'info', false, $('#alertDetallesVotacion'));
            $('#contentInfoVotacion').addClass("hidden");
            DetallesVotacion.consultarDetallesVotacion();
        },
        inscribirComoCandidato: function () {
            var btn = $(this);
            if (DetallesVotacion.idVotacion > 0) {
                if (btn.hasClass('inscrito')) {
                    __dom.confirmar({mensaje: "Se removerá la inscripción permantentemente ¿desea continuar?"}, function () {
                        __app.post("candidatovotacion/eliminar", {idVotacion: DetallesVotacion.idVotacion}, function (respuesta) {
                            DetallesVotacion.reiniciarFormulario();
                            var valid = __app.respuestaExistosa(respuesta);
                            var titulo = valid ? "Inscripción Eliminada" : "Error";
                            var tipo = valid ? "success" : "error";
                            __dom.imprimirToast(titulo, respuesta.mensaje, tipo);
                            btn.attr('class', 'btn btn-primary').html('<i class="fa fa-fw fa-user-plus"></i> Inscribirse como candidato');
                        });
                    });
                    return;
                }
                btn.html('<i class="fa fa-fw fa-refresh fa-spin"></i> Enviando...');
                var obj = new Object();
                obj.votacionConsVotacion = {consVotacion: DetallesVotacion.idVotacion};
                __app.post("candidatovotacion/insertar", {candidatoVotacion: JSON.stringify(obj)}, DetallesVotacion.onInscribirComoCandidato);
            } else {
                __dom.imprimirToast("Error", "La votación actual no es válida", "error");
            }
        },
        onInscribirComoCandidato: function (respuesta) {
            DetallesVotacion.reiniciarFormulario();
            var valid = __app.respuestaExistosa(respuesta);
            var titulo = valid ? "Inscrito" : "Error";
            var tipo = valid ? "success" : "error";
            __dom.imprimirToast(titulo, respuesta.mensaje, tipo);
            if (valid) {
                $('#btnPostular').attr('class', 'btn btn-link inscrito').html('Ya te encuentras inscrito como candidato, has clic en este enlace si deseas cancelar la inscripción.');
            } else {
                $('#btnPostular').attr('class', 'btn btn-link').html('No se pudo registrar, si desea reintentarlo haga clic en este enlace.');
            }
        },
        consultarDetallesVotacion: function () {
            var id = sessionStorage.getItem("idvotacion");
            if (id) {
                DetallesVotacion.idVotacion = id;
                __app.get("detallesvotacion", {"id": id}, DetallesVotacion.onConsultarDetallesVotacion);
            }
        },
        onConsultarDetallesVotacion: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            $('#alertDetallesVotacion').hide();
            if (datos) {
                var votacion = datos.votacion;
                $('#titulo').html(votacion.idTipoVotacion.nombreTipoVotacion);
                $('#estadoVotacion').html(DetallesVotacion.detectarEstado(votacion));
                $('#fechaInicioInscripcion').html(__dom.formatearFecha(votacion.fechaInicioInscripcion));
                $('#fechaFinInscripcion').html(__dom.formatearFecha(votacion.fechaFinInscripcion));
                $('#fechaInicioVotacion').html(__dom.formatearFecha(votacion.fechaInicioVotacion));
                $('#fechaFinVotacion').html(__dom.formatearFecha(votacion.fechaFinVotacion));
                $('#numVotos').html(votacion.cantidadVotos);
                $('#numCandidatos').html(votacion.cantidadCandidatos);
                $('#contentInfoVotacion').removeClass('hidden');
                if (datos.usuarioActualEsCandidato) {
                    $('#btnPostular').attr('class', 'btn btn-link inscrito').html('Ya te encuentras inscrito como candidato, has click en este enlace si deseas cancelar la inscripción.');
                }
                if (datos.usuarioActualHaVotado) {
                    $('.btn-item-votar').remove();
                    __dom.imprimirAlerta('<i class="fa fa-fw fa-info-circle"></i> Ud ya participó en esta votación, gracias por votar.', "warning", false, $('#alertaCuerpoDetalleVotacion'));
                }
                if (votacion.estadoVotacion !== "E") {
                    __dom.imprimirAlerta('<i class="fa fa-fw fa-info-circle"></i> Las votaciones estarán disponibles a partir del ' + __dom.formatearFecha(votacion.fechaInicioInscripcion) + " hasta " + __dom.formatearFecha(votacion.fechaFinInscripcion), "warning", false, $('#alertaCuerpoDetalleVotacion'));
                    $('.btn-item-votar').remove();
                } else if (votacion.estadoVotacion !== "S") {
                    $('#btnPostular').next().remove();
                    $('#btnPostular').remove();
                }
                DetallesVotacion.listarCandidatos(datos.listaCandidatos);
            } else {
                __dom.imprimirAlerta("No se pudo consultar los detalles de la votación", "danger", false, $('#alertDetallesVotacion'));
            }
        },
        listarCandidatos: function (datos) {
            var content = $('#listaCandidatos');
            content.find(".item-candidato").remove();
            if (datos) {
                DetallesVotacion.listaCandidatos = datos;
                var dato = null;
                var modelo = content.find('.modelo');
                var clon = null;
                for (var i = 0; i < datos.length; i++) {
                    dato = datos[i];
                    clon = modelo.clone().removeClass('model hidden').addClass('item-candidato').attr('data-indice', i);
                    clon.find('.btn-item-votar').attr('data-indice', i);
                    var nombre = dato.primerNombre + " " + dato.segundoNombre + " " + dato.primerApellido + " " + dato.segundoApellido;
                    clon.find('#nombrePersona').html(nombre).attr('title', nombre);
                    clon.find('#nombreUsuario').html(dato.consUsuario.nombreUsuario);
                    var imagen = dato.imagenPerfil ? dato.imagenPerfil : "../assets/img/avatars/usuario.jpg";
                    clon.find('#imagenUsuario').attr("src", imagen);
                    content.append(clon);
                }
                $('.btn-item-votar').on('click', DetallesVotacion.onClickVotar);
            } else {
                __dom.imprimirAlerta('<i class="fa fa-fw fa-info-circle"></i> No hay candidatos.', "warning", false, $('#alertaCuerpoDetalleVotacion'));
            }
        },
        onClickVotar: function () {
            var indice = $(this).attr('data-indice');
            var datos = DetallesVotacion.listaCandidatos[indice];
            DetallesVotacion.votar(datos);
        },
        enviarVoto: function () {
            var btn = $(this);
            $('#alertConfirmarVotacion').removeClass("hidden");
            var obj = new Object();
            obj.candidatoVotacionConsCandidato = {consCandidato: btn.attr('data-id')};
            __app.post("votacionusuariocandidato/insertar",
                    {
                        votacionUsuarioCandidato: JSON.stringify(obj),
                        idVotacion: DetallesVotacion.idVotacion
                    },
                    function (respuesta) {
                        $('#alertConfirmarVotacion').addClass("hidden");
                        $('#modalConfirmarVotacion').modal("hide");
                        var valid = __app.validarRespuesta(respuesta);
                        var titulo = (valid) ? "Registrado" : "Error";
                        var tipo = (valid) ? "success" : "error";
                        var mensaje = (valid) ? "Se ha registrado el voto correctamente." : "No se pudo registrar su votación";
                        __dom.imprimirToast(titulo, mensaje, tipo);
                        DetallesVotacion.reiniciarFormulario();
                    });
        },
        votar: function (datos) {
            if (datos) {
                var modal = $('#modalConfirmarVotacion')
                modal.modal("show");
                var nombres = datos.primerNombre + " " + datos.segundoNombre;
                var apellidos = datos.primerApellido + " " + datos.segundoApellido;
                modal.find('#nombres').html(nombres);
                modal.find('#apellidos').html(apellidos);
                var imagen = datos.imagenPerfil ? datos.imagenPerfil : "../assets/img/avatars/usuario.jpg";
                modal.find('#imgUsuario').attr('src', imagen);
                modal.find('#usuario').html(datos.consUsuario.nombreUsuario);
                modal.find('#btnEnviarVoto').attr('data-id', datos.consUsuario.consUsuario);
            }
        }
        ,
        detectarEstado: function (obj) {
            var estado = "";
            switch (obj.estadoVotacion) {
                case "S": //Selección de candidatos.
                    estado = '<span class="badge cursor-pointer badge-info">Selección</span>';
                    break;
                case "E": //En curso.
                    estado = '<span class="badge cursor-pointer badge-success">En curso</span>';
                    ;
                    break;
                case "F": //Finalizado.
                    estado = '<span class="badge cursor-pointer badge-default">Finalizada</span>';
                    break;
                case "C": //Cancelado.
                    estado = '<span class="badge cursor-pointer badge-danger">Cancelada</span>';
                    break;
            }
            return estado;
        }
    }
    ;
    DetallesVotacion.init();
});