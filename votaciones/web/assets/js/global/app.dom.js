/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var __dom = {
    /**
     * 
     * @param {Elemento} cmb
     * @param {Array} array
     * @param {Object} keyNames: {text: String|Array, value:String}
     */
    llenarCombo: function (cmb, array, keyNames) {
        cmb.html("");
        if (Array.isArray(array)) {
            if (array.length > 0) {
                cmb.append(new Option("Seleccione", ""));
                for (var i = 0; i < array.length; i++) {
                    var dato = array[i];
                    var value = "";
                    if (Array.isArray(keyNames.text)) {
                        var keys = keyNames.text.length;
                        for (var j = 0; j < keys; j++) {
                            value += dato[keyNames.text[j]] + ((j < (keys - 1)) ? " - " : "");
                        }
                    } else {
                        value = dato[keyNames.text];
                    }
                    cmb.append(new Option(value, dato[keyNames.value]));
                }
            }
        } else {
            __dom.comboVacio(cmb);
        }
    },
    comboVacio: function (cmb) {
        cmb.html("");
        cmb.append(new Option("No hay registros", "-1"));
    },
    imprimirAlerta: function (mensaje, tipo, animar, alerta) {
        alerta = ((alerta) ? alerta : $('#alerta'));
        alerta.attr('class', 'alert alert-' + tipo + ' alert-dismissable');
        if (animar === true) {
            alerta.hide().slideDown(200);
        } else {
            alerta.show();
        }
        alerta.find('#text').html(mensaje);
    },
    imprimirAlertaUl: function (mensaje, tipo, alerta, limpiar) {
        if (limpiar === true) {
            alerta.find('ul').html("");
        }
        alerta.attr('class', 'alert alert-' + tipo + ' alert-dismissable');
        alerta.find('ul').append('<li>' + mensaje + '</li>');
        alerta.show();
    },
    formatearFecha: function (dateString) {
        var fecha = new Date(dateString);
        return formatDate(fecha, 'yyyy/MM/dd');
    },
    imprimirToast: function (titulo, mensaje, tipo) {
        switch (tipo) {
            case "success":
                toastr.success(mensaje, titulo, {
                    closeButton: true,
                    progressBar: true,
                });
                break;
            case "warning":
                toastr.warning(mensaje, titulo, {
                    closeButton: true,
                    progressBar: true,
                });
                break;
            case "info":
                toastr.info(mensaje, titulo, {
                    closeButton: true,
                    progressBar: true,
                });
                break;
            case "error":
                toastr.error(mensaje, titulo, {
                    closeButton: true,
                    progressBar: true,
                });
                break;
            case "error":
                break;
        }
    },
    /**     
     * @param {Object} obj
     * @param {function} fn1
     * @param {function} fn2
     * @returns {undefined}
     */
    confirmar(obj, fn1, fn2) {
        swal({
            title: "Confirmar",
            text: obj.mensaje,
            type: "warning",
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Confirmar",
            cancelButtonText: "Cancelar",
            showCancelButton: true,
            html: true,
            closeOnConfirm: (obj.closeOnConfirm === false) ? false : true,
            closeOnCancel: (obj.closeOnCancel === false) ? false : true
        },
                function (confirm) {
                    if (confirm) {
                        if (typeof fn1 === "function") {
                            fn1();
                        }
                    } else {
                        if (typeof fn2 === "function") {
                            fn2();
                        }
                    }
                });
    },
    configurarCalendario: function (control, fechaInicio, fechaFin, fechaDefecto, btnToday) {
        control.mask("9999/99/99");
        control.attr('placeholder', 'AAAA/MM/DD');
        var args = {
            format: 'yyyy/mm/dd',
            weekStart: 1,
            todayBtn: (btnToday) ? 'linked' : false,
            clearBtn: false,
            language: 'es',
            calendarWeeks: true,
            autoclose: true,
            todayHighlight: true
        };
        if (!!fechaInicio) {
            args.startDate = fechaInicio;
        }
        if (!!fechaFin) {
            args.endDate = fechaFin;
        }
        if (!!fechaDefecto) {
            args.defaultViewDate = fechaDefecto;
            control.val(fechaDefecto);
        }
        if (control.parent('.input-group.date').length > 0) {
            return control.parent('.input-group.date').datepicker(args);
        }

        var btn = control.parent().find('.btn');
        if (btn.length > 0) {
            btn.addClass('cursor-pointer');
            btn.on('click', function () {
                $(this).parent().parent().find('input').trigger('focus');
            });
        }

        return control.datepicker(args);
    },
    fechaMayorQue: function (fechaInicial, fechaFinal) {
        var obtenerFecha = function (sFecha) {
            var partes = sFecha.split("/");
            var anio = partes[0];
            var mes = partes[1];
            var dia = partes[2];
            return new Date(anio + "/" + mes + "/" + dia);
        };

        if (obtenerFecha(fechaInicial) > obtenerFecha(fechaFinal)) {
            return false;
        }

        return true;
    },
    configurarTabla(tabla, args) {
        return tabla.DataTable({
            data: args.datos,
            "language": {
                "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
            },
            columns: args.columnas,
            columnDefs: [{
                    defaultContent: "",
                    targets: 0,
                    orderable: false
                }],
            select: {
                style: 'os',
                selector: 'td:first-child'
            },
            order: [[1, 'asc']],
            "sDom": "<'row mb-1'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6 center'p>>",
            renderer: 'bootstrap'
        });
    }
};

$('#contenido').on('click', '.custom-close', function () {
    $(this).parent().hide();
});