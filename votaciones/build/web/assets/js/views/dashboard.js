/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    var Dashboard = {
        init: function () {
            Dashboard.eventos();
            Dashboard.consultarNotificaciones();
            Dashboard.hashChange();
        },
        eventos: function () {
            $(window).bind('hashchange', Dashboard.hashChange);
        },
        configurarVista: function () {

        },
        //Interpreta el cambio de url y carga la página.
        hashChange: function () {
            var hash = location.hash;
            console.log(hash);
            if (hash.trim() != "") {
                Dashboard.cargarPagina(Dashboard.obtenerURL(hash));
            }
        },
        consultarNotificaciones: function () {
            __app.get("consultarnotificaciones", null, Dashboard.onConsultarNotificaciones);
        },
        onConsultarNotificaciones: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            if (datos) {
                if (datos.usuariosregistrados.length > 0) {
                    var total = datos.usuariosregistrados.length;
                    $('.numero_notificaciones').removeClass('hidden').html(total);
                    $('#usuarios_registrados').html(datos.usuariosregistrados.length);
                }
            }
        },
        obtenerURL: function (hash) {
            var url = "#undefined";
            switch (hash) {
                case "#usuarios":
                    url = "pages/admindashboard/usuarios.jsp";
                    break;
                case "#tipousuarios":
                    url = "pages/admindashboard/tipousuarios.jsp";
                    break;
                case "#tipodocumentos":
                    url = "pages/admindashboard/tipodocumentos.jsp";
                    break;
                case "#tipovotaciones":
                    url = "pages/admindashboard/tipovotaciones.jsp";
                    break;
                case "#tipoestadosvotacion":
                    url = "pages/admindashboard/tipoestadovotacion.jsp";
                    break;
                case "#votaciones":
                    url = "pages/admindashboard/votaciones.jsp";
                    break;
                case "#configuracioncuenta":
                    url = "pages/generic/configuracioncuenta.html";
                    break;
                case "#detallesvotacion":
                    url = "pages/generic/detallesvotacion.html";
                    break;
            }
            return url;
        },
        cargarPagina: function (url) {
            if (url === "#undefined") {
                $('#contenido').html(
                        '<div class="container"><div class="row '
                        + 'justify-content-center"><div class="col-md-6">'
                        + '<div class="clearfix text-center"><h1 class="mr-2">'
                        + 'Error 400</h1><h4 class="pt-1">Ups! Estás perdido.'
                        + '</h4><p class="text-muted">La página que quieres ver '
                        + 'no existe.</p><a href="/votaciones/" class="btn btn-'
                        + 'primary">Volver al inicio</a></div></div></div></div>');
                return;
            }
            $.ajax({
                url: __app.base(url),
                type: 'POST',
                success: function (html) {
                    $('#contenido').html(html);
                },
            });
        },
    };
    Dashboard.init();
});