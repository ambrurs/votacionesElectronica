$(function () {
    var ListaVotaciones = {
        init: function () {
            ListaVotaciones.eventos();
            ListaVotaciones.consultarVotaciones();
        },
        eventos: function () {
            $('#listaVotaciones').off('click', '.item-votacion', ListaVotaciones.onClickVotaciones);
            $('#listaVotaciones').on('click', '.item-votacion', ListaVotaciones.onClickVotaciones);
        },
        onClickVotaciones: function () {
            var item = $(this);
            sessionStorage.setItem("idvotacion", item.attr('data-id'));
            location.href = "#detallesvotacion";
        },
        configurarVista: function () {

        },
        consultarVotaciones: function () {
            __app.get("listarvotaciones", null, ListaVotaciones.onConsultarVotaciones);
        },
        onConsultarVotaciones: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            datos = (datos.length > 0) ? datos : false;
            if (!datos) {
                __dom.imprimirAlerta('<i class="fa fa-fw fa-warning"></i> No hay votaciones en el momento.', 'warning', false, $('#alertaConsultandoVotaciones'));
                return;
            }
            $('#alertaConsultandoVotaciones').addClass('hidden');
            var contenedor = $('#listaVotaciones');
            var model = contenedor.find('.model');
            var clon = null;
            var tipos = null;
            for (var i = 0, dato; dato = datos[i], i < datos.length; i++) {
                clon = model.clone().removeClass("hidden model").addClass('item-votacion').attr('data-id', dato.consVotacion);
                tipos = ListaVotaciones.detectarTipos(dato);
                clon.find('.fa').attr('class', 'fa ' + tipos[1] + ' bg-' + tipos[0] + ' p-2 font-2xl mr-1 float-left');
                clon.find('.h5').attr('class', 'h5 text-' + tipos[0] + ' mb-0 pt-1').html(dato.idTipoVotacion.nombreTipoVotacion);
                clon.find('.text-muted').html(tipos[2]);
                contenedor.append(clon);
            }
        },
        detectarTipos: function (obj) {
            var tipo = obj.estadoVotacion;
            var tipoFinal = ["default", "fa-question", "Desconocido"];
            switch (tipo) {
                case "S": //Selección candidato.
                    tipoFinal = ["info", "fa-bell", obj.cantidadCandidatos + " Candidatos"];
                    break;
                case "E": //En curso.
                    tipoFinal = ["success", "fa-check", obj.cantidadVotos + " Votos"];
                    break;
                case "F": //Finalizda.
                    tipoFinal = ["default", "fa-flag", obj.cantidadVotos + " Votos/" + obj.cantidadCandidatos + " Cantidatos"];
                    break;
                case "C": //Cancelada.
                    tipoFinal = ["danger", "fa-times", obj.cantidadVotos + " Votos"];
                    break;
            }
            return tipoFinal;
        }
    };
    ListaVotaciones.init();
});