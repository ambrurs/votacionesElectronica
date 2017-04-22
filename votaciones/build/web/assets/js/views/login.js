/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function ()
{
    var Login = {        
        init: function () {
            Login.eventos();            
        },
        eventos: function () {
            $('.close').on('click', function () {
                $(this).parent().hide();
            });
            $('#formLogin').on('submit', Login.onSubmitForm);
        },
        onSubmitForm: function (e) {
            __app.detenerEvento(e);
            var form = $(this);
            if (Login.validarFormulario(form)) {
                __app.post("iniciarsesion",
                        {
                            usuario: form.find('#usuario').val(),
                            clave: md5(form.find("#clave").val())
                        },
                        function (respuesta) {
                            if (__app.respuestaExistosa(respuesta)) {
                                location.href = __app.base("dashboard/");
                            } else {
                                Login.imprimirError("Error en el usuario o la clave.");
                            }
                        },
                        function (e) {
                            console.error(e);
                            Login.imprimirError("Ocurrió un error y no se pudo iniciar.");
                        });
            } else {
                return;
            }
        },
        /**
         * Valida el formulario.
         * @param {Element} form
         * @returns {Boolean}
         */
        validarFormulario: function (form) {
            form.find('.alert ul').html("");
            var valid = 0;
            if (form.find('#usuario').val().trim() === "") {
                Login.imprimirError("Debes ingresar un usuario válido", "errorusuario");
                valid--;
            } else {
                form.find('.alert ul #errorusuario').remove();
            }
            if (form.find('#clave').val().trim() === "") {
                Login.imprimirError("Debes ingresar una clave válida", "errorclave");
                valid--;
            } else {
                form.find('.alert ul #errorclave').remove();
            }
            if (valid == 0) {
                form.find('.alert').hide();
                return true;
            } else {
                return false;
            }
        },
        imprimirError: function (mensaje, clave) {
            var alert = $('form .alert');
            alert.attr('class', 'alert alert-danger alert-dismissable').hide().slideDown(500);
            alert.find('ul').append('<li id="' + clave + '">' + mensaje + '</li>');
        }
    };

    Login.init();
});