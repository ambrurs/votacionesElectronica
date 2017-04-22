/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {
    var RecoverAccount = {
        init: function () {
            RecoverAccount.eventos();
        },
        eventos: function () {
            $('#formRecoverAccount').on('submit', RecoverAccount.onSubmitForm);
        },
        onSubmitForm: function (e) {
            __app.detenerEvento(e);
            var form = $(this);
            var credencial = form.find('#usuario');
            if (credencial.val().trim() != "") {
                __app.beforeSend = function () {
                    $('#alert').removeClass('hidden alert-success').addClass('alert-info').find('#text').html('<i class="fa fa-fw fa-refresh fa-spin"></i> Enviando...');
                };
                __app.post('recuperarcuenta', {credencial: credencial.val()}, RecoverAccount.onConsultaRecuperarCuenta);
//                __dom.imprimirAlerta("Lo sentimos, no se encontró ninguna coincidencia.");
            } else {
                __dom.imprimirAlerta("Debes ingresar la credencial que recuerdas de tu cuenta.", "danger");
            }
        },
        onConsultaRecuperarCuenta: function (respuesta) {
            if (__app.respuestaExistosa(respuesta)) {
                __dom.imprimirAlerta("En unos segundos llegará el correo de recuperación de cuenta a tu bandeja de entrada.", "success");
            } else {
                __dom.imprimirAlerta("Lo sentimos no existe ninguna coincidencia con las credenciales ingresadas.", "danger");
            }
            console.log(respuesta);
        },
    };
    RecoverAccount.init();
});