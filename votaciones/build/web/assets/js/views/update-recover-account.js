/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {
    UpdateAccount = {
        form: $('form#formRecoverAccount'),
        init: function () {
            UpdateAccount.eventos();
            UpdateAccount.validarToken();
        },
        validarToken: function () {
            var token = UpdateAccount.getToken();
            UpdateAccount.estadoCampos(true);
            __dom.imprimirAlerta('<i class="fa fa-fw fa-refresh fa-spin"></i> Verificando el token...', "info");
            __app.post('comprobartoken', {token: token}, UpdateAccount.onValidarToken);
        },
        onValidarToken: function (respuesta) {
            if (__app.respuestaExistosa(respuesta)) {
                UpdateAccount.estadoCampos(false);
                $('#alerta').hide();
                UpdateAccount.form.find('#_token').val(UpdateAccount.getToken());
            } else {
                UpdateAccount.estadoCampos(true);
                __dom.imprimirAlerta('El enlace ha vencido o no existe. Si desea obtener un nuevo enlace de recuperación de cuenta, haga <a href="' + __app.base("recoveraccount/") + '">clic aquí</a>', "danger");
            }
        },
        estadoCampos: function (estado) {
            UpdateAccount.form.find('input,button').prop('disabled', estado);
        },
        getToken: function () {
            var path = location.href;
            return path.substr(path.lastIndexOf('/') + 1);
        },
        eventos: function () {
            $('#formRecoverAccount').validator().on('submit', function (e) {
                if (e.isDefaultPrevented()) {
                    __dom.imprimirAlerta("Las contraseñas no coinciden", "danger");
                    return;
                } else {
                    __app.detenerEvento(e);
                    var form = $(this);
                    var clave = form.find('#clave').val();
                    UpdateAccount.actualizarClave(clave);
                }
            });
        },
        actualizarClave: function (clave) {
            __app.post("actualizarcuenta",
                    {
                        "clave": md5(clave),
                        "token": UpdateAccount.getToken()
                    },
                    UpdateAccount.onActualizarClaveCompleto);
        },
        onActualizarClaveCompleto: function (respuesta) {
            if (__app.respuestaExistosa(respuesta)) {
                var form = $('#formRecoverAccount');
                form.find('input').val("");
                form.find('input, button').prop("disabled", true);
                __dom.imprimirAlerta('Se ha actualizado la cuenta correctamente. Ahora puede <a href="' + __app.base("login/") + '">iniciar sesión</a>.', 'success', true);
            } else {
                __dom.imprimirAlerta(respuesta.mensaje, 'danger', true);
            }
        }
    };

    UpdateAccount.init();
});