var __app = {
    urlbase: 'http://localhost:8084/votaciones/',
    validarRespuesta: function (respuesta) {
        switch (respuesta.codigo) {
            case 1:
                respuesta = respuesta;
                break;
            case 0:
                respuesta = respuesta;
                break;
            case - 1:
                respuesta = false;
                break;
            default :
                if (respuesta.codigo < 0) {
                    respuesta = false;
                } else {
                    respuesta = respuesta;
                }
                break;

        }
        return respuesta;
    },
    base: function (url) {
        return __app.urlbase + url;
    },
    respuestaExistosa: function (respuesta) {
        return respuesta.codigo > 0;
    },
    parsearRespuesta: function (respuesta) {
        var datos = __app.validarRespuesta(respuesta);
        if (datos) {
            return datos.datos;
        } else {
            return false;
        }
    },
    detenerEvento: function (e) {
        if (e) {
            if (e.preventDefault) {
                e.preventDefault();
            }
            if (e.stopPropagation) {
                e.stopPropagation();
            }
            if (!!e.returnValue) {
                e.returnValue = false;
            }
        }
        return;
    },
    /**
     * 
     * @param {String} url
     * @param {Object} data
     * @param {function} success
     * @param {function} error
     * @param {function} before
     * @param {function} complete     
     */
    get: function (url, data, success, error, before, complete) {
        var ajax = __app.getObjectAjax(url, data, success, error, "GET", before, complete);
        __app.ajax(ajax);
    },
    /**     
     * @param {String} url
     * @param {Object} data
     * @param {function} success
     * @param {function} error
     * @param {function} before
     * @param {function} complete
     */
    post: function (url, data, success, error, before, complete) {
        var ajax = __app.getObjectAjax(url, data, success, error, "POST", before, complete);
        __app.ajax(ajax);
    },
    getObjectAjax(url, data, success, error, method, before, complete) {
        var ajax = new Object();
        ajax.url = url;
        ajax.data = data;
        ajax.type = method;
        ajax.success = success;
        ajax.error = (error) ? error : __app.ajaxError;
        ajax.beforeSend = before;
        ajax.complete = complete;
        return ajax;
    },
    beforeSend: function (data) {
    },
    ajax: function (args) {
        var ajax = new Object();
        ajax.url = (__app.urlbase + args.url);
        ajax.type = (args.type) ? args.type : "POST";
        ajax.data = (args.data);
        ajax.dataType = (args.dataType) ? args.dataType : "json";
        ajax.beforeSend = (args.beforeSend) ? args.beforeSend : __app.beforeSend;
        ajax.complete = args.complete;
        ajax.success = (args.success);
        ajax.error = (args.error) ? args.error : __app.error;
        $.ajax(ajax);
    },
    error: function (error) {
        console.error(error);
        __dom.imprimirToast("Error", "Se ha producido un error, "
                + "compruebe su conexión, reintenlo o de lo contrario contacte "
                + "el administrador.", "error");
    },
    formToJSON: function (formArray) {
        var returnArray = {};
        for (var i = 0; i < formArray.length; i++) {
            returnArray[formArray[i]['name']] = formArray[i]['value'];
        }
        return returnArray;
    },
};