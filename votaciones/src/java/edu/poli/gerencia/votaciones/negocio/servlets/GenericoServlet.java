package edu.poli.gerencia.votaciones.negocio.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.poli.gerencia.votaciones.negocio.excepciones.VotacionesException;
import edu.poli.gerencia.votaciones.negocio.utiles.UtilURL;
import edu.poli.gerencia.votaciones.modelo.conexion.ConnectionDB;
import edu.poli.gerencia.votaciones.modelo.vo.Database;
import edu.poli.gerencia.votaciones.modelo.vo.Respuesta;
import edu.poli.gerencia.votaciones.negocio.constantes.EMensajes;
import edu.poli.gerencia.votaciones.negocio.constantes.DatabaseType;
import edu.poli.gerencia.votaciones.negocio.constantes.EAcciones;
import edu.poli.gerencia.votaciones.negocio.constantes.EConfiguracion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.poli.gerencia.votaciones.negocio.recursos.DatabaseProperties;
import edu.poli.gerencia.votaciones.negocio.utiles.SesionUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jhonjaider1000
 * @email contacto@jhonjaider1000.com
 */
public abstract class GenericoServlet extends HttpServlet implements IServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse httpResponse)
            throws ServletException, IOException {
        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("UTF-8");
        try (PrintWriter out = httpResponse.getWriter()) {
            Connection cnn = null;
            try {                
                Database db = new Database(
                        DatabaseProperties.get("host"),
                        DatabaseProperties.get("port"),
                        DatabaseProperties.get("database"),
                        DatabaseProperties.get("username"),
                        DatabaseProperties.get("password"),
                        DatabaseType.MYSQL);
                cnn = ConnectionDB.conectar(db);
                String respuesta = processRequest(request, cnn, httpResponse);
                ConnectionDB.commit(cnn);
                //Se aumenta el tiempo de expiración.
                SesionUtil.actualizarSesion(request);
                out.print(respuesta);
            } catch (SQLException ex) {
                Respuesta respuesta = new Respuesta(EMensajes.ERROR_CONEXION_BD);
                out.print(new Gson().toJson(respuesta));
            } catch (VotacionesException ex) {
                ex.printStackTrace();
                if (ex.getEMensajes() != EMensajes.ERROR_CONEXION_BD) {
                    ConnectionDB.rollback(cnn);
                    ConnectionDB.desconectar(cnn);
                }
                if (EMensajes.SESION_EXPIRADA == ex.getEMensajes() && request.getMethod().equalsIgnoreCase("GET")) {
                    httpResponse.sendRedirect("/login");
                }
                Respuesta respuesta = new Respuesta(ex.getCodigo(), ex.getMensaje());
                respuesta.setCodigoVotaciones(ex.getCodigoVotaciones());
                out.print(new Gson().toJson(respuesta));
            } catch (Throwable ex) {
                Respuesta respuesta = new Respuesta(EMensajes.ERROR_FATAL);
                out.print(new Gson().toJson(respuesta));
                ConnectionDB.rollback(cnn);
                ConnectionDB.desconectar(cnn);
                ex.printStackTrace();
            } finally {
                ConnectionDB.desconectar(cnn);
            }
        }
    }

    private String processRequest(HttpServletRequest request, Connection cnn, HttpServletResponse httpResponse) throws VotacionesException {
        Respuesta respuesta = null;
        String url = request.getServletPath();
        EAcciones eAcciones = EAcciones.parse(UtilURL.lastParteURL(url));
        respuesta = procesar(eAcciones, request, cnn);
        if (respuesta.getCodigo() == 100) {
            try {
                httpResponse.sendRedirect("login/");
            } catch (Exception ex) {
                Logger.getLogger(GenericoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Ajustar token...
        Gson gson = new GsonBuilder().setDateFormat(EConfiguracion.DATE_FORMAT_SHORT.getValue()).create();
        return gson.toJson(respuesta);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private String getIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}
