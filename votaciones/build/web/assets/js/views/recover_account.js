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
                __dom.imprimirAlerta('<i class="fa fa-fw fa-refresh fa-spin"></i> Enviando...', 'info');
                form.find('input,button').prop('disabled', true);
                __app.post('recuperarcuenta', {credencial: credencial.val()}, RecoverAccount.onConsultaRecuperarCuenta);
            } else {
                __dom.imprimirAlerta("<i class=\"fa fa-fw fa-warning\"></i> Debes ingresar la credencial que recuerdas de tu cuenta.", "danger");
            }
        },
        onConsultaRecuperarCuenta: function (respuesta) {
            if (__app.respuestaExistosa(respuesta)) {
                $('#formRecoverAccount').find('input,button').prop('disabled', false);
                $('#formRecoverAccount').find('input#usuario').val("");
                __dom.imprimirAlerta("<i class=\"fa fa-fw fa-info\"></i> En unos segundos llegará el correo de recuperación de cuenta a tu bandeja de entrada.", "success");
            } else {
                var mensaje = '<i class="fa fa-fw fa-warning"></i>' + " Lo sentimos no existe ninguna coincidencia con las credenciales ingresadas.";
                if (respuesta.codigo < 0) {
                    mensaje = respuesta.mensaje;
                }
                __dom.imprimirAlerta(mensaje, "danger");
                $('#formRecoverAccount').find('input,button').prop('disabled', false);
            }
            console.log(respuesta);
        },
    };
    RecoverAccount.init();
});