$(function () {
    var Registro = {
        //Controles:
        cmbTipoDocumento: $('#cmbTipoDocumento'),
        init: function () {
            Registro.eventos();
            Registro.consultarTiposDocumento();
        },
        consultarTiposDocumento: function () {
            __app.get("listartipodocumento", null, Registro.onListarTiposDocumento, null);
        },
        onListarTiposDocumento: function (respuesta) {
            var datos = __app.parsearRespuesta(respuesta);
            if (datos) {
                __dom.llenarCombo(Registro.cmbTipoDocumento, datos, {"text": "nombreDocumento", "value": "idTipoDocumento"});
            }
        },
        eventos: function () {
            $('#form_registro').validator().on('submit', function (e) {
                __app.detenerEvento(e);
                console.log("Submit");
                var form = $(this);
                var obj = Registro.obtenerObjetoRegistro(form);
                __app.post('registrarusuario', obj, Registro.onRegistrarCompleto);
            });
        },
        onRegistrarCompleto: function (respuesta) {
            console.log(respuesta);
            if (__app.respuestaExistosa(respuesta)) {
                if (respuesta.codigo > 0) {
                    Registro.imprimirAlerta("Registrado correctamente, vuelve al inicio para ingresar al sistema.", "success");
                    $('#form_registro').find('input,select').val("");
                }
            } else {
                Registro.imprimirAlerta("No se ha podido completar el registro..", "danger");
            }
        },
        imprimirAlerta: function (mensaje, tipo) {
            var alert = $('#alert');
            alert.attr('class', 'alert alert-' + tipo + ' alert-dismissable').find('#text').html(mensaje).hide().slideDown(300);
        },
        obtenerObjetoRegistro: function (form) {
            var obj = new Object();
            obj.usuario = {
                nombreUsuario: form.find('#txtNombreusuario').val(),
                contrasena: md5(form.find('#txtClave').val()),
            };
            obj.persona = {
                correo: form.find('#txtCorreoElectronico').val(),
                primerNombre: form.find('#txtPrimerNombre').val(),
                segundoNombre: form.find('#txtSegundoNombre').val(),
                primerApellido: form.find('#txtPrimerApellid').val(),
                segundoApellido: form.find('#txtSegundoApellido').val(),
                nombreEmpresa: form.find('#txtNombreEmpresa').val(),
                idTipoDocumento: {idTipoDocumento: form.find('#cmbTipoDocumento').val()},
                numeroDocumento: form.find('#txtNumeroDocumento').val()
            };
            console.log(obj.usuario);
            obj.usuario = JSON.stringify(obj.usuario);
            console.log(obj.usuario);
            obj.persona = JSON.stringify(obj.persona);
            return obj;
        }
    };
    Registro.init();
});