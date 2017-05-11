/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var __sesion = {
    urlbase: 'http://localhost:8084/votaciones/',
    init: function () {
        __sesion.validarSesion();
    },
    guardarSesion: function (usuario) {
        if (!sessionStorage.getItem("usuario")) {
            usuario = JSON.stringify(usuario);
            sessionStorage.setItem("usuario", usuario);
        }
    },
    validarSesion: function () {
        $.ajax({
            url: __sesion.urlbase + "comprobarsesion",
            type: 'POST',
            async: false,
            success: function (respuesta) {
                if (respuesta.codigo <= 0) {
                    console.log(respuesta);
                    location.href = __sesion.urlbase;
                } else {
                    __sesion.guardarSesion(respuesta.datos);
                }
            }
        });
    },
    obtenerSesion: function () {
        var sesion = sessionStorage.getItem("usuario");
        if (sesion) {
            return JSON.parse(sesion);
        }
    }
};
$(function () {
    __sesion.init();
});