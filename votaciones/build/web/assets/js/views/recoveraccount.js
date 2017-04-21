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
                __app.post('recuperarcuenta', {credencial: credencial.val()}, RecoverAccount.onConsultaRecuperarCuenta);
//                __dom.imprimirAlerta("Lo sentimos, no se encontró ninguna coincidencia.");
            } else {
                __dom.imprimirAlerta("Debes ingresar la credencial que recuerdas de tu cuenta.", "danger");
            }
        },
        onConsultaRecuperarCuenta: function (respuesta) {
            console.log(respuesta);
        },
    };
    RecoverAccount.init();
});