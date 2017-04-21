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
        window.setTimeout(function () {
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
        }, 100);
    },
    comboVacio: function (cmb) {
        cmb.html("");
        cmb.append(new Option("No hay registros", "-1"));
    },
    imprimirAlerta: function (mensaje, tipo) {
        var alerta = $('#alerta');
        alerta.attr('class', 'alert alert-' + tipo + ' alert-dismissable').hide().slideDown(300);
        alerta.find('#text').html(mensaje);
    }
};