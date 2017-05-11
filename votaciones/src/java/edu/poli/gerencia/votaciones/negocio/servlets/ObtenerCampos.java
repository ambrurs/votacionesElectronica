/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.poli.gerencia.votaciones.negocio.servlets;

import edu.poli.gerencia.votaciones.modelo.conexion.ConnectionDB;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.negocio.constantes.EAcciones;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.negocio.utiles.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
@WebServlet(name = "UsuarioServlet", urlPatterns = {
    "/consultarcampos"
})
public class ObtenerCampos extends GenericoServlet implements IServlet {

    @Override
    public Respuesta procesar(EAcciones eAcciones, HttpServletRequest request, Connection cnn) throws VotacionesException {
        Respuesta respuesta = null;
        respuesta = consultarCampos(request, cnn);
        return respuesta;
    }

    private Respuesta consultarCampos(HttpServletRequest request, Connection cnn) {
        Respuesta respuesta = new Respuesta(EMensajes.NO_SE_ENCONTRARON_REGISTROS);
        PreparedStatement pstmt = null;
        ArrayList<Field> fields = new ArrayList<>();
        String sql = "DESCRIBE " + request.getParameter("tabla");
        try {
            pstmt = cnn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            Field field = null;
            while (rs.next()) {
                field = new Field();
                field.setName(rs.getString("Field"));
                fields.add(field);
            }
            respuesta.setDatos(fields);
        } catch (SQLException ex) {
            Logger.getLogger(ObtenerCampos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionDB.desconectar(pstmt);
        }

        return respuesta;
    }

}
