package edu.poli.gerencia.votaciones.negocio.utiles;

import edu.poli.gerencia.votaciones.modelo.vo.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jhon1
 */
public class SesionUtil {

    public static Usuario obtenerSesion(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        return usuario;
    }

    public static boolean esAdministrador(HttpServletRequest request) {
        Usuario usuario = obtenerSesion(request);
        return esAdministrador(usuario);
    }

    public static boolean esAdministrador(Usuario usuario) {
        if (usuario != null) {
            if (usuario.getIdTipoUsuario().getIdTipoUsuario() == 1) {
                return true;
            }
        }
        return false;
    }

    public static boolean esEmpresa(HttpServletRequest request) {
        Usuario usuario = obtenerSesion(request);
        return esEmpresa(usuario);
    }

    public static boolean esEmpresa(Usuario usuario) {
        if (usuario != null) {
            if (usuario.getIdTipoUsuario().getIdTipoUsuario() == 2) {
                return true;
            }
        }
        return false;
    }

    public static boolean esEmpleado(HttpServletRequest request) {
        Usuario usuario = obtenerSesion(request);
        return esEmpleado(usuario);
    }

    public static boolean esEmpleado(Usuario usuario) {
        if (usuario != null) {
            if (usuario.getIdTipoUsuario().getIdTipoUsuario() == 3) {
                return true;
            }
        }
        return false;
    }

    public static boolean logeado(HttpServletRequest request) {
        return (SesionUtil.obtenerSesion(request) != null) ? true : false;
    }

    public static void cerrarSesion(HttpServletRequest request) {
        HttpSession sesion = request.getSession();
        sesion.invalidate();
    }

    public static void actualizarSesion(HttpServletRequest request) {
        Usuario usuario = SesionUtil.obtenerSesion(request);
        if (usuario != null) {
            request.getSession().removeAttribute("usuario");
            request.getSession().setAttribute("usuario", usuario);
        }
    }

}
