/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votacion.negocio.servlets;

import edu.poli.gerencia.votacion.negocio.constantes.EDireccion;
import edu.poli.gerencia.votacion.negocio.constantes.EMensajes;
import edu.poli.gerencia.votacion.negocio.delegado.TipoDocumentoDelegado;
import edu.poli.gerencia.votacion.negocio.recursos.Respuesta;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jhon1
 */
@WebServlet(
        name = "TipoDocumentoServlet",
        urlPatterns = {
            "/listartipodocumento",
            "/insertartipodocumento",
            "/actualizartipodocumento",
            "/eliminartipodocumento"
        }
)
public class TipoDocumentoServlet extends GenericoServlet implements IServlet {

    private TipoDocumentoDelegado delegado;

    @Override
    public void init() throws ServletException {
        super.init();
        delegado = TipoDocumentoDelegado.getInstancia();
    }

    @Override
    public Respuesta procesar(EDireccion direccion, HttpServletRequest request) throws Exception {
        Respuesta respuesta = new Respuesta(EMensajes.RECURSO_NO_ENCONTRADO);
        switch (direccion) {
            case LISTAR_TIPODOCUMENTO:
                respuesta = listarTiposDocumento(request);
                break;
        }
        return respuesta;
    }

    private Respuesta listarTiposDocumento(HttpServletRequest request) {
        return delegado.listarTodos();
    }

}
